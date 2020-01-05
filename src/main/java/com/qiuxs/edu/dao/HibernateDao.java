package com.qiuxs.edu.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateDao<T extends Serializable> extends HibernateDaoSupport {

	@Resource
	private SessionFactory sessionFactory;
	

	@PostConstruct
	public void initSessionFactory() {
		super.setSessionFactory(sessionFactory);
	}
	public Session getCurrentSession(){
		
		return getSessionFactory().getCurrentSession();
		
	}

	public <K extends Serializable>K findById(Class<K> clazz, String id){
		Session session = null;
		K k = null;
		try {
			session = getSessionFactory().getCurrentSession();
			k = (K)session.get(clazz,id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return k;
	}
	
	public void save(T t){
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.save(t);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	public void saveOrUpdate(T t){
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.saveOrUpdate(t);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	public void update(T t){
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.update(t);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	@SuppressWarnings("unchecked")
	public T findOneByHql(String hql){
		Session session = null;
		T t = null ;
		try {
			session = getSessionFactory().getCurrentSession();
			// 1、得到Query对象，并写入hql语句
			Query query = session.createQuery(hql);
			//2、使用Query对象的list方法得到数据集合
			try {
				t = (T) query.list().get(0);
			} catch (Exception e) {
			}
			//3、遍历集合获取数据
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public T findOneByHql(String hql, List<Object> params){

		Session session = null;
		T t = null;
		try {
			session = getSessionFactory().getCurrentSession();
			// 1、得到Query对象，并写入hql语句
			Query query = session.createQuery(hql);
			//2、填写上一步中占位符的内容
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
			//3、使用Query对象的list方法得到数据集合
			try {
				t = (T) query.list().get(0);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return t;

	}
	@SuppressWarnings("unchecked")
	public T findOneByHql(String hql, Object[] params){

		Session session = null;
		T t = null;
		try {
			session = getSessionFactory().getCurrentSession();
			// 1、得到Query对象，并写入hql语句
			Query query = session.createQuery(hql);
			//2、填写上一步中占位符的内容
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			//3、使用Query对象的list方法得到数据集合
			try {
				t = (T) query.list().get(0);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return t;

	}



	/**
	 * 
	 * 功能描述: 获取多个结果
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findListByHql(String hql) {
		Session session = null;
		List<T> list = null ;
		try {
			session = getSessionFactory().getCurrentSession();
			// 1、得到Query对象，并写入hql语句
			Query query = session.createQuery(hql);
			//2、使用Query对象的list方法得到数据集合
			list = query.list();
			//3、遍历集合获取数据
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> findListByHql(String hql,List<Object> params){

		Session session = null;
		List<T> list = null;
		try {
			session = getSessionFactory().getCurrentSession();
			// 1、得到Query对象，并写入hql语句
			Query query = session.createQuery(hql);
			//2、填写上一步中占位符的内容
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
			//3、使用Query对象的list方法得到数据集合
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return list;

	}
	@SuppressWarnings("unchecked")
	public List<T> findListByHql(String hql,Object[] params){

		Session session = null;
		List<T> list = null;
		try {
			session = getSessionFactory().getCurrentSession();
			// 1、得到Query对象，并写入hql语句
			Query query = session.createQuery(hql);
			//2、填写上一步中占位符的内容
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			//3、使用Query对象的list方法得到数据集合
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return list;

	}

	//---------------------update开始----------------------
	
	public int update(String hql){
		return update(hql, new ArrayList<Object>());
	}
	
	public int update(String hql,List<Object> params){
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createQuery(hql);
			if (params!=null&&params.size()>0) {
				for (int i = 0; i < params.size(); i++) {
					query.setParameter(i, params.get(i));
				}
			}
			return query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public int update(String hql,Object[] params){
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createQuery(hql);
			if (params!=null&&params.length>0) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			}
			return query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//---------------------update结束----------------------
	public <K extends Serializable> List<K> findAll(Class<K> kClass) {

		return findAll(kClass,null);
	}

	public <K extends Serializable> List<K> findAll(Class<K> kClass,String orderHql) {

		Session session = null;
		List<K> list = null;
		try {
			session = getSessionFactory().getCurrentSession();
			String hql = "from " + kClass.getName();
			if (orderHql != null){
				hql += " order by "+ orderHql;
			}

			Query query = session.createQuery(hql);

			//3、使用Query对象的list方法得到数据集合
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return  list;
	}

	public void delete(Object object){
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
			session.delete(object);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}


}
