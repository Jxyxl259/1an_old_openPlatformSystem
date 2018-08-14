//刷新页面
function refreshPage(){
	var modifyForm = document.getElementById("modifyForm");
	modifyForm.reset();
	$('#passwordOldMsg').html('');
	$('#passwordNewSecMsg').html('');
	$('#passwordNewMsg').html('');
}

//消息提示
function alertMsg(msg,type,fn){
	 $("#alertMsg").html(msg);
	 switch(type.toLowerCase()){
	 	case "confirm":		//confirm
	 		$("#alertInfo").html(confirmMsgTxt);
		 	if($("#alertOk").hasClass("hide")){
		 		//如果先弹出错误提示，后再确认提示，则可能会隐藏保存按钮
		 		$("#alertOk").removeClass("hide");
		 		$("#alertOk").attr("class","btn btn-primary");
		 	}
		 	if($("#alertOk").has("onclick")){
		 		$("#alertOk").removeAttr("onclick");
		 		$("#alertOk").attr("onclick","javascript:" + fn + ";");
		 	}else{
		 		$("#alertOk").attr("onclick","javascript:" + fn + ";");
		 	}
	 		break;
	 	case "error":		//error
	 		$("#alertInfo").html(errorMsgTxt);
	 		$("#alertOk").attr("class","hide");	 		
	 		$("#alertOk").removeAttr("onclick");	 		
	 		break;
	 	case "warn":		//warn
	 		$("#alertInfo").html(warnMsgTxt);
	 		$("#alertOk").attr("class","hide");
	 		$("#alertOk").removeAttr("onclick");	
	 		break;
	 	case "success":		//success
	 		$("#alertInfo").html(successMsgTxt);
	 		$("#alertOk").attr("class","hide");
	 		$("#alertOk").removeAttr("onclick");	
	 		break;
	 	case "info":		//info
	 		$("#alertInfo").html(infoMsgTxt);
	 		$("#alertOk").attr("class","hide");
	 		$("#alertOk").removeAttr("onclick");	
	 		break;
	 	default:;
	 }
	 $("#alertModal").modal("show");
}

//关闭当前tab页
function closeModifyTab(){
	closeTab(tabId);
}

//修改密码
function modifyPassword(){
	var passwordOld =$('#passwordOld').val();
	var passwordNew =$('#passwordNew').val();
	var passwordNewSec =$('#passwordNewSec').val();
	
	if(passwordOld==null||passwordOld==""){
		$('#passwordOldMsg').html('<font color="red"><i class="fa fa-exclamation-triangle"></i>'+passwordOldMsg+'</font> ');
		return;
	}else{
		$('#passwordOldMsg').html('');
	}
	
    if(passwordNew==null||passwordNew ==""){
    	$('#passwordNewMsg').html('<font color="red"><i class="fa fa-exclamation-triangle"></i>'+passwordNewMsg+'</font> ');
    	return;
    }else{
    	$('#passwordNewMsg').html('');
    }
    
	if(passwordOld==passwordNew){
		$('#passwordNewMsg').html('<font color="red"><i class="fa fa-exclamation-triangle"></i>'+errorPasswordNewMsg+'</font> ');
		$('#passwordNew').val('');
		return;
	}
	
	if(passwordNewSec==null||passwordNewSec==""){
		$('#passwordNewSecMsg').html('<font color="red"><i class="fa fa-exclamation-triangle"></i>'+passwordNewSecMsg+'</font> ');
    	return;
	}else{
		$('#passwordNewSecMsg').html('');
	}
	
	if(passwordNew!= passwordNewSec){
		$('#passwordNewMsg').html('<font color="red"><i class="fa fa-exclamation-triangle"></i>'+errorPasswordNewSecMsg+'</font> ');
		$('#passwordNew').val('');
		$('#passwordNewSec').val('');
		return;
	}
	
	RestfulClient.post(contextPath + "/user/modifyPassword", 
			 {
		 		"extend" :{
		 			"passwordOld" : passwordOld,
		 			"passwordNew" : passwordNew,
		 			"passwordNewSec" : passwordNewSec
		 		}
			 },
			 function(data) {
				 if(data.flag=="errorNew"){
					 $('#passwordNewMsg').html('<font color="red"><i class="fa fa-exclamation-triangle"></i>'+data.msg+'</font> ');
				 }else if(data.flag=="errorNewSec"){
					 $('#passwordNewSecMsg').html('<font color="red"><i class="fa fa-exclamation-triangle"></i>'+data.msg+'</font> ');
				 }else if(data.flag=="errorOld"){
					 $('#passwordOldMsg').html('<font color="red"><i class="fa fa-exclamation-triangle"></i>'+data.msg+'</font> ');
				 }else{
					 alertMsg(data.msg,"success","");//弹出提示框
					 $('#btnQry').attr('onclick','javascript:closeModifyTab();');
				 }

  		}
	);
}
