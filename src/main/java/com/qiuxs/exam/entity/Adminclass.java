package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.BaseCode;

import javax.persistence.*;


/**
 * 班级
 */
@Entity
@Table(name = "T_adminclass")
public class Adminclass extends BaseCode<Integer> {

    private static final long serialVersionUID = 4986039152777349505L;
    /*年级*/
    protected Grade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="grade_id")
    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

}
