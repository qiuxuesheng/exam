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
			<h3 class="no-margin-bottom">成绩上传</h3>
		</div>
	</header>
	<!-- Breadcrumb-->
	<div class="container-fluid">
		<div class="card">
			<div class="card-body">

				<div class="table-responsive">
					<form action="score!uplaodScore.action" method="post" enctype="multipart/form-data" style="width: 95%">
						<div class="form-group row">
							<label for="examBatch" class="col-sm-2 col-form-label">考试批次</label>
							<div class="col-sm-10">

								<select name="pair.examId" class="form-control" id="examBatch" required>
									<s:iterator value="examBatchs" var="examBatch">
										<option value="${examBatch.id }" <s:if test="pair.examId==id">selected="selected"</s:if> >${examBatch.name }</option>
									</s:iterator>
								</select>

							</div>
						</div>

						<div class="form-group row">
							<label for="grade" class="col-sm-2 col-form-label">年级</label>
							<div class="col-sm-10">
								<select name="pair.gradeId" class="form-control" id="grade" required>
									<option value="">...</option>
									<s:iterator value="grades" var="grade" status="st">
										<option <s:if test="%{pair.gradeId == #grade.id}">selected="selected"</s:if> value="${grade.id}">${grade.name}</option>
									</s:iterator>

								</select>
							</div>
						</div>

						<div class="form-group row">
							<label for="grade" class="col-sm-2 col-form-label">录入课程</label>
							<div class="col-sm-10">
								<s:iterator value="courses" var="course" status="st">
									<div class="form-check form-check-inline">
										<input name="pair.course" class="form-check-input" type="checkbox" id="${course.id}" value="${course.name}">
										<label class="form-check-label" for="${course.id}">${course.name}</label>
									</div>
								</s:iterator>
							</div>
						</div>

						<div class="form-group row">
							<label for="file" class="col-sm-2 col-form-label">文件</label>
							<div class="col-sm-10">

								<input type="file" name="file" value="上传文件" id="file" required/>

							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-10">
								<input type="hidden" name="adminclass.id" value="${adminclass.id}">
								<input id="submit_form" type="submit" class="btn btn-success save" value="保存" />
							</div>
						</div>
					</form>
				</div>

			</div>
			<div class="card-body">
				${pair.state}<br/>${pair.msg}
			</div>
		</div>
	</div>
</div>

</body>

</html>

<script>

</script>
