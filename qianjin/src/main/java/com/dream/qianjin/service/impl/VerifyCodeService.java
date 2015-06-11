package com.dream.qianjin.service.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.dream.qianjin.bean.VerifyCodes;
import com.dream.qianjin.constant.Constant;
import com.dream.qianjin.core.service.BaseService;

public class VerifyCodeService extends BaseService<VerifyCodes>{


	/**
	 * 批量生成邀请码
	 * @return
	 */
	public boolean createCatchData(final int num,final String userID){
		//编程式事务处理
		TransactionTemplate tt = new TransactionTemplate(dao.getTransactionManager());
		tt.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dao.getDataSource());
				for (int i = 0; i < num; i++) {
					//初始化方法
//					jdbcTemplate.update("insert into "+Constant.TABLE_VERIFYCODES+" (id,userID) values('"+getUUID()+"','"+00000000000000000000000000000000+"');");
					jdbcTemplate.update("insert into "+Constant.TABLE_VERIFYCODES+" (id,userID) values('"+getUUID()+"','"+userID+"');");
				}
			}
		});
		return true;
	}
}
