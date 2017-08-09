package com.iptv.sys.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysUserMenuService extends BaseService{
	public List<Map> getAllUserMenuForNode();
	
	public void doSave(Map map) throws BizException;
	
	public List menuList(Map map);
}
