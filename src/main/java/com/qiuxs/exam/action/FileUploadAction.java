package com.qiuxs.exam.action;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller("fileUpload")
@Scope("prototype")
public class FileUploadAction extends ActionSupport{

	private static final long serialVersionUID = 8329735763212201816L;
	private File file;//����һ��File ,������Ҫ��jsp�е�input��ǩ��nameһ��
	private String fileContentType;//�ϴ��ļ���mimeType����
	private String fileFileName;//�ϴ��ļ�������


	//�ļ��ϴ�
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
	//�����ϴ��ļ����ļ������ļ����ԣ�uuid+"_"+�ļ���ԭʼ����
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
