<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head lang="en">
    <base href="<%=basePath%>">

    <meta charset="UTF-8">
    <title>超市账单管理系统</title>
    
     <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
    <link rel="stylesheet" href="css/public.css"/>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>超市账单管理系统</h1>

    <div class="publicHeaderR">
        <p><span>下午好！</span><span style="color: #fff21b">${userSession.userName }</span> , 欢迎你！</p>
        <a href="logout.do">退出</a>
    </div>
</header>
<!--时间-->
<section class="publicTime">
    <span id="time">2015年1月1日 11:11  星期一</span>
    <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
</section>
<!--主体内容-->
<section class="publicMian">
    <div class="left">
        <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
        <nav>
            <ul class="list">
                <li ><a href="billList.html">账单管理</a></li>
                <li><a href="providerList.html">供应商管理</a></li>
                <li><a href="jsp/userlist.jsp">用户管理</a></li>
                <li><a href="jsp/pwdmodify.jsp">密码修改</a></li>
                <li><a href="logout.do">退出系统</a></li>
            </ul>
        </nav>
    </div>
    <div class="right">
        <img class="wColck" src="images/clock.jpg" alt=""/>
        <div class="wFont">
            <h2>${userSession.userName }</h2>
            <p>欢迎来到超市账单管理系统!</p>
        </div>
    </div>
</section>
<footer class="footer">
    版权归北大青鸟
</footer>
<script src="js/time.js"></script>
</body>
</html>