<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
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
                <li id="active"><a href="jsp/userlist.jsp">用户管理</a></li>
                <li><a href="jsp/pwdmodify.jsp">密码修改</a></li>
                <li><a href="exit.html">退出系统</a></li>
            </ul>
        </nav>
    </div>
    <div class="right">

        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 &gt;&gt; 用户添加页面</span>
        </div>
        <div class="providerAdd">
            <form action="user.html" id="userForm" name="userForm" method="post">
                <input type="hidden" name="method" value="add">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div class="content">
                    <label for="userId">用户编码：</label>
                    <input type="text" name="userCode" id="userCode"/>
                    <span></span>
                </div>
                <div>
                    <label for="userName">用户名称：</label>
                    <input type="text" name="userName" id="userName"/>
                    <span ></span>
                </div>
                <div>
                    <label for="userpassword">用户密码：</label>
                    <input type="password" name="userPassword" id="userPassword"/>
                    <span></span>

                </div>
                <div>
                    <label for="userRemi">确认密码：</label>
                    <input type="password" name="ruserPassword" id="ruserPassword"/>
                    <span></span>
                </div>
                <div>
                    <label >用户性别：</label>

                    <select name="gender" id="gender">
                        <option value="1" selected="selected">男</option>
                        <option value="2">女</option>
                    </select>
                    <span></span>
                </div>
                <div>
                    <label for="data">出生日期：</label>
                    <input type="text" Class="Wdate" id="birthday" name="birthday" 
                    readonly="readonly" onclick="WdatePicker();"/>
                    <span ></span>
                </div>
                <div>
                    <label for="userphone">用户电话：</label>
                    <input type="text" name="phone" id="phone"/>
                    <span ></span>
                </div>
                <div>
                    <label for="userAddress">用户地址：</label>
                    <input type="text" name="address" id="address"/>
                </div>
                <div>
                    <label >用户类别：</label>
                    <input type="radio" name="userType" value="1"/>管理员
                    <input type="radio" name="userType" value="2" checked="checked"/>经理
                    <input type="radio" name="userType" value="3"/>普通用户

                </div>
                <div class="providerAddBtn">
                    <!--<a href="#">保存</a>-->
                    <!--<a href="userList.html">返回</a>-->
                    <input type="button" value="保存" name="add" id="add" class="input-button"/>
                    <input type="button" value="返回" onclick="history.back(-1)"/>
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
<script type="text/javascript" src="js/useradd.js"></script>
<script type="text/javascript" src="calendar/WdatePicker.js"></script>
</body>
</html>
