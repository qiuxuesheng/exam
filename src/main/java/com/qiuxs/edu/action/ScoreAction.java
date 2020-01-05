package com.qiuxs.edu.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.qiuxs.edu.entity.Course;
import com.qiuxs.edu.entity.Grade;
import com.qiuxs.edu.util.Strings;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.qiuxs.edu.entity.ExamBatch;
import com.qiuxs.edu.entity.ExamScore;
import com.qiuxs.edu.service.IPublicService;
import com.qiuxs.edu.service.IScoreService;
import com.qiuxs.edu.util.MyReadExcel;

@Controller
@Scope("prototype")
public class ScoreAction extends BaseAction{

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
	private IScoreService scoreService;

	@Autowired
	private IPublicService publicService;


	//======================= URL ==============================
	public String updaloadPage(){

		examBatchs = publicService.findAll(ExamBatch.class);
		courses = publicService.findAll(Course.class);
		grades = publicService.findAll(Grade.class);

		return "updaloadPage";

	}

	public String uplaodScore(){
		examBatchs = publicService.findAll(ExamBatch.class);
		courses = publicService.findAll(Course.class);
		grades = publicService.findAll(Grade.class);
		//获取扩展名
		String ext = fileFileName.substring(fileFileName.lastIndexOf(".")+1);

		String examId = getPairValue("examId");
		String gradeId = getPairValue("gradeId");

		List<String> courseNames = Arrays.asList(Strings.split(getPairValue("course"),","));

		if(!"xls".equals(ext)&&!"xlsx".equals(ext)){//使用xls方式读取
			putPairValue("state", "导入失败");
			putPairValue("msg", "请上传正确格式的Excel文件");
			return "updaloadPage";
		}

		if (courseNames.size() == 0){
			putPairValue("state", "导入失败");
			putPairValue("msg", "请选择需要录入成绩的课程");
			return "updaloadPage";
		}

		try {

			List<List<String >> datas  = MyReadExcel.readExcel(file, fileFileName, -1, 0, 0, 0, 0) ;
			int count = scoreService.uploadScore(datas, examId ,gradeId,courseNames);
			putPairValue("state", "导入成功");
			putPairValue("msg", "导入成绩条数："+count);
		} catch (Exception e) {
			putPairValue("state", "上传失败");
			putPairValue("msg", e.getMessage());
			e.printStackTrace();
		}

		return "updaloadPage";
	}

	public String analysisIndex(){

		examBatchs = publicService.findAll(ExamBatch.class);

		String[] arr = {"","语文","数学","英语","物理","化学","政治","历史"};

		int type = getPairInt("subject");
		String examId = getPairValue("examId");
		if(examId!=null&&!"".equals(examId)){

			ExamBatch exam = publicService.findById(ExamBatch.class,examId);
			rows = scoreService.getDataList(exam,type);
			putPairValue("examName", exam.getName());
			putPairValue("subjectName", arr[type]);

		}
		return "analysisIndex";
	}


	public void exportWord(){
		String[] arr = {"","语文","数学","英语","物理","化学","政治","历史"};
		String examId = getPairValue("examId");
		int type = getPairInt("subject");
		if(examId==null||"".equals(examId)){
			return ;
		}
		ExamBatch exam = publicService.findById(ExamBatch.class,examId);
		rows = scoreService.getDataList(exam, type);
		String[] titleArr = {"班级","考试人数","100分人数","90-100","80-100","70-100","70以下","优秀率","良好率","及格率","平均分"};


		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		//2.创建工作簿
		HSSFSheet sheet = hssfWorkbook.createSheet();

		int rowIndex = 1;
		//3.创建标题行
		String titleValue = exam.getName()+arr[type]+"成绩分析";
		Map<Integer, String> params = new HashMap<Integer, String>();
		params.put(1, titleValue);
		createRow(sheet, rowIndex++, titleArr.length, params, getTitleStyle(hssfWorkbook));

		params = new HashMap<Integer, String>();
		for (int i=0; i< titleArr.length ; i++) {
			params.put(i+1, titleArr[i]);
		}
		createRow(sheet, rowIndex++, titleArr.length, params, getCellStyle(hssfWorkbook));
		//4.遍历数据,创建数据行
		for (List<Object> row : rows) {
			params = new HashMap<Integer, String>();
			int i = 1;
			for (Object object : row) {
				params.put(i++, object.toString());
			}
			createRow(sheet, rowIndex++, titleArr.length, params, getCellStyle(hssfWorkbook));
		}
		//总结
		params = new HashMap<Integer, String>();
		List<Object> list = rows.get(rows.size()-1);
		params.put(1, "全年级90-100人数"+list.get(3)+"人，80-100人数"+list.get(4)
				+"人，70-100人数"+list.get(5)+"人，70分以下人数"+list.get(6)
				+"人。总平均分："+list.get(10)+"分（没参加考试人数不计本次成绩分析）。");
		createRow(sheet, rowIndex++, titleArr.length, params, getStyle(hssfWorkbook, 12));
		createRow(sheet, rowIndex++, titleArr.length, new HashMap<Integer, String>(), getStyle(hssfWorkbook, 12));
		//列宽
		for (int i = 0; i < titleArr.length; i++) {
			sheet.setColumnWidth(i, 3766);
		}

		//合并单元格
		CellRangeAddress titleRegion = new CellRangeAddress(0, 0, 0, titleArr.length-1);
		sheet.addMergedRegion(titleRegion);
		titleRegion = new CellRangeAddress(rowIndex-3, rowIndex-2, 0, titleArr.length-1);
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
		courses = publicService.findAll(Course.class);

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
