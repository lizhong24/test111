var $billCode = null;
var $productName = null;
var $productUnit = null;
var $productCount = null;
var $totalPrice = null;
var $providerId = null;
var $addBtn = null;

function priceReg(value){
	value = value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
	value = value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
    value = value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
    value = value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");//去掉特殊符号￥
	if(value.indexOf(".")>0){
		value = value.substring(0,value.indexOf(".")+3);
	}
	return value;
}

$(function (){
	$billCode = $("#billCode");
	$productName = $("#productName");
	$productUnit = $("#productUnit");
	$productCount = $("#productCount");
	$totalPrice = $("#totalPrice");
	$providerId = $("#providerId");
	$addBtn = $("#add");
	
	//初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
	$billCode.next().html("*");
	$productName.next().html("*");
	$productUnit.next().html("*");
	$productCount.next().html("*");
	$totalPrice.next().html("*");
	$providerId.next().html("*");
	//异步加载供应商列表
	$.ajax({
		type:"get",
		url:"bill.html",
		data:{"method":"getproviderlist"},
		dataType:"json",
		success:function(data){
			if(data != null){
				$providerId.html("");
				var options = "<option value=\"0\">--请选择--</option>";
				for(var i = 0; i < data.length; i++){
					options += "<option value=\""+data[i].id+"\">"+data[i].proName+"</option>";
				}
				$providerId.html(options);
			}
		},
		error:function(data){
			validateTip($providerId.next(),{"color":"red"},imgNo+" 获取供应商列表error",false);
		}
	});
	

	$billCode.on({
		"focus":function (){
			validateTip($billCode.next(),{"color":"#666666"},"* 请输入账单编号",false);
		},
		"blur":function (){
			if($billCode.val() != null && $billCode.val() != ""){
				validateTip($billCode.next(),{"color":"green"},imgYes,true);
			}else{
				validateTip($billCode.next(),{"color":"red"},imgNo+" 账单编号不能为空，请重新输入",false);
			}
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
	
	$addBtn.on("click",function(){
		if($billCode.attr("validateStatus") != "true"){
			$billCode.blur();
		}else if($productName.attr("validateStatus") != "true"){
			$productName.blur();
		}else if($productUnit.attr("validateStatus") != "true"){
			$productUnit.blur();
		}else if($productCount.attr("validateStatus") != "true"){
			$productCount.blur();
		}else if($totalPrice.attr("validateStatus") != "true"){
			$totalPrice.blur();
		}else if($providerId.attr("validateStatus") != "true"){
			$providerId.blur();
		}else{
			if(confirm("是否确认提交数据")){
				$("#billForm").submit();
			}
		}
	});
});