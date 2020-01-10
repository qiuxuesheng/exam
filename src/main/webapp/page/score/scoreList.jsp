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
			<div class="card-header d-flex align-items-center" >
				<button style="margin-right: 20px" type="button" class="btn btn-primary" onclick="editAdminclass()">新建</button>
			</div>
			<div class="card-body">

				<div class="table-responsive">
					<table class="table table-hover">
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
		</div>
	</div>
</div>

</body>

</html>

<script>
	function editAdminclass(id) {

		window.location.href = "adminclass!adminclassEdit.action?id="+id;
	}
	function adminclassUplaodForm(id) {

		window.location.href = "adminclass!adminclassUplaodForm.action";
	}

	function removeEntity(id) {

		if(!confirm("确定删除？")){
			return;
		}
		$.ajax({
			url : "adminclass!adminclassRemove.action",
			type : "post",
			data : {
				"id" : id
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
