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
            <h3 class="no-margin-bottom">数据维护 / 账单统计</h3>
        </div>
    </header>
    <!-- Breadcrumb-->
    <div class="container-fluid">
        <div class="card">
            <div class="card-body">

                <div class="table-responsive">
                    <form style="width: 95%" action="bill!exportExcel.action" id="entityForm" method="post">

                        <div class="form-group row">
                            <label for="name" class="col-sm-2 col-form-label">账单名称</label>
                            <div class="col-sm-10">
                                <input type="text" name="billName" class="form-control" id="name" required>
                            </div>
                        </div>
                        <div class="card-body" style="text-align: center">

                            <table>
                                <thead>
                                <tr>
                                    <td style="width: 15%">姓名</td>
                                    <td style="width: 15%">金额</td>
                                    <td style="width: 15%">支付种类</td>
                                    <td style="width: 15%">药品种类</td>
                                    <td style="width: 15%">医生</td   >
                                    <td style="width: 15%">备注</td>
                                    <td style="width: 10%">操作</td>
                                </tr>
                                </thead>
                                <tbody id="levelList">
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="7">
                                        <button class="btn btn-add" type="button" onclick="addTr()">增加</button>
                                    </td>
                                </tr>
                                </tfoot>

                            </table>
                        </div>

                        <div class="card-body" style="text-align: center">
                                <input type="hidden" name="model.id" value="${model.id}">
                                <button type="button" type="button" class="btn btn-primary" onclick="saveEntity()">导出Excel</button>
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
            '   <input type="text" onkeyup="verifyNumber(this)" name="money_'+index+'" value="" class="form-control" required/>' +
            '</td>' +
            '<td>' +
            '   <select name="payType_'+index+'" class="form-control" required>' +
            '       <option value="">...</option>' +
            '       <option value="0" >医保</option>' +
            '       <option value="1">现金</option>' +
            '       <option value="2">支付宝</option>' +
            '   </select>' +
            '</td>' +
            '<td>' +
            '   <select name="drugType_'+index+'" class="form-control" required>' +
            '       <option value="">...</option>' +
            '       <option value="0" >中医</option>' +
            '       <option value="1">西药</option>' +
            '       <option value="2">推拿</option>' +
            '       <option value="3">针灸</option>' +
            '       <option value="4">理疗</option>' +
            '       <option value="5">小儿推拿</option>' +
            '   </select>' +
            '</td>' +
            '<td>' +
            '   <select name="docType_'+index+'" class="form-control" required>' +
            '       <option value="">...</option>' +
            '       <option value="0" >凡</option>' +
            '       <option value="1">顾</option>' +
            '       <option value="2">马</option>' +
            '       <option value="3">汪</option>' +
            '       <option value="4">零售</option>' +
            '   </select>' +
            '</td>' +
            '<td>' +
            '   <input type="text" name="remark_'+index+'" value="" class="form-control"/>' +
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
        form.submit();

    }

    function verifyNumber(input) {
        var reg = new RegExp("^(-?\\d+)(\\.\\d+)?$");
        if (!reg.test($(input).val())) {
            $(input).val("")
            alert("请输入正确的数字.")
        }
    }

</script>
