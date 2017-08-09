package com.iptv.app.service;

import java.util.Map;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface NoticeService extends BaseService{
	
	public KendoResult getNoticePaged(Map map);
	
	public Map getNotice(Integer id);
	
	public void save(Map map) throws BizException;
	
	public void delete(Map map) throws BizException; 
	
	public KendoResult getNoticeByPage(Map map);

	public Map getLatestNotice();
}
