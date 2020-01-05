package com.qiuxs.edu.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 班级
 */
@Entity
@Table(name = "T_adminclass")
public class Adminclass implements Serializable {

    protected String id;

    /*年级*/
    protected Grade grade;

    /*名称*/
    protected String name;

    /*排序*/
    protected int order;

    @Id
    @Column(name="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="grade_id")
    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="orderBy")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
