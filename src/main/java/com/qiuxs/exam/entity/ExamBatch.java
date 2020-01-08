package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.BaseCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *	考试批次信息
 */
@Entity
@Table(name="T_exam_batch")
public class ExamBatch extends BaseCode<Integer> {

	private static final long serialVersionUID = -3305075780765944573L;

}
