package com.iptv.app.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

public interface UserService extends BaseService{
	@SuppressWarnings("rawtypes")
	public List findAllGenderCount();
	
	public KendoResult getIdPaged(Map map);
	
	public Map findUserById(Map map) ;
	
	public void update(Map map)throws BizException;
	
	public void delete(Map map)throws BizException;
	
	public List findOrganizationIdCount();
}
