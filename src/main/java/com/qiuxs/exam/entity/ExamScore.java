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


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="std_id",columnDefinition = "int(11)")
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="exam_batch_id",columnDefinition = "int(11)")
	private ExamBatch examBatch;

	/*座位号*/
	@Column(name="test_number")
	private String testNumber;


	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "exam_score_id")
	protected List<ScoreItem> scoreItems = new ArrayList<ScoreItem>();

	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	public ExamBatch getExamBatch() {
		return examBatch;
	}
	public void setExamBatch(ExamBatch examBatch) {
		this.examBatch = examBatch;
	}
	
	public String getTestNumber() {
		return testNumber;
	}
	public void setTestNumber(String testNumber) {
		this.testNumber = testNumber;
	}

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
