package com.iptv.core.service;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface SysParamService extends BaseService {
	public void saveLog(String logInfo);
	
	public List getSysParam(String key,Boolean isAll);
}
