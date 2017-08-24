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
    <link rel="stylesheet" href="css/public.css"/>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<!--头部-->
    <header class="publicHeader">
        <h1>超市账单管理系统</h1>
        <div class="publicHeaderR">
            <p><span>下午好！</span><span style="color: #fff21b"> ${userSession.userName }</span> , 欢迎你！</p>
            <a href="exit.html">退出</a>
        </div>
    </header>
<!--时间-->
    <section class="publicTime">
        <span id="time">2015年1月1日 11:11  星期一</span>
        <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
    </section>
<!--主体内容-->
    <section class="publicMian ">
        <div class="left">
            <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
            <nav>
                <ul class="list">                    
                    <li ><a href="jsp/billlist.jsp">账单管理</a></li>
                	<li><a href="jsp/providerlist.jsp">供应商管理</a></li>
                	<li><a href="jsp/userlist.jsp">用户管理</a></li>
                	<li id="active"><a href="jsp/pwdmodify.jsp">密码修改</a></li>
                	<li><a href="exit.html">退出系统</a></li>
                </ul>
            </nav>
        </div>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>密码修改页面&gt;&gt;&gt;${message }</span>
            </div>
            <div class="providerAdd">
                <form action="user.html" id="pwdModifyForm" name="pwdModifyForm" method="post">
                	<input type="hidden" name="method" value="savepwd">          
                    
                    <div class="">
                        <label for="oldPassword">旧密码：</label>
                        <input type="password" name="oldpassword" id="oldpassword" value=""/>
                        <span></span>
                    </div>
                    <div>
                        <label for="newPassword">新密码：</label>
                        <input type="password" name="newpassword" id="newpassword" value=""/>
                        <span ></span>
                    </div>
                    <div>
                        <label for="reNewPassword">确认新密码：</label>
                        <input type="password" name="rnewpassword" id="rnewpassword" value=""/>
                        <span ></span>
                    </div>                  
                    <div class="providerAddBtn">                   
                    	<input type="button" value="保存" name="save" id="save" class="input-button"/>
                	</div>
                </form>
            </div>
        </div>
    </section>
    <footer class="footer">
        版权归北大青鸟
    </footer>
<script src="js/jquery.js"></script>
<script src="js/js.js"></script>    
<script src="js/time.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/pwdmodify.js"></script>
</body>
</html>
