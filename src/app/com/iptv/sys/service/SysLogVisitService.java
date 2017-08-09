package com.iptv.sys.service;

import java.util.Map;

import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysLogVisitService extends BaseService {
	public KendoResult getLogVisitPaged(Map map);
}
