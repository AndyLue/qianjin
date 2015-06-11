package com.dream.qianjin.path;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dream.qianjin.bean.VerifyCodes;
import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.jersey.core.BaseResource;
import com.dream.qianjin.service.impl.VerifyCodeService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 * 当前用户的 全部未用的邀请码
 * @author Andy_Liu
 *
 */
@Path("verifycodeall")
@Component
public class VerifyCodeAllPath extends BaseResource {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyCodeAllPath.class);
	
	VerifyCodeService verifyDao = (VerifyCodeService) context
			.getBean(Constant.SERVICE_VERIFY);

	public String Select() {
		String userId = getReqAttribute(Constant.JSON_ID);
		ObjectNode resJson = getJsonObjectid();
		if ((!StringUtils.isEmpty(userId))) {
			List<VerifyCodes> verifyList = verifyDao.findList(" from "
					+ Constant.TABLE_VERIFYCODES + " where userid=? ",userId);

			if (verifyList.size()>0) {
				ArrayNode arrayNode = resJson.arrayNode();
				int size = verifyList.size();
				for (int i = 0; i < size; i++) {
					ObjectNode resJsonTemp = getJsonObjectid();
					resJsonTemp.put(Constant.JSON_VERIFYCODE, verifyList.get(i).getID());
					resJsonTemp.put(Constant.JSON_VERIFY_ISUSED, verifyList.get(i).getIsUsed());
					arrayNode.add(resJsonTemp);
				}
				resJson.put(Constant.JSON_VERIFYCODES, arrayNode);
				resJson.put(Constant.JSON_RESULT, Constant.RESULT_SUCCESS);
				LOGGER.info("获取用户全部的邀请码:",resJson.toString());
				return resJson.toString();
			}
		}
		return resJson.toString();
	}

}
