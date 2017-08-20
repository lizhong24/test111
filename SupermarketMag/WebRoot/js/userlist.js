$(function(){
	//通过jQuery的class选择器（数组）
	//对每个class为viewUser的元素进行动态绑定（click）
	/**
	 * bind、live、delegate
	 * on
	 */
	$(".viewUser").on("click",function(){
		//将被绑定的元素（a）转换成jQuery对象，可以使用jQuery方法
		var obj = $(this);
		window.location.href="user.do?method=view&uid="+obj.attr("userid");
	});
	
	$(".modifyUser").on("click",function(){
		var obj = $(this);
		window.location.href="user.do?method=modify&uid="+obj.attr("userid");
	});
	

	$(".deleteUser").on("click",function(){
		var obj = $(this);
		$("#delid").val(obj.attr("userid"));
		$("#removeUse").show();
		
	});	
});
function delSubmit(){
	var id=$("#delid").val();
	$.ajax({
		type:"GET",
		url:"user.do",
		data:{method:"delUser",uid:id},
		dataType:"json",
		success:function(data){
			if (data.delResult == "true") {//删除成功：移除删除行
				$("#"+id).remove();				
		        window.location.reload();				
				$("#removeUse").hide();		
			} else if(data.delResult == "false") {//删除失败
				//alert("对不起，删除用户【"+obj.attr("username")+"】失败！");
			}else if (data.delResult == "notexist") {
				//alert("对不起，用户【"+obj.attr("username")+"】不存在！");
			}
		},
		error:function(data){
			$("#removeUse").hide();
		}				
	});
}
function delhid(){
	$("#removeUse").hide();
}