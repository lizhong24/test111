<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
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
            <span>账单管理页面&gt;&gt; 订单添加页面</span>
        </div>
        <div class="providerAdd">
            <form id="billForm" name="billForm" method="post" action="bill.html">
				<input type="hidden" name="method" value="modifysave">
				<input type="hidden" name="billId" id="billId" value="${bill.id }">
				<div class="content">
                    <label for="providerId">账单编码：</label>
                    <input type="text" name="billCode" id="billCode" readonly="readonly" value="${bill.billCode}"/>
                    <span></span>
                </div>
                <div>
                    <label for="providerName">商品名称：</label>
                    <input type="text" name="productName" id="productName" value="${bill.productName}"/>
                    <span></span>
                </div>
                <div>
                    <label for="people">商品单位：</label>
                    <input type="text" name="productUnit" id="productUnit" value="${bill.productUnit}"/>
                    <span></span>

                </div>
                <div>
                    <label for="phone">商品数量：</label>
                    <input type="text" name="productCount" id="productCount" value="${bill.productCount}"/>
                    <span></span>
                </div>
                <div>
                    <label for="address">总金额：</label>
                    <input type="text" name="totalPrice" id="totalPrice" value="${bill.totalPrice}"/>
                    <span></span>
                </div>
                <div>
                    <label for="fax">供应商：</label>               
                    <input type="hidden" value="${bill.providerId }" id="proId" />
                    <select name="providerId" id="providerId"></select>
                    <span></span>
                </div>
                <div>
                    <label >是否付款：</label>                                        
                    <input type="radio" name="isPayment" value="1" <c:if test="${bill.isPayment == 1 }">checked="checked"</c:if>>未付款
					<input type="radio" name="isPayment" value="2" <c:if test="${bill.isPayment == 2 }">checked="checked"</c:if>>已付款					                    
                </div>
                <div class="providerAddBtn">
                    <!--<a href="#">保存</a>-->
                    <!--<a href="billList.html">返回</a>-->
                    <input type="button" name="modifysave" id="modifysave"  value="保存" class="input-button"> 
                    <input type="button" value="返回" onclick="history.back(-1)"/>
                </div>
            </form>
        </div>

    </div>
</section>
<footer class="footer">
    版权归北大青鸟
</footer>
<script src="js/time.js"></script>
<script src="js/jquery.js"></script>
<script src="js/js.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/billupdate.js"></script>
</body>
</html>
