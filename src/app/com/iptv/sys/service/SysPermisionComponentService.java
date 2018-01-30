package com.iptv.sys.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysPermisionComponentService extends BaseService {
	/**
	 * 查询全部组件权限数据
	 * 
	 * @param map
	 * @return
	 */
	public KendoResult getPermisionComponentPaged(Map map);

	/**
	 * 查询单条组件权限数据
	 * 
	 * @param map
	 * @return
	 */
	public Map getPermisionComponentById(Map map);

	/**
	 * 更新/新增组件权限数据
	 * 
	 * @param map
	 * @throws BizException
	 */
	public void updatePermisionComponent(Map map) throws BizException;

	/**
	 * 删除组件权限数据
	 * 
	 * @param map
	 * @throws BizException
	 */
	public void deletePermisionComponent(Map map) throws BizException;
}
