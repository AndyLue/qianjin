package com.dream.qianjin.path;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.jersey.core.BaseResource;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("test")
@Component
public class TestPath extends BaseResource {

	public String Select() {
		ObjectNode resJson = getJsonObjectid();
		resJson.put(Constant.JSON_ID, "HELLO WORLD");
		resJson.put(Constant.JSON_RESULT, Constant.RESULT_FAILURE);
		return resJson.toString();
	}


}
