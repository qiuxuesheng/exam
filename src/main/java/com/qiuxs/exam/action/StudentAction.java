package com.qiuxs.exam.action;

import com.qiuxs.base.action.BaseAction;
import com.qiuxs.base.util.MyReadExcel;
import com.qiuxs.exam.entity.Adminclass;
import com.qiuxs.exam.entity.Grade;
import com.qiuxs.exam.entity.Student;
import com.qiuxs.exam.service.StudentService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;


@Controller
@Scope("prototype")
public class StudentAction extends BaseAction {

    private Student student;
    private File file;
    private String fileFileName;
    private String fileContentType;

    @Resource(name = "studentService")
    protected StudentService studentService;


    public String studentList(){

        List<Student> students = baseService.getAll(Student.class);
        put("students",students);
        return "studentList";
    }

    public String studentEdit(){
        List<Grade> grades = baseService.getAll(Grade.class);
        List<Adminclass> adminclasses = baseService.getAll(Adminclass.class);
        Student student = baseService.get(Student.class,getInt("id"));
        put("grades",grades);
        put("adminclasses",adminclasses);
        put("student",student);
        return "studentForm";
    }

    public void studentSave(){

        try {
            baseService.saveOrUpdate(student);
            writeSuccese("保存成功");
        } catch (Exception e) {
            writeFail(e.getMessage());
        }

    }
    public void studentRemove(){
        try {
            baseService.remove(Student.class,getInt("id"));
            writeSuccese("删除成功");
        } catch (Exception e) {
            writeFail(e.getMessage());
        }

    }

    public String studentUplaodSave(){
        //获取扩展名
        try {
            String ext = fileFileName.substring(fileFileName.lastIndexOf(".")+1);

            if(!"xls".equals(ext)&&!"xlsx".equals(ext)){//使用xls方式读取
                put("state", "上传失败");
                put("msg", "文件格式不正确（xls或xlsx）");
                return "adminclassUplaodForm";
            }

            List<List<String >> datas  = MyReadExcel.readExcel(file, fileFileName) ;
            int count = studentService.uplaodStudent(datas,getInt("gradeId"));
            put("gradeId",getInt("gradeId"));
            put("state", "导入成功");
            put("msg", "导入 "+count+" 个学生");
        } catch (Exception e) {
            put("state", "导入失败");
            put("msg", e.getMessage());
            e.printStackTrace();
        }
        List<Grade> grades = baseService.getAll(Grade.class);
        put("grades",grades);
        return "studentUplaodForm";
    }

    public String studentUplaodForm(){
        List<Grade> grades = baseService.getAll(Grade.class);
        put("grades",grades);
        return "studentUplaodForm";
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
}
