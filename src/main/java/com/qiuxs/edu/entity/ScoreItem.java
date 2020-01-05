package com.qiuxs.edu.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 各科目得分
 */

@Entity
@Table(name = "t_score_item")
public class ScoreItem implements Serializable {

    protected String id;

    protected ExamScore examScore;

    protected Course course;

    protected double score;

    @Id
    @Column(name="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
