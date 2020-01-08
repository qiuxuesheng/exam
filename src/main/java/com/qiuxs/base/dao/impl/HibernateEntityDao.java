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
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author chaostone
 */
public class HibernateEntityDao extends HibernateDaoSupport implements EntityDao {


    protected SessionFactory sessionFactory;





    @SuppressWarnings({ "unchecked" })
    public <T extends Entity<ID>, ID extends Serializable> T get(Class<T> clazz, ID id) {
        return (T) get(clazz.getName(), id);
    }

    @SuppressWarnings({ "unchecked" })
    public <T> T get(String entityName, Serializable id) {
        if (Strings.contains(entityName, '.')) {
            getSessionFactory().getCurrentSession();
            return (T) getSession().get(entityName, id);
        } else {
            String hql = "from " + entityName + " where id =:id";
            Query query = getSession().createQuery(hql);
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
        String hql = "from " + modelMeta.getEntityType(clazz).getEntityName();
        Query query = getSession().createQuery(hql);
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
        String entityName = modelMeta.getEntityType(entityClass).getEntityName();
        return get(entityName, keyName, values);
    }

    public <T extends Entity<?>> List<T> get(Class<T> clazz, String keyName, Collection<?> values) {
        if (clazz == null || Strings.isEmpty(keyName) || values == null || values.isEmpty()) { return Collections
                .emptyList(); }
        String entityName = modelMeta.getEntityType(clazz).getEntityName();
        return get(entityName, keyName, values.toArray());
    }






    public int executeUpdate(final String queryString, final Object... argument) {
        return QuerySupport.setParameter(getNamedOrCreateQuery(queryString), argument).executeUpdate();
    }


    public int executeUpdate(final String queryString, final Map<String, Object> parameterMap) {
        return QuerySupport.setParameter(getNamedOrCreateQuery(queryString), parameterMap).executeUpdate();
    }

    public void saveOrUpdate(Object... entities) {
        if (null == entities) return;
        for (Object entity : entities) {
            if (entity instanceof Collection<?>) {
                for (Object elementEntry : (Collection<?>) entity) {
                    persistEntity(elementEntry, null);
                }
            } else {
                persistEntity(entity, null);
            }
        }
    }

    public void save(Object... entities) {
        if (null == entities) return;
        for (Object entity : entities) {
            if (entity instanceof Collection<?>) {
                for (Object elementEntry : (Collection<?>) entity) {
                    saveEntity(elementEntry, null);
                }
            } else {
                saveEntity(entity, null);
            }
        }
    }


    public void saveOrUpdate(Collection<?> entities) {
        if (null != entities && !entities.isEmpty()) {
            for (Object entity : entities) {
                persistEntity(entity, null);
            }
        }
    }

    private void saveEntity(Object entity, String entityName) {
        if (null == entity) return;
        if (null != entityName) {
            getSession().save(entityName, entity);
        } else {
            if (entity instanceof HibernateProxy) {
                getSession().save(entity);
            } else {
                getSession().save(modelMeta.getEntityType(entity.getClass()).getEntityName(), entity);
            }
        }
    }

    private void persistEntity(Object entity, String entityName) {
        if (null == entity) return;
        if (null != entityName) {
            getSession().saveOrUpdate(entityName, entity);
        } else {
            if (entity instanceof HibernateProxy) {
                getSession().saveOrUpdate(entity);
            } else {
                getSession().saveOrUpdate(modelMeta.getEntityType(entity.getClass()).getEntityName(), entity);
            }
        }
    }




    public int update(Class<?> entityClass, String attr, Object[] values, Map<String, Object> updateParams) {
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
    }

    public void remove(Collection<?> entities) {
        if (null == entities || entities.isEmpty()) return;
        for (Object entity : entities)
            if (null != entity) getSession().delete(entity);
    }

    public void remove(Object... entities) {
        for (Object entity : entities) {
            if (null != entity) {
                if (entity instanceof Collection<?>) {
                    for (Object innerEntity : (Collection<?>) entity) {
                        getSession().delete(innerEntity);
                    }
                } else {
                    getSession().delete(entity);
                }
            }
        }
    }




    public long count(String entityName, String keyName, Object value) {
        String hql = "select count(*) from " + entityName + " where " + keyName + "=:value";
        Map<String, Object> params = CollectUtils.newHashMap();
        params.put("value", value);
        List<?> rs = search(hql, params);
        if (rs.isEmpty()) {
            return 0;
        } else {
            return ((Number) rs.get(0)).longValue();
        }
    }

    public long count(Class<?> entityClass, String keyName, Object value) {
        return count(entityClass.getName(), keyName, value);
    }


    public boolean exist(Class<?> entityClass, String attr, Object value) {
        return count(entityClass, attr, value) > 0;
    }



    /**
     * 构造查询记录数目的查询字符串
     *
     * @param query
     * @return query string
     */
    private String buildCountQueryStr(Query query) {
        String queryStr = "select count(*) ";
        if (query instanceof SQLQuery) {
            queryStr += "from (" + query.getQueryString() + ")";
        } else {
            String lowerCaseQueryStr = query.getQueryString().toLowerCase();
            String selectWhich = lowerCaseQueryStr.substring(0, query.getQueryString().indexOf("from"));
            int indexOfDistinct = selectWhich.indexOf("distinct");
            int indexOfFrom = lowerCaseQueryStr.indexOf("from");
            // 如果含有distinct
            if (-1 != indexOfDistinct) {
                if (Strings.contains(selectWhich, ",")) {
                    queryStr = "select count("
                            + query.getQueryString().substring(indexOfDistinct, query.getQueryString().indexOf(",")) + ")";
                } else {
                    queryStr = "select count(" + query.getQueryString().substring(indexOfDistinct, indexOfFrom) + ")";
                }
            }
            queryStr += query.getQueryString().substring(indexOfFrom);
        }
        return queryStr;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }





}
