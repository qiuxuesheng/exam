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
                <button type="button" class="btn btn-primary" onclick="adminclassUplaodForm()">上传</button>
            </div>
            <div class="card-body">

                <div class="table-responsive">
                    <table class="table table-hover" style="text-align: center">
                        <thead>
                        <tr>
                            <th style="width: 10%">序号</th>
                            <th style="width: 20%">年级名称</th>
                            <th style="width: 20%">班级名称</th>
                            <th style="width: 20%">排序</th>
                            <th style="width: 20%">操作</th>

                        </tr>
                        </thead>
                        <tbody>

                        <s:iterator value="adminclasses" var="adminclass" status="st">
                            <tr>
                                <td>${st.index+1 }</td>
                                <td>${adminclass.grade.name }</td>
                                <td>${adminclass.name }</td>
                                <td>${adminclass.order }</td>
                                <td>
                                    <div class="btn-group btn-group-sm" role="group" aria-label="Basic example">
                                        <button type="button" class="btn" onclick="editAdminclass('${adminclass.id}')">修改</button>
                                        <button type="button" class="btn" onclick="removeEntity('${adminclass.id}')">删除</button>
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
