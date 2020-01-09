package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.BaseCode;
import com.qiuxs.base.entity.pojo.NumberIdEntity;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 成绩阶段
 */
@Entity
@Table(name = "T_Score_Level")
public class ScoreLevel extends NumberIdEntity<Integer> {

    private static final long serialVersionUID = -3283840810609347513L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id",columnDefinition = "int(11)")
    protected WordModel model;

    @Column(name = "name",nullable = false,length = 32)
    @Size(max = 32,message = "阶段名最多32字")
    protected String name;

    @Column(name = "max",nullable = false)
    protected double max;

    @Column(name = "min",nullable = false)
    protected double min;

    @Column(name = "percent")
    protected boolean percent = false;

    @Column(name = "sort",nullable = false)
    @Digits(integer = 3,fraction = 0,message = "阶段排序必须是数字(最大三位数)")
    protected int sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }


    public boolean conform(double score){
        return score>= min && score <= max;
    }

}
