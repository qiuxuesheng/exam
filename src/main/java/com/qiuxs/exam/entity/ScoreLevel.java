package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.BaseCode;

import javax.persistence.*;

/**
 * 成绩阶段
 */
@Entity
@Table(name = "T_Score_Level")
public class ScoreLevel extends BaseCode<Integer> {

    private static final long serialVersionUID = -3283840810609347513L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id",columnDefinition = "int(11)")
    protected WordModel model;

    @Column(name = "max",nullable = false)
    protected double max;

    @Column(name = "min",nullable = false)
    protected double min;

    @Column(name = "percent")
    protected boolean percent = false;


    public WordModel getModel() {
        return model;
    }

    public void setModel(WordModel model) {
        this.model = model;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public boolean isPercent() {
        return percent;
    }

    public void setPercent(boolean percent) {
        this.percent = percent;
    }
}
