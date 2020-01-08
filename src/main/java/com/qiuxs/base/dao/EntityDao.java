package com.qiuxs.base.dao;

import com.qiuxs.base.entity.Entity;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface EntityDao {


    /**
     * 查询指定id的对象
     *
     * @param clazz 类型
     * @param id 唯一标识
     * @return null
     */
    <T extends Entity<ID>, ID extends Serializable> T get(Class<T> clazz, ID id);

    /**
     * Returns model by identifier,null when not found.
     *
     * @param entityName
     * @param id
     */
    <T extends Entity<ID>, ID extends Serializable> T get(String entityName, ID id);

    /**
     * Returns a list of all entity of clazz.
     *
     * @param clazz
     */
    <T extends Entity<?>> List<T> getAll(Class<T> clazz);
    /**
     * Returns a list of all entity of clazz.
     *
     * @param clazz
     */
    <T extends Entity<?>> List<T> getAll(Class<T> clazz,String orderBy);

    /**
     * 根据属性列举实体
     *
     * @param entityClass
     * @param values
     */
    <T extends Entity<ID>, ID extends Serializable> List<T> get(Class<T> entityClass, ID[] values);

    /**
     * 根据属性列举实体
     *
     * @param entityClass
     * @param values
     */
    <T extends Entity<ID>, ID extends Serializable> List<T> get(Class<T> entityClass, Collection<ID> values);

    List<?> search(String hql, Map<String, Object> params) ;

    List<?> search(String hql, Object[] params) ;

    /**
     * 根据属性列举实体
     *
     * @param <T>
     * @param clazz
     * @param keyName
     * @param values
     */
    <T extends Entity<?>> List<T> get(Class<T> clazz, String keyName, Collection<?> values);

    /**
     * 根据属性列举实体
     *
     * @param <T>
     * @param clazz
     * @param values
     */
    <T extends Entity<?>> List<T> get(Class<T> clazz, String[] keyNames, Object[] values);


    /**
     * 保存或更新单个或多个实体.
     */
    void saveOrUpdate(Entity<?>... entities);

    /**
     * 保存单个或多个实体.
     */
    void save(Entity<?>... entities);

    /**
     * Save Collection
     *
     * @param entities
     */
    void saveOrUpdate(Collection<Entity<?>> entities);


    /**
     * 删除单个对象
     *
     * @param entities
     */
    void remove(Entity<?>... entities);

    /**
     * 删除集合内的所有对象
     *
     * @param entities
     */
    void remove(Collection<Entity<?>> entities);

    <T extends Entity<?>> boolean exist(Class<T> entityClass, String attr, Object value);

    <T extends Entity<?>> boolean exist(Class<T> entityClass, String[] attrs, Object[] values);

    boolean exist(String hql, Object[] values);


    boolean exist(String hql, List<Object> values);


    long count(String entityName, String keyName, Object value);

    <T extends Entity<?>> long count(Class<T> entityClass, String keyName, Object value);

    <T extends Entity<?>> long count(Class<T> entityClass, String[] keyNames, Object[] values);

    long count(String entityName, String[] keyNames, Object[] values);

    long count(String hql, Object[] values);

}
