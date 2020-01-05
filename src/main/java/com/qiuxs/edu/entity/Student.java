package com.qiuxs.edu.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="T_student")
public class Student implements Serializable{

	private String id;
	
	private String name;
	
	private Adminclass adminclass;

	
	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="adminclass_id")
	public Adminclass getAdminclass() {
		return adminclass;
	}

	public void setAdminclass(Adminclass adminclass) {
		this.adminclass = adminclass;
	}
}
