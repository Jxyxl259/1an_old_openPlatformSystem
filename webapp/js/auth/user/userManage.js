
/////// ac ///////
function dataFn() {
	var data = JSON.parse(parameterTypeAcData);
	return data;
};

function valueFn(item) {
    return item["value"];
}

function labelFn(item) {
    return item["value"] + "-" + item["yyy"];
}

$("#paraTypeOperDiv").draggable({
    handle: ".modal-header",   
    cursor: 'move',   
    refreshPositions: false  
});

$("#paraOperDiv").draggable({
    handle: ".modal-header",   
    cursor: 'move',   
    refreshPositions: false  
});

var clickCount = 0;//是否刷新系统参数table控制标志
//刷新页面
function refreshPage(){
	var appId_search = $("#appId_search").val();
	var appSecret_search = $("#appSecret_search").val();
	var invalidFlag_search = $("#invalidFlag_search").val();
	var userName_search = $("#userName_search").val();
	var phoneNumber_search = $("#phoneNumber_search").val();
	getParaTypeJson(false , appId_search , appSecret_search , invalidFlag_search , userName_search , phoneNumber_search );
	
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
		 		$("#alertOk").attr("onclick","javascript:" + fn + "();");
		 	}else{
		 		$("#alertOk").attr("onclick","javascript:" + fn + "();");
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

//禁用
function bannedParaType(){
	var paraTypeIds=$("#paraTypeTable").jqGrid("getGridParam","selarrrow");
	if(paraTypeIds.length <= 0){
		alertMsg(bannedRowMsgTxt,"error","");
	}else{
		alertMsg(bannedRowMsgSureTxt,"confirm","bannedParaTypeSure");//确认后调用bannedParaTypeSure()
	}
	
}
//确认禁用
function bannedParaTypeSure(){
	
//	 $("#alertModal").modal("hide");//隐藏确认窗口
	 
	 var paraTypeIds=$("#paraTypeTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 for(var k = 0;k<paraTypeIds.length;k++){
		 var rowData = $("#paraTypeTable").jqGrid("getRowData",paraTypeIds[k]);
		 arr.push(rowData.parameterTypeId);
	 }
	
	RestfulClient.post(contextPath + "/parameterType/updateStates", 
			 {
		 		"extend" :{
		 			"ids" : JSON.stringify(arr) 
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg, "success" , "");
				 refreshPage();
  		}
	);
}


//禁用
function bannedPara(){
	var paraTypeIds=$("#paraTable").jqGrid("getGridParam","selarrrow");
	if(paraTypeIds.length <= 0){
		alertMsg(bannedRowMsgTxt,"error","");
	}else{
		alertMsg(bannedRowMsgSureTxt,"confirm","bannedParaSure");//确认后调用bannedParaSure()
	}
	
}
//确认禁用
function bannedParaSure(){
	
//	 $("#alertModal").modal("hide");//隐藏确认窗口
	 
	 var paraIds=$("#paraTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 var parameterType = '';
	 for(var k = 0;k<paraIds.length;k++){
		 var rowData = $("#paraTable").jqGrid("getRowData",paraIds[k]);
		 arr.push(rowData.parameterId);
		 parameterType = rowData.parameterType;
	 }
	
	RestfulClient.post(contextPath + "/parameter/updateStates", 
			 {
		 		"extend" :{
		 			"ids" : JSON.stringify(arr) 
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg, "success" , "");
				 ++ clickCount;
				 getParaJson(parameterType);
		}
	);
}
//加载系统参数类型表格按钮
function loadParaTypeTableButtons(){
	//按钮组
	$("#paraTypeTable")  
	.navGrid('#gridPager1',{edit:false,add:false,del:false,search:false,refresh:false}) 
	.navButtonAdd('#gridPager1',{  
		 title:addBtnTxt, 
		 caption:'',
	  buttonicon:"fa-plus",   
	  onClickButton: function(){   
		   openWin('add' , 'paraTypeOperDiv');
	  },   
	  position:"last"  
	})
	.navSeparatorAdd("#gridPager1",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager1',{  
		title:editBtnTxt, 
		caption:'',
		buttonicon:"fa-edit",   
		onClickButton: function(){   
			openWin('edit' , 'paraTypeOperDiv');
		},   
		position:"last"  
	})
	.navSeparatorAdd("#gridPager1",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager1',{  
		title:delBtnTxt, 
		caption:'',
		buttonicon:"fa-trash",   
		onClickButton: function(){   
			delParaType();
		},   
		position:"last"  
	}); 
}

function showInvalidFlag(cellvalue, options, rowObject){
	return cellvalue == "0" ? "有效":"无效";
} 

//加载或刷新表格
function reloadParaTypeTable(parameterTypeList){
	 $("#paraTypeTable").jqGrid( {
	          datatype : "local",
	          colNames : [ "appId","appId", "appSecret","用户名","手机号", "回调地址", "创建人", "更新人", "状态" ,"状态" ],
	          colModel : [ 
	                       {name : 'appId',index : 'parameterTypeShow',width : 220,align : "center", formatter: showParameterType}, 
	                       {name : 'appId',index : 'appId', hidden: true }, 
	                       {name : 'appSecret',index : 'appSecret',width : 220,align : "center"}, 
	                       {name : 'userName',index : 'userName',width : 100,align : "center"}, 
	                       {name : 'phoneNumber',index : 'phoneNumber',width : 80,align : "center"}, 
	                       {name : 'callbackUrl',index : 'callbackUrl',width : 80,align : "center"},
	                       {name : 'createdUser',index : 'createdUser',width : 60,align : "center"},
	                       {name : 'updatedUser',index : 'updatedUser',width : 60,align : "center"},
	                       {name : 'invalidFlag',index : 'invalidFlagShow', width : 60,align : "center", formatter: showInvalidFlag},
	                       {name : 'invalidFlag',index : 'invalidFlag', hidden: true}
	                     ],
	          rowNum : 10,
	          rowList : [ 10, 20, 30 ],
	          autowidth:true,//自动宽
	          multiboxonly :true,
	          multiselectWidth : 29,
	          viewrecords : true,
	          sortname : 'updatedDate',
	          sortorder : "desc",
	          multiselect : true,
	          viewrecords: true,//是否在浏览导航栏显示记录总数
	          recordpos : 'right',//定义了记录信息的位置
	          pager : '#gridPager1'
	        });
   
   ////先清空
   $("#paraTypeTable").jqGrid().clearGridData();
   for ( var i = 0; i < parameterTypeList.length; i++){
       $("#paraTypeTable").jqGrid('addRowData', i + 1, parameterTypeList[i]);
     }
   //加载完数据，分页
   var rowNumTmp = jQuery("#paraTypeTable").jqGrid('getGridParam','rowNum');
   $("#paraTypeTable").setGridParam({ rowNum: rowNumTmp }).trigger("reloadGrid");
}
//加载系统参数表格按钮
function loadParaTableButtons(){
	 //按钮组
	  $("#paraTable")  
	  .navGrid('#gridPager2',{edit:false,add:false,del:false,search:false,refresh:false}) 
	  .navButtonAdd('#gridPager2',{  
	  	 title:addBtnTxt, 
	  	 caption:'',
	       buttonicon:"fa-plus",   
	       onClickButton: function(){   
	    	   openWin('add' , 'paraOperDiv');
	       },   
	       position:"last"  
	  })
	  .navSeparatorAdd("#gridPager2",{sepclass : 'ui-separator',sepcontent: ''})
	  .navButtonAdd('#gridPager2',{  
	  	title:editBtnTxt, 
	  	caption:'',
	  	buttonicon:"fa-edit",   
	  	onClickButton: function(){   
	  		openWin('edit' , 'paraOperDiv');
	  	},   
	  	position:"last"  
	  })
	  .navSeparatorAdd("#gridPager2",{sepclass : 'ui-separator',sepcontent: ''})
	  .navButtonAdd('#gridPager2',{  
	  	title:delBtnTxt, 
	  	caption:'',
	  	buttonicon:"fa-trash",   
	  	onClickButton: function(){   
	  		delPara();
	  	},   
	  	position:"last"  
	  }); 
}

function showInvalidFlag_Res(cellvalue, options, rowObject){
	return cellvalue == "0" ? "有效":"无效";
} 
//加载或刷新系统参数表格按钮
function reloadParaTable(paraList){
	$("#paraTable").jqGrid( {
        datatype : "local",
        colNames : [ "id","appId","资源ID","资源名称" ,"资源URL" ,"合作机构", "协议号", "创建人", "更新人", "状态", "状态"],
        colModel : [ 
                     {name : 'idStr',index : 'idStr', hidden:true }, 
                     {name : 'appId',index : 'appId',width : 200,align : "center" }, 
                     {name : 'resourceIdStr',index : 'resourceIdStr',width : 60,align : "center" }, 
                     {name : 'interfaceName',index : 'interfaceName',width : 100,align : "center" }, 
                     {name : 'requestUrl',index : 'requestUrl',width : 100,align : "center" }, 
                     {name : 'orgId',index : 'orgId',width : 80,align : "center" }, 
                     {name : 'agrtCode',index : 'agrtCode',width : 80,align : "center" }, 
                     {name : 'createdUser',index : 'createdUser',width : 60,align : "center"}, 
                     {name : 'updatedUser',index : 'updatedUser',width : 60,align : "center"}, 
                     {name : 'invalidFlag',index : 'invalidFlagShow', width : 60,align : "center", formatter: showInvalidFlag_Res},
                     {name : 'invalidFlag',index : 'invalidFlag', hidden: true}
                   ],
        rowNum : 10,
        rowList : [ 10, 20, 30 ],
        sortname : 'updatedDate',
        autowidth:true,//自动宽
        multiboxonly :true,
        multiselectWidth : 29,
        viewrecords : true,
        sortorder : "desc",
        multiselect : true,
        viewrecords: true,//是否在浏览导航栏显示记录总数
//        emptyrecords: "没有数据！",
        recordpos : 'right',//定义了记录信息的位置
//        recordtext:"{0} - {1} 共 {2}条", 
//        pgtext : " {0}  共 {1}页" , 
        pager : '#gridPager2'
      });

  ////先清空
  $("#paraTable").jqGrid().clearGridData(); 
  //再加载
  for ( var i = 0; i < paraList.length; i++){
      $("#paraTable").jqGrid('addRowData', i + 1, paraList[i]);
    }
  //加载完数据，分页
  var rowNumTmp = jQuery("#paraTable").jqGrid('getGridParam','rowNum');
  $("#paraTable").setGridParam({ rowNum: rowNumTmp }).trigger("reloadGrid");
  
}
//系统参数
function getParaJson(paraType){
	RestfulClient.post(
			contextPath + "/oauthResource/getOauthResourceList", 
			{
		        "form" : null,
		        "extend" : {
		            "appId" : paraType
		        },
		        "page" : null
	     }, 
	     function(data) {
	      var paraList = data.oauthResourceList;
	      reloadParaTable(paraList);
	      if(clickCount == 1){
	    	  loadParaTableButtons();
	      }
	      
	  });
	
}
//系统参数类型
function getParaTypeJson(buttonFlag , appId_search , appSecret_search , 
		invalidFlag_search , userName_search , phoneNumber_search){
	RestfulClient.post(contextPath + "/auth/getOauthUserList", {
		"extend":{
			"appId":appId_search,
			"appSecret":appSecret_search,
			"invalidFlag":invalidFlag_search,
			"userName":userName_search,
			"phoneNumber":phoneNumber_search
		}
	}, function(data) {
        var parameterTypeList = data.dataList;
        reloadParaTypeTable(parameterTypeList);
        if(buttonFlag){
        	loadParaTypeTableButtons();
        }
        //
    } );
}
//删除确认提示
function delParaType(){
	
		var paraTypeIds=$("#paraTypeTable").jqGrid("getGridParam","selarrrow");
		if(paraTypeIds.length <= 0){
			alertMsg(deleteRowMsgTxt,"error","");
		}else{
			alertMsg("确定要作废所选记录？","confirm","delParaTypeSure");//确认后调用delParaTypeSure()
		}
		
}
//确认删除
function delParaTypeSure(){	 
	 var paraTypeIds=$("#paraTypeTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 for(var k = 0;k<paraTypeIds.length;k++){
		 var rowData = $("#paraTypeTable").jqGrid("getRowData",paraTypeIds[k]);
		 arr.push(rowData.appId);
	 }
	
	RestfulClient.post(contextPath + "/auth/delParaType", 
			 {
		 		"extend" :{
		 			"appIds" : JSON.stringify(arr) 
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg , "success" , "");
				 refreshPage();
  		}
	);
}

//删除确认提示
function delPara(){
	
		var paraTypeIds=$("#paraTable").jqGrid("getGridParam","selarrrow");
		if(paraTypeIds.length <= 0){
			alertMsg(deleteRowMsgTxt,"error","");
		}else{
			alertMsg("确定要删除所选记录？","confirm","delParaSure");//确认后调用delParaSure()
		}
		
}
//确认删除
function delParaSure(){
	
//	 $("#alertModal").modal("hide");//隐藏确认窗口
	 
	 var paraTypeIds=$("#paraTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 var parameterType ;
	 for(var k = 0;k<paraTypeIds.length;k++){
		 var rowData = $("#paraTable").jqGrid("getRowData",paraTypeIds[k]);
		arr.push(rowData.idStr);
		 parameterType = rowData.idStr;
	 }
	
	RestfulClient.post(contextPath + "/oauthResource/delPara", 
			 {
		 		"extend" :{
		 			"ids" : JSON.stringify(arr) 
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg , "success" , "");
				 ++ clickCount;
				 getParaJson($("#appId_Res").val());
  		}
	);
}

//新增/修改类型
function openWin(operType , winId){
	
	//修改时验证，一次只能修改一条
	if(operType.toLowerCase() == "edit"){
		
		if(winId == "paraTypeOperDiv"){
			var paraTypeIds=$("#paraTypeTable").jqGrid("getGridParam","selarrrow");
			
			if(paraTypeIds.length <= 0){
				alertMsg(editMsgTxt,"error","");
				return;
			}else if(paraTypeIds.length > 1){
				alertMsg(editOneMsgTxt,"error","");
				return;
			}else{
				var t = paraTypeIds[0];
				var rowData = $("#paraTypeTable").jqGrid("getRowData",paraTypeIds[0]);
				
				$("#operType").val("edit");
				$("#appId").val(rowData.appId);
				document.getElementById("appId").setAttribute("readOnly",'true');
				document.getElementById("appIdCreate").style.display = "none";
				$("#appSecret").val(rowData.appSecret );
				$("#userName").val(rowData.userName);
				$("#phoneNumber").val(rowData.phoneNumber);
				$("#callbackUrl").val(rowData.callbackUrl);
				$("#createdUser").val(rowData.createdUser);
				$("#updatedUser").val(rowData.updatedUser);
				$("#invalidFlag").val(rowData.invalidFlag);
			}
			
		}else if(winId == "paraOperDiv"){
			var paraIds=$("#paraTable").jqGrid("getGridParam","selarrrow");
			
			if(paraIds.length <= 0){
				alertMsg(editMsgTxt,"error","");
				return;
			}else if(paraIds.length > 1){
				alertMsg(editOneMsgTxt,"error","");
				return;
			}else{
				++clickCount;
				var t = paraIds[0];
				var rowData = $("#paraTable").jqGrid("getRowData",paraIds[0]);
				$("#operType_Res").val("edit");
				$("#id_Res").val(rowData.idStr);
				$("#appId_Res").val(rowData.appId);
				$("#resourceId_Res").val(rowData.resourceIdStr);
				$("#createdUser_Res").val(rowData.createdUser);
				$("#updatedUser_Res").val(rowData.updatedUser);
				$("#invalidFlag_Res").val(rowData.invalidFlag);
				
			}
			
		}
		
	}
	
	if(operType.toLowerCase() == "add"){
		if(winId == "paraTypeOperDiv"){
			$("#paraTypeOperDivTitle").html("资源配置");
	 	 	$("#btnParaTypeOK").html("确认");
	 	 	$("#validFlagT").val('1');
	 	 	//表单清空
	 	 	$("#operType_Res").val("add");
	 	 	$("#appId").val('');
	 	 	document.getElementById("appId").removeAttribute("readOnly");
	 	 	document.getElementById("appIdCreate").style.display = "block";
			$("#appSecret").val('');
			$("#userName").val('');
			$("#phoneNumber").val('');
			$("#callbackUrl").val('');
			$("#createdUser").val('');
			$("#updatedUser").val('');
			$("#invalidFlag").val('0');
			
		}else if(winId == "paraOperDiv"){
			$("#paraOperDivTitle").html("第三方用户");
	 	 	$("#btnParaOK").html("确认");
	 	 	$("#validFlag").val('1');
	 	 	//表单清空
	 	 	$("#operType_Res").val("add");
	 	 	$("#id_Res").val('');
			//$("#appId_Res").val('');
			$("#resourceUrl_Res").val('');
			$("#createdUser_Res").val('');
			$("#updatedUser_Res").val('');
			$("#invalidFlag_Res").val('0');
		}
		
	}else if(operType.toLowerCase() == "edit"){
		if(winId == "paraTypeOperDiv"){
			$("#paraTypeOperDivTitle").html("资源配置");
	 	 	$("#btnParaTypeOK").html("确认");
		}else if(winId == "paraOperDiv"){
			$("#paraOperDivTitle").html("第三方用户");
	 	 	$("#btnParaOK").html("确认");
		}
	}
	//清除表单错误验证
	if(winId == "paraTypeOperDiv"){
		$("#paraTypeOperForm").validate().resetForm();
	}else if(winId == "paraOperDiv"){
		$("#paraOperForm").validate().resetForm();
	}
	
	$("#operType").val(operType);//
	$("#" + winId).modal("show");
}
//保存\修改参数类型
function saveOrUpdateParaType(){
	
	 //触发表单验证
	 if(!$("#paraTypeOperForm").valid()){
		 return;
	 };
	
	RestfulClient.post(contextPath + "/oauthResource/saveOrUpdParaType", 
			 {
		 		"extend" :{
		 			"operType" : $("#operType_Res").val(),
		 			"id" : $("#id_Res").val() ,
		 			"appId" : $("#appId_Res").val() ,
		 			"resourceId" : $("#resourceId_Res").val() ,
		 			"invalidFlag": $("#invalidFlag_Res").val()
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg,"success","");//弹出提示框
				 $("#paraOperDiv").modal("hide");//隐藏操作窗口
				 //getParaTypeJson(false , null , null , null , null , null);
				 getParaJson($("#appId_Res").val());
   		}
	);
}

function createUUID(operType){
	  var len=32;//32长度
	  var radix=16;//16进制
	  var chars='0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');var uuid=[],i;radix=radix||chars.length;if(len){for(i=0;i<len;i++)uuid[i]=chars[0|Math.random()*radix];}else{var r;uuid[8]=uuid[13]=uuid[18]=uuid[23]='-';uuid[14]='4';for(i=0;i<36;i++){if(!uuid[i]){r=0|Math.random()*16;uuid[i]=chars[(i==19)?(r&0x3)|0x8:r];}}}
	  if(operType == 0) {
		  $("#appId").val(uuid.join(''));
	  }else if(operType == 1) {
		  $("#appSecret").val(uuid.join(''));
	  }
}  

//保存\修改参数
function saveOrUpdatePara(){
	
	//触发表单验证
	 if(!$("#paraOperForm").valid()){
		 return;
	 };
	
	RestfulClient.post(contextPath + "/auth/saveOrUpdParaType", 
			 {
		 		"extend" :{
		 			"operType" : $("#operType").val(),
		 			"appId" : $("#appId").val() ,
		 			"appSecret" : $("#appSecret").val() ,
		 			"userName" : $("#userName").val() ,
		 			"phoneNumber": $("#phoneNumber").val() ,
		 			"callbackUrl": $("#callbackUrl").val() ,
		 			"invalidFlag": $("#invalidFlag").val()
		 		}
			 },
			 function(data) {
				 if(data.flag){
					 alertMsg(data.msg,"success","");//弹出提示框
					 $("#paraTypeOperDiv").modal("hide");//隐藏操作窗口
					 ++ clickCount;
					 getParaJson($("#appId").val());
					 refreshPage();
				 }else{
					 alert(data.msg);
				 }

   		}
	);
}

//显示系统参数
function showParaByType(paraType){
	
	//显示系统参数
	$("#paraDiv").removeClass("hidden");
	//为参数窗口赋值paraType
	$("#parameterType").val(paraType);
	$("#parameterType").attr("readOnly","true");

	++ clickCount;
	$("#appId_Res").val(paraType);
	getParaJson(paraType);
}

//格式化参数类型
function showParameterType(cellvalue, options, rowObject){
	return '<a href="#" onclick="javascript:showParaByType(\'' + rowObject.appId + '\');">' + cellvalue + '</a>';
} 

function showValidFlag(cellvalue, options, rowObject){
	var ret = '-';
	if(cellvalue == '1'){
		ret = validFlagTxt1;
	}else if(cellvalue == '0'){
		ret = '<font color=red>' + validFalgTxt0 + '</font>';
	}
	return ret;
} 

$(function(){
	
	///////加载参数类型列表////////
	getParaTypeJson(true, null , null , null , null , null);
   /////// 表单验证系统参数类型
	
	var validate = $("#paraOperForm").validate({
        
        rules:{
        	appId:{
                required:true,
                byteRangeLength:[0,100]
            },
            appSecret:{
            	required:true,
            	byteRangeLength:[0,400]
             },
             userName:{
            	required:true,
            	byteRangeLength:[0,400]
	        },
	        invalidFlag:{
            	required:true 
            } 
			                   
        }
     }); 
	
	var validate = $("#paraTypeOperForm").validate({
        
        rules:{
        	appId_Res:{
                required:true,
                byteRangeLength:[0,100]
            },
            invalidFlag_Res:{
            	required:true,
            	byteRangeLength:[0,400]
             },
            resourceId_Res:{
            	required:true,
            	byteRangeLength:[0,400]
	        }    
        }
     }); 
	
	
	$("#btnQry").on("click", function () {
		refreshPage();
		
		if(!$("#paraDiv").hasClass("hidden")){
			$("#paraDiv").addClass("hidden");
		}
	});
	
	$(function() {
        $(document).scroll(function() {
            $("#paraTable").setGridWidth($(window).width()-10);
            $("#paraTypeTable").setGridWidth($(window).width()-10);
        });
        $(window).resize(function() {
            $("#paraTable").setGridWidth($(window).width()-10);
            $("#paraTypeTable").setGridWidth($(window).width()-10);
        });
    });
	
});