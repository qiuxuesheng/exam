package com.qiuxs.exam.service;

import com.qiuxs.base.service.impl.BaseServiceImpl;
import com.qiuxs.base.util.MyUtil;
import com.qiuxs.base.util.Strings;
import com.qiuxs.exam.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 */
@Service("scoreService")
@Transactional
public class ScoreServiceImpl extends BaseServiceImpl implements ScoreService {


	public int uploadScore(List<List<String>> datas,Integer examBatchId,Integer gradeId,List<String> courseNames) {

		int success = 0 ;

		ExamBatch examBatch = entityDao.get(ExamBatch.class,examBatchId);
		if (examBatch==null) {
			throw new RuntimeException("该考试批次不存在！");
		}

		Grade grade = (Grade) entityDao.get(Grade.class,gradeId);
		if (grade==null) {
			throw new RuntimeException("该年级不存在！");
		}

		List<String> row = datas.get(0);

		//课程信息
		Map<String,Course> courseMap = new HashMap<String, Course>();
		Map<String,Integer> courseIndexMap = new HashMap<String, Integer>();
		for (String courseName : courseNames) {

			List<?> courses = entityDao.search("from Course where name = ? ", new Object[]{courseName});
			if (courses.size()==0){
				throw new RuntimeException("没有维护课程:"+courseName);
			}
			courseMap.put(courseName, (Course) courses.get(0));

			boolean b = false;
			for (int i = 0; i < row.size(); i++) {
				if (courseName.equals(row.get(i))){
					courseIndexMap.put(courseName,i);
					b = true;
					break;
				}
			}

			if (!b){
				throw new RuntimeException("第一列没有找到["+courseName+"]字段");
			}
		}


		//准考号
		int numberIndex = -1;
		//班级
		int adminclassIndex = -1;
		//姓名
		int nameIndex = -1;
		for (int i = 0; i < row.size(); i++) {

			if ("准考号".equals(row.get(i))){
				numberIndex = i;
			} else if ("班级".equals(row.get(i))){
				adminclassIndex = i;
			} else if ("姓名".equals(row.get(i))){
				nameIndex = i;
			}
		}
		if (numberIndex == -1){
			throw new RuntimeException("第一列没有找到[准考号]字段");
		}
		if (adminclassIndex == -1){
			throw new RuntimeException("第一列没有找到[班级]字段");
		}
		if (nameIndex == -1){
			throw new RuntimeException("第一列没有找到[姓名]字段");
		}


		//删除该考次下的得分记录
		List<ExamScore> examScores = (List<ExamScore>) entityDao.search("from ExamScore where examBatch.id = ?",new Object[]{examBatchId});
		for (ExamScore examScore : examScores) {
			entityDao.remove(examScore);
		}


		for (int rowIndex = 1; rowIndex < datas.size(); rowIndex++) {

			row = datas.get(rowIndex);
			String testNumber = row.get(numberIndex);
			String adminclassName = row.get(adminclassIndex);
			String stuName = row.get(nameIndex);

			//看班级是否存在，不存在则新建
			List<?> adminclasses = entityDao.search("from Adminclass where name=?", new Object[]{adminclassName});
			if (adminclasses.size()==0) {
				throw new RuntimeException("第"+(rowIndex+1)+"行出错，班级不存在:"+adminclassName);
			}

			//查询学生,不存在则新建学生
			List<?> students = entityDao.search("from Student where name = ? and adminclass.name =? ",new Object[]{stuName,adminclassName});
			if (students.size()==0) {
				throw new RuntimeException("第"+(rowIndex+1)+"行出错，学生不存在:"+stuName);
			}


			//成绩
			ExamScore examScore = new ExamScore();
			examScore.setStudent((Student) students.get(0));
			examScore.setExamBatch(examBatch);
			examScore.setTestNumber(testNumber);
			entityDao.saveOrUpdate(examScore);

			//分项成绩
			for (String courseName : courseNames) {
				ScoreItem scoreItem = new ScoreItem();
				scoreItem.setCourse(courseMap.get(courseName));
				scoreItem.setExamScore(examScore);
				String score = row.get(courseIndexMap.get(courseName));
				if (Strings.isEmpty(score)){
					scoreItem.setMiss(true);
				}
				scoreItem.setScore(convertToScore(score,rowIndex,courseName));
				entityDao.saveOrUpdate(scoreItem);
			}
			success ++;
		}

		return success;
	}


	private double convertToScore(String score,int rowIndex,String courseName){
		double d = 0;
		try {
			d = Double.parseDouble("".equals(score)?"0":score);
		} catch (NumberFormatException e) {
			throw new RuntimeException("第"+(rowIndex+1)+"行出错，"+courseName+"分数异常："+score);
		}
		return d;
	}

	public List<ExamScore> findPageList() {
		return findPageList(null,null);
	}

	public List<ExamScore> findPageList(Integer examId,Integer gradeId) {

		StringBuffer hql = new StringBuffer("from ExamScore where 1=1");
		List<Object> params = new ArrayList<Object>();
		if (examId != null) {
			hql.append("and examBatch.id=?");
			params.add(examId);
		}
		if (gradeId !=null) {
			hql.append("and student.adminclass.grade.id=?");
			params.add(gradeId);
		}
		hql.append("order by testNumber");

		return (List<ExamScore>) entityDao.search(hql.toString(), params.toArray());
	}

	public List<List<Object>> getDataList(ExamBatch examBatch, Grade grade, Integer courseId,Integer modelId){


		List<List<Object>> rows = new ArrayList<List<Object>>();

		if (examBatch ==null) {
			return rows;
		}
		WordModel model = entityDao.get(WordModel.class,modelId);
		List<ExamScore> scores = findPageList(examBatch.getId(),grade.getId());
		List<ScoreLevel> levels = model.getLevels();

		Map<String, Double> adminDataMap = new HashMap<String, Double>();

		//考试人数
		String admin_std_count_key = "std_count_key";
		//总分数
		String admin_total_score_key = "score_all_key";

		for (ExamScore examScore : scores) {
			//分项成绩
			ScoreItem scoreItem = examScore.getScoreItem(courseId);
			//过滤缺考的学生
			if (scoreItem==null||scoreItem.isMiss()){
				continue;
			}

			double score = scoreItem.getScore();

			addMapValue(adminDataMap, examScore.getStudent().getAdminclass().getName()+admin_std_count_key);
			addMapValue(adminDataMap, examScore.getStudent().getAdminclass().getName()+admin_total_score_key,score);
			for (ScoreLevel level : levels) {
				if (level.conform(score)){
					addMapValue(adminDataMap, examScore.getStudent().getAdminclass().getName()+level.getName());
				}
			}
		}

		List<Adminclass> adminclasses = (List<Adminclass>) entityDao.search("from Adminclass where grade.id = ? order by code desc",new Object[]{grade.getId()});

		String grade_std_all_key =  "_std_all";
		String grade_score_all_key = "_score_all";
		Map<String,Double> gradeDataMap = new HashMap<String, Double>();
		gradeDataMap.put(grade_std_all_key,0D);
		gradeDataMap.put(grade_score_all_key,0D);
		for (ScoreLevel level : levels) {
			gradeDataMap.put(level.getName(),0D);
		}

		for (Adminclass adminclass : adminclasses) {
			String adminclassName = adminclass.getName();
			List<Object> row = new ArrayList<Object>();
			//班级名称
			row.add(adminclassName);
			// 考试人数
			int admin_std_all = getMapValue_Int(adminDataMap,adminclassName + admin_std_count_key);
			row.add(admin_std_all);
			addMapValue(gradeDataMap,grade_std_all_key,admin_std_all);

			//各阶段信息
			for (ScoreLevel level : levels) {
				String key = adminclassName + level.getName();
				int admin_std_level = getMapValue_Int(adminDataMap,key);
				addMapValue(gradeDataMap,level.getName(),admin_std_level);
				if (level.isPercent()){
					row.add(MyUtil.getPercent((double) admin_std_level, (double) admin_std_all));
				} else {
					row.add(admin_std_level);
				}
			}
			//平均分
			double admin_score_all = adminDataMap.get(adminclassName + admin_total_score_key);
			row.add(MyUtil.getPercent(admin_score_all, (double) admin_std_all,false));
			addMapValue(gradeDataMap,grade_score_all_key,admin_score_all);
			rows.add(row);
		}


		Collections.reverse(rows);
		List<Object> allRow = new ArrayList<Object>();

		List<Object> row = new ArrayList<Object>();
		row.add("全年级");
		Double gradeStdCount = gradeDataMap.get(grade_std_all_key);
		row.add(gradeStdCount);
		for (ScoreLevel level : levels) {
			if (level.isPercent()){

				row.add(MyUtil.getPercent(gradeDataMap.get(level.getName()), gradeStdCount));
			} else {
				row.add(gradeDataMap.get(level.getName()));
			}
		}
		row.add(MyUtil.getPercent(gradeDataMap.get(grade_score_all_key),gradeStdCount,false));
		rows.add(row);
		rows.add(allRow);
		return rows;

	}


	private void addMapValue(Map<String, Double> map, String key) {
		addMapValue(map, key, 1);
	}

	private void addMapValue(Map<String, Double> map, String key, double d) {
		if (map.containsKey(key)) {
			map.put(key, map.get(key)+d);
		}else{
			map.put(key, d);
		}
	}




	public int getMapValue_Int(Map<String, Double> map ,String key){

		int result = 0;
		if (map!=null) {
			try {
				double d = map.get(key);
				result = (int)d;
			} catch (Exception e) {
			}
		}
		return result;
	}


}
