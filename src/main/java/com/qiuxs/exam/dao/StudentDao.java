package com.qiuxs.exam.dao;

import com.qiuxs.base.dao.impl.HibernateDao;
import org.springframework.stereotype.Repository;

import com.qiuxs.exam.entity.Student;

@Repository
public class StudentDao extends HibernateDao<Student> {

}
