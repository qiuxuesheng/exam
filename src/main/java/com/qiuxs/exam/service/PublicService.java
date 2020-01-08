package com.qiuxs.exam.service;

import com.qiuxs.base.service.impl.BaseServiceImpl;
import com.qiuxs.exam.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PublicService extends BaseServiceImpl implements IPublicService{

	public void saveGrade(Grade grade) {
		List<Object> params = new ArrayList<Object>();
		params.add(grade.getName());
		String hql = "from Grade where name = ?";
		if (grade.isPersisted()){
			hql += " and id <> ?";
			params.add(grade.getId());
		}
		if (entityDao.exist(hql,params)) {
			throw new RuntimeException("添加失败，该名称已存在！");
		}
		entityDao.saveOrUpdate(grade);

	}

	public void deleteGrade(Integer gradeId) {
		Grade exist = entityDao.get(Grade.class,gradeId);
		if (exist ==null) {
			throw new RuntimeException("删除失败，该年级不存在！");
		}
		if (entityDao.exist(Adminclass.class,"grade.id",gradeId)) {
			throw new RuntimeException("删除失败，该年级下有班级信息！");
		}
		entityDao.remove(exist);
	}



	public void saveAdminclass(Adminclass adminclass) {

		List<Object> params = new ArrayList<Object>();
		params.add(adminclass.getName());
		params.add(adminclass.getGrade().getId());
		String hql = "from Adminclass where name = ? and grade.id = ?";
		if (adminclass.isPersisted()){
			hql += " and id <> ?";
			params.add(adminclass.getId());
		}
		if (entityDao.exist(hql,params)) {
			throw new RuntimeException("添加失败，当前年级下，该名称已存在！");
		}

		entityDao.saveOrUpdate(adminclass);

	}

	public void deleteAdminclass(Integer id) {

		Adminclass exist = entityDao.get(Adminclass.class,id);
		if (exist ==null) {
			throw new RuntimeException("删除失败，该班级不存在！");
		}
		if (entityDao.exist(Student.class,"adminclass.id",id)) {
			throw new RuntimeException("删除失败，该班级下有学生信息！");
		}
		entityDao.remove(exist);

	}


	public void saveStudent(Student student) {

		entityDao.saveOrUpdate(student);
	}

	public void deleteStudent(Integer id) {

		Student exist = entityDao.get(Student.class,id);
		if (exist ==null) {
			throw new RuntimeException("删除失败，该学生不存在！");
		}
		if (entityDao.exist(ExamScore.class,"student.id",id)) {
			throw new RuntimeException("删除失败，该学生有考试信息！");
		}
		entityDao.remove(exist);
	}


	public void saveExamBatch(ExamBatch examBatch) {
		entityDao.saveOrUpdate(examBatch);
	}

	public void deleteExamBatch(Integer id) {

		ExamBatch exist = entityDao.get(ExamBatch.class,id);
		if (exist ==null) {
			throw new RuntimeException("删除失败，该考次不存在！");
		}
		if (entityDao.exist(ExamScore.class,"examBatch.id",id)) {
			throw new RuntimeException("删除失败，该考次下有成绩信息！");
		}
		entityDao.remove(exist);
	}


	public int uplaodAdminclass(List<List<String>> datas, Integer gradeId) {

		int success = 0;

		try {
			Grade grade = entityDao.get(Grade.class,gradeId);
			if (grade==null) {
				throw new RuntimeException("该年级不存在！");
			}

			int index = -1;
			List<String> rowList = datas.get(0);
			for (int i = 0; i < rowList.size(); i++) {
				if ("班级".equals(rowList.get(i).trim())){
					index = i;
					break;
				}
			}
			if (index == -1){
				throw new RuntimeException("第一列没有找到[班级]字段");
			}

			//该年级下的班级信息
			List<Adminclass> existList = (List<Adminclass>) entityDao.search("from Adminclass where grade.id = ?",new Object[]{gradeId});

			List<String> existNames = new ArrayList<String>();
			for (Adminclass adminclass : existList) {
				existNames.add(adminclass.getName());
			}

			//新建
			for (int i = 1; i < datas.size(); i++) {
				String name = datas.get(i).get(index);
				if (!existNames.contains(name)){
					Adminclass adminclass = new Adminclass();
					adminclass.setGrade(grade);
					adminclass.setName(name);
					adminclass.setCode(name);
					entityDao.saveOrUpdate(adminclass);
					success ++;
					existNames.add(name);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public int uplaodStudent(List<List<String>> datas, Integer gradeId) {

		int success = 0;

		Grade grade = entityDao.get(Grade.class,gradeId);
		if (grade==null) {
			throw new RuntimeException("该年级不存在！");
		}

		int classIndex = -1;

		int nameIndex = -1;

		List<String> rowList = datas.get(0);
		for (int i = 0; i < rowList.size(); i++) {
			if ("班级".equals(rowList.get(i).trim())){
				classIndex = i;
				continue;
			}
			if ("姓名".equals(rowList.get(i).trim())){
				nameIndex = i;
				continue;
			}
		}
		if (classIndex == -1){
			throw new RuntimeException("第一列没有找到[班级]字段");
		}
		if (nameIndex == -1){
			throw new RuntimeException("第一列没有找到[姓名]字段");
		}

		//该年级下的学生信息
		List<Student> existList = (List<Student>) entityDao.search("from Student where adminclass.grade.id = ?",new Object[]{gradeId});
		List<String> exists = new ArrayList<String>();
		for (Student student : existList) {
			exists.add(student.getName()+"-"+student.getAdminclass().getName());
		}
		//该年级下的班级信息
		List<Adminclass> adminclasses = (List<Adminclass>) entityDao.search("from Adminclass where grade.id = ?",new Object[]{gradeId});
		Map<String,Adminclass> adminclassMap = new HashMap<String, Adminclass>();
		for (Adminclass adminclass : adminclasses) {
			adminclassMap.put(adminclass.getName(),adminclass);
		}

		//新建
		for (int i = 1; i < datas.size(); i++) {
			String name = datas.get(i).get(nameIndex);
			String adminclassName = datas.get(i).get(classIndex);
			String key = name + "-" + adminclassName;
			if (!exists.contains(key)){
				Student student = new Student();
				Adminclass adminclass = adminclassMap.get(adminclassName);
				if (adminclass == null ){
					throw new RuntimeException("第 "+i+" 行，班级["+adminclassName+"]不存在");
				}
				student.setAdminclass(adminclass);
				student.setName(name);
				entityDao.saveOrUpdate(student);
				exists.add(key);
				success ++;
			}
		}
		return success;
	}


	public void saveCourse(Course course) {
		entityDao.saveOrUpdate(course);
	}

	public void deleteCourse(Integer id) {

		Course exist = entityDao.get(Course.class,id);
		if (exist ==null) {
			throw new RuntimeException("删除失败，该课程不存在！");
		}
		if (entityDao.exist(ScoreItem.class,"course.id",id)) {
			throw new RuntimeException("删除失败，该课程下有成绩信息！");
		}
		entityDao.remove(exist);
	}

	public void saveModel(WordModel model) {

		entityDao.saveOrUpdate(model);
	}


	public void saveScoreLevel(ScoreLevel level) {

		entityDao.saveOrUpdate(level);

	}
}
