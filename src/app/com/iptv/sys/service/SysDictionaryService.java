package com.iptv.sys.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysDictionaryService extends BaseService{
	public KendoResult getDictionaryPaged(Map map);
	
	public Map findDictionary(Map map) ;
	
	public void update(Map map)throws BizException;
	
	public void delete(Map map)throws BizException;
	
	
}
