package com.iptv.app.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface BannerService extends BaseService{
	
	public KendoResult getBannerPaged(Map map);
	
	public Map getBanner(Integer id);
	
	public void save(Map map) throws BizException;
	
	public void delete(Map map) throws BizException; 
	
	public List getBannerByArea(Map map);
}
