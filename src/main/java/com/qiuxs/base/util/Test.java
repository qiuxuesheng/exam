package com.qiuxs.base.util;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

public class Test {
	public static void main(String[] args) {
		
		
		
		
		
		//1.在内存中创建一个excel文件
        @SuppressWarnings("resource")
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        
        CellStyle style = hssfWorkbook.createCellStyle();
	    style.setBorderBottom(CellStyle.BORDER_THIN);
	    style.setBottomBorderColor(IndexedColors.GREEN.getIndex());
	    style.setBorderLeft(CellStyle.BORDER_THIN);
	    style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
	    style.setBorderRight(CellStyle.BORDER_THIN);
	    style.setRightBorderColor(IndexedColors.GREEN.getIndex());
	    style.setBorderTop(CellStyle.BORDER_THIN);
	    style.setTopBorderColor(IndexedColors.GREEN.getIndex());
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
	        
        //2.创建工作簿
        HSSFSheet sheet = hssfWorkbook.createSheet();
        
         
        
        //3.创建标题行
        HSSFRow titlerRow = sheet.createRow(0);
        HSSFCell cell = titlerRow.createCell(0);
        cell.setCellValue("我是标题");
        
        //样式
        HSSFCellStyle cellStyle= hssfWorkbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        //居中
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中
        //字体
        HSSFFont font = hssfWorkbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 16);//设置字体大小

        HSSFFont font2 = hssfWorkbook.createFont();
        font2.setFontName("仿宋_GB2312");
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font2.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font2);//选择需要用到的字体格式

        cell.setCellStyle(cellStyle);
        
        for (int i = 0; i < 10; i++) {
        	sheet.setColumnWidth(i, 3766); 
		}
        
        for (int i = 1; i < 4; i++) {
        	HSSFRow row = sheet.createRow(i);
        	for (int j = 0; j < 10; j++) {
        		HSSFCell hssfCell = row.createCell(j);
        		if (j==0) {
        			hssfCell.setCellValue("班级");
				}else if (j==3) {
        			hssfCell.setCellValue("姓名");
				}else if (j==6) {
        			hssfCell.setCellValue("年龄");
				}else if (j==8) {
        			hssfCell.setCellValue("成绩");
				}else if(j==1){
					hssfCell.setCellValue("test");
				}else if(j==4){
					hssfCell.setCellValue("test");
				}else if(j==7){
					hssfCell.setCellValue("test");
				}else if(j==9){
					hssfCell.setCellValue("test");
				}
//        		hssfCell.setCellStyle(style);
			}
		}
        
        
        //4.遍历数据,创建数据行
        for (int i = 4; i < 20; i++) {
        	HSSFRow row = sheet.createRow(i);
        	for (int j = 0; j < 10; j++) {
        		HSSFCell hssfCell = row.createCell(j);
        		hssfCell.setCellValue("666");
        		hssfCell.setCellStyle(style);
			}
		}
        
        HSSFRow rowB =  sheet.createRow(20);
        for (int j = 0; j < 10; j++) {
    		HSSFCell hssfCell = rowB.createCell(j);
    		if (j==0) {
    			hssfCell.setCellValue("班级");
			}else if (j==3) {
    			hssfCell.setCellValue("姓名");
			}else if (j==6) {
    			hssfCell.setCellValue("年龄");
			}else if (j==8) {
    			hssfCell.setCellValue("成绩");
			}else if(j==1){
				hssfCell.setCellValue("test");
			}else if(j==4){
				hssfCell.setCellValue("test");
			}else if(j==7){
				hssfCell.setCellValue("test");
			}else if(j==9){
				hssfCell.setCellValue("test");
			}
//    		hssfCell.setCellStyle(style);
		}
        
        //合并单元格
        CellRangeAddress titleRegion = new CellRangeAddress(0, 0, 0, 9);
        sheet.addMergedRegion(titleRegion);
        for (int i = 1; i < 4; i++) {
        	CellRangeAddress region = new CellRangeAddress(i, i, 1, 2);
        	CellRangeAddress region2 = new CellRangeAddress(i, i, 4, 5);
        	sheet.addMergedRegion(region);// 到rowTo行columnTo的区域 
        	sheet.addMergedRegion(region2);// 到rowTo行columnTo的区域 
			
		}
        
        
        //5.创建文件名
        String fileName = "d://test.xls";
        
        
        try {
			FileOutputStream outputStream = new FileOutputStream(fileName);
			hssfWorkbook.write(outputStream);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
