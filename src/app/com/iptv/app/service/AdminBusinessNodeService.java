package com.iptv.app.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface AdminBusinessNodeService extends BaseService{
	
	public void doSave(Map map) throws BizException;

	public void doDelete(Map map) throws BizException;

	public KendoResult getbusinessListPaged(Map map);
	
	public List getAllCategoryForNode();
	
	public List getCategoryList();
	
	public List getMaterialList();
	
	public Map getBusinessById(Map map) ;
}
