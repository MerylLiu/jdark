package com.iptv.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface RecommendCategoryService extends BaseService {
	public List<Map> getALlCategoryForNode();
	
	public void save(Map map) throws BizException;

	public void delete(Map map) throws BizException;
	
	@Cacheable
	public List getAllCategories();

	public void sync(Map map) throws BizException;
	
	public List getTopCategories();

	public KendoResult Category(Map map);
}
