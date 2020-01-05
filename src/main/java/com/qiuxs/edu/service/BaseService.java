package com.qiuxs.edu.service;

import com.qiuxs.edu.dao.HibernateDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class BaseService {

    @Resource(name = "hibernateDao")
    protected HibernateDao hibernateDao;

    public <K extends Serializable>K findById(Class<K> clazz, String id) {
        return (K) hibernateDao.findById(clazz,id);
    }

    public <K extends Serializable> List<K> findAll(Class<K> kClass) {
        return hibernateDao.findAll(kClass);
    }

    public <K extends Serializable> List<K> findAll(Class<K> kClass,String order) {
        return hibernateDao.findAll(kClass,order);
    }


}
