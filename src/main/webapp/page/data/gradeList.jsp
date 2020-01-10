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
			<h3 class="no-margin-bottom">数据维护 / 年级维护</h3>
		</div>
	</header>
	<!-- Breadcrumb-->
	<div class="container-fluid">
		<div class="card">
			<div class="card-header d-flex align-items-center" >
				<button type="button" class="btn btn-primary" onclick="editGrade()">新建</button>
			</div>
			<div class="card-body">

				<div class="table-responsive">
					<table class="table" style="text-align: center">
						<thead>
						<tr>
							<th style="width: 10%">序号</th>
							<th style="width: 50%">年级名称</th>
							<th style="width: 20%">代码</th>
							<th style="width: 20%">操作</th>

						</tr>
						</thead>
						<tbody>

						<s:iterator value="grades" var="grade" status="st">
							<tr>
								<td>${st.index+1 }</td>
								<td>${grade.name }</td>
								<td>${grade.code }</td>
								<td>
									<div class="btn-group btn-group-sm" role="group" aria-label="Basic example">
										<button type="button" class="btn" onclick="editGrade('${grade.id}')">修改</button>
										<button type="button" class="btn" onclick="removeEntity('${grade.id}')">删除</button>
									</div>
								</td>
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
	function editGrade(id) {

		window.location.href = "grade!gradeEdit.action?id="+id;
	}

	function removeEntity(id) {

		if(!confirm("确定删除？")){
			return;
		}
		$.ajax({
			url : "grade!gradeRemove.action",
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
