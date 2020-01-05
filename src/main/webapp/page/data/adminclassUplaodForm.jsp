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
            <h3 class="no-margin-bottom">班级信息上传</h3>
        </div>
    </header>
    <!-- Breadcrumb-->
    <div class="container-fluid">
        <div class="card">
            <div class="card-body">

                <div class="table-responsive">
                    <form action="pub!adminclassUplaodSave.action" method="post" enctype="multipart/form-data" style="width: 95%">
                        <div class="form-group row">
                            <label for="grade" class="col-sm-2 col-form-label">所属年级</label>
                            <div class="col-sm-10">

                                <select name="pair.gradeId" class="form-control" id="grade" required>
                                    <option value="">...</option>
                                    <s:iterator value="grades" var="grade">
                                        <option value="${grade.id }" <s:if test="pair.gradeId==id">selected="selected"</s:if> >${grade.name }</option>
                                    </s:iterator>
                                </select>

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
