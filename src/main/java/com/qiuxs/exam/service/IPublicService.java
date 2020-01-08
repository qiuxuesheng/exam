package com.qiuxs.exam.service;

import com.qiuxs.base.service.BaseService;
import com.qiuxs.exam.entity.*;

import java.util.List;

public interface IPublicService extends BaseService {

	void saveExamBatch(ExamBatch examBatch);
	void deleteExamBatch(Integer id);

	void saveGrade(Grade grade);
	void deleteGrade(Integer id);

	void saveAdminclass(Adminclass adminclass);
	void deleteAdminclass(Integer id);

	void saveStudent(Student student);
	void deleteStudent(Integer id);

	/**
	 * 上传班级信息
	 * @param datas
	 * @param gradeId
	 */
    int uplaodAdminclass(List<List<String>> datas, Integer gradeId);

	int uplaodStudent(List<List<String>> datas, Integer gradeId);

	void saveCourse(Course course);

	void deleteCourse(Integer id);

	void saveModel(WordModel model);

	void saveScoreLevel(ScoreLevel level);
}
