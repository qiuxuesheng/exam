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

    protected ExamScore examScore;

    protected Course course;

    /*缺考*/
    protected boolean miss = false;

    protected double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exam_score_id")
    public ExamScore getExamScore() {
        return examScore;
    }

    public void setExamScore(ExamScore examScore) {
        this.examScore = examScore;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Column(name="score")
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isMiss() {
        return miss;
    }

    @Column(name="miss")
    public void setMiss(boolean miss) {
        this.miss = miss;
    }
}
