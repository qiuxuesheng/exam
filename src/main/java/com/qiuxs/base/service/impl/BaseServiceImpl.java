package com.qiuxs.base.service.impl;

import com.qiuxs.base.dao.impl.HibernateDao;
import com.qiuxs.base.entity.Entity;
import com.qiuxs.base.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class BaseServiceImpl implements BaseService {

    @Resource(name = "hibernateDao")
    protected HibernateDao hibernateDao;

    public <K extends Serializable>K findById(Class<K> clazz, Serializable id) {
        return (K) hibernateDao.findById(clazz,id);
    }

    public <K extends Serializable> List<K> findAll(Class<K> kClass) {
        return hibernateDao.findAll(kClass);
    }

    public <K extends Serializable> List<K> findAll(Class<K> kClass,String order) {
        return hibernateDao.findAll(kClass,order);
    }

    public void delete(Object object) {
        hibernateDao.delete(object);
    }


    public void  delete(Class clazz, Serializable id) {

        hibernateDao.delete(clazz,id);
    }

    public void save(Entity entity) {

    }

    public <T extends Entity<?>> List<T> getAll(Class<T> clazz) {
        return null;
    }


}
