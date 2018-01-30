package com.iptv.sys.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysUserService extends BaseService {
	public KendoResult getUserPaged(Map map);

	public Map findUserById(Map map);

	public void save(Map map) throws Exception;

	public void delete(Map map) throws BizException;

	public void passwordModiy(Map map) throws Exception;

	public KendoResult findAllUser(Map map);
}
