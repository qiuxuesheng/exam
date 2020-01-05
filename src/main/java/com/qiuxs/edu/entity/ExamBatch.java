package com.qiuxs.edu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *	考试批次信息
 */
@Entity
@Table(name="T_exam_batch")
public class ExamBatch implements Serializable{

	private static final long serialVersionUID = -3305075780765944573L;

	private String id;

	private String name;

	private int order;

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

	@Column(name="orderBy")
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
