package com.dream.qianjin.jersey.core;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.constant.HttpConstant;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class BaseResource extends HttpBaseResource implements
		IBaseResource {
	public JsonFactory jsonFactory = new JsonFactory();

	protected WebApplicationContext context = ContextLoader
			.getCurrentWebApplicationContext();
	@Context
	protected HttpServletRequest req;

	@Context
	protected HttpServletResponse resp;

	@Context
	protected ServletConfig servletConfig;

	// @Context
	// protected ApplicationContext appContext;

	/**
	 * 创建JSON工厂
	 * 
	 * @return
	 */
	public ObjectNode getJsonObjectid() {
		JsonNodeFactory jsonFactory = JsonNodeFactory.instance;
		return jsonFactory.objectNode();
	}

	/**
	 * 取得保存图片的子路径
	 * 
	 * @return
	 */
	public String getSavePath() {
		return "";
	}

	/**
	 * 为子类提供 创建 接口
	 * 
	 * @return
	 */
	public String Create() {
		return null;
	}

	/**
	 * 为子类提供 更新 接口
	 * 
	 * @return
	 */
	public String Update() {
		return null;
	}

	/**
	 * 为子类提供 查询 接口
	 * 
	 * @return
	 */
	public String Select() {
		return null;
	}

	/**
	 * 为子类提供 删除 接口
	 * 
	 * @return
	 */
	public String Delete() {
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String SelectExe() {
		setParamToAtt();
		String res = "";
		ObjectNode resJson = getJsonObjectid();
		res = Select();
		if (StringUtils.isEmpty(res) || res.length() == 0) {
			resJson.put(Constant.JSON_ID, "");
			resJson.put(Constant.JSON_RESULT, Constant.RESULT_FAILURE);
			resJson.put(Constant.JSON_RESULT_CODE,
					HttpConstant.STATUS_CLIENT_ERROR);
			return resJson.toString();
		}
		return res;
	}

	/**
	 * 将请求来的 参数设置到 属性中
	 */
	private void setParamToAtt() {
		Map<String, String[]> map = req.getParameterMap();
		Iterator<Entry<String, String[]>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String[]> entry = it.next();
			String key = entry.getKey();
			String[] value = entry.getValue();
			
			System.out.println(key + value);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String CreateExe() {
		String subPath = getSavePath();
		boolean isSuccess = executeRequestData(req, subPath);
		String res = "";
		ObjectNode resJson = getJsonObjectid();
		if (isSuccess) {
			res = Create();
			if (StringUtils.isEmpty(res) || res.length() == 0) {
				resJson.put(Constant.JSON_ID, "");
				resJson.put(Constant.JSON_RESULT, Constant.RESULT_FAILURE);
				resJson.put(Constant.JSON_RESULT_CODE,
						HttpConstant.STATUS_CLIENT_ERROR);
				return resJson.toString();
			}
		}
		return res;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String UpdateExe() {
		String subPath = getSavePath();
		boolean isSuccess = executeRequestData(req, subPath);
		String res = "";
		ObjectNode resJson = getJsonObjectid();
		if (isSuccess) {
			res = Update();
			if (StringUtils.isEmpty(res) || res.length() == 0) {
				resJson.put(Constant.JSON_ID, "");
				resJson.put(Constant.JSON_RESULT, Constant.RESULT_FAILURE);
				resJson.put(Constant.JSON_RESULT_CODE,
						HttpConstant.STATUS_CLIENT_ERROR);
				return resJson.toString();
			}
		}
		return res;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String DeleteExe() {
		boolean isSuccess = executeRequestData(req, "");
		String res = "";
		ObjectNode resJson = getJsonObjectid();
		if (isSuccess) {
			res = Delete();
			if (StringUtils.isEmpty(res) || res.length() == 0) {
				resJson.put(Constant.JSON_ID, "");
				resJson.put(Constant.JSON_RESULT, Constant.RESULT_FAILURE);
				resJson.put(Constant.JSON_RESULT_CODE,
						HttpConstant.STATUS_CLIENT_ERROR);
				return resJson.toString();
			}
		}
		return res;
	}

	/**
	 * 得到 属性对应值
	 * @param key
	 * @return
	 */
	public String getReqAttribute(String key) {
		Object obj = req.getAttribute(key);
		if (!StringUtils.isEmpty(obj)) {
			return obj.toString();
		}
		return "";
	}
}
