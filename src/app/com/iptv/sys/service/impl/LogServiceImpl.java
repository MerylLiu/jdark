package com.iptv.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.sys.service.LogService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class LogServiceImpl extends BaseServiceImpl implements LogService {

	@Override
	public KendoResult getLogPaged(Map param) {
		KendoResult data = QueryUtil.getRecordsPaged("log.getLogPaged", param);
		return data;
	}

	@Override
	public void deleteLogById(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的系统日志。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("log.deleteLogById", map);
		
	}

	@Override
	public void deleteLogAll() throws BizException {
		List errMsg = new ArrayList();


		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("log.deleteLogAll");
	}
}
