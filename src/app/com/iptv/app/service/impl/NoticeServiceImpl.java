package com.iptv.app.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.NoticeService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.DateUtil;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class NoticeServiceImpl extends BaseServiceImpl implements NoticeService {

	@Override
	public KendoResult getNoticePaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("notice.getNoticePaged", map);
		return data;
	}

	@Override
	public Map getNotice(Integer id) {
		Map data = getDao().selectOne("notice.getNoticeById", id);
		return data;
	}

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();
		
		if (map.get("Title") == null||map.get("Title").toString().trim().isEmpty()) {
			errMsg.add("请输入标题。");
		}
		if (map.get("Introduction") == null||map.get("Introduction").toString().trim().isEmpty()){
			errMsg.add("请输入简介。");
		}
		if (map.get("Content") == null||map.get("Content").toString().trim().isEmpty()){
			errMsg.add("请输入内容。");
		}
		if (map.get("CategoryId") == null) {
			errMsg.add("请选择分类。");
		}
		if (map.get("Province") == null) {
			errMsg.add("请选择所在的省份。");
		}
		if (map.get("City") == null) {
			errMsg.add("请选择所在的市。");
		}
		if (map.get("Area") == null) {
			errMsg.add("请选择所在的区／县。");
		}
		
		if (map.get("StartDate") == null) {
			errMsg.add("请输入开始时间。");
		}
		if (map.get("EndDate") == null) {
			errMsg.add("请输入结束时间。");
		}
		
		if(map.get("StartDate") != null && map.get("EndDate") != null ){
			try {
				Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("StartDate").toString());
				Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("EndDate").toString());
				if(startDate.compareTo(endDate)>0||startDate.compareTo(endDate)==0){
					errMsg.add("开始时间必须在结束时间之前");
				}
			} catch (ParseException e) {
				log.error("添加或者修改公告-开始时间和结束时间的转换错误：" + e.getMessage());
				BaseUtil.saveLog(0, "添加或者修改公告-开始时间和结束时间的转换错误", e.getMessage());
				e.printStackTrace();
			}
		}
		
		if (map.get("Id") == null || map.get("Id").equals(0)) {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			map.put("CreateDate", DateUtil.getNow());
			
			getDao().insert("notice.saveNotice", map);
		} else {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			getDao().update("notice.updateNotice", map);
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的公告。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("notice.deleteNotice", map);
	}

	@Override
	public KendoResult getNoticeByPage(Map map) {
		KendoResult kendoResult = new KendoResult();
		
		Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
		Integer offset = (Integer.valueOf(map.get("page").toString()) - 1) * pageSize;
		
		Map param = new HashMap();
		param.put("offset", offset);
		param.put("rows", pageSize);

		List list = getDao().selectList("notice.getNoticeByPage", param);
		kendoResult.setData(list);
		
		Integer pageNum = getDao().selectOne("notice.getNoticePageNum", param);
		kendoResult.setPageNum(pageNum);
		
		return kendoResult;
	}

	@Override
	public Map getLatestNotice() {
		Map data = getDao().selectOne("notice.getLatestNotice");
		return data;
	}
}
