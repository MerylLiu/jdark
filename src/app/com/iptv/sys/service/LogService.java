package com.iptv.sys.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface LogService extends BaseService {

	public KendoResult getLogPaged(Map param);

	public void deleteLogById(Map map) throws BizException;

	public void deleteLogAll() throws BizException;
	
}
