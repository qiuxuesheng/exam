package com.qiuxs.edu.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 成绩阶段
 */
@Entity
@Table(name = "T_Score_Level")
public class ScoreLevel implements Serializable {

    @Id
    @Column(name = "id")
    protected String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id",columnDefinition = "varchar(64)",nullable = false)
    protected WordModel model;

    @Column(name = "name",nullable = false)
    protected String name;

    @Column(name = "max",nullable = false)
    protected double max;

    @Column(name = "min",nullable = false)
    protected double min;

    @Column(name = "percent")
    protected boolean percent = false;

    @Column(name = "sort",nullable = false)
    protected int sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WordModel getModel() {
        return model;
    }

    public void setModel(WordModel model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
