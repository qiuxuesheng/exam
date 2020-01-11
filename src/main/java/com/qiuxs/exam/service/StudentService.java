package com.qiuxs.exam.service;

import com.qiuxs.base.service.impl.BaseServiceImpl;
import com.qiuxs.exam.entity.Adminclass;
import com.qiuxs.exam.entity.Grade;
import com.qiuxs.exam.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("studentService")
@Transactional
public class StudentService extends BaseServiceImpl{


    public int uplaodStudent(List<List<String>> datas, Integer gradeId) {

        int success = 0;

        Grade grade = entityDao.get(Grade.class,gradeId);
        if (grade==null) {
            throw new RuntimeException("该年级不存在！");
        }

        int classIndex = -1;

        int nameIndex = -1;

        List<String> rowList = datas.get(0);
        for (int i = 0; i < rowList.size(); i++) {
            if ("班级".equals(rowList.get(i).trim())){
                classIndex = i;
                continue;
            }
            if ("姓名".equals(rowList.get(i).trim())){
                nameIndex = i;
                continue;
            }
        }
        if (classIndex == -1){
            throw new RuntimeException("第一列没有找到[班级]字段");
        }
        if (nameIndex == -1){
            throw new RuntimeException("第一列没有找到[姓名]字段");
        }

        //该年级下的学生信息
        List<Student> existList = (List<Student>) entityDao.search("from Student where adminclass.grade.id = ?",new Object[]{gradeId});
        List<String> exists = new ArrayList<String>();
        for (Student student : existList) {
            exists.add(student.getName()+"-"+student.getAdminclass().getName());
        }
        //该年级下的班级信息
        List<Adminclass> adminclasses = (List<Adminclass>) entityDao.search("from Adminclass where grade.id = ?",new Object[]{gradeId});
        Map<String,Adminclass> adminclassMap = new HashMap<String, Adminclass>();
        for (Adminclass adminclass : adminclasses) {
            adminclassMap.put(adminclass.getName(),adminclass);
        }

        //新建
        for (int i = 1; i < datas.size(); i++) {
            String name = datas.get(i).get(nameIndex);
            String adminclassName = datas.get(i).get(classIndex);
            String key = name + "-" + adminclassName;
            if (!exists.contains(key)){
                Student student = new Student();
                Adminclass adminclass = adminclassMap.get(adminclassName);
                if (adminclass == null ){
                    throw new RuntimeException("第 "+i+" 行，班级["+adminclassName+"]不存在");
                }
                student.setAdminclass(adminclass);
                student.setName(name);
                entityDao.saveOrUpdate(student);
                exists.add(key);
                success ++;
            }
        }
        return success;
    }


}
