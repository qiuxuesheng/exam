package com.qiuxs.base.service;

import com.qiuxs.base.entity.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService {
    <T extends Entity<ID>, ID extends Serializable> T get(Class<T> clazz, ID id);

    <T extends Entity<?>> List<T> getAll(Class<T> clazz);


    <T extends Entity<?>> List<T> getAll(Class<T> clazz, String orderBy);

    void remove(Entity<?> object);

    <T extends Entity<ID>, ID extends Serializable> void remove(Class<T> clazz, ID id);

    void save(Entity<?> entity);

    void saveOrUpdate(Entity<?> entity);

    List<?> search(String hql, Map<String,Object> params);

    List<?> search(String hql, Object[] params);


    <T extends Entity<?>> List<T> getList(Class<T> clazz,String[] attrs,Object[] params);

    <T extends Entity<?>> boolean exist(Class<T> clazz, String[] attrs, Object[] values);

    <T extends Entity<?>> boolean exist(Class<T> clazz, String attr, Object value);

    <T extends Entity<ID>, ID extends Serializable> boolean exist(Class<T> clazz,ID id);

    boolean exist(String hql,Object[] values);

    boolean exist(String hql,List<Object> values);

}
