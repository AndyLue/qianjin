package com.dream.qianjin.path;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.jersey.core.BaseResource;
import com.dream.qianjin.service.impl.CompanyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("registerupdate")
@Component
public class RegisterUpdatePath extends BaseResource {

	@SuppressWarnings("unchecked")
	CompanyService companyDao = (CompanyService) context
			.getBean(Constant.SERVICE_COMPANY);


	public String Create() {
		ObjectNode resJson = getJsonObjectid();

		String id = getReqAttribute(Constant.JSON_ID);
		String userName = getReqAttribute(Constant.JSON_NAME);
		String companyName = getReqAttribute(Constant.JSON_COMPANY_NAME);
		String isSamePeople = getReqAttribute(Constant.JSON_COMPANY_ISSAMEPEOPLE);
		String businessLicense = getReqAttribute(Constant.JSON_COMPANY_BUSINESSLICENSE);
		String IDCard = getReqAttribute(Constant.JSON_COMPANY_IDCARD);
		String positionProve = getReqAttribute(Constant.JSON_COMPANY_POSITIONPROVE);

		if (!StringUtils.isEmpty(id) && !StringUtils.isEmpty(userName)
				&& !StringUtils.isEmpty(companyName)
				&& !StringUtils.isEmpty(isSamePeople)
				&& !StringUtils.isEmpty(businessLicense)
				&& !StringUtils.isEmpty(IDCard)) {
			companyDao.updateData(id, userName, companyName, isSamePeople,
					businessLicense, IDCard, positionProve);

			resJson.put(Constant.JSON_RESULT, Constant.RESULT_SUCCESS);
			return resJson.toString();
		}

		resp.setStatus(Response.Status.NOT_ACCEPTABLE.getStatusCode());
		resJson.put(Constant.JSON_ID, "");
		resJson.put(Constant.JSON_RESULT, Constant.RESULT_FAILURE);
		return resJson.toString();
	}

}
