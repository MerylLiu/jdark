package com.iptv.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.core.service.SysParamService;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SysParamServiceImpl extends BaseServiceImpl implements SysParamService {

	@Override
	public void saveLog(String logInfo) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Map map = new HashMap();
		map.put("UserCode", "test");
		map.put("UserName", "test");
		map.put("IPAddress", "127.0.0.1");
		map.put("OperationType", "0");
		map.put("Operation", "test");
		map.put("CreateDate", format.format(new Date()));
		map.put("Remark", logInfo);

		getDao().insert("sysParams.saveLog", map);
	}

	@Override
	public List getSysParam(String key, Boolean isAll) {
		Map map = new HashMap();
		map.put("Key", key);
		List res = new ArrayList();

		if (isAll == true) {
			res = getDao().selectList("sysParams.getAllSysDic", map);
		} else {
			res = getDao().selectList("sysParams.getSysDic", map);
		}

		return res;
	}

}
