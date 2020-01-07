package com.qiuxs.edu.service;

import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

public interface IBaseService {

    <K extends Serializable>K findById(Class<K> clazz, Serializable id) ;

    <K extends Serializable> List<K> findAll(Class<K> kClass) ;

    <K extends Serializable> List<K> findAll(Class<K> kClass,String orderBy) ;

    void delete(Object object);

    void delete(Class clazz, Serializable id);


}
