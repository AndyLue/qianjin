package com.dream.qianjin.path;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;

import javax.ws.rs.POST;
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

@Path("register1")
@Component
public class Register1Path extends BaseResource {

	IBaseService<User> loginDao = (IBaseService<User>) context
			.getBean(Constant.SERVICE_USER);

	public String Create(){
		ObjectNode resJson = getJsonObjectid();
		
		String teleP = getReqAttribute(Constant.JSON_TELEPHONE);
		String passWord = getReqAttribute(Constant.JSON_PASSWORD);
		
		if (!StringUtils.isEmpty(teleP) && !StringUtils.isEmpty(passWord )) {
			User user = new User();
			user.setTelephone(teleP);
			user.setPassWord(passWord);
			user.setSex(Constant.INFO_MAN);
			user.setCreateTime(new Date(System.currentTimeMillis())+"");
			Serializable recid = loginDao.save(user);

			resJson.put(Constant.JSON_ID, recid.toString());
			resJson.put(Constant.JSON_RESULT, Constant.RESULT_SUCCESS);
			return resJson.toString();
		}

		return resJson.toString();
	}
}
