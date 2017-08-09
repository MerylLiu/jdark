package com.iptv.app.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface MakerService extends BaseService{
	public KendoResult getMakerPaged(Map map);
	
	public Map getMaker(Integer id);
	
	public void save(Map map) throws BizException;
	
	public void delete(Map map) throws BizException; 
	
	public KendoResult getMakersOptions(Map map);
}
