package com.qiuxs.edu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.qiuxs.edu.dao.*;
import com.qiuxs.edu.entity.*;
import com.qiuxs.edu.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiuxs.edu.util.MyUtil;

@Service
@Transactional
public class ScoreService extends BaseService implements IScoreService{

	@Resource(name="examBatchDao")
	private ExamBatchDao examBatchDao;

	@Resource(name="gradeDao")
	private GradeDao gradeDao;

	@Resource(name="studentDao")
	private StudentDao studentDao;

	@Resource(name="examScoreDao")
	private ExamScoreDao examScoreDao;

	@Resource(name="adminclassDao")
	private AdminclassDao adminclassDao;


	@Autowired
	private IPublicService publicService;



	public int uploadScore(List<List<String>> datas,String examBatchId,String gradeId,List<String> courseNames) {

		int success = 0 ;

		ExamBatch examBatch = (ExamBatch) hibernateDao.findById(ExamBatch.class,examBatchId);
		if (examBatch==null) {
			throw new RuntimeException("该考试批次不存在！");
		}

		Grade grade = (Grade) hibernateDao.findById(Grade.class,gradeId);
		if (grade==null) {
			throw new RuntimeException("该年级不存在！");
		}

		List<String> row = datas.get(0);

		//课程信息
		Map<String,Course> courseMap = new HashMap<String, Course>();
		Map<String,Integer> courseIndexMap = new HashMap<String, Integer>();
		for (String courseName : courseNames) {

			Course course = (Course) hibernateDao.findOneByHql("from Course where name = ? ", new Object[]{courseName});
			if (course == null){
				throw new RuntimeException("没有维护课程:"+courseName);
			}
			courseMap.put(courseName,course);

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
		List<ExamScore> examScores = hibernateDao.findListByHql("from ExamScore where examBatch.id = ?",new Object[]{examBatchId});
		for (ExamScore examScore : examScores) {
			hibernateDao.delete(examScore);
		}


		for (int i = 1; i < datas.size(); i++) {
			if (i==1262){
				i=i;
			}
			row = datas.get(i);
			String testNumber = row.get(numberIndex);
			String adminclassName = row.get(adminclassIndex);
			String stuName = row.get(nameIndex);

			//看班级是否存在，不存在则新建
			Adminclass adminclass = adminclassDao.findOneByHql("from Adminclass where name=?", new Object[]{adminclassName});
			if (adminclass==null) {
				throw new RuntimeException("第"+(i+1)+"行出错，班级不存在:"+adminclassName);
			}

			//查询学生,不存在则新建学生
			Student student = studentDao.findOneByHql("from Student where name = ? and adminclass.name =? ",new Object[]{stuName,adminclassName});
			if (student==null) {
				throw new RuntimeException("第"+(i+1)+"行出错，学生不存在:"+stuName);
			}


			//成绩
			ExamScore examScore = new ExamScore();
			examScore.setId(MyUtil.getUUID());
			examScore.setStudent(student);
			examScore.setExamBatch(examBatch);
			examScore.setTestNumber(testNumber);
			examScoreDao.save(examScore);

			//分项成绩
			for (String courseName : courseNames) {
				ScoreItem scoreItem = new ScoreItem();
				scoreItem.setCourse(courseMap.get(courseName));
				scoreItem.setExamScore(examScore);
				scoreItem.setId(MyUtil.getUUID());
				String score = row.get(courseIndexMap.get(courseName));
				try {
					scoreItem.setScore(Double.parseDouble("".equals(score)?"999":score));
				} catch (NumberFormatException e) {
					throw new RuntimeException("第"+(i+1)+"行出错，"+courseName+"分数异常："+score);
				}
				hibernateDao.save(scoreItem);
			}
			success ++;
		}

		return success;
	}

	public List<ExamScore> findPageList() {
		return findPageList(null,null);
	}

	public List<ExamScore> findPageList(String examId,String gradeId) {

		StringBuffer hql = new StringBuffer("from ExamScore where 1=1");
		List<Object> params = new ArrayList<Object>();
		if (Strings.isNotEmpty(examId)) {
			hql.append("and examBatch.id=?");
			params.add(examId);
		}
		if (Strings.isNotEmpty(gradeId)) {
			hql.append("and student.adminclass.grade.id=?");
			params.add(gradeId);
		}
		hql.append("order by testNumber");

		return examScoreDao.findListByHql(hql.toString(), params.toArray());
	}

	public List<List<Object>> getDataList(ExamBatch examBatch, Grade grade, String courseId){

		List<List<Object>> rows = new ArrayList<List<Object>>();

		if (examBatch !=null) {

			List<ExamScore> scores = findPageList(examBatch.getId(),grade.getId());
			Map<String, Double> map = new HashMap<String, Double>();
			for (ExamScore score : scores) {
				//分项成绩
				double d = score.getScoreItem(courseId);
				//过滤缺考（999）的学生
				if(d==999){
					continue;
				}

				putMapValue(map, score.getStudent().getAdminclass().getName(),"考试人数");//考试人数
				putMapValue(map, score.getStudent().getAdminclass().getName(),"总分数",d);
				if (d==100) {
					putMapValue(map, score.getStudent().getAdminclass().getName(),"100");
				}
				if (100>=d&&d>=90) {
					putMapValue(map, score.getStudent().getAdminclass().getName(),"90-100");
				}
				if (d>=80&&d<=100) {
					putMapValue(map, score.getStudent().getAdminclass().getName(),"80-100");
				}
				if (d>=70&&d<=100) {
					putMapValue(map, score.getStudent().getAdminclass().getName(),"70-100");
				}
				if (d>=0&&d<70) {
					putMapValue(map, score.getStudent().getAdminclass().getName(),"70以下");
				}
				if (d>=90&&d<=100) {
					putMapValue(map, score.getStudent().getAdminclass().getName(),"优秀");
				}
				if (d>=80&&d<=100) {
					putMapValue(map, score.getStudent().getAdminclass().getName(),"良好");
				}
				if (d>=70&&d<=100) {
					putMapValue(map, score.getStudent().getAdminclass().getName(),"及格");
				}
			}

			List<Adminclass> adminclasses = adminclassDao.findListByHql("from Adminclass where grade.id = ? order by order desc",new Object[]{grade.getId()});

			double allStd = 0.0;
			double allStd_100 = 0.0;
			double allStd_90_100 = 0.0;
			double allStd_80_100 = 0.0;
			double allStd_70_100 = 0.0;
			double allStd_70 = 0.0;
			double allStd_yx = 0.0;
			double allStd_lh = 0.0;
			double allStd_jg = 0.0;
			double allStd_zz = 0.0;

			for (Adminclass adminclass : adminclasses) {
				String gradeName = adminclass.getName();
				List<Object> row = new ArrayList<Object>();
				row.add(gradeName);
				row.add(getMapValue_Int(map,gradeName+"考试人数"));
				allStd += getMapValue_Int(map,gradeName+"考试人数");
				row.add(getMapValue_Int(map,gradeName+"100"));
				allStd_100 += getMapValue_Int(map,gradeName+"100");
				row.add(getMapValue_Int(map,gradeName+"90-100"));
				allStd_90_100 += getMapValue_Int(map,gradeName+"90-100");
				row.add(getMapValue_Int(map,gradeName+"80-100"));
				allStd_80_100 += getMapValue_Int(map,gradeName+"80-100");
				row.add(getMapValue_Int(map,gradeName+"70-100"));
				allStd_70_100 += getMapValue_Int(map,gradeName+"70-100");
				row.add(getMapValue_Int(map,gradeName+"70以下"));
				allStd_70 += getMapValue_Int(map,gradeName+"70以下");
				//优秀率
				Double stdAll = map.get(gradeName+"考试人数");
				row.add(MyUtil.getPercent(map.get(gradeName+"优秀"), stdAll))	;
				allStd_yx += getMapValue_Int(map,gradeName+"优秀");
				row.add(MyUtil.getPercent(map.get(gradeName+"良好"), stdAll))	;
				allStd_lh += getMapValue_Int(map,gradeName+"良好");
				row.add(MyUtil.getPercent(map.get(gradeName+"及格"), stdAll))	;
				allStd_jg += getMapValue_Int(map,gradeName+"及格");
				row.add(MyUtil.getPercent(map.get(gradeName+"总分数"), stdAll,false))	;
				allStd_zz += getMapValue_Int(map,gradeName+"总分数");
				rows.add(row);
			}
			Collections.reverse(rows);
			List<Object> allRow = new ArrayList<Object>();
			allRow.add("全年级");
			allRow.add((int)allStd);
			allRow.add((int)allStd_100);
			allRow.add((int)allStd_90_100);
			allRow.add((int)allStd_80_100);
			allRow.add((int)allStd_70_100);
			allRow.add((int)allStd_70);
			allRow.add(MyUtil.getPercent(allStd_yx, allStd));
			allRow.add(MyUtil.getPercent(allStd_lh, allStd));
			allRow.add(MyUtil.getPercent(allStd_jg, allStd));
			allRow.add(MyUtil.getPercent(allStd_zz, allStd,false));
			rows.add(allRow);
		}
		return rows;

	}


	private void putMapValue(Map<String, Double> map, String scoreName,String typeName) {
		putMapValue(map, scoreName, typeName, 1);
	}
	private void putMapValue(Map<String, Double> map, String scoreName,String typeName,double d) {
		if (map.containsKey(scoreName+typeName)) {
			map.put(scoreName+typeName, map.get(scoreName+typeName)+d);
		}else{
			map.put(scoreName+typeName, d);
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
