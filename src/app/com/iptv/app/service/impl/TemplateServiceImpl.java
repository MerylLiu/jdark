package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.TemplateService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.DateUtil;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TemplateServiceImpl extends BaseServiceImpl implements TemplateService {

	@Override
	public KendoResult getTemplatePaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("template.getTemplatePaged", map);
		return data;
	}

	@Override
	public Map getTemplate(Integer id) {
		Map data = getDao().selectOne("template.getTemplateById", id);
		return data;
	}

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();
		
		if (map.get("Name") == null||map.get("Name").toString().trim().isEmpty()) {
			errMsg.add("请输入名称。");
		}
		if (map.get("Content") == null||map.get("Content").toString().trim().isEmpty()){
			errMsg.add("请输入内容。");
		}
		
		if (map.get("Id") == null || map.get("Id").equals(0)) {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			map.put("CreateDate", DateUtil.getNow());
			
			getDao().insert("template.saveTemplate", map);
		} else {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			getDao().update("template.updateTemplate", map);
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的模板。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("template.deleteTemplate", map);
	}
}
