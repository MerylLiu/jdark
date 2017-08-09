package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.MakerService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.DateUtil;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MakerServiceImpl extends BaseServiceImpl implements MakerService {

	@Override
	public KendoResult getMakerPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("maker.getMakersPaged", map);
		return data;
	}

	@Override
	public Map getMaker(Integer id) {
		Map data = getDao().selectOne("maker.getMakerById", id);
		return data;
	}

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Code") == null||map.get("Code").toString().trim().isEmpty()) {
			errMsg.add("请输入制作商编号。");
		}
		if (map.get("Name") == null||map.get("Name").toString().trim().isEmpty()) {
			errMsg.add("请输入制作商名称。");
		}
		if (map.get("Tel") == null||map.get("Tel").toString().trim().isEmpty()) {
			errMsg.add("请输入制作商电话。");
		}
		if (map.get("Province") == null) {
			errMsg.add("请选择制作商所在的省份。");
		}
		if (map.get("City") == null) {
			errMsg.add("请选择制作商所在的市。");
		}
		if (map.get("Area") == null) {
			errMsg.add("请选择制作商所在的区／县。");
		}
		if (map.get("Address") == null||map.get("Address").toString().trim().isEmpty()) {
			errMsg.add("请输入制作商地址。");
		}

		if (map.get("BeforeCode") == null || !(map.get("BeforeCode").equals(map.get("Code")))) {
			Integer existCount = getDao().selectOne("maker.getExistCount", map);
			
			if (existCount > 0) {
				errMsg.add("您输入的制作商编号已存在。");
			}
		}
		
		if (map.get("Id") == null || map.get("Id").equals(0)) {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			map.put("CreateDate", DateUtil.getNow());
			getDao().insert("maker.saveMaker", map);
		} else {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}
			
			getDao().update("maker.updateMaker", map);
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errMsg.add("请选择要删除的制作商。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		getDao().delete("maker.deleteMaker", map);
	}

	@Override
	public KendoResult getMakersOptions(Map map) {
		KendoResult data = QueryUtil.getSelectOptions("maker.getMakersOptions", map);
		return data;
	}

}
