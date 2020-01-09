package com.qiuxs.exam.service;

import com.qiuxs.base.service.BaseService;

import java.util.List;

public interface StudentService extends BaseService {

    int uplaodStudent(List<List<String>> datas, Integer gradeId);

}
