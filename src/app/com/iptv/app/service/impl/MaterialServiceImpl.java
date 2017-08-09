package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.iptv.app.service.MaterialService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MaterialServiceImpl extends BaseServiceImpl implements
		MaterialService {

	@Override
	public KendoResult getMaterialListPaged(Map param) {
		KendoResult data = QueryUtil.getRecordsPaged("material.getMaterialListPaged", param);
		return data;
	}

	@Override
	public Map getMaterialById(Map param) {
		Map data = getDao().selectOne("material.getMaterialById", param);
		return data;
	}

	@Override
	public void saveMaterial(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Code") == null||map.get("Code").toString().trim().isEmpty()) {
			errMsg.add("请输入材料编号。");
		}
		if (map.get("Name") == null||map.get("Name").toString().trim().isEmpty()) {
			errMsg.add("请输入材料名称。");
		}
		if (map.get("Count") == null||map.get("Count").toString().trim().isEmpty()) {
			errMsg.add("请输入材料数量。");
		}else{
			String count = map.get("Count").toString().trim();
			Pattern pa = Pattern.compile("^[1-9]\\d*$"); 
			if(!(pa.matcher(count).matches())){
				errMsg.add("请正确输入材料数量(正整数)。");
			}
		}
		
		if (map.get("Id") == null || map.get("Id").equals(0)) {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			getDao().insert("material.saveMaterial", map);
		} else {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			getDao().update("material.updateMaterial", map);
		}

	}

	@Override
	public void doDeleteMaterial(Map map) throws BizException {
		List errMsg = new ArrayList();
		
		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的档案材料。");
		}
		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("material.deleteMaterial", map);
		getDao().delete("businessNode.deleteByMaterialId",map);
	}

}
