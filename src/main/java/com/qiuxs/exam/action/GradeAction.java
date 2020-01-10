package com.qiuxs.exam.action;

import com.qiuxs.base.action.BaseAction;
import com.qiuxs.exam.entity.Grade;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Scope("prototype")
public class GradeAction extends BaseAction {

    private Grade grade;

    public String gradeList(){

        List<Grade> grades = baseService.getAll(Grade.class);
        put("grades",grades);

        return "gradeList";
    }

    public String gradeEdit(){
        Grade grade = getEntity(Grade.class,getInt("id"));
        put("grade",grade);
        return "gradeForm";
    }

    public void gradeSave(){

        try {
            baseService.saveOrUpdate(grade);
            writeSuccese("保存成功");
        } catch (Exception e) {
            writeFail(e.getMessage());
        }

    }
    public void gradeRemove(){

        try {

            baseService.remove(getGrade().getClass(),getInt("id"));
            writeSuccese("删除成功");

        } catch (Exception e) {
            writeFail(e.getMessage());
        }

    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
