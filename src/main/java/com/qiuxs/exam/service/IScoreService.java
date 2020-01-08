package com.qiuxs.exam.service;

import java.util.List;

import com.qiuxs.base.service.BaseService;
import com.qiuxs.exam.entity.ExamBatch;
import com.qiuxs.exam.entity.ExamScore;
import com.qiuxs.exam.entity.Grade;

public interface IScoreService extends BaseService {
	
	/**
	 * 上传分数
	 * @param datas
	 */
	public int uploadScore(List<List<String>> datas,Integer examBatchId,Integer gradeId,List<String> courseNames);

	/**
	 * 查询成绩
	 * 
	 * @return
	 */
	public List<ExamScore> findPageList();

	public List<ExamScore> findPageList(Integer examId,Integer gradeId);

	public List<List<Object>> getDataList(ExamBatch exam, Grade grade, Integer courseId);
	
	
}
