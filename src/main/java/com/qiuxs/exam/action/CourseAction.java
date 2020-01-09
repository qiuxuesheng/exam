package com.qiuxs.exam.action;

import com.qiuxs.base.action.BaseAction;
import com.qiuxs.exam.entity.Course;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Scope("prototype")
public class CourseAction extends BaseAction {

    private Course course;

    public String courseList(){
        List<Course> courses = baseService.getAll(Course.class);
        put("courses",courses);
        return "courseList";
    }

    public String courseEdit(){
        Course course = getEntity(Course.class,getInt("pair.id"));
        put("course",course);
        return "courseForm";
    }

    public void courseSave(){
        try {
            baseService.saveOrUpdate(course);
            writeSuccese("保存成功");
        } catch (Exception e) {
            writeFail(e.getMessage());
        }

    }
    public void courseRemove(){

        try {
            baseService.remove(Course.class,getInt("pair.id"));
            writeSuccese("删除成功");
        } catch (Exception e) {
            writeFail(e.getMessage());
        }

    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
