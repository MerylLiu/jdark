package com.iptv.app.service;

import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.springframework.cache.annotation.Cacheable;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface VideoService extends BaseService {
	public void UploadVedio() throws AxisFault;
	
	public KendoResult getVideosPaged(Map map);

	public Map getVideo(Integer id);

	public void save(Map map) throws BizException;
	
	public void delete(Map map) throws BizException; 

	public void online(Map map) throws BizException; 

	public void offline(Map map) throws BizException; 

	public void doSubmit(Map map) throws BizException; 
	
	public KendoResult getHomeVideoPaged(Map map);

	@Cacheable
	public List getHomeVideoByCategory(Integer categoryId);

	@Cacheable
	public List getHomeVideoForPreview(Integer categoryId);

	@Cacheable
	public Map getDetail(Integer videoId);

	public KendoResult getSearched(String name);

	public KendoResult getMoreVideoPaged(Map map);
}
