package com.dream.qianjin.path;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dream.qianjin.bean.Company;
import com.dream.qianjin.bean.User;
import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.constant.HttpConstant;
import com.dream.qianjin.constant.ServerConstant;
import com.dream.qianjin.core.service.IBaseService;
import com.dream.qianjin.jersey.core.BaseResource;
import com.dream.qianjin.service.impl.CompanyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("personalcenter/personaldata")
@Component
public class T_PersonalDataPath extends BaseResource {

	private static Logger LOGGER = LoggerFactory
			.getLogger(T_PersonalDataPath.class);

	@SuppressWarnings("unchecked")
	IBaseService<User> userDao = (IBaseService<User>) context
			.getBean(Constant.SERVICE_USER);

	CompanyService companyDao = (CompanyService) context
			.getBean(Constant.SERVICE_COMPANY);

	@Override
	public String getSavePath(){
		return ServerConstant.PATH_USER;
	}
	
	public String Select() {

		ObjectNode resJson = getJsonObjectid();

		String userId = getReqAttribute(Constant.JSON_ID);

		if (!StringUtils.isEmpty(userId)) {
			User userObj = userDao.find(" from " + Constant.TABLE_USER
					+ " where " + Constant.FILED_USER_ID + "=?", userId);
			Company comObj = companyDao.find(" from " + Constant.TABLE_COMPANY
					+ " where " + Constant.FILED_COMPANY_USERID + " =? "
					+ " and " + Constant.FILED_COMPANY_ISSHOW + "=?",
					new Object[] { userId, 1 });
			
			if (userObj != null && comObj != null) {
				resJson.put(Constant.JSON_ID, userId);
				resJson.put(Constant.JSON_LOGINID, userObj.getLoginID());
				resJson.put(Constant.JSON_NAME, userObj.getName());
				resJson.put(Constant.JSON_POSITION_NAME, comObj.getPositionName());
				resJson.put(Constant.JSON_AGE, userObj.getAge());
				resJson.put(Constant.JSON_SEX, userObj.getSex());
				resJson.put(Constant.JSON_POINTS, userObj.getPoints());
				resJson.put(Constant.JSON_LOCATION, userObj.getLocation());
				
				resJson.put(Constant.JSON_RESULT, Constant.RESULT_SUCCESS);
				resJson.put(Constant.JSON_RESULT_CODE, HttpConstant.STATUS_SUCCESSFUL);
				return resJson.toString();
			}
		}

		return resJson.toString();
	}

	@Override
	public String Create() {

		ObjectNode resJson = getJsonObjectid();

		String id = getReqAttribute(Constant.JSON_ID);
		String avatar_mid = getReqAttribute(Constant.JSON_AVATAR_MID);
		String age = getReqAttribute(Constant.JSON_AGE);
		String sex = getReqAttribute(Constant.JSON_SEX);
		String location = getReqAttribute(Constant.JSON_LOCATION);

		if (!StringUtils.isEmpty(id)) {
			User t = new User();
			t.setID(id);
			t.setAvatar_Mid(avatar_mid);
			if(!StringUtils.isEmpty(age)){
				t.setAge(Integer.parseInt(age));
			}
			t.setSex(sex);
			t.setLocation(location);
			userDao.update(t);
			resJson.put(Constant.JSON_RESULT, Constant.RESULT_SUCCESS);
			resJson.put(Constant.JSON_RESULT_CODE, HttpConstant.STATUS_SUCCESSFUL);
			LOGGER.info("个人资料更新数据，用户ID为：",id);
			return resJson.toString();
		}
		return resJson.toString();
	}

}
