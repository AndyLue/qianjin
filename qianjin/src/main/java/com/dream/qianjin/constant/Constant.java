package com.dream.qianjin.constant;

import java.util.Properties;

public class Constant {
	
	public static String QIANJIN_URL;

	static{
		try{
			Properties props = new Properties();
			props.load(Constant.class.getClassLoader().getResourceAsStream("app-url.properties"));
			QIANJIN_URL = props.getProperty("app.url");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 定义SERVICE
	 */
	public static final String SERVICE_USER = "userService"; 
	public static final String SERVICE_VERIFY = "verifyCodeService";
	public static final String SERVICE_COMPANY = "companyService";
	
	/**
	 * USER表
	 */
	public static final String TABLE_USER = "User";
	public static final String FILED_USER_NAME = "name";
	public static final String FILED_USER_ID = "id";
	public static final String FILED_USER_TELEPHONE = "Telephone";
	public static final String FILED_USER_LOGINID = "LoginID";
	public static final String FILED_USER_COMPANYNUM = "CompanyNum";
	public static final String FILED_USER_PASSWORD = "PassWord";
	public static final String JSON_TELEPHONE = "telephone"; 
	public static final String JSON_LOGINID = "loginid"; 
	public static final String JSON_PASSWORD = "password";
	public static final String JSON_ID = "id";
	public static final String JSON_NAME = "name";
	public static final String JSON_POINTS = "points";
	public static final String JSON_LOCATION = "location";
	public static final String JSON_AGE = "age";
	public static final String JSON_SEX = "sex";
	public static final String JSON_AVATAR_MID = "avatar_mid";
	
	/**
	 * 邀请码表
	 */
	public static final String TABLE_VERIFYCODES = "VerifyCodes";
	public static final String JSON_VERIFYCODE = "verifycode";
	public static final String JSON_VERIFYCODES = "verifycodes";
	public static final String JSON_VERIFY_ISUSED = "isused";
	
	/**
	 *  公司表
	 */
	public static final String TABLE_COMPANY = "Company";
	public static final String FILED_COMPANY_NAME = "name";
	public static final String FILED_COMPANY_USERID = "userid";
	public static final String FILED_COMPANY_ISSHOW = "isshow";
	public static final String FILED_COMPANY_ISVERIFY = "isverify";
	
	public static final String JSON_COMPANY_ID = "companyid";
	public static final String JSON_COMPANY_USERID = "userid";
	public static final String JSON_COMPANY_NAME = "companyname";
	public static final String JSON_COMPANY_ISSAMEPEOPLE = "issamepeople";
	public static final String JSON_COMPANY_BUSINESSLICENSE = "businesslicense";
	public static final String JSON_COMPANY_POSITIONPROVE = "positionprove";
	public static final String JSON_COMPANY_IDCARD = "idcard";
	public static final String JSON_COMPANY_ISVERIFYBUSLIC = "isverifybuslic";
	public static final String JSON_COMPANY_ISVERIFYPROVE = "isverifyprove";
	public static final String JSON_COMPANY_ISVERIFYIDCARD = "isverifyidcard";
	public static final String JSON_COMPANY_ISSHOW = "isshow";
	public static final String JSON_COMPANY_ISVERIFY = "isverify";
	public static final String JSON_COMPANY_ANNUALTURNOVER = "annualturnover";
	public static final String JSON_COMPANY_SCOPEBUSINESS = "scopebusiness";
	public static final String JSON_COMPANY_BUY = "buy";
	public static final String JSON_COMPANY_SELL = "sell";
	
	/**
	 * 职位信息
	 */
	public static final String JSON_POSITION_NAME = "positionname";
	
	
	
	public static final String JSON_ENTITIES = "entities";
	public static final String JSON_RESULT = "result";
	public static final String JSON_RESULT_CODE = "resultcode";
	public static final String RESULT_SUCCESS = "success";
	public static final String RESULT_FAILURE = "failure";
//	public static final String RESULT_TYPEERROR = "content-type error";
	
	/**
	 * 相关信息
	 */
	public static final String INFO_MAN = "男";
	public static final String INFO_WOMAN = "女";
	
	
	

}
