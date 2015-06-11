package com.dream.qianjin.path;

import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dream.qianjin.bean.VerifyCodes;
import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.jersey.core.BaseResource;
import com.dream.qianjin.service.impl.VerifyCodeService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("verifycode")
@Component
public class VerifyCodePath extends BaseResource {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyCodePath.class);
	
	VerifyCodeService verifyDao = (VerifyCodeService) context
			.getBean(Constant.SERVICE_VERIFY);

	public String Select() {
		String verifyCode = req.getParameter(Constant.JSON_VERIFYCODE);
		ObjectNode resJson = getJsonObjectid();
		if ((!StringUtils.isEmpty(verifyCode))) {
			VerifyCodes verifyList = verifyDao.find(" from "
					+ Constant.TABLE_VERIFYCODES + " where id=?",verifyCode);

			if (verifyList != null) {
				resJson.put(Constant.JSON_RESULT, Constant.RESULT_SUCCESS);
				return resJson.toString();
			} else {
				LOGGER.info("邀请码查询中:", "存在多个用户唯一信息！！！请管理员查看~~");
			}
		}
		return resJson.toString();
	}

}
