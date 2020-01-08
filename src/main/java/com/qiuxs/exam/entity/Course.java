package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.BaseCode;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 课程
 */
@Entity
@Table(name = "T_course")
public class Course extends BaseCode<Integer> {

    private static final long serialVersionUID = -8568964887795971544L;

}
