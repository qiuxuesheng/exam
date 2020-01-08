<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/page/basePage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>首页</title>

    <style>
        * {
            margin: 0;
        }
        table {
            width: 80%;
            margin: 0 auto;
            border: 1px solid #ddd;
            border-collapse: collapse;
            text-align: center;
            margin-top: 50px;
        }
        td {
            height: 40px;
            border: 1px solid #ddd;
        }
        input {
            border: 1px solid #ddd;
            outline: none;
            padding: 6px;
            border-radius: 4px;
        }
        .btn {
            border: none;
            outline: none;
            cursor: pointer;
            padding: 6px 8px;
            color: #fff;
            border-radius: 4px;
        }
        .del_change {
            background: #FF6428;
        }
        .btn-add {
            background: green;
            width: 200px;
        }
    </style>
</head>
<body>

<div class="content-inner" style="width: 100%">
    <!-- Page Header-->
    <header class="page-header">
        <div class="container-fluid">
            <h3 class="no-margin-bottom">数据维护 / 学生维护 / 编辑</h3>
        </div>
    </header>
    <!-- Breadcrumb-->
    <div class="container-fluid">
        <div class="card">
            <div class="card-body">

                <div class="table-responsive">
                    <form style="width: 95%" action="pub!modelSave.action" id="entityForm" method="post">

                        <div class="form-group row">
                            <label for="name" class="col-sm-2 col-form-label">模板名称</label>
                            <div class="col-sm-10">
                                    <input type="text" name="model.name" value="${model.name}" class="form-control" id="name" required>
                            </div>
                        </div>
                        <div class="card-body" style="text-align: center">

                            <table>
                                <thead>
                                <tr>
                                    <td colspan="6">
                                        <button class="btn btn-add" type="button" onclick="addTr()">增加</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 30%">阶段名称</td>
                                    <td style="width: 20%">是否百分比</td>
                                    <td style="width: 15%">最大值</td>
                                    <td style="width: 15%">最小值</td>
                                    <td style="width: 10%">排序</td   >
                                    <td style="width: 10%">操作</td>
                                </tr>
                                </thead>

                                <tbody>

                                </tbody>
                                <tfoot id="levelList">

                                </tfoot>

                            </table>

                        </div>

                        <div class="form-group row">
                            <div class="col-sm-10">
                                <input type="hidden" name="model.id" value="${model.id}">
                                <button type="button" type="button" class="btn btn-primary" onclick="saveEntity()">保存</button>
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


    //增加
    var index = 0;
    function addTr() {
        var str =
            '<tr>' +
            '<td>' +
            '   <input type="text" name="name_'+index+'" value="" class="form-control" required/>' +
            '   <input type="hidden" name="index" value="'+index+'"/>' +
            '</td>' +
            '<td>' +
            '   <input type="radio" name="percent_'+index+'" value="1" checked/>是' +
            '   <input type="radio" name="percent_'+index+'" value="0" />否' +
            '</td>' +
            '<td>' +
            '   <input type="text" name="max_'+index+'" value="" class="form-control" required/>' +
            '</td>' +
            '<td>' +
            '   <input type="text" name="min_'+index+'" value="" class="form-control" required/>' +
            '</td>' +
            '<td>' +
            '   <input type="text" name="code_'+index+'" value="" class="form-control" required/>' +
            '</td>' +
            '<td>' +
            '   <button class="btn del_change" type="button" >删除</button>' +
            '</td>' +
            '</tr>';
        index++;
        $("#levelList").append(str)
    }

    $(function() {
        //删除
        $(document).on("click", ".del_change", function() {
            $(this).parents("tr").remove()
        })
    })


    function saveEntity() {
        var form = $("#entityForm");
        var validity = form[0].checkValidity();
        if (!validity){
            form.addClass("was-validated");
            return;
        }
        var formData = form.serialize();
        $.ajax({
            url : "pub!modelSave.action",
            type : "post",
            data : formData,
            dataType : "json",
            success : function(result) {
                alert(result.msg)
                if (result.status == 'success'){
                    window.location.href = "pub!modelList.action";
                }
            },
            error : function() {
                alert("未知错误，请联系仇学胜")
            }
        });
    }

</script>
