package com.iptv.app.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface VersionService extends BaseService{
	public KendoResult getVersionPaged(Map map);
	
	public Map getVersion(Integer id);
	
	public void save(Map map) throws BizException;
	
	public void delete(Map map) throws BizException; 
	
	public List getAllVersion();
	
	public void doPublish(String date);
}
