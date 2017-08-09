package com.iptv.sys.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysPermisionService extends BaseService{
	
	public KendoResult getPermisionPaged(Map map);
	
	public Map findPermisionById(Map map) ;
	
	public void update(Map map)throws BizException;
	
	public void delete(Map map)throws BizException;
	
}
