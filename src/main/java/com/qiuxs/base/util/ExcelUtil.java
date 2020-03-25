package com.qiuxs.base.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ExcelUtil {

    public static void exportExcel(HSSFWorkbook hssfWorkbook, String titleValue) {
        HttpServletResponse response = ServletActionContext.getResponse();

        OutputStream os =  null;

        // 取得输出流
        try {
            os = response.getOutputStream();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        response.reset();// 清空输出流
        //创建文件名
        String fileName = titleValue+".xls";
        //设置信息头
        response.setContentType(fileName);
        try {
            fileName = new String (fileName.getBytes ("utf-8"),"ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition","attachment;filename="+fileName);
        //写出文件,关闭流
        try {
            hssfWorkbook.write(os);
        } catch (IOException e) {
        }
        try {
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     *
     * @param sheet
     * @param rowIndex 从1开始
     * @param cellCount 总行数
     * @param params 从1开始
     * @param style
     * @return
     */
    public static HSSFRow createRow(HSSFSheet sheet, int rowIndex, int cellCount, Map<Integer,String> params, CellStyle style){
        HSSFRow row = sheet.createRow(rowIndex-1);
        for (int i = 0; i < cellCount; i++) {
            HSSFCell cell = row.createCell(i);
            if (params.containsKey(i+1)) {//填充内容
                cell.setCellValue(params.get(i+1));
            }
            if (style!=null) {
                cell.setCellStyle(style);
            }

        }
        return row;
    }


    public static CellStyle getStyle(HSSFWorkbook hssfWorkbook,int fontSize) {
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
        HSSFFont font2 = hssfWorkbook.createFont();
        font2.setFontName("仿宋_GB2312");
        font2.setFontHeightInPoints((short) fontSize);
        style.setFont(font2);//选择需要用到的字体格式

        return style;
    }
    public static CellStyle getTitleStyle(HSSFWorkbook hssfWorkbook) {
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
        HSSFFont font2 = hssfWorkbook.createFont();
        font2.setFontName("仿宋_GB2312");
        font2.setFontHeightInPoints((short) 16);
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        style.setFont(font2);//选择需要用到的字体格式

        return style;
    }
    public static HSSFCellStyle getCellStyle(HSSFWorkbook hssfWorkbook) {
        //样式
        HSSFCellStyle cellStyle= hssfWorkbook.createCellStyle();
        cellStyle.setBottomBorderColor(IndexedColors.GREEN.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.GREEN.getIndex());
        cellStyle.setTopBorderColor(IndexedColors.GREEN.getIndex());
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
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);

        return cellStyle;
    }

    public static void mergedRows(HSSFSheet sheet,int firstRow,int lastRow,int firstCol,int lastCol){
        CellRangeAddress titleRegion = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(titleRegion);
    }

}
