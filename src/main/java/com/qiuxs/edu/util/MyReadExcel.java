
package com.qiuxs.edu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;





import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel文件操作工具类，包括读、写、合并等功能
 */
public class MyReadExcel {
	
	
	
	
	

	/**
	 * 自动根据文件扩展名，调用对应的读取方法
	 */
	public static List<List<String>> readExcel(String xlsPath) throws IOException{

		//扩展名为空时，
		if (xlsPath.equals("")){
			throw new IOException("文件路径不能为空！");
		}else{
			File file = new File(xlsPath);
			if(!file.exists()){
				throw new IOException("文件不存在！");
			}
		}

		//获取扩展名
		String ext = xlsPath.substring(xlsPath.lastIndexOf(".")+1);

		try {

			if("xls".equals(ext)){				//使用xls方式读取
				return readExcel_xls(xlsPath);
			}else if("xlsx".equals(ext)){		//使用xlsx方式读取
				return readExcel_xlsx(xlsPath);
			}else{									//依次尝试xls、xlsx方式读取
				try{
					return readExcel_xls(xlsPath);
				} catch (IOException e1) {
					return readExcel_xlsx(xlsPath);
				}
			}
		} catch (IOException e) {
			throw e;
		}
	}

	
	/**
	 * 自动根据文件扩展名，调用对应的读取方法
	 */
	public static List<List<String>> readExcel(File srcFile,String fileName) throws IOException{
		return readExcel(srcFile, fileName, -1, 0, 0, 0, 0);
	}
	/**
	 * 自动根据文件扩展名，调用对应的读取方法
	 */
	public static List<List<String>> readExcel(File srcFile,String fileName,int sheetIndex,int rowStartIndex,int rowEndIndex,int cellStartIndex,int cellEndIndex) throws IOException{
		
		//扩展名为空时，
		if (fileName.equals("")){
			throw new IOException("文件名不能为空");
		}
		
		//获取扩展名
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		
		try {
			
			if("xls".equals(ext)){				//使用xls方式读取
				return readExcel_xls(srcFile, sheetIndex, rowStartIndex, rowEndIndex, cellStartIndex, cellEndIndex);
			}else if("xlsx".equals(ext)){		//使用xlsx方式读取
				return readExcel_xlsx(srcFile, sheetIndex, rowStartIndex, rowEndIndex, cellStartIndex, cellEndIndex);
			}else{									//依次尝试xls、xlsx方式读取
				try{
					return readExcel_xls(srcFile, sheetIndex, rowStartIndex, rowEndIndex, cellStartIndex, cellEndIndex);
				} catch (IOException e1) {
					try{
						return readExcel_xlsx(srcFile, sheetIndex, rowStartIndex, rowEndIndex, cellStartIndex, cellEndIndex);
					} catch (IOException e2) {
						throw e2;
					}
				}
			}
		} catch (IOException e) {
			throw e;
		}
	}
	
	

	/**
	 * 自动根据文件扩展名，调用对应的写入方法
	 * 
	 * @Title: writeExcel
	 * @Date : 2014-9-11 下午01:50:38
	 * @param rowList
	 * @param xlsPath
	 * @throws IOException
	 */
	public static void writeExcel(List<List<String>> rowList, String xlsPath) throws IOException {

		//扩展名为空时，
		if (xlsPath.equals("")){
			throw new IOException("文件路径不能为空！");
		}

		//获取扩展名
		String ext = xlsPath.substring(xlsPath.lastIndexOf(".")+1);

		try {

			if("xls".equals(ext)){				//使用xls方式写入
				writeExcel_xls(rowList,xlsPath);
			}else if("xlsx".equals(ext)){		//使用xlsx方式写入
				writeExcel_xlsx(rowList,xlsPath);
			}else{									//依次尝试xls、xlsx方式写入
				try{
					writeExcel_xls(rowList,xlsPath);
				} catch (IOException e1) {
					try{
						writeExcel_xlsx(rowList,xlsPath);
					} catch (IOException e2) {
						throw e2;
					}
				}
			}
		} catch (IOException e) {
			throw e;
		}
	}


	/**
	 * 修改Excel（97-03版，xls格式）
	 */
	public static void writeExcel_xls(List<List<String>> rowList, String dist_xlsPath) throws IOException {

		// 判断文件路径是否为空
		if (dist_xlsPath == null || dist_xlsPath.equals("")) {
			throw new IOException("文件路径不能为空");
		}


		// 判断列表是否有数据，如果没有数据，则返回
		if (rowList == null || rowList.size() == 0) {
			return;
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		wb.createSheet("Sheet1");

		// 将rowlist的内容写到Excel中
		writeExcel(wb, rowList, dist_xlsPath);
	}

	/**
	 * 写入Excel（2007版，xlsx格式）
	 * 
	 */
	public static void writeExcel_xlsx(List<List<String>> rowList, String dist_xlsPath) throws IOException {

		// 判断文件路径是否为空
		if (dist_xlsPath == null || dist_xlsPath.equals("")) {
			throw new IOException("文件路径不能为空");
		}

		// 判断列表是否有数据，如果没有数据，则返回
		if (rowList == null || rowList.size() == 0) {
			return;
		}
		// 读取文档
		XSSFWorkbook wb = new XSSFWorkbook();
		wb.createSheet("Sheet1");
		// 将rowlist的内容添加到Excel中
		writeExcel(wb, rowList, dist_xlsPath);
	}
	/**
	 * 写入Excel，若文件存在则覆盖
	 * 
	 * @Title: WriteExcel
	 * @Date : 2014-9-11 下午01:33:59
	 * @param wb
	 * @param rowList
	 * @param xlsPath
	 */
	public static void writeExcel(Workbook wb, List<List<String>> rowList, String xlsPath) {

		if (wb == null) {
			return;
		}

		Sheet sheet = wb.getSheetAt(0);// 修改第一个sheet中的值

		int rowCount = 0;//记录最新添加的行数
		for (List<String> cellList : rowList) {
			Row r = sheet.createRow( rowCount++);

			//用于设定单元格样式
			CellStyle newstyle = wb.createCellStyle();

			//循环为新行创建单元格
			for (int i = 0; i < cellList.size(); i++) {
				Cell cell = r.createCell(i);// 获取数据类型
				cell.setCellValue(cellList.get(i));// 取值到新的单元格
				cell.setCellStyle(newstyle);// 设置样式
			}
		}

		try {
			// 重新将数据写入Excel中
			FileOutputStream outputStream = new FileOutputStream(xlsPath);
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取Excel 2007版，xlsx格式
	 */
	public static List<List<String>> readExcel_xlsx(String xlsPath) throws IOException{
		return readExcel_xlsx(xlsPath,-1,0,0,0,0);
	}
	/**
	 * 读取Excel 2007版，xlsx格式
	 */
	public static List<List<String>> readExcel_xlsx(String xlsPath,int sheetIndex,int rowStartIndex,int rowEndIndex,int cellStartIndex,int cellEndIndex) throws IOException {
		// 判断文件是否存在
		File file = new File(xlsPath);
		if (!file.exists()) {
			throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
		}

		XSSFWorkbook wb = null;
		List<List<String>> rowList = new ArrayList<List<String>>();
		try {
			FileInputStream fis = new FileInputStream(file);
			// 去读Excel
			wb = new XSSFWorkbook(fis);

			// 读取Excel 2007版，xlsx格式
			rowList = readExcel(wb, sheetIndex, rowStartIndex, rowEndIndex, cellStartIndex, cellEndIndex);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowList;
	}
	/**
	 * 读取Excel 2007版，xlsx格式
	 */
	public static List<List<String>> readExcel_xlsx(File srcFile) throws IOException {
		return readExcel_xlsx(srcFile,-1,0,0,0,0);
	}
	/**
	 * 读取Excel 2007版，xlsx格式
	 */
	public static List<List<String>> readExcel_xlsx(File srcFile,int sheetIndex,int rowStartIndex,int rowEndIndex,int cellStartIndex,int cellEndIndex) throws IOException {
		// 判断文件是否存在
		if (srcFile==null) {
			throw new IOException("Excel文件不能为空");
		}
		
		XSSFWorkbook wb = null;
		List<List<String>> rowList = new ArrayList<List<String>>();
		try {
			FileInputStream fis = new FileInputStream(srcFile);
			// 去读Excel
			wb = new XSSFWorkbook(fis);
			
			// 读取Excel 2007版，xlsx格式
			rowList = readExcel(wb, sheetIndex, rowStartIndex, rowEndIndex, cellStartIndex, cellEndIndex);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowList;
	}



	/***
	 * 读取Excel(97-03版，xls格式)
	 */
	public static List<List<String>> readExcel_xls(String xlsPath) throws IOException {
		return readExcel_xls(xlsPath,-1,0,0,0,0);
	}
	/***
	 * 读取Excel(97-03版，xls格式)
	 */
	public static List<List<String>> readExcel_xls(String xlsPath,int sheetIndex,int rowStartIndex,int rowEndIndex,int cellStartIndex,int cellEndIndex) throws IOException {

		// 判断文件是否存在
		File file = new File(xlsPath);
		if (!file.exists()) {
			throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
		}

		HSSFWorkbook wb = null;// 用于Workbook级的操作，创建、删除Excel
		List<List<String>> rowList = new ArrayList<List<String>>();

		try {
			// 读取Excel
			wb = new HSSFWorkbook(new FileInputStream(file));

			// 读取Excel 97-03版，xls格式
			rowList = readExcel(wb, sheetIndex, rowStartIndex, rowEndIndex, cellStartIndex, cellEndIndex);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowList;
	}
	/***
	 * 读取Excel(97-03版，xls格式)
	 */
	public static List<List<String>> readExcel_xls(File srcFile) throws IOException {
		return readExcel_xls(srcFile,-1,0,0,0,0);
	}
	/***
	 * 读取Excel(97-03版，xls格式)
	 */
	public static List<List<String>> readExcel_xls(File srcFile,int sheetIndex,int rowStartIndex,int rowEndIndex,int cellStartIndex,int cellEndIndex) throws IOException {
		
		// 判断文件是否存在
		if (srcFile==null) {
			throw new IOException("Excel文件不存在！");
		}
		
		HSSFWorkbook wb = null;// 用于Workbook级的操作，创建、删除Excel
		List<List<String>> rowList = new ArrayList<List<String>>();
		
		try {
			// 读取Excel
			wb = new HSSFWorkbook(new FileInputStream(srcFile));
			
			// 读取Excel 97-03版，xls格式
			rowList = readExcel(wb, sheetIndex, rowStartIndex, rowEndIndex, cellStartIndex, cellEndIndex);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowList;
	}



	/**
	 * 通用读取Excel
	 * @param wb 
	 * @param sheetIndex 需要读取的sheet序列
	 * @param rowStartIndex 开始行数
	 * @param rowEndIndex 结束的行数 倒数第N行（-N）
	 * @param cellStartIndex
	 * @param cellEndIndex
	 * @return
	 */

	public static List<List<String>> readExcel(Workbook wb,int sheetIndex,int rowStartIndex,int rowEndIndex,int cellStartIndex,int cellEndIndex) {
		List<List<String>> rowList = new ArrayList<List<String>>();

		int sheetCount = 1;//需要操作的sheet数量
		int sumSheetCount = wb.getNumberOfSheets();//获取可以操作的sheet总数量
		if (sheetIndex>sumSheetCount) {

		}

		Sheet sheet = null;
		if(sheetIndex!=-1){	//只操作一个sheet
			// 获取设定操作的sheet(按索引值查)
			sheet = wb.getSheetAt(sheetIndex-1);
		}else{	//操作多个sheet
			sheetCount = wb.getNumberOfSheets();//获取可以操作的总数量
		}

		// 获取sheet数目
		for(int t=0; t<sheetCount;t++){
			// 获取设定操作的sheet
			if(sheetIndex==-1) {
				sheet =wb.getSheetAt(t);
			}

			//获取最后行号
			int lastRowNum = sheet.getLastRowNum();

			Row row = null;
			// 循环读取
			for (int i = rowStartIndex; i <= lastRowNum + rowEndIndex; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					List<String> cellList = new ArrayList<String>();
					// 获取每一单元格的值
					for (int j = cellStartIndex; j < row.getLastCellNum() + cellEndIndex; j++) {
						String value = getCellValue(row.getCell(j));
						cellList.add(value);
					}
					rowList.add(cellList);
				}
			}
		}
		return rowList;
	}

	/***
	 * 读取单元格的值
	 */
	@SuppressWarnings("deprecation")
	public static String getCellValue(Cell cell) {
		Object result = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				long longVal = Math.round(cell.getNumericCellValue());
				Double doubleVal = cell.getNumericCellValue();
				if (Double.parseDouble(longVal + ".0") == doubleVal)
					result = longVal;
				else
					result = doubleVal;
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR:
				result = cell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			default:
				break;
			}
		}
		return result.toString().trim();
	}





	/**
	 * 复制一个单元格样式到目的单元格样式
	 * 
	 * @param fromStyle
	 * @param toStyle
	 */
	@SuppressWarnings("deprecation")
	public static void copyCellStyle(CellStyle fromStyle, CellStyle toStyle) {
		toStyle.setAlignment(fromStyle.getAlignment());
		// 边框和边框颜色
		toStyle.setBorderBottom(fromStyle.getBorderBottom());
		toStyle.setBorderLeft(fromStyle.getBorderLeft());
		toStyle.setBorderRight(fromStyle.getBorderRight());
		toStyle.setBorderTop(fromStyle.getBorderTop());
		toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
		toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
		toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
		toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

		// 背景和前景
		toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
		toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

		// 数据格式
		toStyle.setDataFormat(fromStyle.getDataFormat());
		toStyle.setFillPattern(fromStyle.getFillPattern());
		// toStyle.setFont(fromStyle.getFont(null));
		toStyle.setHidden(fromStyle.getHidden());
		toStyle.setIndention(fromStyle.getIndention());// 首行缩进
		toStyle.setLocked(fromStyle.getLocked());
		toStyle.setRotation(fromStyle.getRotation());// 旋转
		toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
		toStyle.setWrapText(fromStyle.getWrapText());

	}

	/**
	 * 获取合并单元格的值
	 * @param sheet
	 * @param rowStartReadPos 开始的行数
	 */
	public static void setMergedRegion(Sheet sheet,int rowStartReadPos) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			// 获取合并单元格位置
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstRow = ca.getFirstRow();
			if (rowStartReadPos - 1 > firstRow) {// 如果第一个合并单元格格式在正式数据的上面，则跳过。
				continue;
			}
			int lastRow = ca.getLastRow();
			int mergeRows = lastRow - firstRow;// 合并的行数
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			// 根据合并的单元格位置和大小，调整所有的数据行格式，
			for (int j = lastRow + 1; j <= sheet.getLastRowNum(); j++) {
				// 设定合并单元格
				sheet.addMergedRegion(new CellRangeAddress(j, j + mergeRows, firstColumn, lastColumn));
				j = j + mergeRows;// 跳过已合并的行
			}

		}
	}

}
