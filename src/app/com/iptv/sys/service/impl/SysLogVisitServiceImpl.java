package com.iptv.sys.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.QueryUtil;
import com.iptv.sys.service.SysLogVisitService;

@Service
@SuppressWarnings({ "rawtypes" })
public class SysLogVisitServiceImpl extends BaseServiceImpl implements SysLogVisitService {

	@Override
	public KendoResult getLogVisitPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("sysLogVisit.getLogVisitPaged", map);
		return data;
	}
}
