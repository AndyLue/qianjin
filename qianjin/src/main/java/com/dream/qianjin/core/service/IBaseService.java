package com.dream.qianjin.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;

public interface IBaseService<T> {


	T find(String queryString, Object value);

	List<T> findList(String queryString);
	
	List<T> findList(String queryString, Object value);
	
	List<T> findList(String queryString, Object[] values);
	
//	List<T> findByNamedQuery(String queryName);
//
//	List<T> findByNamedQuery(String queryName, Object value);
//
//	List<T> findByNamedQuery(String queryName, Object[] values);

//	List<T> findPageByCriteria(DetachedCriteria detachedCriteria, int pageSize,
//			int startIndex);
//
//	List<T> findPageByQuery(String hql, int pageSize, int startIndex);
	
	List<T> findAll();

	
	/**
	 * 判断SESSION中缓存是否存在
	 * @param t
	 * @return
	 */
	boolean contains(T t);
	
	/**
	 * 保存
	 * 
	 * @param t
	 * @return
	 */
	Serializable save(T t);

	/**
	 * 更新
	 * 
	 * @param t
	 */
	void update(T t);

	void update(T t, LockMode lockMode);

	/**
	 * 更新
	 */
	void refresh(T t);

	/**
	 * 总数
	 * 
	 * @return
	 */
	int getTotalCount();

	/**
	 * 删除
	 * 
	 * @param t
	 */
	void delete(T t);

	void delete(T t, LockMode lockMode);

	void deleteAll(Collection<T> entities);

}
