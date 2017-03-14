package com.iptv.app.service;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface CategoryService extends BaseService {
	public List<Map> getALlCategoryForNode();
	
	public void save(Map map) throws BizException;

	public void delete(Map map) throws BizException;
	
	public List getAllCategories();

	public void sync(Map map) throws BizException;
}
