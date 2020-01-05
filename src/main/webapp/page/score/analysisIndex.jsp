<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/page/basePage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>首页</title>
<style>
.fakeimg {
	height: 200px;
	background: #aaa;
}
</style>
<script type="text/javascript">
	function exportWord(){
		var subject = $("#subject").val();
		var examId = $("#examBatch").val();
		location.href = "score!exportWord.action?pair.subject="+subject+"&pair.examId="+examId;
	}
</script>
</head>
<body>
	<div class="container" id="index">
		<form class="form-inline" role="form" action="score!analysisIndex.action">
			<div class="form-group">
				<label for="name">选择考次</label> <select class="form-control" id="examBatch"
					name="pair.examId">
					<s:iterator value="exams">
						<option value="${id }" <s:if test="pair.examId==id">selected="selected"</s:if>>${name }</option>
					</s:iterator>
				</select>
			</div>
			<div class="form-group">
				<label for="name">选择科目</label> <select class="form-control" id="subject"
					name="pair.subject">
					<option value="1" <s:if test="pair.subject==1">selected="selected"</s:if>>语文</option>
					<option value="2"<s:if test="pair.subject==2">selected="selected"</s:if>>数学</option>
					<option value="3"<s:if test="pair.subject==3">selected="selected"</s:if>>英语</option>
					<option value="4"<s:if test="pair.subject==4">selected="selected"</s:if>>物理</option>
					<option value="5" <s:if test="pair.subject==5">selected="selected"</s:if></select>化学</option>
					<option value="6"<s:if test="pair.subject==6">selected="selected"</s:if>>政治</option>
					<option value="7"<s:if test="pair.subject==7">selected="selected"</s:if>>历史</option>
				</select>
			</div>
			<button type="submit" class="btn btn-default">统计</button>
		</form>
		<input type="button" onclick="exportWord()" value="下载">
		<h3 class="text-center">${pair.examName }${pair.subjectName }成绩分析</h3>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th></th>
					<th>考试人数</th>
					<th>100分人数</th>
					<th>90-100</th>
					<th>80-100</th>
					<th>70-100</th>
					<th>70以下</th>
					<th>优秀率</th>
					<th>良好率</th>
					<th>及格率</th>
					<th>平均分</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="rows" var="row">
					<tr>
						<s:iterator value="row" var="data">
							<td>${data }</td>
						</s:iterator>
					</tr>
				</s:iterator>

			</tbody>
		</table>
	</div>


</body>
</html>