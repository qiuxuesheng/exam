package com.qiuxs.base.dao;

import com.qiuxs.base.entity.Entity;

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
    <T> T get(String entityName, Serializable id);

    /**
     * Returns a list of all entity of clazz.
     *
     * @param clazz
     */
    <T extends Entity<?>> List<T> getAll(Class<T> clazz);

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
     * 执行JPQL/NamedQuery 进行更新或者删除
     *
     * @param query
     * @param arguments
     */
    int executeUpdate(String query, Object... arguments);

    /**
     * 执行JPQL/NamedQuery进行更新或者删除
     *
     * @param query
     * @param parameterMap
     */
    int executeUpdate(String query, Map<String, Object> parameterMap);

    /**
     * 保存或更新单个或多个实体.
     */
    void saveOrUpdate(Object... entities);

    /**
     * 保存单个或多个实体.
     */
    void save(Object... entities);

    /**
     * Save Collection
     *
     * @param entities
     */
    void saveOrUpdate(Collection<?> entities);


    /**
     * 删除单个对象
     *
     * @param entities
     */
    void remove(Object... entities);

    /**
     * 删除集合内的所有对象
     *
     * @param entities
     */
    void remove(Collection<?> entities);

    boolean exist(Class<?> entityClass, String attr, Object value);

    long count(Class<?> entityClass, String keyName, Object value);

}
