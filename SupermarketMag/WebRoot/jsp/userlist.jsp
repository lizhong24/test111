<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head lang="en">
    <base href="<%=basePath%>">
 
    <meta charset="UTF-8">
    <title>超市账单管理系统</title>
    <link rel="stylesheet" href="css/public.css"/>
    <link rel="stylesheet" href="css/style.css"/>
    <style type="text/css">
    	#sub{
		 	margin-left: 20px;
		    width: 100px;
		    padding: 0 20px;
		    height: 30px;
		    border: 1px solid #7ba92c;
		    border-radius: 4px;
		    color: #fff;
		    font-weight: bold;
		    font-size: 16px;
		    background: #87c212 url(images/search.png) 10px center no-repeat;
		}
    </style>
</head>
<body>
<!--头部-->
    <header class="publicHeader">
        <h1>超市账单管理系统</h1>
        <div class="publicHeaderR">
            <p><span>下午好！</span><span style="color: #fff21b"> ${userSession.userName }</span> , 欢迎你！</p>
            <a href="welcome.jsp">退出</a>
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
                    <li><a href="billList.html">账单管理</a></li>
                    <li><a href="providerList.html">供应商管理</a></li>
                    <li  id="active"><a href="user.do?method=query">用户管理</a></li>
                    <li><a href="password.html">密码修改</a></li>
                    <li><a href="login.jsp">退出系统</a></li>
                </ul>
            </nav>
        </div>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>用户管理页面</span>
            </div>
            <div class="search">
             
			<form action="user.do" method="post" name="queryUser" id="queryUser">
                <input name="method" value="query" class="input-text" type="hidden"/>
                <input type="hidden" name="method" value="delUser">
                <span>用户名：</span>
                <input type="text" placeholder="请输入用户名" name="queryUserName" value="${queryUserName }"/>&nbsp;&nbsp;&nbsp;&nbsp;             
                <input type="submit" value="查询"   id="sub"/>	
                <a href="jsp/useradd.jsp">添加用户</a>	
                	
              	<!-- 创建分页使用的隐藏域       当前页 -->
 				<input type="hidden" name="pageIndex">
            </form>
            </div>
            <!--用户-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                    <th width="10%">用户编码</th>
                    <th width="20%">用户名称</th>
                    <th width="10%">性别</th>
                    <th width="10%">年龄</th>
                    <th width="10%">电话</th>
                    <th width="10%">用户类型</th>
                    <th width="30%">操作</th>
                </tr>
                <c:forEach items="${userList}" var="user" varStatus="status">
                <tr id="${user.id}">
                    <td>${user.userCode}</td>
                    <td>${user.userName}</td>
                    <td>
                    <c:if test="${user.gender==1 }">男</c:if>
                    <c:if test="${user.gender==2 }">女</c:if>
                    </td>
                    <td>${user.age }</td>
                    <td>${user.phone }</td>
                    <td>
                    <c:if test="${user.userType==1 }">系统管理员</c:if>
                    <c:if test="${user.userType==2 }">经理</c:if>
                    <c:if test="${user.userType==3 }">普通员工</c:if>
                    </td>
                    <td>
                        <a class="viewUser" href="javascript:;" userid=${user.id } username=${user.userName } >查看</a>
                        <a class="modifyUser" href="javascript:;" userid=${user.id } username=${user.userName } >修改</a>
                        <a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userName } >删除</a>
                    </td>
                </tr>
                </c:forEach>
            </table>
			<input type="hidden" id="delid"/>
		 <nav>
		  <div>	  
		  <c:if test="${pageUtil.pageIndex>1}">
		    <span><a href="javascript:newsPage(document.forms[0],1)">首页</a></span>
		    <span><a href="javascript:newsPage(document.forms[0],${pageUtil.pageIndex-1})" >上一页</a></span>
		  </c:if>  
		  
		    <c:if test="${pageUtil.pageIndex< pageUtil.pageCount}">
		    <span><a href="javascript:newsPage(document.forms[0],${pageUtil.pageIndex+1})">下一页</a></span>
		    <span><a href="javascript:newsPage(document.forms[0],${pageUtil.pageCount})">尾页</a></span>
		     </c:if>  
		  </div>
		 </nav>   

        </div>              
    </section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
        <!-- 隐藏域  -->
			<input  type="hidden" id="url">
            <p>你确定要删除该用户吗？</p>
            <a href="javascript:;" id="yes" onClick="delSubmit()">确定</a>
            <a href="javascript:delhid();" id="no">取消</a>
        </div>
    </div>
</div>

    <footer class="footer">
        版权归北大青鸟
    </footer>

<script src="js/jquery.js"></script>
<script src="js/js.js"></script>
<script src="js/time.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/userlist.js"></script>
<script type="text/javascript">	  	 
	  
	 //分页的请求
	 function  newsPage(form,pageIndex){
	 //获取form表中的name属性值是pageIndex的隐藏域
	 form.pageIndex.value=pageIndex;
	 form.submit();  //表单提交
	 }
 
</script>
</body>
</html>
