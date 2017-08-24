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
                    <li id="active"><a href="jsp/billlist.jsp">账单管理</a></li>                   
                    <li><a href="jsp/providerlist.jsp">供应商管理</a></li>
                	<li><a href="jsp/userlist.jsp">用户管理</a></li>
                	<li><a href="jsp/pwdmodify.jsp">密码修改</a></li>
               		<li><a href="exit.html">退出系统</a></li>
                </ul>
            </nav>
        </div>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>账单管理页面</span>
            </div>                 
            <div class="search">            	                                     
                <span>商品名称：</span>
                <input type="text" placeholder="请输入商品的名称"name="queryProductName" id="queryProductName" value=""/>&nbsp;&nbsp;&nbsp;&nbsp;
                
                <span>供应商：</span>              
                <select name="queryProviderId" id="queryProviderId" class="input-text">    
                                                     
                </select>

                <span>是否付款：</span>
                <select name="queryIsPayment" id="queryIsPayment" class="input-text">
                    <option value="0">--不限--</option>
					<option value="1" <c:if test="${queryIsPayment == 1 }">selected="selected"</c:if>>未付款</option>
					<option value="2" <c:if test="${queryIsPayment == 2 }">selected="selected"</c:if>>已付款</option>                                       
                </select> 
                
                <input type="button" value="查询" id="queryButton" name="queryButton"/>	
                <a href="jsp/billadd.jsp">添加订单</a>	                
            </div>
            <!--账单表格 样式和供应商公用-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                    <th width="10%">账单编码</th>
                    <th width="20%">商品名称</th>
                    <th width="10%">供应商</th>
                    <th width="10%">账单金额</th>
                    <th width="10%">是否付款</th>
                    <th width="10%">创建时间</th>
                    <th width="30%">操作</th>
                </tr>
                <tbody id="tbody">

                </tbody>                  
            </table>

          <!-- 隐藏域  -->
          <input type="hidden" id="url"/>
          
          <input  type="hidden"  name="pageIndex">		  
		  <a  href="javascript:"   id="one">首页</a>
		  <a  href="javascript:"   id="back">上一页</a>
		  <a  href="javascript:"   id="next">下一页</a>
		  <a  href="javascript:"   id="last">尾页</a>
        </div>
    </section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该订单吗？</p>
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
<script type="text/javascript" src="js/billlist.js"></script>
</body>
</html>
