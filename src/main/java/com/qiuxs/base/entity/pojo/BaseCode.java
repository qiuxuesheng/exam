package com.qiuxs.base.entity.pojo;

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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * 基础代码的基类
 * </p>
 * 很对基础代码数据成员结构相似，仅在数据库中表名和列名 不一样，带都含有这些基类中规定的数据类型，所以把这种结构相似性抽出来，
 * 节省代码的编制量.每个子类代码仍要有自己的类型定义和数据库映射定义. 基类和数据库表没有映射关系，仅仅是数据抽象.
 */
@MappedSuperclass
@Cacheable
public abstract class BaseCode<ID extends Number> extends NumberIdEntity<ID> implements Comparable<Object> {

    private static final long serialVersionUID = 5728157880502841506L;

    /**
     * 基础代码的代码关键字
     */
    @Column(name="code", unique = true, nullable = false, length = 32)
    @Size(max = 32,message = "课程代码最大长度为32")
    protected String code;
    /**
     * 代码中文名称
     */
    @Column(name = "name", unique = true,nullable = false,length = 128)
    @Size(max = 128,message = "最大长度为128")
    protected String name;

    /** 创建时间 */
    @Column(name = "created_At")
    protected Date createdAt;

    /** 最后修改时间 */
    @Column(name = "updated_At")
    protected Date updatedAt;

    public BaseCode() {

    }

    public BaseCode(ID id) {
        this.id = id;
    }


    /**
     * 查询基础代码是否具有扩展属性，一般供子类使用。
     */
    public boolean hasExtPros() {
        Field[] fields = getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (!(Modifier.isFinal(fields[i].getModifiers()) || Modifier.isStatic(fields[i].getModifiers()))) { return true; }
        }
        return false;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int compareTo(Object arg0) {
        BaseCode<?> other = (BaseCode<?>) arg0;
        return this.getCode().compareTo(other.getCode());
    }

}
