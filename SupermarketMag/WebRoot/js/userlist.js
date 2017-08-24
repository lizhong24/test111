$(function(){
  //分页的四要素
    var pageIndex="";  //当前页
    var pageSize="";//页大小
    var totalPageCount=""; //总页数
    var totalCountSize=""; //总记录数
    var queryUserName="";//输入的用户名
    var queryButton=$("#queryButton");
    queryButton.on("click",function(){
    	pageInit(pageIndex);   	  
    });
    //初始化分页数据
    pageInit(pageIndex);   
    //分页AJAX请求
    function pageInit(pageIndex){
    	queryUserName=$("#queryUserName").val();
    	$("#one").show();
        $("#back").show();
        $("#next").show();
        $("#last").show();   //第一次显示 所有的 超链接
    	$.ajax({
            url:"user.html",
            type:"POST",
            data:{"pageIndex":pageIndex,"queryUserName":queryUserName},
            dataType:"json",
            contentType:"application/x-www-form-urlencoded;charset=utf-8",
            success:callBack
        });
    }
    //回掉函数
    function callBack(data) {
        //清楚上一页的数据
        $("#tbody").html("");
        $(data).each(function(){
        	//alert(this.id);
            if(this.pageUtil != null) {
            	pageIndex=this.pageUtil.pageIndex;
				pageSize=this.pageUtil.pageSize;
				totalPageCount=this.pageUtil.pageCount;
				totalCountSize=this.pageUtil.totalCount;      
            }          			
            var age = new Date().getFullYear() - new Date(this.birthday).getFullYear();              
            var userType;
            if(this.userType==1){
        	   userType="系统管理员";
            }
            if(this.userType==2){
        	   userType="经理";
            }
            if(this.userType==3){
        	   userType="普通用户";
            }  
            $("#tbody").append("<tr>                                                                                                  "
                +"   <td>"+this.userCode+"</td>                                                                                          "
                +"   <td>"+this.userName+"</td>                                                                                          "
                +"   <td>"+((this.gender == 1) ? "男" : "女")+"</td>                          "
                +"   <td>"+age+"</td>                                                                                              "
                +"   <td>"+this.phone +"</td>   "
                + " <td>" +userType + "</td> "
                + " <td><a class='viewUser' href='user.html?method=view&uid="+ this.id +"'><img src='img/read.png' alt='查看' title='查看'/></a> "		        
		        + " <a class='modifyUser' href='user.html?method=modify&uid="+ this.id +"'><img src='img/xiugai.png' alt='修改' title='修改'/></a> "
				+ " <a class='deleteUser' href='javascript:tohid("+this.id+")'><img src='img/schu.png' alt='删除' title='删除'/></a> "
			      + "  </td></tr> ");                                                                                         
        });
    } 
        //********************** 分页功能 ********************************   
    
    $("#one").click(function(){  //首页
      pageInit(1);
    });
    $("#last").click(function(){  //尾页
      pageInit(totalPageCount);
    });
    $("#back").click(function(){  //上一页
        if((pageIndex-1)>0){
           pageInit(pageIndex-1);
        }else{
           $("#one").hide();
           $("#back").hide();
        }
      
    });
    $("#next").click(function(){  //下一页
      if((pageIndex+1)<=totalPageCount){
           pageInit(pageIndex+1);
        }else{
           $("#last").hide();
           $("#next").hide();
        }
    });
    
});    
function tohid(id){
	$("#url").val(id);
	$("#removeUse").show();
}
function del(){
	window.location.href='user.html?method=delUser&uid='+$("#url").val();
}

