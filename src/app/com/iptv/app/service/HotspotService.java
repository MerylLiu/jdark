package com.iptv.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface HotspotService extends BaseService{
	
	public void save(Map map) throws BizException;	
	
	public KendoResult getHotspotPaged(Map map);
	
	public Map getHotspot(Map map);
	
	public List getVideoIdByHotspotId(Map map);
	
	public void delete(Map map) throws BizException; 

	@Cacheable
	public Map getHomeHotspot();

	@Cacheable
	public Map getTopicByPage(Map map);
	
	public List getHotspotVideos(Map map);
}
