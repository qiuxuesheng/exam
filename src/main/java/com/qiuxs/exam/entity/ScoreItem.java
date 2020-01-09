package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.NumberIdEntity;

import javax.persistence.*;

/**
 * 各科目得分
 */

@Entity
@Table(name = "t_score_item")
public class ScoreItem extends NumberIdEntity<Integer> {

    private static final long serialVersionUID = 6147339267990516992L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exam_score_id",columnDefinition = "int(11)")
    protected ExamScore examScore;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id",columnDefinition = "int(11)")
    protected Course course;

    /*缺考*/
    @Column(name="miss")
    protected boolean miss = false;

    @Column(name="score")
    protected double score;

    public ExamScore getExamScore() {
        return examScore;
    }

    public void setExamScore(ExamScore examScore) {
        this.examScore = examScore;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isMiss() {
        return miss;
    }

    public void setMiss(boolean miss) {
        this.miss = miss;
    }
}
