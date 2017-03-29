package com.iptv.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iptv.app.service.UserService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.DateUtil;
import com.iptv.core.utils.QueryUtil;

@Service
@SuppressWarnings("rawtypes")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Override
	public List findAllGenderCount() {
		List list = getDao().selectList("user.findAllGenderCount");
		return list;
	}

	@Override
	public KendoResult getIdPaged(Map map) {
		KendoResult data = QueryUtil.getRecordsPaged("user.getIdPaged", map);
		return data;
	}

	@Override
	public Map findUserById(Map map) {
		System.out.println(map.get("id"));
		Map data = getDao().selectOne("user.findUserById", map);
		return data;
	}

	@Override
	public void update(Map map) throws BizException {
		List errMsg = new ArrayList();
		if(map.get("Code")==null){
			errMsg.add("请输入编号。");
		}
		if (map.get("LoginName") == null) {
			errMsg.add("请输入用户名。");
		}
		if(map.get("UserName") == null) {
			errMsg.add("请输入登录名。");
		}
		if(map.get("OrganizationId") == null) {
			errMsg.add("请输入组织Id。");
		}
		if(map.get("Password")==null){
			errMsg.add("请输入密码。");
		}
		if(map.get("Gender") == null) {
			errMsg.add("请输入性别。");
		}
		if(map.get("Tel") == null) {
			errMsg.add("请输入电话。");
		}
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		if(map.get("Email") == null) {
			errMsg.add("请输入邮箱。");
		}else if(!map.get("Email").toString().matches(regex)){
			errMsg.add("邮箱格式不正确。");
		}
		if(map.get("RoleId")==null){
			errMsg.add("请输入角色Id。");
		}
		map.put("UpdateTime", DateUtil.getNow());
		if(map.get("BeforeLoginName")==null||!(map.get("BeforeLoginName").equals(map.get("LoginName")))){
			Map loginname = getDao().selectOne("user.findUserByLoginName", map.get("LoginName"));
			if(loginname!=null){
				errMsg.add("账号已存在");
			}
		}
		if(map.get("Id")==null||map.get("Id").equals("0")){
			map.put("CreateTime", DateUtil.getNow());
			if(errMsg.size()>0){
				throw new BizException(errMsg);
			}
			getDao().insert("user.save", map);
		}else{
			if(errMsg.size()>0){
				throw new BizException(errMsg);
			}
			if(map.get("PasswordJudge").equals("true")){
				getDao().update("user.updateNoPasswordModify",map);
			}else{
				getDao().update("user.update",map);
			}
		}
	}

	@Override
	public void delete(Map map) throws BizException {
		List errmsg = new ArrayList();
		if(map.get("Id")==null||((ArrayList) map.get("Id")).size() <= 0){
			errmsg.add("请选择用户");
		}
		if(errmsg.size()>0){
			throw new BizException(errmsg);
		}
		getDao().delete("user.delete", map);
	}

	@Override
	public List findOrganizationIdCount() {
		List data = getDao().selectList("user.findOrganizationIdCount");
		return data;
	}


}
