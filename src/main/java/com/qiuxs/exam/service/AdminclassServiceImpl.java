package com.qiuxs.exam.service;

import com.qiuxs.base.service.impl.BaseServiceImpl;
import com.qiuxs.exam.action.AdminclassAction;
import com.qiuxs.exam.entity.Adminclass;
import com.qiuxs.exam.entity.Grade;
import com.qiuxs.exam.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("adminclassService")
@Transactional
public class AdminclassServiceImpl extends BaseServiceImpl implements AdminclassService{

    public int uplaodAdminclass(List<List<String>> datas, Integer gradeId) {

        int success = 0;

        try {
            Grade grade = entityDao.get(Grade.class,gradeId);
            if (grade==null) {
                throw new RuntimeException("该年级不存在！");
            }

            int index = -1;
            List<String> rowList = datas.get(0);
            for (int i = 0; i < rowList.size(); i++) {
                if ("班级".equals(rowList.get(i).trim())){
                    index = i;
                    break;
                }
            }
            if (index == -1){
                throw new RuntimeException("第一列没有找到[班级]字段");
            }

            //该年级下的班级信息
            List<Adminclass> existList = (List<Adminclass>) entityDao.search("from Adminclass where grade.id = ?",new Object[]{gradeId});

            List<String> existNames = new ArrayList<String>();
            for (Adminclass adminclass : existList) {
                existNames.add(adminclass.getName());
            }

            //新建
            for (int i = 1; i < datas.size(); i++) {
                String name = datas.get(i).get(index);
                if (!existNames.contains(name)){
                    Adminclass adminclass = new Adminclass();
                    adminclass.setGrade(grade);
                    adminclass.setName(name);
                    adminclass.setCode(name);
                    entityDao.saveOrUpdate(adminclass);
                    success ++;
                    existNames.add(name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }



}
