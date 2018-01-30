package com.iptv.sys.service;

import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysSynchronizeService extends BaseService {
	public void doSynchronize() throws Exception;
}
