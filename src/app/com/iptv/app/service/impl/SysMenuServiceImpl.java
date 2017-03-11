package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.SysMenuService;
import com.iptv.core.common.BizException;
import com.iptv.core.service.impl.BaseServiceImpl;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SysMenuServiceImpl extends BaseServiceImpl implements SysMenuService {
	@Override
	public List<Map> getAllMenus() {
		List res = getDao().selectList("sysMenu.getAllMenus");
		return res;
	}

	@Override
	public List<Map> getALlMenusForNode() {
		List nodes = new ArrayList<Map>();
		List data = getDao().selectList("sysMenu.getRootNodes");
		getNodes(data);

		Map root = new HashMap();
		root.put("id", 0);
		root.put("name", "系统菜单");
		root.put("url", null);
		root.put("open", true);
		root.put("children", data);
		nodes.add(root);

		return nodes;
	}

	private void getNodes(List<Map> list) {
		List<Map> nodeList = new ArrayList<Map>();

		for (Object item : list) {
			Map node = (Map) item;

			Map map = new HashMap();
			map.put("parentId", node.get("id"));
			List children = getDao().selectList("sysMenu.getNodesByParentID", map);

			if (!children.isEmpty()) {
				node.put("children", children);
				nodeList.add(node);

				getNodes(children);
			}
		}
	}

	@Override
	public void save(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Code") == null) {
			errMsg.add("请输入菜单编码。");
		}
		if (map.get("Name") == null) {
			errMsg.add("请输入菜单名称。");
		}
		if (map.get("Level") == null) {
			errMsg.add("请输入菜单层级。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}

		if (map.get("Id") == null || map.get("Id").equals(0)) {
			getDao().insert("sysMenu.saveMenu", map);
		} else {
			getDao().update("sysMenu.updateMenu", map);
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("Id") == null) {
			errMsg.add("请选择要删除的菜单。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		}
		
		getDao().delete("sysMenu.deleteMenu", map);
	}
}
