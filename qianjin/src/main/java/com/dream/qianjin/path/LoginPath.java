package com.dream.qianjin.path;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dream.qianjin.bean.User;
import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.core.service.IBaseService;
import com.dream.qianjin.easemob.EasemobUserUtil;
import com.dream.qianjin.jersey.core.BaseResource;
import com.dream.qianjin.service.impl.CompanyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("login")
@Component
public class LoginPath extends BaseResource {

	@SuppressWarnings("unchecked")
	IBaseService<User> userDao = (IBaseService<User>) context
			.getBean(Constant.SERVICE_USER);

	CompanyService companyDao = (CompanyService) context
			.getBean(Constant.SERVICE_COMPANY);

	public String Select() {
		ObjectNode resJson = getJsonObjectid();
		
		String teleP = getReqAttribute(Constant.JSON_TELEPHONE);
		String loginID = getReqAttribute(Constant.JSON_LOGINID);
		String passWord = getReqAttribute(Constant.JSON_PASSWORD);
		
		if ((!StringUtils.isEmpty(teleP) || !StringUtils.isEmpty(loginID))
				&& !StringUtils.isEmpty(passWord)) {
			User userObj = userDao.find(" from " + Constant.TABLE_USER
					+ " where (" + Constant.FILED_USER_LOGINID + "=? or "
					+ Constant.FILED_USER_TELEPHONE + "" + "=?) and "
					+ Constant.FILED_USER_PASSWORD + "=?", new String[] {
					loginID, teleP, passWord });
			if (userObj != null) {
				boolean isVerify = companyDao.judgeVerifyState(userObj.getID());
				if (isVerify) {
					// 登陆环信
					ObjectNode node = EasemobUserUtil.Login(userObj.getID(),
							passWord);
					if (node != null && node.toString().length() > 0) {
						node.put(Constant.JSON_ID, userObj.getID());
						node.put(Constant.JSON_COMPANY_ISVERIFY, isVerify);
						return node.toString();
					}
				} else {
					resJson.put(Constant.JSON_ID, userObj.getID());
					resJson.put(Constant.JSON_COMPANY_ISVERIFY, isVerify);
					return resJson.toString();
				}
			}
		}

		return resJson.toString();
	}

}
