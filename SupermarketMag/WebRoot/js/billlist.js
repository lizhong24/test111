$(function(){
	  //分页的四要素
	    var pageIndex="";  //当前页
	    var pageSize="";//页大小
	    var totalPageCount=""; //总页数
	    var totalCountSize=""; //总记录数
	    var queryProductName="";//输入的用户名
	    var queryProviderId = null;//供应商
	    var queryIsPayment="";//是否付款
	    var queryButton=$("#queryButton");
	    queryButton.on("click",function(){
	    		pageInit(pageIndex); 		    	  
	    });
	    
	    
	    $.ajax({
			type:"get",
			url:"bill.html",
			data:{"method":"getproviderlist"},
			dataType:"json",
			success:function(data){
				if(data != null){
					var proId = $("#proId").val();
					$("#queryProviderId").html("");
					var options = "<option value=\"0\">请选择</option>";
					for(var i = 0; i < data.length; i++){
						if(proId != null && proId != undefined && data[i].id == proId ){
							options += "<option selected=\"selected\" value=\""+data[i].id+"\" >"+data[i].proName+"</option>";
						}else{
							options += "<option value=\""+data[i].id+"\" >"+data[i].proName+"</option>";
						}				
					}
					$("#queryProviderId").append(options);
				}
			},
		});
	    	    
	    //初始化分页数据
	    pageInit(pageIndex);   
	    //分页AJAX请求
	    function pageInit(pageIndex){
	    	queryProductName=$("#queryProductName").val();
	    	queryProviderId=$("#queryProviderId").val();
	    	queryIsPayment=$("#queryIsPayment").val();
	    	$("#one").show();
	        $("#back").show();
	        $("#next").show();
	        $("#last").show();   //第一次显示 所有的 超链接
	    	$.ajax({
	            url:"bill.html",
	            type:"POST",
	            data:{"pageIndex":pageIndex,"queryProductName":queryProductName,"queryProviderId":queryProviderId,"queryIsPayment":queryIsPayment},
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
	            var d = new Date(this.creationDate);
	            var creationDate = d.getFullYear() + "-"+ (d.getMonth() + 1) +"-"+ d.getDate();
	             
	            $("#tbody").append("<tr><td>"+this.billCode +"</td>"
	                +" <td>"+this.productName +"</td>"
	                +" <td>"+this.providerName +"</td>"
	                +" <td>"+this.totalPrice +"</td>"
	                +" <td>"+((this.isPayment == 1) ? "否" : "是")+"</td>"
	                +" <td>"+creationDate +"</td>"
	                +" <td><a class='viewBill' href='bill.html?method=view&billid="+this.id +"'><img src='img/read.png' alt='查看' title='查看'/></a>"
					+"	<a class='modifyBill' href='bill.html?method=modify&billid="+this.id +"'><img src='img/xiugai.png' alt='修改' title='修改'/></a>"
					+"	<a class='delBill' href='javascript:tohid("+this.id+")'><img src='img/schu.png' alt='删除' title='删除'/></a>"                  
	                +" </td></tr>");                                                                                         
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
	$("#removeBi").show();
}
function del(){
	window.location.href='bill.html?method=delete&billid='+$("#url").val();
}




