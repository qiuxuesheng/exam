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
			<h3 class="no-margin-bottom"> 成绩查询 </h3>
		</div>
	</header>
	<!-- Breadcrumb-->
	<div class="container-fluid">
		<div class="card">
			<div class="card-header d-flex align-items-center" >
				<form id="searchForm" class="form-inline" role="form" action="score!scoreList.action">
					<div class="form-group">
						<label for="examBatch">选择考次</label>
						<select class="form-control" id="examBatch" name="examBatchId">
							<option value="">...</option>
							<s:iterator value="examBatches">
								<option value="${id}" <s:if test="#examBatchId==id">selected="selected"</s:if>>${name }</option>
							</s:iterator>
						</select>
					</div>
					<div class="form-group">
						<label for="gradeId">年级</label>
						<select class="form-control" id="gradeId" name="gradeId" onchange="gradeChange()">
							<option value="">...</option>
							<s:iterator value="grades">
								<option value="${id}" <s:if test="#gradeId==id">selected="selected"</s:if>>${name }</option>
							</s:iterator>
						</select>
					</div>

					<div class="form-group">
						<label for="adminclassId">班级</label>
						<select class="form-control" id="adminclassId"  name="adminclassId">
							<option value="">...</option>
							<s:iterator value="adminclasses" var="adminclass">
								<option value="${adminclass.id}"<s:if test="#adminclassId==#adminclass.id">selected="selected"</s:if>>${adminclass.name}</option>
							</s:iterator>
						</select>
					</div>
					<div class="form-group">
						<label for="stdName">姓名</label>
						<input name="stdName" value="${stdName}" id="stdName" style="width: 100px">
					</div>
					<button style="margin-left: 20px" type="submit" class="btn btn-default">查询</button>
				</form>
			</div>
			<div class="card-body">
				<div class="table-responsive">
					<table class="table table-hover table-sm">
						<thead>
						<tr>
							<th >序号</th>
							<th>准考号</th>
							<th>年级</th>
							<th>班级</th>
							<th>姓名</th>
							<s:iterator value="courses" var="course" status="st">
								<th >${course.name}</th>
							</s:iterator>
						</tr>
						</thead>
						<tbody>
						<s:iterator value="scores" var="score" status="st">
							<tr>
								<td>${st.index+1 }</td>
								<td>${score.testNumber }</td>
								<td>${score.student.adminclass.grade.name}</td>
								<td>${score.student.adminclass.name}</td>
								<td>${score.student.name}</td>
								<s:iterator value="courses" var="course" status="st">
									<th>${score.getScore(course.id)}</th>
								</s:iterator>
							</tr>
						</s:iterator>

						</tbody>
					</table>
				</div>
			</div>
			<center>
				<form action="${pageLimit.getUrl()}" id="pageForm" method="get" target="mainFrame">
					<input type="hidden" id="curr_page" name="page" value="${pageLimit.page}">
					<input type="hidden" id="mxa_page" name="page" value="${pageLimit.getMaxPage()}">
					<s:iterator value="pageLimit.parameterMap">
						<input type="hidden" name="${key}" value="${value[0]}">
					</s:iterator>
				</form>
				第&nbsp;${pageLimit.page}&nbsp;页,共&nbsp;${pageLimit.getMaxPage()}&nbsp;页&nbsp;&nbsp;&nbsp;
				    <a href="javascript:go_page(1)"> <font size="2" color="blue">首页</font></a>
				    <a href="javascript:previous_page()"><font size="2" color="red">上一页</font></a>
				    <a href="javascript:next_page()"><font size="2" color="red">下一页</font></a> 
				    <a href="javascript:go_page(${pageLimit.getMaxPage()})"><font size="2" color="blue">末页</font></a>
			</center>
			<script>
				function go_page(go_page) {
					go_page = parseInt(go_page);
					var curr_page = parseInt($("#curr_page").val());
					if (curr_page != go_page){
						$("#curr_page").val(go_page);
						$("#pageForm").submit()
					}
				}
				function previous_page(){
					var curr_page = parseInt($("#curr_page").val());
					if (curr_page > 1){
						go_page(curr_page-1);
					}

				}
				function next_page(){
					var curr_page = parseInt($("#curr_page").val());
					var mxa_page = parseInt($("#mxa_page").val());
					if (curr_page < mxa_page){
						go_page(curr_page+1);
					}
				}
			</script>
		</div>
	</div>
</div>

</body>

</html>

<script>

	function gradeChange(id) {

		var gradeId = $("#gradeId").val()
		var adminclassSel = $("#adminclassId");
		adminclassSel.empty();
		$.ajax({
			url : "adminclass!adminclassListJson.action",
			type : "post",
			data : {
				"gradeId" : gradeId
			},
			dataType : "json",
			success : function(result) {
				adminclassSel.append("<option value=''>...</option>");
				for (var i = 0; i < result.length; i++) {
					adminclassSel.append("<option value='"+result[i].id+"'>"+result[i].name+"</option>");
				}

			},
			error : function() {
				alert("未知错误，请联系仇学胜")
			}
		});
	}



</script>
