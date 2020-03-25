<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
<%@include file="/page/basePage.jsp"%>
<head>
</head>
<body>
<div class="page">
    <!-- Main Navbar-->
    <header class="header">
        <nav class="navbar">
            <!-- Search Box-->
            <div class="search-box">
                <button class="dismiss"><i class="icon-close"></i></button>
                <form id="searchForm" action="#" role="search">
                    <input type="search" placeholder="What are you looking for..." class="form-control">
                </form>
            </div>
            <div class="container-fluid">
                <div class="navbar-holder d-flex align-items-center justify-content-between">
                    <!-- Navbar Header-->
                    <div class="navbar-header">
                        <!-- Navbar Brand --><a href="index.action" class="navbar-brand d-none d-sm-inline-block">
                        <div class="brand-text d-none d-lg-inline-block"><span></span><strong>成绩分析系统</strong></div>
                        <div class="brand-text d-none d-sm-inline-block d-lg-none"><strong>BD</strong></div></a>
                        <!-- Toggle Button--><a id="toggle-btn" href="#" class="menu-btn active"><span></span><span></span><span></span></a>
                    </div>
                    <!-- Navbar Menu -->
                    <ul class="nav-menu list-unstyled d-flex flex-md-row align-items-md-center">
                        <!-- Search-->
                        <li class="nav-item d-flex align-items-center"><a id="search" href="javascript:void(0)"><i class="icon-search"></i></a></li>
                        <!-- Notifications-->
                        <li class="nav-item dropdown"> <a id="notifications" rel="nofollow" data-target="#" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link"><i class="fa fa-bell-o"></i><span class="badge bg-red badge-corner"></span></a>

                        </li>
                        <!-- Messages                        -->
                        <li class="nav-item dropdown"> <a id="messages" rel="nofollow" data-target="#" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link"><i class="fa fa-envelope-o"></i><span class="badge bg-orange badge-corner"></span></a>

                        </li>
                        <!-- Languages dropdown    -->
                        <li class="nav-item dropdown"><a id="languages" rel="nofollow" data-target="#" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link language dropdown-toggle"><img src="img/flags/16/CN.png" alt="Chinese"><span class="d-none d-sm-inline-block">Chinese</span></a>

                        </li>
                        <!-- Logout    -->
                        <li class="nav-item"><a href="javascript:void(0)" class="nav-link logout"> <span class="d-none d-sm-inline">Logout</span><i class="fa fa-sign-out"></i></a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <div class="page-content d-flex align-items-stretch">
        <!-- Side Navbar -->
        <nav class="side-navbar">
            <!-- Sidebar Header-->
            <div class="sidebar-header d-flex align-items-center">
                <div class="avatar" style="width: 70px;height: 70px"><img src="img/avatar-1.jpg" alt="..." class="img-fluid rounded-circle"></div>
                <div class="title">
                    <h1 class="h4">Qiuxs</h1>
                    <p>123</p>
                </div>
            </div>
            <div id="menuDiv">
                <ul  class="list-unstyled">
                    <li ><a href="bill!list.action"  target="mainFrame"> <i class="icon-home"></i> 后台首页 </a></li>
                    <%--<li><a href="score!updaloadPage.action"  target="mainFrame"> <i class="icon-grid"></i> 成绩上传 </a></li>
                    <li><a href="score!scoreList.action"  target="mainFrame"> <i class="icon-grid"></i> 成绩查询 </a></li>
                    <li><a href="score!analysisIndex.action"  target="mainFrame"> <i class="icon-grid"></i> 成绩分析 </a></li>
                    <li><a href="#exampledropdownDropdown" aria-expanded="false" data-toggle="collapse"> <i class="icon-interface-windows"></i> 数据维护 </a>
                        <ul id="exampledropdownDropdown" class="collapse list-unstyled ">
                            <li><a href="course!courseList.action" target="mainFrame">课程维护</a></li>
                            <li><a href="grade!gradeList.action" target="mainFrame">年级维护</a></li>
                            <li><a href="adminclass!adminclassList.action" target="mainFrame">班级维护</a></li>
                            <li><a href="student!studentList.action" target="mainFrame">学生维护</a></li>
                            <li><a href="examBatch!examBatchList.action" target="mainFrame">考试批次维护</a></li>
                            <li><a href="model!modelList.action" target="mainFrame">等级线维护</a></li>
                            <li><a href="bill!list.action" target="mainFrame">金额统计</a></li>
                        </ul>
                    </li>--%>
                </ul>
            </div>
        </nav>
        <div class="content-inner" style="padding-bottom: 0px;" align="center">
            <iframe frameborder="0" src="bill!list.action" name="mainFrame" width="100%" height="100%"></iframe>
        </div>
    </div>
</div>
</body>
</html>
<script>

    $(function () {

        $("#menuDiv li").click(function () {

            var $liArr = $(".active");

            for (var i = 0; i < $liArr.length; i++) {
                $($liArr[0]).removeClass("active");
            }

            $(this).addClass("active")
        })

    });

</script>