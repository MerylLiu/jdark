package com.iptv.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.iptv.core.service.SysParamService;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.DateUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SysParamServiceImpl extends BaseServiceImpl implements SysParamService {
	@Resource
	private HttpSession session;
	@Resource
	private HttpServletRequest request;

	@Override
	public void saveLog(String logInfo) {
		Map map = new HashMap();
		map.put("UserCode", "test");
		map.put("UserName", "test");
		map.put("IPAddress", "127.0.0.1");
		map.put("OperationType", "8");
		map.put("Operation", "test");
		map.put("CreateDate", DateUtil.getNow());
		map.put("Remark", logInfo);

		getDao().insert("sysParams.saveLog", map);
	}

	/**
	 * 保存错误日志到数据库
	 * 
	 * @param opreationType
	 *            0.错误日志,1.插入数据，2.修改数据，3，删除数据，8，其他
	 * @param operation
	 *            错误信息标题
	 * @param remark
	 *            错误信息
	 */
	public void saveLog(int opreationType, String operation, String remark) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HttpSession session = request.getSession();

		Map map = new HashMap();
		map.put("UserCode", session.getAttribute("userCode"));
		map.put("UserName", session.getAttribute("userName"));
		map.put("IPAddress", BaseUtil.getIpAddress(request));
		map.put("OperationType", opreationType);
		map.put("Operation", operation);
		map.put("CreateDate", format.format(new Date()));
		map.put("Remark", remark);

		getDao().insert("sysParams.saveLog", map);
	}

	@Override
	public List getSysParam(String key, Boolean isAll) {
		Map map = new HashMap();
		map.put("Key", key);
		List res = new ArrayList();

		if (isAll == true) {
			res = getDao().selectList("sysParams.getAllSysDic", map);
		} else {
			res = getDao().selectList("sysParams.getSysDic", map);
		}

		return res;
	}

}
