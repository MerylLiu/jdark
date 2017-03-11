package com.iptv.app.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysMenuService extends BaseService{
	public List<Map> getAllMenus();
	
	public List<Map> getALlMenusForNode();
	
	public void save(Map map) throws BizException;

	public void delete(Map map) throws BizException;
}
