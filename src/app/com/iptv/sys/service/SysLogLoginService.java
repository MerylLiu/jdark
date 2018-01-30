package com.iptv.sys.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysLogLoginService extends BaseService {

	public KendoResult getLogLoginPaged(Map map);

	public List getLogLoginLatest();

}
