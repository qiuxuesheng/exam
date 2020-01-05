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
			<h3 class="no-margin-bottom">数据维护 / 班级维护</h3>
		</div>
	</header>
	<!-- Breadcrumb-->
	<div class="container-fluid">
		<div class="card">

			<div class="card-body">

				<div class="table-responsive">
					<form class="form-inline" role="form" action="score!analysisIndex.action">
						<div class="form-group">
							<label for="examBatch">选择考次</label>
							<select class="form-control" id="examBatch" name="pair.examId" required>
								<option value="">...</option>
								<s:iterator value="examBatchs">
									<option value="${id}" <s:if test="pair.examId==id">selected="selected"</s:if>>${name }</option>
								</s:iterator>
							</select>
						</div>
						<div class="form-group">
							<label for="subject">选择科目</label>
							<select class="form-control" id="subject"  name="pair.subject" required>
								<option value="">...</option>
								<s:iterator value="courses" var="course">
									<option value="1"<s:if test="pair.subject==#course.id">selected="selected"</s:if>>${course.name}</option>
								</s:iterator>
							</select>
						</div>
						<button type="submit" class="btn btn-default">统计</button>
						<input type="button" onclick="exportWord()" value="下载">
					</form>
					<h3 class="text-center">${pair.examName }${pair.subjectName }成绩分析</h3>
					<table class="table table-bordered">
						<thead>
						<tr>
							<th>班级</th>
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
			</div>
		</div>
	</div>
</div>

</body>

</html>

<script>
	function editAdminclass(id) {

		window.location.href = "pub!adminclassEdit.action?pair.id="+id;
	}
	function adminclassUplaodForm(id) {

		window.location.href = "pub!adminclassUplaodForm.action";
	}

	function removeEntity(id) {

		if(!confirm("确定删除？")){
			return;
		}
		$.ajax({
			url : "pub!adminclassRemove.action",
			type : "post",
			data : {
				"pair.id" : id
			},
			dataType : "json",
			success : function(result) {
				alert(result.msg)
				window.location.reload()

			},
			error : function() {
				alert("未知错误，请联系仇学胜")
			}
		});
	}

</script>
