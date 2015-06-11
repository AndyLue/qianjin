package com.dream.qianjin.path;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dream.qianjin.bean.Company;
import com.dream.qianjin.bean.User;
import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.constant.ServerConstant;
import com.dream.qianjin.core.service.IBaseService;
import com.dream.qianjin.jersey.core.BaseResource;
import com.dream.qianjin.service.impl.CompanyService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("register2")
@Component
public class Register2Path extends BaseResource {

	@SuppressWarnings("unchecked")
	CompanyService companyDao = (CompanyService) context
			.getBean(Constant.SERVICE_COMPANY);
	IBaseService<User> userDao = (IBaseService<User>) context
			.getBean(Constant.SERVICE_USER);

	@Override
	public String getSavePath(){
		return ServerConstant.PATH_COMPANY;
	}
	
	public String Select() {
		ObjectNode resJson = getJsonObjectid();

		String userId = getReqAttribute(Constant.JSON_ID);

		if (!StringUtils.isEmpty(userId)) {
			Company comObj = companyDao.find(" from "
					+ Constant.TABLE_COMPANY + " where "
					+ Constant.FILED_COMPANY_USERID + "=?" , userId);

			User userObj = userDao.find(" from " + Constant.TABLE_USER
					+ " where " + Constant.FILED_USER_ID + "=?",userId);
			
			if (comObj != null && comObj != null) {
				resJson.put(Constant.JSON_ID, userId);
				resJson.put(Constant.JSON_NAME, userObj.getName());
				resJson.put(Constant.JSON_COMPANY_ID, comObj.getID());
				resJson.put(Constant.JSON_COMPANY_NAME, comObj.getName());
				resJson.put(Constant.JSON_COMPANY_ISSAMEPEOPLE,
						comObj.getIsSamePeople());
				resJson.put(Constant.JSON_COMPANY_ISVERIFYBUSLIC,
						comObj.getIsVerifyBusLic());
				resJson.put(Constant.JSON_COMPANY_ISVERIFYIDCARD,
						comObj.getIsVerifyIDCard());
				resJson.put(Constant.JSON_COMPANY_ISVERIFYPROVE,
						comObj.getIsVerifyProve());

				return resJson.toString();
			}
		}
		resJson.put(Constant.JSON_ID, "");
		resJson.put(Constant.JSON_RESULT, Constant.RESULT_FAILURE);
		return resJson.toString();
	}

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
			String companyId = getReqAttribute(Constant.JSON_COMPANY_ID);
			System.out.println("数据有效，执行公司插入语句companyId：" + companyId);
			companyDao.insertData(companyId, id, userName, companyName,
					isSamePeople, businessLicense, IDCard, positionProve);
			
			resJson.put(Constant.JSON_RESULT, Constant.RESULT_SUCCESS);
			return resJson.toString();
		}

		resp.setStatus(Response.Status.NOT_ACCEPTABLE.getStatusCode());
		resJson.put(Constant.JSON_ID, "");
		resJson.put(Constant.JSON_RESULT, Constant.RESULT_FAILURE);
		return resJson.toString();
	}

}
