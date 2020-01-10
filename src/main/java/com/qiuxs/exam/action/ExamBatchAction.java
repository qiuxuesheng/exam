package com.qiuxs.exam.action;

import com.qiuxs.base.action.BaseAction;
import com.qiuxs.exam.entity.ExamBatch;
import com.qiuxs.exam.entity.Grade;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Scope("prototype")
public class ExamBatchAction extends BaseAction {

    private ExamBatch examBatch;

    public String examBatchList(){

        List<ExamBatch> examBatchs = baseService.getAll(ExamBatch.class);
        put("examBatchs",examBatchs);
        return "examBatchList";
    }

    public String examBatchEdit(){


        List<Grade> grades = baseService.getAll(Grade.class);
        put("grades",grades);

        examBatch = getEntity(ExamBatch.class,getInt("id"));

        return "examBatchForm";
    }

    public void examBatchSave(){

        try {
            baseService.saveOrUpdate(examBatch);
            writeSuccese("保存成功");
        } catch (Exception e) {
            writeFail(e.getMessage());
        }

    }
    public void examBatchRemove(){

        try {
            baseService.remove(ExamBatch.class,getInt("id"));
            writeSuccese("删除成功");
        } catch (Exception e) {
            writeFail(e.getMessage());
        }
    }

    public ExamBatch getExamBatch() {
        return examBatch;
    }

    public void setExamBatch(ExamBatch examBatch) {
        this.examBatch = examBatch;
    }
}
