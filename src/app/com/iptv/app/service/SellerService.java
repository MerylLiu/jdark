package com.iptv.app.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SellerService extends BaseService{
	public KendoResult getSellersPaged(Map map);
	
	public void save(Map map) throws BizException;
	
	public void delete(Map map) throws BizException; 
}
