package com.qiuxs.edu.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出word模板
 */
@Entity()
@Table(name = "T_Word_Model")
public class WordModel implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable=false)
    protected Integer id;

    @Column(name = "name")
    protected String name;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "model_id")
    protected List<ScoreLevel> levels = new ArrayList<ScoreLevel>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScoreLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<ScoreLevel> levels) {
        this.levels = levels;
    }
}
