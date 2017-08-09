package com.iptv.sys.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysUserRoleService extends BaseService{
	public List<Map> getAllUserRoleForNode();
	
	public void doSave(Map map) throws BizException;
	
	public List roleList(Map map);
}
