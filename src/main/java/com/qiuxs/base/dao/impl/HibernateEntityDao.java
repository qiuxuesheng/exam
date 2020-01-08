/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.qiuxs.base.dao.impl;

import com.qiuxs.base.dao.EntityDao;
import com.qiuxs.base.entity.Entity;
import com.qiuxs.base.util.CollectUtils;
import com.qiuxs.base.util.Strings;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * @author chaostone
 */
@Repository("entityDao")
public class HibernateEntityDao extends HibernateDaoSupport implements EntityDao {

    @Resource
    private SessionFactory sessionFactory;


    @PostConstruct
    public void initSessionFactory() {
        super.setSessionFactory(sessionFactory);
    }
    public Session getCurrentSession(){
        return getSessionFactory().getCurrentSession();

    }

    @SuppressWarnings({ "unchecked" })
    public <T extends Entity<ID>, ID extends Serializable> T get(Class<T> clazz, ID id) {
        return (T) get(clazz.getName(), id);
    }

    @SuppressWarnings({ "unchecked" })
    public <T extends Entity<ID>, ID extends Serializable> T get(String entityName, ID id) {
        if (Strings.contains(entityName, '.')) {
            return (T) getSessionFactory().getCurrentSession().get(entityName, id);
        } else {
            String hql = "from " + entityName + " where id =:id";
            Query query = getSessionFactory().getCurrentSession().createQuery(hql);
            query.setParameter("id", id);
            List<?> rs = query.list();
            if (rs.isEmpty()) {
                return null;
            } else {
                return (T) rs.get(0);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity<?>> List<T> getAll(Class<T> clazz) {
        return getAll(clazz,null);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity<?>> List<T> getAll(Class<T> clazz,String orderBy) {
        String hql = "from " + clazz.getName();
        if (orderBy!=null){
            hql += " order by " + orderBy;
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setCacheable(true);
        return query.list();
    }

    public <T extends Entity<ID>, ID extends Serializable> List<T> get(Class<T> clazz, ID[] values) {
        return get(clazz, "id", (Object[]) values);
    }

    public <T extends Entity<ID>, ID extends Serializable> List<T> get(Class<T> clazz, Collection<ID> values) {
        return get(clazz, "id", values.toArray());
    }

    public <T extends Entity<?>> List<T> get(Class<T> entityClass, String keyName, Object... values) {
        if (entityClass == null || Strings.isEmpty(keyName) || values == null || values.length == 0) { return Collections
                .emptyList(); }
        String entityName = entityClass.getName();
        Session session = getSessionFactory().getCurrentSession();
        String hql = " from " + entityName + " where " + keyName + " in (:values)";
        Query query = session.createQuery(hql);
        query.setParameterList("values",values);
        return query.list();
    }

    public <T extends Entity<?>> List<T> get(Class<T> clazz, String[] keyNames, Object[] values) {
        String hql = createHql(clazz.getName(),keyNames,false);
        Map<String, Object> params = convertToMap(keyNames, values);
        return (List<T>) search(hql, params);
    }

    public <T extends Entity<?>> List<T> get(Class<T> clazz, String keyName, Collection<?> values) {
        if (clazz == null || Strings.isEmpty(keyName) || values == null || values.isEmpty()) { return Collections
                .emptyList(); }
        return get(clazz, keyName, values.toArray());
    }


    public void saveOrUpdate(Entity<?>... entities) {
        if (null == entities) return;
        for (Entity<?> entity : entities) {
            persistEntity(entity, null);
        }
    }

    public void save(Entity<?>... entities) {
        if (null == entities) return;
        for (Entity<?> entity : entities) {
            saveEntity(entity, null);
        }
    }


    public void saveOrUpdate(Collection<Entity<?>> entities) {
        if (null != entities && !entities.isEmpty()) {
            for (Entity<?> entity : entities) {
                persistEntity(entity, null);
            }
        }
    }

    private void saveEntity(Entity<?> entity, String entityName) {
        if (null == entity) return;
        if (null != entityName) {
            getSessionFactory().getCurrentSession().save(entityName, entity);
        } else {
            if (entity instanceof HibernateProxy) {
                getSessionFactory().getCurrentSession().save(entity);
            } else {
                getSessionFactory().getCurrentSession().save(entity.getClass().getName(), entity);
            }
        }
    }

    private void persistEntity(Entity<?> entity, String entityName) {
        if (null == entity) return;
        if (null != entityName) {
            getSessionFactory().getCurrentSession().saveOrUpdate(entityName, entity);
        } else {
            if (entity instanceof HibernateProxy) {
                getSessionFactory().getCurrentSession().saveOrUpdate(entity);
            } else {
                getSessionFactory().getCurrentSession().saveOrUpdate(entity.getClass().getName(), entity);
            }
        }
    }


/*    public int update(Class<?> entityClass, String attr, Object[] values, Map<String, Object> updateParams) {
        if (null == values || values.length == 0 || updateParams.isEmpty()) { return 0; }
        String entityName = entityClass.getName();
        StringBuilder hql = new StringBuilder();
        hql.append("update ").append(entityName).append(" set ");
        Map<String, Object> newParams = CollectUtils.newHashMap();
        for (final String parameterName : updateParams.keySet()) {
            if (null == parameterName) {
                continue;
            }
            String locateParamName = Strings.replace(parameterName, ".", "_");
            hql.append(parameterName).append(" = ").append(":").append(locateParamName).append(",");
            newParams.put(locateParamName, updateParams.get(locateParamName));
        }
        hql.deleteCharAt(hql.length() - 1);
        hql.append(" where ").append(attr).append(" in (:ids)");
        newParams.put("ids", values);
        return executeUpdate(hql.toString(), newParams);
    }*/

    public void remove(Collection<Entity<?>> entities) {
        if (null == entities || entities.isEmpty()) return;
        for (Object entity : entities)
            if (null != entity) getSessionFactory().getCurrentSession().delete(entity);
    }

    public void remove(Entity<?>... entities) {
        for (Entity entity : entities) {
            if (null != entity) {
                getSessionFactory().getCurrentSession().delete(entity);
            }
        }
    }


    public List<?> search(String hql, Map<String, Object> params) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        if (params!=null){
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(),entry.getValue());
            }
        }
        return query.list();
    }

    public List<?> search(String hql, Object[] params) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        if (params!=null){
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i,params[i]);
            }
        }
        return query.list();
    }

    public <T extends Entity<?>> long count(Class<T> entityClass, String keyName, Object value) {
        return count(entityClass.getName(), keyName, value);
    }


    public long count(String entityName, String keyName, Object value) {
        return count(entityName,new String[]{keyName},new Object[]{value});
    }



    public <T extends Entity<?>> boolean exist(Class<T> entityClass, String attr, Object value) {
        return count(entityClass, attr, value) > 0;
    }

    public <T extends Entity<?>> boolean exist(Class<T> entityClass, String[] attrs, Object[] values) {
        return count(entityClass,attrs,values) > 0;
    }

    public <T extends Entity<?>> long count(Class<T> entityClass, String[] keyNames, Object[] values) {
        String entityName = entityClass.getName();
        return count(entityName,keyNames,values);

    }


    private Map<String, Object> convertToMap(String[] keyNames, Object[] values) {
        Map<String, Object> params = CollectUtils.newHashMap();;
        try {
            if (keyNames!=null && keyNames.length >0){
                for (int i = 0; i < keyNames.length; i++) {
                    String keyName = keyNames[i];
                    params.put(keyName,values[i]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("参数列表不正确");
        }
        return params;
    }


    private String createHql(String entityName, String[] keyNames, boolean count){
        StringBuilder hql = new StringBuilder();
        if (count){
            hql.append("select count(*) ");
        }
        hql.append(" from ").append(entityName).append(" where 1=1 ") ;
        if (keyNames!=null && keyNames.length >0){
            for (int i = 0; i < keyNames.length; i++) {
                hql.append(" and ").append(keyNames[i]).append(" = :").append(keyNames[i]);
            }
        }
        return hql.toString();
    }

    public long count(String entityName, String[] keyNames, Object[] values) {

        String hql = createHql(entityName,keyNames,true);
        Map<String, Object> params = convertToMap(keyNames,values);
        List<?> rs = search(hql, params);
        if (rs.isEmpty()) {
            return 0;
        } else {
            return ((Number) rs.get(0)).longValue();
        }
    }

    public boolean exist(String hql, Object[] values) {
        return count(hql,values)>0;
    }

    public boolean exist(String hql, List<Object> values) {
        return count(hql,values.toArray()) > 0 ;
    }

    public long count(String hql, Object[] values) {
        if (!hql.toLowerCase().contains("select")){
            hql += "select count(*) " + hql;
        }
        List<?> rs = search(hql, values);
        if (rs.isEmpty()) {
            return 0;
        } else {
            return ((Number) rs.get(0)).longValue();
        }
    }
}
