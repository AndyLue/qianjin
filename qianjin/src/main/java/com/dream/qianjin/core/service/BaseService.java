package com.dream.qianjin.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.dream.qianjin.core.dao.IBaseDao;

public abstract class BaseService<T> implements IBaseService<T> {

	protected IBaseDao<T> dao;

	public String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	@Override
	public Serializable save(T t) {
		return this.dao.save(t);
	}

	public void delete(T obj) {
		dao.delete(obj);
	}

	public void update(T obj) {
		dao.update(obj);
	}

	public int getTotalCount() {
		return dao.getTotalCount();
	}

	// public List<T> findPageByQuery(String hql,int startIndex,int count){
	// return dao.findPageByQuery(hql, count, startIndex);
	// }
	public T find(String queryString, Object value) {
		return dao.find(queryString, value);
	}

	public List<T> findAll() {
		return dao.findAll();
	}

	@Override
	public List<T> findList(String queryString) {
		return this.dao.findList(queryString);
	}

	@Override
	public List<T> findList(String queryString, Object value) {
		return this.dao.findList(queryString, value);
	}

	@Override
	public List<T> findList(String queryString, Object[] values) {
		return this.dao.findList(queryString, values);
	}

	@Override
	public boolean contains(T t) {
		return this.dao.contains(t);
	}

	@Override
	public void update(T t, LockMode lockMode) {
		this.dao.update(t, lockMode);
	}

	@Override
	public void refresh(T t) {
		this.dao.refresh(t);
	}

	@Override
	public void delete(T t, LockMode lockMode) {
		this.dao.delete(t, lockMode);
	}

	@Override
	public void deleteAll(Collection<T> entities) {
		this.dao.deleteAll(entities);
	}

	// @Override
	// public List<T> findByNamedQuery(String queryName) {
	// return this.dao.findByNamedQuery(queryName);
	// }
	//
	// @Override
	// public List<T> findByNamedQuery(String queryName, Object value) {
	// return this.dao.findByNamedQuery(queryName, value);
	// }
	//
	// @Override
	// public List<T> findByNamedQuery(String queryName, Object[] values) {
	// return this.dao.findByNamedQuery(queryName, values);
	// }

	// @Override
	// public List<T> findPageByCriteria(DetachedCriteria detachedCriteria,
	// int pageSize, int startIndex) {
	// return this.dao.findPageByCriteria(detachedCriteria, pageSize,
	// startIndex);
	// }

	public void setDao(IBaseDao<T> dao) {
		this.dao = dao;
	}

}