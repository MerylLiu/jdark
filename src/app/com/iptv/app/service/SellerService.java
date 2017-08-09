package com.iptv.app.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SellerService extends BaseService{
	public KendoResult getSellersPaged(Map map);
	
	public Map getSeller(Integer id);
	
	public String save(Map map) throws BizException;
	
	public void delete(Map map) throws BizException; 
	
	public KendoResult getAllSellers();

	public KendoResult getSellersOptions(Map map);
}
