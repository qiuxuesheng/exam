package com.qiuxs.edu.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 导出word模板
 */
public class WordModel implements Serializable {


    protected String id;

    protected String name;

    protected List<ScoreLevel> levels = new ArrayList<ScoreLevel>();



}
