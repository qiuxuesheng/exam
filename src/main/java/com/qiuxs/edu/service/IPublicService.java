package com.qiuxs.edu.service;

import com.qiuxs.edu.entity.*;

import java.util.List;

public interface IPublicService extends IBaseService{

	void saveExamBatch(ExamBatch examBatch);
	void deleteExamBatch(String id);

	void saveGrade(Grade grade);
	void deleteGrade(String id);

	void saveAdminclass(Adminclass adminclass);
	void deleteAdminclass(String id);

	void saveStudent(Student student);
	void deleteStudent(String id);

	/**
	 * 上传班级信息
	 * @param datas
	 * @param gradeId
	 */
    int uplaodAdminclass(List<List<String>> datas, String gradeId);

	int uplaodStudent(List<List<String>> datas, String gradeId);

	void saveCourse(Course course);

	void deleteCourse(String id);
}
