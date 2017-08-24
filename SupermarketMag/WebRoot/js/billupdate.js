var $billCode = null;
var $productName = null;
var $productUnit = null;
var $productCount = null;
var $totalPrice = null;
var $providerId = null;
var $saveBtn = null;

function priceReg (value){
	value = value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
	value = value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
    value = value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
    value = value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");//去掉特殊符号￥
	if(value.indexOf(".")>0){
		value = value.substring(0,value.indexOf(".")+3);
	}
	return value;
}

$(function(){
	$billCode = $("#billCode");
	$productName = $("#productName");
	$productUnit = $("#productUnit");
	$productCount = $("#productCount");
	$totalPrice = $("#totalPrice");
	$providerId = $("#providerId");
	$saveBtn = $("#modifysave");

	$billCode.next().html("*");
	$productName.next().html("*");
	$productUnit.next().html("*");
	$productCount.next().html("*");
	$totalPrice.next().html("*");
	$providerId.next().html("*");
	
	$.ajax({
		type:"get",
		url:"bill.html",
		data:{"method":"getproviderlist"},
		dataType:"json",
		success:function(data){
			if(data != null){
				var proId = $("#proId").val();
				$providerId.html("");
				var options = "<option value=\"0\">请选择</option>";
				for(var i = 0; i < data.length; i++){
					if(proId != null && proId != undefined && data[i].id == proId ){
						options += "<option selected=\"selected\" value=\""+data[i].id+"\" >"+data[i].proName+"</option>";
					}else{
						options += "<option value=\""+data[i].id+"\" >"+data[i].proName+"</option>";
					}
					
				}
				$providerId.html(options);
			}
		},
		error:function(data){
			validateTip($providerId.next(),{"color":"red"},imgNo+" 获取供应商列表error",false);
		}
	});
	
	$productName.on({
		"focus":function (){
			validateTip($productName.next(),{"color":"#666666"},"* 请输入商品名称",false);
		},
		"blur":function (){
			if($productName.val() != null && $productName.val() != ""){
				validateTip($productName.next(),{"color":"green"},imgYes,true);
			}else{
				validateTip($productName.next(),{"color":"red"},imgNo+" 商品名称不能为空，请重新输入",false);
			}
		}
	});
	
	$productUnit.on({
		"focus":function (){
			validateTip($productUnit.next(),{"color":"#666666"},"* 请输入商品单位",false);
		},
		"blur":function (){
			if($productUnit.val() != null && $productUnit.val() != ""){
				validateTip($productUnit.next(),{"color":"green"},imgYes,true);
			}else{
				validateTip($productUnit.next(),{"color":"red"},imgNo+" 商品单位不能为空，请重新输入",false);
			}
		}
	});
	
	$productCount.on({
		"focus":function (){
			validateTip($productCount.next(),{"color":"#666666"},"* 请输入大于0的整数，小数点后保留2位",false);
		},
		"keyup":function (){
			this.value = priceReg(this.value);
		},
		"blur":function (){
			if($productCount.val() != null && $productCount.val() != ""){
				validateTip($productCount.next(),{"color":"#666666"},imgYes,true);
			}else{
				validateTip($productCount.next(),{"color":"red"},imgNo+" 商品数量不能为空，请重新输入",false);
			}
		}
	});
	
	$totalPrice.on({
		"focus":function (){
			validateTip($totalPrice.next(),{"color":"#666666"},"* 请输入大于0的整数，小数点后保留2位",false);
		},
		"keyup":function (){
			this.value = priceReg(this.value);
		},
		"blur":function (){
			if($totalPrice.val() != null && $totalPrice.val() != ""){
				validateTip($totalPrice.next(),{"color":"#666666"},imgYes,true);
			}else{
				validateTip($totalPrice.next(),{"color":"red"},imgNo+" 总额不能为空，请重新输入",false);
			}
		}
	});
	
	$providerId.on({
		"focus":function (){
			validateTip($providerId.next(),{"color":"#666666"},"* 请选择供应商",false);
		},
		"blur":function (){
			if($providerId.val() != null && $providerId.val() != "" && $providerId.val() > 0 ){
				validateTip($providerId.next(),{"color":"green"},imgYes,true);
			}else{
				validateTip($providerId.next(),{"color":"red"},imgNo+" 供应商不能为空，请选择",false);
			}
		}
	});
	
	$saveBtn.on("click",function(){
		$productName.blur();
		$productUnit.blur();
		$productCount.blur();
		$totalPrice.blur();
		$providerId.blur();
		if($productName.attr("validateStatus") == "true" 
			&& $productUnit.attr("validateStatus") == "true" 
			&& $productCount.attr("validateStatus") == "true" 
			&& $totalPrice.attr("validateStatus") == "true" 
			&& $providerId.attr("validateStatus") == "true"){
			if(confirm("是否确认提交数据")){
				$("#billForm").submit();
			}
		}
	});
});