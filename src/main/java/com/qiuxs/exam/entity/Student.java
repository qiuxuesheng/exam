package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.NumberIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="T_student")
public class Student extends NumberIdEntity<Integer> {

	private static final long serialVersionUID = -63470238267616117L;

	@Column(name="name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="adminclass_id",columnDefinition = "int(11)")
	private Adminclass adminclass;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Adminclass getAdminclass() {
		return adminclass;
	}

	public void setAdminclass(Adminclass adminclass) {
		this.adminclass = adminclass;
	}
}
