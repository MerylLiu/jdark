package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.BizParamsService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BizParamsServiceImpl extends BaseServiceImpl implements BizParamsService {

	@Override
	public KendoResult getBizParamsPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("bizParams.getBizParamsPaged", map);
		return data;
	}

	@Override
	public Map findParam(Map map) {
		Map data = getDao().selectOne("bizParams.findParam", map);
		return data;
	}

	@Override
	public void update(Map map) throws BizException {
		List errMsg = new ArrayList();
		
		if (map.get("KeyCode") == null||map.get("KeyCode").toString().trim().isEmpty()) {
			errMsg.add("请输入参数名。");
		}
		if (map.get("KeyValue") == null||map.get("KeyValue").toString().trim().isEmpty()) {
			errMsg.add("请输入参数值。");
		}
		if (map.get("Text") == null||map.get("Text").toString().trim().isEmpty()) {
			errMsg.add("请输入文本。");
		}
		if (map.get("IsEnable") == null) {
			errMsg.add("请选择是否启用。");
		}
		if (map.get("IsVisible") == null) {
			errMsg.add("请选择是否可见。");
		}
		if (map.get("OrderNum") == null||map.get("OrderNum").toString().trim().isEmpty()) {
			errMsg.add("请输入排序号。");
		}
		
		if (map.get("BeforeValue") == null || !(map.get("BeforeValue").equals(map.get("KeyValue")))) {
			Map Code = getDao().selectOne("bizParams.findParam", map);
			if (Code != null) {
				errMsg.add("参数值不能重复，请重新输入。");
			}
		}

		if (map.get("BeforeValue") == null || map.get("BeforeKey") == null) {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			getDao().insert("bizParams.save", map);
		} else {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			getDao().update("bizParams.update", map);
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errmsg = new ArrayList();
		
		if (map.get("KeyValue") == null || map.get("KeyCode") == null) {
			errmsg.add("请选择要删除的系统参数。");
		}
		if (errmsg.size() > 0) {
			throw new BizException(errmsg);
		}
		
		List keyList = (List)map.get("KeyCode");
		
		List valueList = (List)map.get("KeyValue");
		
		Map data = new HashMap();
		
		for(int i = 0;i<keyList.size();i++){
			data.put("KeyCode", keyList.get(i));
			data.put("KeyValue", valueList.get(i));
			getDao().delete("bizParams.delete", data);
		}
	}

	@Override
	public List getBizParam(String key) {
		Map map = new HashMap();
		map.put("Key", key);

		List res = new ArrayList();
		res = getDao().selectList("bizParams.getBizDic", map);

		return res;
	}
}
