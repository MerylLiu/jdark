package com.iptv.app.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.VersionService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.DateUtil;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class VersionServiceImpl extends BaseServiceImpl implements VersionService {

	@Override
	public KendoResult getVersionPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("version.getVersionPaged", map);
		return data;
	}

	@Override
	public Map getVersion(Integer id) {
		Map data = getDao().selectOne("version.getVersionById", id);
		return data;
	}

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Code") == null||map.get("Code").toString().trim().isEmpty()) {
			errMsg.add("请输入版本编号。");
		}
		if (map.get("StartDate") == null) {
			errMsg.add("请输入版本开始时间。");
		}
		if (map.get("EndDate") == null) {
			errMsg.add("请输入版本结束时间。");
		}
		
		if(map.get("StartDate") != null && map.get("EndDate") != null ){
			try {
				Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("StartDate").toString());
				Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("EndDate").toString());
				if(startDate.compareTo(endDate)>0||startDate.compareTo(endDate)==0){
					errMsg.add("开始时间必须在结束时间之前");
				}
			} catch (ParseException e) {
				log.error("添加或者修改版本管理-开始时间和结束时间的转换错误：" + e.getMessage());
				BaseUtil.saveLog(0, "添加或者修改版本管理-开始时间和结束时间的转换错误", e.getMessage());
				e.printStackTrace();
			}
		}
		
		if (map.get("Province") == null) {
			errMsg.add("请选择省份。");
		}
		if (map.get("City") == null) {
			errMsg.add("请选择市。");
		}
		if (map.get("Area") == null) {
			errMsg.add("请选择区／县。");
		}
		if (map.get("Status") == null) {
			errMsg.add("请选择版本状态");
		}

		if (map.get("BeforeCode") == null || !(map.get("BeforeCode").equals(map.get("Code")))) {
			Integer existCount = getDao().selectOne("version.getExistCount", map);
			
			if (existCount > 0) {
				errMsg.add("您输入的版本编号已存在。");
			}
		}
		
		if (map.get("Id") == null || map.get("Id").equals(0)) {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			map.put("CreateDate", DateUtil.getNow());
			getDao().insert("version.saveVersion", map);
		} else {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			getDao().update("version.updateVersion", map);
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的版本。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("version.deleteVersion", map);
	}

	@Override
	public List getAllVersion() {
		List list = getDao().selectList("version.getAllVersion");
		return list;
	}

	@Override
	public void doPublish(String date) {
		getDao().update("version.beforPublish");
		
		Map map = new HashMap();
		map.put("CurrDate", date);
		getDao().update("version.publish", map);
		
		log.info("定时发布版本:"+map.toString());
	}
}
