package com.qiuxs.edu.service;

import java.util.List;

import com.qiuxs.edu.entity.ExamBatch;
import com.qiuxs.edu.entity.ExamScore;
import com.qiuxs.edu.entity.Grade;

public interface IScoreService extends IBaseService{
	
	/**
	 * 上传分数
	 * @param datas
	 */
	public int uploadScore(List<List<String>> datas,String examBatchId,String gradeId,List<String> courseNames);

	/**
	 * 查询成绩
	 * 
	 * @return
	 */
	public List<ExamScore> findPageList();

	public List<ExamScore> findPageList(String examId,String gradeId);

	public List<List<Object>> getDataList(ExamBatch exam, Grade grade, String courseId);
	
	
}
