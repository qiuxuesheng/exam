package com.qiuxs.edu.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Test2 {
	public static void main(String[] args) {

		//1、获取文件输入流
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("d://test.xls");
			//2、获取Excel工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			//3、得到Excel工作表对象
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row = sheet.getRow(0);
			System.out.println(row.getCell(0).getStringCellValue());;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
