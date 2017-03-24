package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.iptv.app.service.CategoryService;
import com.iptv.core.common.BizException;
import com.iptv.core.service.impl.BaseServiceImpl;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CategoryServiceImpl extends BaseServiceImpl implements CategoryService {
	@Override
	public List<Map> getALlCategoryForNode() {
		List nodes = new ArrayList<Map>();
		List data = getDao().selectList("category.getRootNodes");
		getNodes(data);

		Map root = new HashMap();
		root.put("id", 0);
		root.put("name", "视频分类");
		root.put("url", null);
		root.put("open", true);
		root.put("children", data);
		nodes.add(root);

		return nodes;
	}

	private void getNodes(List<Map> list) {
		List<Map> nodeList = new ArrayList<Map>();

		for (Object item : list) {
			Map node = (Map) item;

			Map map = new HashMap();
			map.put("ParentId", node.get("Id"));
			List children = getDao().selectList("category.getNodesByParentID", map);

			if (!children.isEmpty()) {
				node.put("children", children);
				nodeList.add(node);

				getNodes(children);
			}
		}
	}

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Name") == null) {
			errMsg.add("请输入分类名称。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		if (map.get("Id") == null || map.get("Id").equals(0)) {
			getDao().insert("category.saveCategory", map);
		} else {
			getDao().update("category.updateCategory", map);
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null) {
			errMsg.add("请选择要删除的分类。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}
		
		getDao().delete("category.deleteCategory", map);
	}

	@Override
	@Cacheable
	public List getAllCategories() {
		List data = getDao().selectList("category.getAllCategory");
		return data;
	}

	@Override
	public void sync(Map map) throws BizException {
		getDao().delete("category.syncCategory", map);
	}

	@Override
	public List getTopCategories() {
		List data = getDao().selectList("category.getTopCategories");
		return data;
	}
	
}
