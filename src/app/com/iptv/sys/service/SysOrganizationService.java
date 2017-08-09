package com.iptv.sys.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysOrganizationService extends BaseService{
	public List<Map> getALlOrganizationsForNode();
	
	public void save(Map map) throws BizException;

	public void delete(Map map) throws BizException;

	public KendoResult getOrgOptions(Map map);
}
