package com.qiuxs.exam.entity;

import com.qiuxs.base.entity.pojo.BaseCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出word模板
 */
@Entity()
@Table(name = "T_Word_Model")
public class WordModel extends BaseCode<Integer> {

    private static final long serialVersionUID = -1884310945952403917L;


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "model_id")
    protected List<ScoreLevel> levels = new ArrayList<ScoreLevel>();

    public List<ScoreLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<ScoreLevel> levels) {
        this.levels = levels;
    }
}
