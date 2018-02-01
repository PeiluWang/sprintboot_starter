package org.pillow.common.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.pillow.common.exception.WebServerError;
import org.pillow.common.exception.WebServerException;
import org.pillow.model.dto.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 灵活的动态查询工具
 * @author peilu.wang
 *
 */
@Component
public class DBUtil {

	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * sql查询参数
	 */
	private String sql;
	private HashMap<String, Object> params;
	
	/*
	 * criteria查询参数
	 */
	private Class queryClass;
	private List<Criterion> criterions;
	private List<Order> orders;
	private int pageOffset;
	private int pageSize;
	
	public DBUtil() {
		init();
	}
	
	public void init() {
		sql= "";
		params = new HashMap<String, Object>();
		criterions = new ArrayList<Criterion>();
		orders = new ArrayList<Order>();
		pageOffset = 0;
		pageSize = 0;
	}
	
	public DBUtil setSql(String sql) {
		this.sql = sql;
		params = new HashMap<String, Object>();
		return this;
	}
	
	public DBUtil setSqlParams(String key, Object value) {
		params.put(key, value);
		return this;
	}
	
	public List<Object[]> executeSql() throws WebServerException {
		if(sql==null || sql.isEmpty()) {
			throw new WebServerException(WebServerError.DBUTIL_INVALID_INPUT,"sql is null");
		}
		if(params==null || params.size()==0) {
			return executeQuerySql(sql);
		}
		System.out.println(params.size());
		return executeQuerySql(sql, params);
	}
	
	
	public DBUtil query(Class queryClass) {
		this.queryClass = queryClass;
		return this;
	}
	
	public DBUtil eq(String propertyName, Object value) {
		criterions.add(Restrictions.eq(propertyName, value));
		return this;
	}
	
	public DBUtil lt(String propertyName, Object value) {
		criterions.add(Restrictions.lt(propertyName, value));
		return this;
	}
	
	public DBUtil le(String propertyName, Object value) {
		criterions.add(Restrictions.le(propertyName, value));
		return this;
	}
	
	public DBUtil gt(String propertyName, Object value) {
		criterions.add(Restrictions.gt(propertyName, value));
		return this;
	}
	
	public DBUtil ge(String propertyName, Object value) {
		criterions.add(Restrictions.ge(propertyName, value));
		return this;
	}
	
	public DBUtil like(String propertyName, Object value) {
		criterions.add(Restrictions.like(propertyName, value));
		return this;
	}
	
	public DBUtil pageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
		return this;
	}
	
	public DBUtil pageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	
	public DBUtil orderByAsc(String propertyName) {
		orders.add(Order.asc(propertyName));
		return this;
	}
	
	public DBUtil orderByDesc(String propertyName) {
		orders.add(Order.desc(propertyName));
		return this;
	}
	
	public PageData executeQuery() throws WebServerException{
		Session session=sessionFactory.openSession();
		return criteriaQuery(session, queryClass);
	}
	
	/**
	 * 执行查询类sql
	 * @param sql
	 * @return
	 */
	public List<Object[]> executeQuerySql(String sql){
		return executeQuerySql(sql, null);
	}
	
	/**
	 * 执行查询类sql
	 * @param sql，sql命令
	 * @param sqlParams，sql命令的参数
	 * @return
	 */
	public List<Object[]> executeQuerySql(String sql, HashMap<String, Object> sqlParams){
		Session session=sessionFactory.getCurrentSession();
		SQLQuery query = (SQLQuery) session.createSQLQuery(sql);
		if(sqlParams!=null && sqlParams.size()>0){
			for(Entry<String, Object> entry: sqlParams.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.list();
	}
	
	/**
	 * 执行更新类sql
	 * @param sql
	 * @param sqlParams
	 * @return
	 */
	public boolean executeUpdateSql(String sql, HashMap<String, Object> sqlParams){
		Session session=sessionFactory.getCurrentSession();
		SQLQuery query = (SQLQuery) session.createSQLQuery(sql);
		if(sqlParams.size()>0){
			for(Entry<String, Object> entry: sqlParams.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.executeUpdate();
		return true;
	}
	
	
	/**
	 * Criteria条件查询
	 * @param entityClass
	 * @param criteriaParam
	 * @return
	 * @throws WebServerException 
	 */
	public PageData criteriaQuery(Session session, Class entityClass) throws WebServerException{
		//get current session
		Criteria criteria = session.createCriteria(entityClass,entityClass.toString());
		/*
		 * 添加限制条件
		 */
		for (Criterion criterion : criterions){
			criteria.add(criterion);
		}
		/*
		 * 添加order条件
		 */
		for (Order order : orders){
			criteria.addOrder(order);
		}
		// 统计总的个数，如果放在.list()后会返回Null，是bug!
		criteria.setProjection(Projections.rowCount());
		Long resultCount = (Long) criteria.uniqueResult();
		Long pageNum = 1L; //默认数据页数为1
		//重新设置返回对象
		// TODO: 如果不加distinct，会出现重复的返回对象，原因未知
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		/*
		 * 分页参数
		 */
		if(hasPageParam()){
			criteria = criteria.setFirstResult(pageOffset*pageSize)
					.setMaxResults(pageSize);
			pageNum = resultCount / pageSize + (resultCount % pageSize == 0 ? 0:1);
		}
		//获取数据
		return new PageData(resultCount, pageNum,  criteria.list());
	}

	
	private boolean hasPageParam() throws WebServerException {
		if(pageSize<=0) {
			return false;
		}
		if(pageOffset<0) {
			throw new WebServerException(WebServerError.DBUTIL_INVALID_INPUT,"pageOffset < 0:"+pageOffset);
		}
		return true;
	}
}
