package com.iptv.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.QueryUtil;
import com.iptv.sys.service.SysPermisionComponentService;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SysPermisionComponentServiceImpl extends BaseServiceImpl implements SysPermisionComponentService {

	@Override
	// 查询全部组件权限数据
	public KendoResult getPermisionComponentPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("permisionComponent.getPermisionComponentPaged", map);
		return data;
	}

	@Override
	// 查询单条组件权限数据
	public Map getPermisionComponentById(Map map) {
		Map data = getDao().selectOne("permisionComponent.getPermisionComponentById", map);
		return data;
	}

	@Override
	// 更新/新增组件权限数据
	public void updatePermisionComponent(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Code") == null || map.get("Code").toString().trim().isEmpty()) {
			errMsg.add("请输入编号。");
		}
		if (map.get("Name") == null || map.get("Name").toString().trim().isEmpty()) {
			errMsg.add("请输入名称。");
		}
		if (map.get("IsEnable") == null) {
			errMsg.add("请选择是否启用。");
		}
		if (map.get("BeforeCode") == null || !(map.get("BeforeCode").equals(map.get("Code")))) {
			Map Code = getDao().selectOne("permisionComponent.getPermisionComponentByCode", map.get("Code"));

			if (Code != null) {
				errMsg.add("编号不能重复");
			}
		}

		if (map.get("Id") == null || map.get("Id").equals("0")) {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}

			getDao().insert("permisionComponent.savePermisionComponent", map);
		} else {
			if (errMsg.size() > 0) {
				throw new BizException(errMsg);
			}

			getDao().update("permisionComponent.updatePermisionComponent", map);
		}

	}

	@Override
	// 删除组件权限数据
	public void deletePermisionComponent(Map map) throws BizException {
		List errmsg = new ArrayList();

		if (map.get("Id") == null || ((ArrayList) map.get("Id")).size() <= 0) {
			errmsg.add("请选择角色");
		}
		if (errmsg.size() > 0) {
			throw new BizException(errmsg);
		}

		getDao().delete("permisionComponent.deletePermisionComponent", map);
	}
}
