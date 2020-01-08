package com.qiuxs.base.service;

import com.qiuxs.base.entity.Entity;

import java.io.Serializable;
import java.util.List;

public interface BaseService {

    <K extends Serializable>K findById(Class<K> clazz, Serializable id) ;

    <K extends Serializable> List<K> findAll(Class<K> kClass) ;

    <K extends Serializable> List<K> findAll(Class<K> kClass,String orderBy) ;

    void delete(Object object);

    void delete(Class clazz, Serializable id);

    void save(Entity entity);

    <T extends Entity<?>> List<T> getAll(Class<T> clazz);

}
