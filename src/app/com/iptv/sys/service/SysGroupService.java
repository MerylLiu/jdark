package com.iptv.sys.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysGroupService extends BaseService{
	public List<Map> getALlGroupForNode();
	
	public void save(Map map) throws BizException;

	public void delete(Map map) throws BizException;

}
