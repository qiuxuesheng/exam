package com.qiuxs.edu.service;

import java.util.List;

import com.qiuxs.edu.entity.ExamBatch;
import com.qiuxs.edu.entity.ExamScore;

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

	public List<ExamScore> findPageList(String examId);

	public List<List<Object>> getDataList(ExamBatch exam, String courseId);
	
	
}
