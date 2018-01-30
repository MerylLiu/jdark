package com.iptv.sys.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysRoleService extends BaseService {
	public KendoResult getRolePaged(Map map);

	public Map findUserById(Map map);

	public void update(Map map) throws BizException;

	public void delete(Map map) throws BizException;

	public List getRoleList();

}
