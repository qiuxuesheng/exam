package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.BaseCode;
import com.qiuxs.base.entity.pojo.NumberIdEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;


/**
 * 班级
 */
@Entity
@Table(name = "T_adminclass")
public class Adminclass extends NumberIdEntity<Integer> {

    @Column(name = "name", nullable = false,length = 128)
    @Size(max = 128,message = "最大长度为128")
    private String name;

    @Column(name="code",nullable = false, length = 32)
    @Size(max = 32,message = "课程代码最大长度为32")
    private String code;

    private static final long serialVersionUID = 4986039152777349505L;
    /*年级*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="grade_id",columnDefinition = "int(11)")
    protected Grade grade;

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
