package com.dream.qianjin.path;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dream.qianjin.bean.User;
import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.core.service.IBaseService;
import com.dream.qianjin.jersey.core.BaseResource;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("personalcenter")
@Component
public class PersonalCenterPath extends BaseResource {

	IBaseService<User> loginDao = (IBaseService<User>) context
			.getBean(Constant.SERVICE_USER);

	public String Select() {
		
		ObjectNode resJson = getJsonObjectid();
		
		String userId = getReqAttribute(Constant.JSON_ID);
		
		if (!StringUtils.isEmpty(userId)) {
			User userObj = loginDao.find(" from " + Constant.TABLE_USER
					+ " where "+ Constant.FILED_USER_ID + "=?", userId);
			if (userObj != null) {
				resJson.put(Constant.JSON_NAME, userObj.getName());
				resJson.put(Constant.JSON_POINTS, userObj.getPoints());
				resJson.put(Constant.JSON_LOCATION, userObj.getLocation());
				resJson.put(Constant.JSON_RESULT, Constant.RESULT_SUCCESS);
				return resJson.toString();
			}
		}

		return resJson.toString();
	}

}
