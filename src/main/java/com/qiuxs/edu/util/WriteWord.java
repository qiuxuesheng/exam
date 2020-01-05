package com.qiuxs.edu.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

public class WriteWord {

	public static void main(String[] args) {
		XWPFDocument doc;
		try {

			String[] arr = {"1-","2-","3","4","5","6","7","8","9","10"};

			List<List<Object>> rows = new ArrayList<List<Object>>();

			for (int i = 0; i < 20; i++) {
				List<Object> row = new ArrayList<Object>();
				for (int j = 0; j < 10; j++) {
					row.add(j);
				}
				rows.add(row);
			}

			doc = WriteWord.getDoc("考试", arr, rows,"在本章中，您将学习如何创建一个段落以及如何使用Java将其添加到文档中。 段落是Word文件中页面的一部分。完成本章后，您将能够创建一个段落并对其执行读取操作。");

			String path = "D:\\test.doc";  
			OutputStream os = new FileOutputStream(path);  
			doc.write(os);  
			if(os!=null){  
				try{  
					os.close();  
					System.out.println("文件已输出！");  
				}  
				catch(IOException e){  
					e.printStackTrace();  
				}  
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static XWPFDocument getDoc(String title,String[] columns ,List<List<Object>> values,String desc) throws Exception {  
		XWPFDocument doc = new XWPFDocument();  
		XWPFParagraph para;  
		XWPFRun run;  
		//添加标题
		para = doc.createParagraph();  
		para.setAlignment(ParagraphAlignment.CENTER);//设置居中对齐  
		run = para.createRun();  
		run.setFontFamily("仿宋");  
		run.setFontSize(20);  
		run.setText(title); 
		//添加空格
		doc.createParagraph();  

		//添加表格  
		XWPFTable table  = doc.createTable(values.size()+1,columns.length);  
		table.setCellMargins(3, 5, 3, 5);  
		//        table.addNewCol();//添加新列  
		//        table.createRow();//添加新行  
		XWPFTableRow row;  
		XWPFTableCell cell;  
		CTTcPr cellPr;

		//表头
		row = table.getRow(0);  
		row.setHeight(400);
		for(int i=0;i<columns.length;i++){               
			cell = row.getCell(i);  
			cellPr = cell.getCTTc().addNewTcPr();  
			cellPr.addNewTcW().setW(BigInteger.valueOf(3000));  
			para = cell.getParagraphs().get(0);  
			para.setAlignment(ParagraphAlignment.CENTER);  
			run = para.createRun();             
			run.setFontFamily("仿宋");  
			run.setFontSize(12);  
			run.setBold(true);  
			run.setText(columns[i]);  
		}
		for(int j=0;j<values.size();j++){  
			row = table.getRow(j+1);  
			row.setHeight(400);  
			for(int i=0;i<columns.length;i++){               
				cell = row.getCell(i);  
				cellPr = cell.getCTTc().addNewTcPr();  
				cellPr.addNewTcW().setW(BigInteger.valueOf(3000));  
				para = cell.getParagraphs().get(0);  
				para.setAlignment(ParagraphAlignment.CENTER);  
				run = para.createRun();             
				run.setFontFamily("仿宋");  
				run.setFontSize(11);  
				String s = "";
				try {
					s = values.get(j).get(i).toString();
				} catch (Exception e) {
				}
				run.setText(s);  
			}    
		}  
		//添加备注
		doc.createParagraph();
		para = doc.createParagraph();  
		para.setAlignment(ParagraphAlignment.CENTER);//设置居中对齐  
		run = para.createRun();  
		run.setFontFamily("仿宋");  
		run.setFontSize(11);  
		run.setText(desc); 

		return doc;  
	}  

}
