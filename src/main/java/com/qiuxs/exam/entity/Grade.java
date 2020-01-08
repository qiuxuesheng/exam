package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.BaseCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 年级信息
 */
@Entity
@Table(name="T_grade")
public class Grade extends BaseCode<Integer> {
	
	private static final long serialVersionUID = -797043363572714429L;

	
}
