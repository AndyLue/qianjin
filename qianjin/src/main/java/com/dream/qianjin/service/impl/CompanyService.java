package com.dream.qianjin.service.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import com.dream.qianjin.bean.Company;
import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.core.service.BaseService;

public class CompanyService extends BaseService<Company>{


	/**
	 * 插入 用户表的 真实姓名 及公司表的信息
	 * @return
	 */
	public boolean insertData(final String companyId,final String id,final String userName,final String companyName,final String isSamePeople,final String businessLicense,final String IDCard,final String positionProve){
		TransactionTemplate tt = new TransactionTemplate(dao.getTransactionManager());
		tt.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dao.getDataSource());
				int proNum = 1 ; 
				if(StringUtils.isEmpty(positionProve)){
					proNum = 0 ;
				}
				jdbcTemplate.update("insert into "+Constant.TABLE_COMPANY+" (ID,name,IsSamePeople,BusinessLicense,IDCard,PositionProve,userid,numOfCompany,companyRegCapital,isVerifyIDCard,isVerifyBusLic,isVerifyProve,isverify,isshow)"
						+ " values('"+companyId+"','"+companyName+"','"+isSamePeople+"','"+businessLicense+"','"+IDCard+"','"+positionProve+"','"+id+"',0,0.0,1,1,"+proNum+",0,1);");
				jdbcTemplate.update(" update "+Constant.TABLE_USER+" set "+Constant.FILED_USER_NAME+"='"+userName+"',"+Constant.FILED_USER_COMPANYNUM+"=1  where id='"+id+"' ;");
			}
			
		});
		return true;
	}
	
	/**
	 * 插入 用户表的 真实姓名 及公司表的信息
	 * @return
	 */
	public boolean updateData(final String id,final String userName,final String companyName,final String isSamePeople,final String businessLicense,final String IDCard,final String positionProve){
		TransactionTemplate tt = new TransactionTemplate(dao.getTransactionManager());
		tt.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dao.getDataSource());
				jdbcTemplate.update(" update "+Constant.TABLE_COMPANY+" set Name='"+companyName+"',IsSamePeople='"+isSamePeople+"',BusinessLicense='"+businessLicense+"'"
						+ ",IDCard='"+IDCard+"',PositionProve='"+positionProve+"' where userid='"+id+"' ");
				jdbcTemplate.update(" update "+Constant.TABLE_USER+" set "+Constant.FILED_USER_NAME+"='"+userName+"' where id='"+id+"' ;");
			}
			
		});
		return true;
	}

	/**
	 * 判断该用户关联的企业中，是否有认证通过的
	 */
	public boolean judgeVerifyState(String userID) {
		Company comObj = dao.find(" from " + Constant.TABLE_COMPANY
				+ " where " + Constant.FILED_COMPANY_ISVERIFY + "=? and "
						+ ""+Constant.FILED_COMPANY_USERID+" =? ", new Object[] {1,userID});
		if(comObj==null){
			return false;
		}else{
			return true;
		}
	}
}
