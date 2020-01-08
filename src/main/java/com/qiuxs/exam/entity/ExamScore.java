package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.NumberIdEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/*考试成绩*/

@Entity
@Table(name="t_exam_score")
public class ExamScore extends NumberIdEntity<Integer> {

	private static final long serialVersionUID = 5093476455024646521L;


	private Student student;
	
	private ExamBatch examBatch;

	/*座位号*/
	private String testNumber;

	protected List<ScoreItem> scoreItems = new ArrayList<ScoreItem>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="std_id")
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="exam_batch_id")
	public ExamBatch getExamBatch() {
		return examBatch;
	}
	public void setExamBatch(ExamBatch examBatch) {
		this.examBatch = examBatch;
	}
	
	@Column(name="test_number")
	public String getTestNumber() {
		return testNumber;
	}
	public void setTestNumber(String testNumber) {
		this.testNumber = testNumber;
	}


	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "exam_Score_id")
	public List<ScoreItem> getScoreItems() {
		return scoreItems;
	}

	public void setScoreItems(List<ScoreItem> scoreItems) {
		this.scoreItems = scoreItems;
	}


	public ScoreItem getScoreItem(Integer courseId){
		if (scoreItems.size()==0) return null;
		for (ScoreItem scoreItem : scoreItems) {
			if (scoreItem.getCourse().getId().equals(courseId)){
				return scoreItem;
			}
		}
		return null;
	}

	public double getScore(Integer courseId){
		if (scoreItems.size()==0) return 0;
		for (ScoreItem scoreItem : scoreItems) {
			if (scoreItem.getCourse().getId().equals(courseId)){
				return scoreItem.getScore();
			}
		}
		return 0;
	}

}
