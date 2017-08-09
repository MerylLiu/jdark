package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.AdminBusinessNodeService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AdminBusinessNodeServiceImpl extends BaseServiceImpl implements AdminBusinessNodeService {

	@Override
	public List<Map> getAllCategoryForNode() {
		List nodes = new ArrayList<Map>();
		List data = getDao().selectList("businessNode.getRootNodes");
		getNodes(data);

		Map root = new HashMap();
		root.put("id", 0);
		root.put("name", "档案分类");
		root.put("open", true);
		root.put("children", data);
		nodes.add(root);

		return nodes;
	}

	private void getNodes(List<Map> list) {
		for (Object item : list) {
			Map node = (Map) item;
			Map map = new HashMap();
			map.put("parentId", node.get("id"));
			List children = getDao().selectList("businessNode.getNodesByParentID", map);
			
			if (!children.isEmpty()) {
				node.put("children", children);
				getNodes(children);
			}
		}
	}

	@Override
	public void doSave(Map map) throws BizException {
		List errMsg = new ArrayList();
		
		if (map.get("Code") == null) {
			errMsg.add("请输入环节编号。");
		}
		if (map.get("Name") == null) {
			errMsg.add("请输入环节名称。");
		}
		if (map.get("CategoryId") == null || Integer.valueOf(map.get("CategoryId").toString())==0) {
			errMsg.add("请选择档案分类。");
		}
		if(map.get("MaterialId")==null){
			errMsg.add("请选择档案材料。");
		}
		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}
		
		if (map.get("Id") == null || map.get("Id").equals(0)) {
			getDao().insert("businessNode.save", map);
			map.put("BusinessNodeId", map.get("Id"));
			if(map.get("MaterialId") instanceof java.util.ArrayList){
				List list = (List)map.get("MaterialId");
				for(Object obj:list){
					map.put("MaterialId", obj.toString());
					getDao().insert("businessNode.saveMaterial",map);
				}
			}else{
				getDao().insert("businessNode.saveMaterial",map);
			}
			
		} else {
			map.put("BusinessNodeId", map.get("Id"));
			getDao().update("businessNode.update", map);
			getDao().delete("businessNode.deleteMaterial",map);
			if(map.get("MaterialId") instanceof java.util.ArrayList){
				List list = (List)map.get("MaterialId");
				for(Object obj:list){
					map.put("MaterialId", obj.toString());
					getDao().insert("businessNode.saveMaterial",map);
				}
			}else{
				getDao().insert("businessNode.saveMaterial",map);
			}
		}
	}

	@Override
	public void doDelete(Map map) throws BizException {
		List errMsg = new ArrayList();
		
		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的环节。");
		}
		
		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}
		
		getDao().delete("businessNode.delete",map);
		getDao().delete("businessNode.deleteMaterialList", map);
	}

	@Override
	public KendoResult getbusinessListPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("businessNode.getbusinessListPaged",map);
		return data;
	}

	@Override
	public List getCategoryList() {
		List list = getDao().selectList("businessNode.getAllCategory");
		return list;
	}

	@Override
	public List getMaterialList() {
		List list = getDao().selectList("businessNode.getAllMaterial");
		return list;
	}

	@Override
	public Map getBusinessById(Map map) {
		Map res = getDao().selectOne("businessNode.getBusinessById", map);
		return res;
	}
}
