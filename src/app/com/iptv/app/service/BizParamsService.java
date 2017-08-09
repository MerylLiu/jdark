package com.iptv.app.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface BizParamsService extends BaseService{
	public KendoResult getBizParamsPaged(Map map);
	
	public Map findParam(Map map) ;
	
	public void update(Map map)throws BizException;
	
	public void delete(Map map)throws BizException;
	
	public List getBizParam(String key);
}
