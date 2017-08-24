<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		    background: #87c212 url(img/search.png) 10px center no-repeat;
		}
    </style>    
</head>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>超市账单管理系统</h1>

    <div class="publicHeaderR">
        <p><span>下午好！</span><span style="color: #fff21b">${userSession.userName}</span> , 欢迎你！</p>
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
                <li id="active"><a href="jsp/providerlist.jsp">供应商管理</a></li>
                <li><a href="jsp/userlist.jsp">用户管理</a></li>
                <li><a href="jsp/pwdmodify.jsp">密码修改</a></li>
                <li><a href="exit.html">退出系统</a></li>
            </ul>
        </nav>
    </div>
    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>供应商管理页面</span>
        </div>
        <div class="search">          
                <span>供应商名称：</span>
                <input type="text" placeholder="请输入供应商的名称" name="queryProviderName" id="queryProviderName" value=""/>&nbsp;&nbsp;&nbsp;&nbsp;             
                <input type="button" value="查询" id="queryButton" name="queryButton"/>	
                <a href="jsp/provideradd.jsp">添加供应商</a>	          
        </div>
        <!--供应商操作表格-->
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="10%">供应商编码</th>
                <th width="20%">供应商名称</th>
                <th width="10%">联系人</th>
                <th width="10%">联系电话</th>
                <th width="10%">传真</th>
                <th width="10%">创建时间</th>
                <th width="30%">操作</th>
            </tr>
            
            <tbody id="tbody">

            </tbody>               
        </table>		
	      <!-- 隐藏域  -->
          <input type="hidden" id="url2"/>
          
	      <input  type="hidden"  name="pageIndex">		  
		  <a  href="javascript:"   id="one">首页</a>
		  <a  href="javascript:"   id="back">上一页</a>
		  <a  href="javascript:"   id="next">下一页</a>
		  <a  href="javascript:"   id="last">尾页</a>

        </div>            
    </section>
    
<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeProv">
   <div class="removerChid">
       <h2>提示</h2>
       <div class="removeMain" >
           <p>你确定要删除该供应商吗？</p>
           <a href="javascript:del();" id="yes">确定</a>
           <a href="javascript:;" id="no">取消</a>
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
<script type="text/javascript" src="js/providerlist.js"></script>
</body>
</html>