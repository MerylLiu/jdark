package com.iptv.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import com.iptv.core.common.Configuration;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.JsonUtil;
import com.iptv.core.utils.WebServiceUtil;
import com.iptv.core.utils.XmlUtil;
import com.iptv.sys.service.SysSynchronizeService;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SysSynchronizeServiceImpl extends BaseServiceImpl implements SysSynchronizeService {
	private static final String token = Configuration.webCfg.getProperty("cfg.webservice.token");

	@Override
	public void doSynchronize() throws Exception {
		
		Map userNameMap = new HashMap();
		userNameMap.put("Code", "A00101");
		userNameMap.put("Token", token);
		userNameMap.put("DataType", "json");

		List<Map> list = getDao().selectList("user.getAllUserLoginName");
		String ret = WebServiceUtil.cgi(userNameMap);
		List<Map> targetData = (List<Map>) JsonUtil.getObject(ret);
		Map deleteMap = new HashMap();
		List deleteList = new ArrayList();
		
		for(Map map:list){
			if(!targetData.contains(map)){
				deleteList.add(((Map)getDao().selectOne("user.getUserIdByLoginName",map)).get("Id"));
			}
		}
		
		deleteMap.put("Id", deleteList);
		
		if(deleteMap.get("Id")!=null && ((List)deleteMap.get("Id")).size()>0){
			getDao().delete("user.delete",deleteMap);
		}
	
		for (Map map : targetData) {

			boolean isContains = list.contains(map) ? true : false;

			Map userMap = new HashMap();
			userMap.put("Code", "A00102");
			userMap.put("DataType", "xml");
			userMap.put("Token", token);
			userMap.put("LoginName", map.get("LoginName"));

			Map user = XmlUtil.xml2map(WebServiceUtil.cgi(userMap), false);

			List<Map> orgs = getDao().selectList("sysOrganization.getOrganizationByName", user);

			if (orgs == null || orgs.size() <= 0) {

				Map orgMap = new HashMap();
				orgMap.put("Code", "A00105");
				orgMap.put("Token", token);
				orgMap.put("DataType", "xml");
				orgMap.put("Id", user.get("OrganizationId"));

				String orgXml = WebServiceUtil.cgi(orgMap);
				Map org = XmlUtil.xml2map(orgXml, false);

				doSaveOrgNode(org, user, new ArrayList<Map>());
			} else {
				user.put("OrganizationId", orgs.get(0).get("Id"));
			}

			Map userRoleMap = new HashMap();
			userRoleMap.put("Code", "A00103");
			userRoleMap.put("Token", token);
			userRoleMap.put("DataType", "xml");
			userRoleMap.put("UserId", user.get("Id"));

			String roleXml = WebServiceUtil.cgi(userRoleMap);
			Map roleMap = XmlUtil.xml2map(roleXml, false);

			if (isContains) {
				user.put("Id", ((Map)getDao().selectOne("user.getUserIdByLoginName",map.get("LoginName"))).get("Id"));
				getDao().insert("user.update", user);
			} else {
				getDao().insert("user.addUser", user);
			}

			if (roleMap.get("Rows") != null && roleMap.get("Rows") != "") {
				Object obj = ((Map) roleMap.get("Rows")).get("Row");
				List<Map> roles = new ArrayList();

				if (obj instanceof java.util.ArrayList) {
					roles = (List) obj;
				} else {
					roles.add((Map) obj);
				}

				Map data = new HashMap();
				data.put("UserId", user.get("Id"));
				data.put("userId", user.get("Id"));
				
				getDao().delete("sysUserRole.deleteRoleList",data);
				
				for (Map copyRole : roles) {
					List<Map> roleNames = getDao().selectList("role.getRoleByName", copyRole);

					if (roleNames == null || roleNames.size() <= 0) {
						getDao().insert("role.saveRoleFromCopy", copyRole);
						data.put("RoleId", copyRole.get("Id"));
						getDao().insert("sysUserRole.saveRole", data);
					} else {
						for (Map role : roleNames) {
							data.put("RoleId", role.get("Id"));
							getDao().insert("sysUserRole.saveRole", data);
						}
					}
				}
			}
		}
	}

	private void doSaveOrgNode(Map map, Map user, List<Map> orgArr) throws DocumentException {

		if (map.get("ParentId") == null || Integer.valueOf(map.get("ParentId").toString()) == 0) {
			getDao().insert("sysOrganization.saveOrganizationFromCopy", map);
			orgArr.add(map);
			user.put("OrganizationId", orgArr.get(0).get("Id"));

			if (orgArr.size() > 1) {
				for (int i = 0; i < orgArr.size() - 1; i++) {
					orgArr.get(i).put("ParentId", orgArr.get(i + 1).get("Id"));
					orgArr.get(i).put("ParentCode", orgArr.get(i + 1).get("Code"));
					getDao().update("sysOrganization.updateOrganizationPatentId", orgArr.get(i));
				}
			}
			return;
		}

		Map orgId = new HashMap();
		orgId.put("OrganizationId", map.get("ParentId"));

		Map orgMap = new HashMap();
		orgMap.put("Code", "A00105");
		orgMap.put("Token", token);
		orgMap.put("DataType", "xml");
		orgMap.put("Id", map.get("ParentId"));

		Map org = XmlUtil.xml2map(WebServiceUtil.cgi(orgMap), false);

		List<Map> orgs = getDao().selectList("sysOrganization.getOrganizationByName", org);

		if (orgs == null || orgs.size() <= 0) {
			getDao().insert("sysOrganization.saveOrganizationFromCopy", map);
			orgArr.add(map);
			doSaveOrgNode(org, user, orgArr);
		} else {
			map.put("ParentId", orgs.get(0).get("Id"));
			map.put("ParentCode", orgs.get(0).get("Code"));
			getDao().insert("sysOrganization.saveOrganizationFromCopy", map);
			orgArr.add(map);
			user.put("OrganizationId", orgArr.get(0).get("Id"));

			if (orgArr.size() > 1) {
				for (int i = 0; i < orgArr.size() - 1; i++) {
					orgArr.get(i).put("ParentId", orgArr.get(i + 1).get("Id"));
					orgArr.get(i).put("ParentCode", orgArr.get(i + 1).get("Code"));
					getDao().update("sysOrganization.updateOrganizationPatentId", orgArr.get(i));
				}
			}
			return;
		}
	}
}
