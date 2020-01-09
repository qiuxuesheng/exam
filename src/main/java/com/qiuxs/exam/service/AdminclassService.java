package com.qiuxs.exam.service;

import com.qiuxs.base.service.BaseService;

import java.util.List;

public interface AdminclassService extends BaseService {
    int uplaodAdminclass(List<List<String>> datas, Integer gradeId);
}
