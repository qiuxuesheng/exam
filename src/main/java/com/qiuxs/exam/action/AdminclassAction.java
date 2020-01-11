package com.qiuxs.exam.action;

import com.qiuxs.base.action.BaseAction;
import com.qiuxs.base.util.MyReadExcel;
import com.qiuxs.exam.entity.Adminclass;
import com.qiuxs.exam.entity.Grade;
import com.qiuxs.exam.service.AdminclassService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Controller
@Scope("prototype")
public class AdminclassAction extends BaseAction {

    private File file;
    private String fileFileName;
    private String fileContentType;
    private Adminclass adminclass;

    @Resource(name = "adminclassService")
    protected AdminclassService adminclassService;

    public String adminclassList(){

        List<Adminclass> adminclasses = baseService.getAll(Adminclass.class,"grade.code,code");
        put("adminclasses",adminclasses);
        return "adminclassList";
    }

    public String adminclassEdit(){

        List<Grade> grades = baseService.getAll(Grade.class);
        put("grades",grades);

        adminclass = getEntity(Adminclass.class,getInt("id"));

        return "adminclassForm";
    }

    public void adminclassSave(){

        try {
            baseService.saveOrUpdate(adminclass);
            writeSuccese("保存成功");
        } catch (Exception e) {
            writeFail(e.getMessage());
        }

    }
    public void adminclassRemove(){

        try {

            baseService.remove(Adminclass.class,getInt("id"));
            writeSuccese("删除成功");
        } catch (Exception e) {
            writeFail(e.getMessage());
        }

    }

    /**
     * 上传班级
     * @return
     */
    public String adminclassUplaodSave(){
        //获取扩展名
        try {
            String ext = fileFileName.substring(fileFileName.lastIndexOf(".")+1);
            if(!"xls".equals(ext)&&!"xlsx".equals(ext)){//使用xls方式读取
                put("state", "上传失败");
                put("msg", "文件格式不正确（xls或xlsx）");
                return "adminclassUplaodForm";
            }

            List<List<String >> datas  = MyReadExcel.readExcel(file, fileFileName) ;
            int count =  adminclassService.uplaodAdminclass(datas,getInt("gradeId"));
            put("gradeId",getInt("gradeId"));
            put("state", "导入成功");
            put("msg", "导入 "+count+" 个班级");
        } catch (Exception e) {
            put("state", "导入失败");
            put("msg", e.getMessage());
            e.printStackTrace();
        }
        List<Grade> grades = baseService.getAll(Grade.class);
        put("grades",grades);
        return "adminclassUplaodForm";
    }

    public String adminclassUplaodForm(){
        List<Grade> grades = baseService.getAll(Grade.class);
        put("grades",grades);
        return "adminclassUplaodForm";
    }



    public Adminclass getAdminclass() {
        return adminclass;
    }

    public void setAdminclass(Adminclass adminclass) {
        this.adminclass = adminclass;
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
