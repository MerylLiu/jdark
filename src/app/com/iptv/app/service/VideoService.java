package com.iptv.app.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	public String save(Map map) throws BizException;

	public void delete(Map map) throws BizException;

	public void online(Map map) throws BizException;

	public void offline(Map map) throws BizException;

	public KendoResult getHomeVideoPaged(Map map);

	@Cacheable
	public List getHomeVideoByCategory(Integer categoryId);

	@Cacheable
	public List getHomeVideoForPreview(Integer categoryId, Integer page);

	@Cacheable
	public Map getDetail(Integer videoId);

	public KendoResult getSearched(String name);

	public KendoResult getMoreVideoPaged(Map map);
	
	public KendoResult getWapHomeVideoPaged(Map map);
	
	public Map getVideoDetailById(Integer id);
	
	public List getRecommendedVideo(Integer id);
	
	public List getRecentUpdatesVideo();
	
	public Map getSearchAssociate(Map map) throws Exception;
	
	public void videoExcel(HttpServletResponse res,HttpServletRequest req,String title) throws IOException;
	
	public void doAutoExpire() throws ParseException;
	
	public KendoResult getPublishVideoPaged(Map map);
	/**
	 * 工单详细的查询
	 * @param id
	 * @return
	 */
	public Map getWorkOrderDetail(Integer id);
}
