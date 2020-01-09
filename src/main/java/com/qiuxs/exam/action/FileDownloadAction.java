package com.qiuxs.exam.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class FileDownloadAction extends ActionSupport{
	private static final long serialVersionUID = 3308647252884800895L;
	//�������
	private InputStream inputStream;//ָ��InputStream������
	private String filename;//����һ���ļ�������
	private String path;//�ļ�·��

	//
	public String download() throws Exception{
		String filePath=ServletActionContext.getServletContext().getRealPath(path);
		inputStream=new FileInputStream(filePath);
		
		return SUCCESS;
	}



	//set get
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getFilename() {
		String temp=null;
		try {
			temp = new String(this.filename.getBytes(),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}



}
