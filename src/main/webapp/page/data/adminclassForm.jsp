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
            <h3 class="no-margin-bottom">数据维护 / 班级维护 / 编辑</h3>
        </div>
    </header>
    <!-- Breadcrumb-->
    <div class="container-fluid">
        <div class="card">
            <div class="card-body">

                <div class="table-responsive">
                    <form style="width: 95%" action="adminclass!adminclassSave.action" id="entityForm" method="post">
                        <div class="form-group row">
                            <label for="grade" class="col-sm-2 col-form-label">年级</label>
                            <div class="col-sm-10">
                                <select name="adminclass.grade.id" class="form-control" id="grade" required>
                                    <option value="">...</option>
                                    <s:iterator value="grades" var="level" status="st">
                                        <option <s:if test="%{adminclass.grade.id == #grade.id}">selected="selected"</s:if> value="${level.id}">${level.name}</option>
                                    </s:iterator>

                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="name" class="col-sm-2 col-form-label">班级名称</label>
                            <div class="col-sm-10">
                                <input type="text" name="adminclass.name" value="${adminclass.name}" class="form-control" id="name" placeholder="" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="code" class="col-sm-2 col-form-label">代码</label>
                            <div class="col-sm-10">
                                <input type="text" name="adminclass.code" value="${adminclass.code}" class="form-control" id="code" placeholder="" required>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-10">
                                <input type="hidden" name="adminclass.id" value="${adminclass.id}">
                                    <button type="button" class="btn btn-primary" onclick="saveEntity()">保存</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>

<script>

    function saveEntity() {
        var form = $("#entityForm");
        var validity = form[0].checkValidity();
        if (!validity){
            form.addClass("was-validated");
            return;
        }
        var formData = form.serialize();
        $.ajax({
            url : "adminclass!adminclassSave.action",
            type : "post",
            data : formData,
            dataType : "json",
            success : function(result) {
                alert(result.msg)
                if (result.status == 'success'){
                    window.location.href = "adminclass!adminclassList.action";
                }
            },
            error : function() {
                alert("未知错误，请联系仇学胜")
            }
        });
    }



</script>
