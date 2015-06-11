package com.dream.qianjin.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;

public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {
	private Log logger = LogFactory.getLog(getClass());
	
	protected String className;
	protected Class<T> entityClass;
	protected DataSource dataSource;
	protected PlatformTransactionManager transactionManager;
	
	public BaseDao() {

	}

	@SuppressWarnings("unchecked")
	protected Class getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
			logger.debug("T class = " + entityClass.getName());
			className = entityClass.getName();
		}
		return entityClass;
	}
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@SuppressWarnings("unchecked")
	public int getTotalCount() {
		Object obj = this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						return session.createQuery(
								"select count(id) from " + className)
								.uniqueResult();
					}
				});
		return (int) ((Long) obj).longValue();
	}

//	public void saveOrUpdate(T t) throws DataAccessException {
//		this.getHibernateTemplate().saveOrUpdate(t);
//	}

	public T find(String queryString, Object value) {
		List<T> find = (List<T>) getHibernateTemplate()
				.find(queryString, value);
		if(find.size()==1){
			return find.get(0);
		}
		return null;
	}
	public List<T> findList(String queryString, Object value)
			throws DataAccessException {
		List<T> find = (List<T>) getHibernateTemplate()
				.find(queryString, value);
		return find;
	}
	
	public List<T> findList(String queryString, Object[] values)
			throws DataAccessException {
		List<T> finds = (List<T>) getHibernateTemplate().find(queryString,
				values);
		return finds;
	}
	
	public List<T> findList(String queryString) throws DataAccessException {
		return (List<T>) getHibernateTemplate().find(queryString);
	}

	public boolean contains(T t) throws DataAccessException {
		return getHibernateTemplate().contains(t);
	}

	public void delete(T t, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().delete(t, lockMode);
	}

	public void delete(T t) throws DataAccessException {
		getHibernateTemplate().delete(t);
	}

	public void deleteAll(Collection<T> entities) throws DataAccessException {
		getHibernateTemplate().deleteAll(entities);
	}


	public void refresh(T t, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().refresh(t, lockMode);
	}

	public void refresh(T t) throws DataAccessException {
		getHibernateTemplate().refresh(t);
	}

	public Serializable save(T t) throws DataAccessException {
		return getHibernateTemplate().save(t);
	}

	public void update(T t, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().update(t, lockMode);
	}

	public void update(T t) throws DataAccessException {
		getHibernateTemplate().update(t);
	}

	public List<T> findAll() throws DataAccessException {
		return getHibernateTemplate().loadAll(getEntityClass());

	}

//	public List<T> findByNamedQuery(String queryName)
//			throws DataAccessException {
//		return getHibernateTemplate().findByNamedQuery(queryName);
//	}
//
//	public List<T> findByNamedQuery(String queryName, Object value)
//			throws DataAccessException {
//		return getHibernateTemplate().findByNamedQuery(queryName, value);
//	}
//
//	public List<T> findByNamedQuery(String queryName, Object[] values)
//			throws DataAccessException {
//		return getHibernateTemplate().findByNamedQuery(queryName, values);
//	}

//	@SuppressWarnings("unchecked")
//	public List<T> findPageByCriteria(final DetachedCriteria detachedCriteria,
//			final int pageSize, final int startIndex) {
//		return (List<T>) getHibernateTemplate().execute(
//				new HibernateCallback() {
//					public Object doInHibernate(org.hibernate.Session session)
//							throws HibernateException {
//						Criteria criteria = detachedCriteria
//								.getExecutableCriteria(session);
//						// int totalCount = ((Integer) criteria.setProjection(
//						// Projections.rowCount()).uniqueResult())
//						// .intValue();
//						criteria.setProjection(null);
//						List<T> items = criteria.setFirstResult(startIndex)
//								.setMaxResults(pageSize).list();
//						// PaginationSupport ps = new PaginationSupport(items,
//						// totalCount, pageSize, startIndex);
//						return items;
//					}
//				});
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<T> findPageByQuery(final String hql,final int pageSize, final int startIndex) {
//		return (List<T>) getHibernateTemplate().execute(
//				new HibernateCallback() {
//					public Object doInHibernate(Session session)
//							throws HibernateException, SQLException {
//						Query query = session.createQuery(hql);
//						query.setFirstResult(startIndex);
//						query.setMaxResults(pageSize);
//						List<T> items = query.list();
//						return items;
//
//					}
//				});
//	}
}
