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
            <h3 class="no-margin-bottom">数据维护 / 年级维护 / 编辑</h3>
        </div>
    </header>
    <!-- Breadcrumb-->
    <div class="container-fluid">
        <div class="card">
            <div class="card-body">

                <div class="table-responsive">
                    <form id="entityForm" style="width: 95%" action="pub!gradeSave.action" method="post">
                        <div class="form-group row">
                            <label for="inputEmail3" class="col-sm-2 col-form-label">年级名称</label>
                            <div class="col-sm-10">
                                <input type="text" name="grade.name" value="${grade.name}"  class="form-control" id="inputEmail3" placeholder="" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputPassword3" class="col-sm-2 col-form-label">排序</label>
                            <div class="col-sm-10">
                                <input type="text" name="grade.order" value="${grade.order}" class="form-control" id="inputPassword3" placeholder="" required >
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <input type="hidden" name="grade.id" value="${grade.id}">
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
            url : "pub!gradeSave.action",
            type : "post",
            data : formData,
            dataType : "json",
            success : function(result) {
                console.log(result)
                alert(result.msg)
                if (result.status == 'success'){
                    window.location.href = "pub!gradeList.action";
                }
            },
            error : function() {
                alert("未知错误，请联系仇学胜")
            }
        });

    }


</script>
