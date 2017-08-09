package com.iptv.sys.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface LoginService extends BaseService {
	public Map Login(Map map) throws BizException;
}
