package com.dream.qianjin.path;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dream.qianjin.bean.Company;
import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.jersey.core.BaseResource;
import com.dream.qianjin.service.impl.CompanyService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("companylist")
@Component
public class CompanyListPath extends BaseResource {

	private static Logger LOGGER = LoggerFactory.getLogger(CompanyListPath.class);
	
	CompanyService companyDao = (CompanyService) context
			.getBean(Constant.SERVICE_COMPANY);

	public String Select() {
		ObjectNode resJson = getJsonObjectid();

		String userId = getReqAttribute(Constant.JSON_ID);

		if (!StringUtils.isEmpty(userId)) {
			List<Company> comObjs = companyDao.findList(" from " + Constant.TABLE_COMPANY
					+ " where " + Constant.FILED_COMPANY_USERID + "=?", userId);

			if (comObjs.size()>0) {
				ArrayNode array = resJson.arrayNode();
				int size = comObjs.size();
				for (int i = 0; i < size; i++) {
					ObjectNode resJsonTemp = getJsonObjectid();
					resJsonTemp.put(Constant.JSON_ID, userId);
					resJsonTemp.put(Constant.JSON_COMPANY_ID, comObjs.get(i).getID());
					resJsonTemp.put(Constant.JSON_COMPANY_NAME, comObjs.get(i).getName());
					resJsonTemp.put(Constant.JSON_COMPANY_ISSHOW, comObjs.get(i).getIsShow());
					array.add(resJsonTemp);
				} 
				resJson.put(Constant.JSON_ENTITIES, array);
				resJson.put(Constant.JSON_RESULT, Constant.RESULT_SUCCESS);

				return resJson.toString();
			}
		}
		return resJson.toString();
	}

	public String Delete() {
		ObjectNode resJson = getJsonObjectid();

		String companyId = getReqAttribute(Constant.JSON_COMPANY_ID);

		LOGGER.debug("公司删取到的ID为：",companyId);
		
		if (!StringUtils.isEmpty(companyId)) {
			Company com = new Company();
			com.setID(companyId);
			companyDao.delete(com);
			
			LOGGER.info("公司删除ID为：",companyId);
			
			resJson.put(Constant.JSON_RESULT, Constant.RESULT_SUCCESS);
			return resJson.toString();
		}
		return resJson.toString();
	}

}
