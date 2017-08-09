package com.iptv.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.QueryUtil;
import com.iptv.sys.service.SysLogLoginService;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SysLogLoginServiceImpl extends BaseServiceImpl implements SysLogLoginService {

	@Override
	public KendoResult getLogLoginPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("sysLogLogin.getLogLoginPaged", map);
		return data;
	}

	@Override
	public List getLogLoginLatest() {
		List list =  getDao().selectList("sysLogLogin.getLogLoginLatest");
		return list;
	}
}
