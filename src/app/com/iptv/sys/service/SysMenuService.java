package com.iptv.sys.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface SysMenuService extends BaseService {
	public List<Map> getAllMenus();

	public List<Map> getALlMenusForNode();

	public void save(Map map) throws BizException;

	public void delete(Map map) throws BizException;

	public List<Map> getUserMenus(Integer userId);

	public List<Map> getCurrentPermisions(Integer userId, Integer menuId);

	public List<Map> getCurrentPermisionsComponent(Integer userId, Integer menuId);
}
