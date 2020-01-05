package com.qiuxs.edu.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller("fileAction")
@Scope("prototype")
public class FileUploadAction extends ActionSupport{

	private static final long serialVersionUID = 8329735763212201816L;
	private File file;//定义一个File ,变量名要与jsp中的input标签的name一致
	private String fileContentType;//上传文件的mimeType类型
	private String fileFileName;//上传文件的名称


	//文件上传
	public void uploadFile(){
		String savePath = "d://eduUpload/";
		File saveFile = new File(savePath);
		if(!saveFile.exists()&&!saveFile.isDirectory()){
			saveFile.mkdir();
		}
		try {
			FileUtils.copyFile(file, new File(savePath+mkFileName(fileFileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	public String mkFileName(String fileName){
		return UUID.randomUUID().toString()+"_"+fileName;
	}

	//get set
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	

}
