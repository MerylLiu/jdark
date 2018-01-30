package com.iptv.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.iptv.core.common.BizException;
import com.iptv.core.service.impl.BaseServiceImpl;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.DateUtil;
import com.iptv.core.utils.EncryptUtil;
import com.iptv.sys.service.LoginService;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {
	@Resource
	private HttpServletRequest request;

	@Override
	public Map Login(Map map) throws BizException {
		List errMsg = new ArrayList();

		if (map.get("LoginName") == null) {
			errMsg.add("请输入用户名。");
		}
		if (map.get("Password") == null) {
			errMsg.add("请输入用户密码。");
		}
		if (map.get("ValidateCode") == null) {
			errMsg.add("请按住滑块，拖动到最右边进行验证。");
		}

		HttpSession session = request.getSession();
		Map user = (Map) getDao().selectOne("findUserByLoginName", map);

		if (user != null) {
			try {
				String password = EncryptUtil.encrypt(map.get("Password").toString());

				if (!user.get("Password").toString().equals(password)) {
					errMsg.add("您输入的密码不正确，请重新输入。");
				}

				String codeEncrypt = map.get("ValidateCode").toString();
				String code = EncryptUtil.decrypt(codeEncrypt);
				String vcode = String.format("eline2017@!@#*&^2%s", code);
				String validateCode = session.getAttribute("validateCode").toString();

				if (!vcode.equals(validateCode)) {
					errMsg.add("验证不通，请刷新页面重新登录。");
				}
			} catch (Exception e) {
				log.error("加密用户密码时发生错误" + e.getMessage());
				BaseUtil.saveLog(0, "加密用户密码时发生错误", e.getMessage());
				e.printStackTrace();
			}
		} else if (map.get("LoginName") != null) {
			errMsg.add("您输入的用户名不正确，请重新输入。");
		}

		if (errMsg.size() > 0) {
			throw new BizException(errMsg);
		} else {
			session.setAttribute("userId", user.get("Id"));
			session.setAttribute("userCode", user.get("Code"));
			session.setAttribute("userName", user.get("UserName"));
			session.setAttribute("User", user);

			String ipAddress = BaseUtil.getIpAddress(request);
			Map m = new HashMap();
			m.put("IPAddress", ipAddress);
			m.put("UserCode", user.get("Code"));
			m.put("UserName", user.get("UserName"));
			m.put("LoginDate", DateUtil.getNow());

			getDao().insert("sysLogLogin.doLogLogin", m);
		}

		session.removeAttribute("validateCode");

		return user;
	}
}
