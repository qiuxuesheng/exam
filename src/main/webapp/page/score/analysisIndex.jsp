<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/page/basePage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>首页</title>
</head>
<body>

<div class="content-inner" style="width: 100%">
	<!-- Page Header-->
	<header class="page-header">
		<div class="container-fluid">
			<h3 class="no-margin-bottom"> 成绩分析 </h3>
		</div>
	</header>
	<!-- Breadcrumb-->
	<div class="container-fluid">
		<div class="card">

			<div class="card-body">

				<div class="table-responsive">
					<form id="exportWordForm" class="form-inline" role="form" action="score!analysisIndex.action">
						<div class="form-group">
							<label for="examBatch">选择考次</label>
							<select class="form-control" id="examBatch" name="examId" required>
								<option value="">...</option>
								<s:iterator value="examBatchs">
									<option value="${id}" <s:if test="#examId==id">selected="selected"</s:if>>${name }</option>
								</s:iterator>
							</select>
						</div>
						<div class="form-group">
							<label for="grade">年级</label>
							<select class="form-control" id="grade" name="gradeId" required>
								<option value="">...</option>
								<s:iterator value="grades">
									<option value="${id}" <s:if test="#gradeId==id">selected="selected"</s:if>>${name }</option>
								</s:iterator>
							</select>
						</div>

						<div class="form-group">
							<label for="courseId">选择科目</label>
							<select class="form-control" id="courseId"  name="courseId" required>
								<option value="">...</option>
								<s:iterator value="courses" var="course">
									<option value="${course.id}"<s:if test="#courseId==#course.id">selected="selected"</s:if>>${course.name}</option>
								</s:iterator>
							</select>
						</div>
						<div class="form-group">
							<label for="model">选择模板</label>
							<select class="form-control" id="model"  name="modelId" required>
								<option value="">...</option>
								<s:iterator value="models">
									<option value="${id}"<s:if test="#modelId==id">selected="selected"</s:if>>${name}</option>
								</s:iterator>
							</select>
						</div>
						<button style="margin-left: 20px" type="submit" class="btn btn-default">统计</button>
						<input style="margin-left: 20px" type="button" class="btn btn-default" onclick="exportWord()" value="下载">
					</form>
					<h3 style="margin-top: 20px" class="text-center">${examName }${courseName }成绩分析</h3>
					<table class="table table-bordered">
						<thead>
						<tr>
							<th>班级</th>
							<th>考试人数</th>
							<s:iterator value="#selectedModel.levels">
								<th>${name}</th>
							</s:iterator>

							<th>平均分</th>
						</tr>
						</thead>
						<tbody>
						<s:iterator value="#rows" var="row">
							<tr>
								<s:iterator value="row" var="data">
									<td>${data }</td>
								</s:iterator>
							</tr>
						</s:iterator>

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

</body>

</html>

<script>
	function exportWord() {
		var form = $("#exportWordForm");
		var validity = form[0].checkValidity();
		if (!validity){
			form.addClass("was-validated");
			return;
		}
		var params = form.serialize();

		window.location.href = "score!exportWord.action?"+params;

		/*$.ajax({
			url : "model!exportWord.action",
			type : "post",
			data : formData,
			dataType : "json",
			success : function(result) {
				alert(result.msg)
				if (result.status == 'success'){

				}
			},
			error : function() {
				alert("未知错误，请联系仇学胜")
			}
		});*/
	}

</script>
