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
		    background: #87c212 url(images/search.png) 10px center no-repeat;
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
                <li><a href="bill.html">账单管理</a></li>
                <li id="active"><a href="provider.html">供应商管理</a></li>
                <li><a href="user.html">用户管理</a></li>
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
            <form action="provider.html" method="post" name="queryProvider" id="queryProvider">
                <input name="method" value="query" class="input-text" type="hidden"/>
                
                <span>供应商名称：</span>
                <input type="text" placeholder="请输入供应商的名称" name="queryProviderName" value="${queryProviderName }"/>&nbsp;&nbsp;&nbsp;&nbsp;             
                <input type="submit" value="查询"   id="sub"/>	
                <a href="jsp/provideradd.jsp">添加供应商</a>	
                	
              	<!-- 创建分页使用的隐藏域       当前页 -->
 				<input type="hidden" name="pageIndex">           
            </form>
            
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
            <c:forEach items="${providerList}" var="provider" varStatus="status">
                <tr>
                    <td>${provider.proCode}</td>
                    <td>${provider.proName}</td>                   
                    <td>${provider.proContact}</td>
                    <td>${provider.proPhone}</td>
                    <td>${provider.proFax}</td>
                    <td><fmt:formatDate value="${provider.creationDate}" pattern="yyyy-MM-dd"/></td>
                    <td>
                        <a class="viewProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName } >查看</a>
                        <a class="modifyProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName } >修改</a>
                        <a class="deleteProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName } >删除</a>
                    </td>
                </tr>
             </c:forEach>   
        </table>
		
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
<footer class="footer">
    版权归北大青鸟
</footer>

<script src="js/jquery.js"></script>
<script src="js/js.js"></script>
<script src="js/time.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/providerlist.js"></script>
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