package com.qiuxs.exam.action;

import com.qiuxs.base.action.BaseAction;
import com.qiuxs.base.util.MyReadExcel;
import com.qiuxs.base.util.Strings;
import com.qiuxs.exam.entity.*;
import com.qiuxs.exam.service.ScoreService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@Scope("prototype")
public class ScoreAction extends BaseAction {

	private static final long serialVersionUID = -6144980421933975780L;

	//===================属性======================//
	private File file;
	private String fileFileName;
	private String fileContentType;
	private List<ExamScore> scores;
	private List<Grade> grades;
	private List<ExamBatch> examBatchs;
	private List<Course> courses;
	private List<List<Object>> rows = new ArrayList<List<Object>>();
	//================== 注入 ==========================//
	@Resource(name="scoreService")
	private ScoreService scoreService;


	//======================= URL ==============================
	public String updaloadPage(){

		examBatchs = baseService.getAll(ExamBatch.class);
		courses = baseService.getAll(Course.class);
		grades = baseService.getAll(Grade.class);

		return "updaloadPage";

	}

	public String uplaodScore(){
		examBatchs = baseService.getAll(ExamBatch.class);
		courses = baseService.getAll(Course.class);
		grades = baseService.getAll(Grade.class);
		//获取扩展名
		String ext = fileFileName.substring(fileFileName.lastIndexOf(".")+1);

		Integer examId = getInt("examId");
		Integer gradeId = getInt("gradeId");

		List<String> courseNames = Arrays.asList(Strings.split(getString("course"),","));

		if(!"xls".equals(ext)&&!"xlsx".equals(ext)){//使用xls方式读取
			put("state", "导入失败");
			put("msg", "请上传正确格式的Excel文件");
			return "updaloadPage";
		}

		if (courseNames.size() == 0){
			put("state", "导入失败");
			put("msg", "请选择需要录入成绩的课程");
			return "updaloadPage";
		}

		try {

			List<List<String >> datas  = MyReadExcel.readExcel(file, fileFileName) ;
			int count = scoreService.uploadScore(datas, examId ,gradeId,courseNames);
			put("state", "导入成功");
			put("msg", "导入成绩条数："+count);
		} catch (Exception e) {
			put("state", "上传失败");
			put("msg", e.getMessage());
			e.printStackTrace();
		}

		return "updaloadPage";
	}

	public String analysisIndex(){

		List<ExamBatch> examBatchs = baseService.getAll(ExamBatch.class);
		List<Course> courses = baseService.getAll(Course.class);
		List<Grade> grades = baseService.getAll(Grade.class);
		List<WordModel> models = baseService.getAll(WordModel.class);
		List<List<Object>> rows = new ArrayList<List<Object>>();
		Integer courseId = getInt("courseId");
		Integer examId = getInt("examId");
		Integer gradeId = getInt("gradeId");
		Integer modelId = getInt("modelId");

		if(courseId!=null){
			ExamBatch examBatch = baseService.get(ExamBatch.class,examId);
            Course course = baseService.get(Course.class,courseId);
            Grade grade = baseService.get(Grade.class,gradeId);
            WordModel selectedModel = baseService.get(WordModel.class,modelId);
			rows = scoreService.getDataList(examBatch,grade,courseId,modelId);
			put("examName", examBatch.getName());
			put("courseName", course.getName());
			put("selectedModel",selectedModel);
			put("courseId",courseId);
			put("examId",examId);
			put("gradeId",gradeId);
			put("modelId",modelId);

		}

		put("examBatchs",examBatchs);
		put("courses",courses);
		put("grades",grades);
		put("models",models);
		put("rows",rows);

		return "analysisIndex";
	}


	public void exportWord(){
		Integer examId = getInt("examId");
		Integer courseId = getInt("courseId");
		Integer gradeId = getInt("gradeId");
		Integer modelId = getInt("modelId");
		if(examId==null||courseId==null||gradeId==null||modelId==null){
			return ;
		}
		ExamBatch examBatch = baseService.get(ExamBatch.class,examId);
		Course course = baseService.get(Course.class,courseId);
        Grade grade = baseService.get(Grade.class,gradeId);
        WordModel model = baseService.get(WordModel.class,modelId);
		rows = scoreService.getDataList(examBatch, grade,courseId,modelId);
		List<String> titles = new ArrayList<String>();
		titles.add("班级");
		titles.add("考试人数");
		for (ScoreLevel level : model.getLevels()) {
			titles.add(level.getName());
		}
		titles.add("平均分");

		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		//2.创建工作簿
		HSSFSheet sheet = hssfWorkbook.createSheet();

		int rowIndex = 1;
		//3.创建标题行
		String titleValue = examBatch.getName()+course.getName()+"成绩分析";
		Map<Integer, String> params = new HashMap<Integer, String>();
		params.put(1, titleValue);
		createRow(sheet, rowIndex++, titles.size(), params, getTitleStyle(hssfWorkbook));

		params = new HashMap<Integer, String>();
		for (int i=0; i< titles.size() ; i++) {
			params.put(i+1, titles.get(i));
		}
		createRow(sheet, rowIndex++, titles.size(), params, getCellStyle(hssfWorkbook));
		//4.遍历数据,创建数据行
		for (List<Object> row : rows) {
			params = new HashMap<Integer, String>();
			int i = 1;
			for (Object object : row) {
				params.put(i++, object.toString());
			}
			createRow(sheet, rowIndex++, titles.size(), params, getCellStyle(hssfWorkbook));
		}
		//总结
		params = new HashMap<Integer, String>();
		params.put(1, "没参加考试人数不计本次成绩分析。");
		createRow(sheet, rowIndex++, titles.size(), params, getStyle(hssfWorkbook, 12));
		createRow(sheet, rowIndex++, titles.size(), new HashMap<Integer, String>(), getStyle(hssfWorkbook, 12));
		//列宽
		for (int i = 0; i < titles.size(); i++) {
			sheet.setColumnWidth(i, 3766);
		}

		//合并单元格
		CellRangeAddress titleRegion = new CellRangeAddress(0, 0, 0, titles.size()-1);
		sheet.addMergedRegion(titleRegion);
		titleRegion = new CellRangeAddress(rowIndex-3, rowIndex-2, 0, titles.size()-1);
		sheet.addMergedRegion(titleRegion);



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
	private HSSFRow createRow(HSSFSheet sheet,int rowIndex,int cellCount,Map<Integer,String> params,CellStyle style){
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


	private CellStyle getStyle(HSSFWorkbook hssfWorkbook,int fontSize) {
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
	private CellStyle getTitleStyle(HSSFWorkbook hssfWorkbook) {
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
	private HSSFCellStyle getCellStyle(HSSFWorkbook hssfWorkbook) {
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

	public int getMapValue_Int(Map<String, Double> map ,String key){

		int result = 0;
		if (map!=null) {
			try {
				double d = map.get(key);
				result = (int)d;
			} catch (Exception e) {
			}
		}
		return result;
	}





	public String scoreList(){

		scores = scoreService.findPageList();
		courses = baseService.getAll(Course.class);

		return "scoreList";
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



	public List<ExamScore> getScores() {
		return scores;
	}



	public void setScores(List<ExamScore> scores) {
		this.scores = scores;
	}


	public List<ExamBatch> getExamBatchs() {
		return examBatchs;
	}

	public void setExamBatchs(List<ExamBatch> examBatchs) {
		this.examBatchs = examBatchs;
	}

	public List<List<Object>> getRows() {
		return rows;
	}

	public void setRows(List<List<Object>> rows) {
		this.rows = rows;
	}


	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
}
