package com.qiuxs.base.service.impl;

import com.qiuxs.base.dao.EntityDao;
import com.qiuxs.base.entity.Entity;
import com.qiuxs.base.page.PageLimit;
import com.qiuxs.base.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BaseServiceImpl implements BaseService {

    @Resource(name = "entityDao")
    protected EntityDao entityDao;

    public <T extends Entity<ID>, ID extends Serializable> T get(Class<T> clazz, ID id) {
        return entityDao.get(clazz,id);
    }

    public <T extends Entity<?>> List<T> getAll(Class<T> clazz) {
        return entityDao.getAll(clazz);
    }

    public <T extends Entity<?>> List<T> getAll(Class<T> clazz, String orderBy) {
        return entityDao.getAll(clazz,orderBy);
    }

    public <T extends Entity<?>> List<T> pageList(String hql, Map<String, Object> map, PageLimit pageLimit) {
        return entityDao.pageList(hql,map,pageLimit);
    }

    public <T extends Entity<?>> List<T> pageList(Class<T> clazz, PageLimit pageLimit) {
        return entityDao.pageList(clazz,pageLimit);
    }

    public void remove(Entity<?> object) {
        entityDao.remove(object);
    }


    public <T extends Entity<ID>, ID extends Serializable> void remove(Class<T> clazz, ID id) {

        entityDao.remove(get(clazz,id));
    }

    public void save(Entity<?> entity) {
        entityDao.save(entity);
    }

    public void saveOrUpdate(Entity<?> entity) {
        entityDao.saveOrUpdate(entity);
    }

    public List<?> search(String hql, Map<String, Object> params) {
        return entityDao.search(hql,params);
    }

    public List<?> search(String hql, Object[] params) {
        return entityDao.search(hql,params);
    }

    public <T extends Entity<?>> List<T> getList(Class<T> clazz, String[] attrs, Object[] params) {
        return entityDao.get(clazz,attrs,params);
    }

    public <T extends Entity<?>> boolean exist(Class<T> clazz, String attr, Object value) {
        return entityDao.count(clazz,attr,value) >0 ;
    }

    public <T extends Entity<?>> boolean exist(Class<T> clazz, String[] attrs, Object[] values) {
        return entityDao.exist(clazz,attrs,values);
    }

    public boolean exist(String hql, Object[] values) {
        return entityDao.exist(hql,values);
    }

    public boolean exist(String hql, List<Object> values) {
        return entityDao.exist(hql,values.toArray());
    }

    public <T extends Entity<ID>, ID extends Serializable> boolean exist(Class<T> clazz, ID id) {
        return exist(clazz,new String[]{"id"},new Object[]{id});
    }
}
