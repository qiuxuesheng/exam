package com.qiuxs.edu.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService {

    <K extends Serializable>K findById(Class<K> clazz, String id) ;

    <K extends Serializable> List<K> findAll(Class<K> kClass) ;

    <K extends Serializable> List<K> findAll(Class<K> kClass,String orderBy) ;
}
