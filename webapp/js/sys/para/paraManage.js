
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

var clickCount = 0;//是否刷新系统参数table控制标志
//刷新页面
function refreshPage(){
//	window.location.href = contextPath + "/parameterType/manage" ;
	var parameterType_search_key = $("#parameterType_search").val();
	var parameterCname_search_key = $("#parameterCname_search").val();
	var parameterEname_search_key = $("#parameterEname_search").val();
	var parameterTname_search_key = $("#parameterTname_search").val();
	var validFlag_search_key = $("#validFlag_search").val();
	getParaTypeJson(false , parameterType_search_key , parameterCname_search_key , parameterEname_search_key , parameterTname_search_key , validFlag_search_key );
	
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
	})
	.navSeparatorAdd("#gridPager1",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager1',{  
		title:bannedBtnTxt, 
		caption:'',
		buttonicon:"fa-lock",   
		onClickButton: function(){   
			bannedParaType();
		},   
		position:"last"  
	}); 
}
//加载或刷新表格
function reloadParaTypeTable(parameterTypeList){
	 $("#paraTypeTable").jqGrid( {
	          datatype : "local",
	          colNames : [ parameterTypeIdText, parameterTypeText,parameterTypeText,cname, ename, tname, validFlagTxt,validFlagTxt, flagTxt,remarkTxt ],
	          colModel : [ 
	                       {name : 'parameterTypeId',index : 'parameterTypeId', hidden: true}, 
	                       {name : 'parameterType',index : 'parameterTypeShow',width : 100,align : "center" , formatter: showParameterType}, 
	                       {name : 'parameterType',index : 'parameterType', hidden: true }, 
	                       {name : 'parameterTypeCname',index : 'parameterTypeCname',width : 100,align : "center"}, 
	                       {name : 'parameterTypeEname',index : 'parameterTypeEname',width : 100,align : "center"}, 
	                       {name : 'parameterTypeTname',index : 'parameterTypeTname',width : 100,align : "center"}, 
	                       {name : 'validFlag',index : 'validFlagShow',width : 80,align : "center", formatter: showValidFlag},
	                       {name : 'validFlag',index : 'validFlag', hidden: true}, 
	                       {name : 'flag',index : 'flag', hidden: true} , 
	                       {name : 'remark',index : 'remark', hidden: true}
	                     ],
	          rowNum : 10,
	          rowList : [ 10, 20, 30 ],
	          autowidth:true,//自动宽
	          //height : 'auto',
	          //height :300,
	          viewrecords : true,
	          sortname : 'parameterTypeId',
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
	  })
	  .navSeparatorAdd("#gridPager2",{sepclass : 'ui-separator',sepcontent: ''})
	  .navButtonAdd('#gridPager2',{  
	  	title:bannedBtnTxt, 
	  	caption:'',
	  	buttonicon:"fa-lock",   
	  	onClickButton: function(){   
	  		bannedPara();
	  	},   
	  	position:"last"  
	  }); 
}
//加载或刷新系统参数表格按钮
function reloadParaTable(paraList){
	
	$("#paraTable").jqGrid( {
        datatype : "local",
        colNames : [ parameterIdText,parameterTypeText,parameterCodeText , cname, ename, tname, validFlagTxt,validFlagTxt, flagTxt ,remarkTxt],
        colModel : [ 
                    {name : 'parameterId',index : 'parameterId', hidden:true }, 
                     {name : 'parameterType',index : 'parameterType',width : 100,align : "center" }, 
                     {name : 'parameterCode',index : 'parameterCode',width : 100,align : "center" }, 
                     {name : 'parameterCname',index : 'parameterCname',width : 100,align : "center"}, 
                     {name : 'parameterEname',index : 'parameterEname',width : 100,align : "center"}, 
                     {name : 'parameterTname',index : 'parameterTname',width : 100,align : "center"}, 
                     {name : 'validFlag',index : 'validFlag',width : 80,align : "center" , formatter: showValidFlag }, 
                     {name : 'validFlag',index : 'validFlag' , hidden:true  }, 
                     {name : 'flag',index : 'flag', hidden:true},
                     {name : 'remark',index : 'remark', hidden:true}
                   ],
        rowNum : 10,
        rowList : [ 10, 20, 30 ],
        sortname : 'parameterCode',
        autowidth:true,//自动宽
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
			contextPath + "/parameter/getParaList", 
			{
		        "form" : null,
		        "extend" : {
		            "paraType" : paraType
		        },
		        "page" : null
	     }, 
	     function(data) {
	      var paraList = data.paraList;
	      reloadParaTable(paraList);
	      if(clickCount == 1){
	    	  loadParaTableButtons();
	      }
	      
	  });
	
}
//系统参数类型
function getParaTypeJson(buttonFlag , parameterType_search_key , 
		parameterCname_search_key , parameterEname_search_key , parameterTname_search_key , validFlag_search_key){
	RestfulClient.post(contextPath + "/parameterType/getParaTypeList", {
		"extend":{
			"parameterType":parameterType_search_key,
			"parameterCname":parameterCname_search_key,
			"parameterEname":parameterEname_search_key,
			"parameterTname":parameterTname_search_key,
			"validFlag":validFlag_search_key
		}
	}, function(data) {
        var parameterTypeList = data.parameterTypeList;
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
			alertMsg(deleteRowMsgSureTxt + "<br>" + deleteRowMsgSureTxtAppend,"confirm","delParaTypeSure");//确认后调用delParaTypeSure()
		}
		
}
//确认删除
function delParaTypeSure(){
	
//	 $("#alertModal").modal("hide");//隐藏确认窗口
	 
	 var paraTypeIds=$("#paraTypeTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 for(var k = 0;k<paraTypeIds.length;k++){
		 var rowData = $("#paraTypeTable").jqGrid("getRowData",paraTypeIds[k]);
		 arr.push(rowData.parameterTypeId);
	 }
	
	RestfulClient.post(contextPath + "/parameterType/delParaType", 
			 {
		 		"extend" :{
		 			"ids" : JSON.stringify(arr) 
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
			alertMsg(deleteRowMsgSureTxt,"confirm","delParaSure");//确认后调用delParaSure()
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
		arr.push(rowData.parameterId);
		 parameterType = rowData.parameterType;
	 }
	
	RestfulClient.post(contextPath + "/parameter/delPara", 
			 {
		 		"extend" :{
		 			"ids" : JSON.stringify(arr) 
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg , "success" , "");
				 ++ clickCount;
				 getParaJson(parameterType);
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
				
				$("#parameterTypeId").val(rowData.parameterTypeId);
				$("#parameterTypeT").val(rowData.parameterType );
				$("#parameterTypeCname").val(rowData.parameterTypeCname);
				$("#parameterTypeEname").val(rowData.parameterTypeEname);
				$("#parameterTypeTname").val(rowData.parameterTypeTname);
				$("#validFlagT").val(rowData.validFlag);
				$("#flagT").val(rowData.flag);
				$("#remarkT").val(rowData.remark);
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
				var t = paraIds[0];
				var rowData = $("#paraTable").jqGrid("getRowData",paraIds[0]);
				
				$("#parameterId").val(rowData.parameterId);
//				$("#parameterType").val(rowData.parameterType);
				$("#parameterCode").val(rowData.parameterCode);
				$("#parameterCname").val(rowData.parameterCname);
				$("#parameterEname").val(rowData.parameterEname);
				$("#parameterTname").val(rowData.parameterTname);
				$("#validFlag").val(rowData.validFlag);
				$("#flag").val(rowData.flag);
				$("#remark").val(rowData.remark);
				
			}
			
		}
		
	}
	
	if(operType.toLowerCase() == "add"){
		if(winId == "paraTypeOperDiv"){
			$("#paraTypeOperDivTitle").html(addBtnTxt + newParameterTypeWinTitle);
	 	 	$("#btnParaTypeOK").html(addBtnTxt);
	 	 	$("#validFlagT").val('1');
	 	 	//表单清空
	 	 	$("#parameterTypeId").val('');
			$("#parameterTypeT").val('');
			$("#parameterTypeCname").val('');
			$("#parameterTypeEname").val('');
			$("#parameterTypeTname").val('');
			$("#flagT").val('');
			$("#remarkT").val('');
			
		}else if(winId == "paraOperDiv"){
			$("#paraOperDivTitle").html(addBtnTxt + newParameterWinTitle);
	 	 	$("#btnParaOK").html(addBtnTxt);
	 	 	$("#validFlag").val('1');
	 	 	//表单清空
	 	 	$("#parameterId").val('');
			$("#parameterCode").val('');
			$("#parameterCname").val('');
			$("#parameterEname").val('');
			$("#parameterTname").val('');
			$("#flag").val('');
			$("#remark").val('');
		}
		
	}else if(operType.toLowerCase() == "edit"){
		if(winId == "paraTypeOperDiv"){
			$("#paraTypeOperDivTitle").html(editBtnTxt + newParameterTypeWinTitle);
	 	 	$("#btnParaTypeOK").html(editBtnTxt);
		}else if(winId == "paraOperDiv"){
			$("#paraOperDivTitle").html(editBtnTxt + newParameterWinTitle);
	 	 	$("#btnParaOK").html(editBtnTxt);
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
	 
	var operType = $("#operType").val();
	var parameterTypeId = $("#parameterTypeId").val();
	var parameterType = $("#parameterTypeT").val();
	var parameterCname = $("#parameterTypeCname").val();
	var parameterEname = $("#parameterTypeEname").val();
	var parameterTname = $("#parameterTypeTname").val();
	var validFlag = $("#validFlagT").val();
	var flag = $("#flagT").val();
	var remark = $("#remarkT").val();
	
	RestfulClient.post(contextPath + "/parameterType/saveOrUpdParaType", 
			 {
		 		"extend" :{
		 			"operType" : operType,
		 			"parameterTypeId" : parameterTypeId ,
		 			"parameterType" : parameterType ,
		 			"parameterCname": parameterCname ,
		 			"parameterEname": parameterEname ,
		 			"parameterTname": parameterTname ,
		 			"validFlag": validFlag ,
		 			"flag": flag ,
		 			"remark": remark
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg,"success","");//弹出提示框
				 $("#paraTypeOperDiv").modal("hide");//隐藏操作窗口
				 getParaTypeJson(false , null , null , null , null , null);
   		}
	);
}
//保存\修改参数
function saveOrUpdatePara(){
	
	//触发表单验证
	 if(!$("#paraOperForm").valid()){
		 return;
	 };
	 
	var operType = $("#operType").val();
	var parameterId = $("#parameterId").val();
	var parameterType = $("#parameterType").val();
	var parameterCode = $("#parameterCode").val();
	var parameterCname = $("#parameterCname").val();
	var parameterEname = $("#parameterEname").val();
	var parameterTname = $("#parameterTname").val();
	var validFlag = $("#validFlag").val();
	var flag = $("#flag").val();
	var remark = $("#remark").val();
	
	RestfulClient.post(contextPath + "/parameter/saveOrUpdaPara", 
			 {
		 		"extend" :{
		 			"operType" : operType,
		 			"parameterId" : parameterId ,
		 			"parameterType" : parameterType ,
		 			"parameterCode" : parameterCode ,
		 			"parameterCname": parameterCname ,
		 			"parameterEname": parameterEname ,
		 			"parameterTname": parameterTname ,
		 			"validFlag": validFlag ,
		 			"flag": flag ,
		 			"remark": remark
		 		}
			 },
			 function(data) {
				 if(data.flag){
					 alertMsg(data.msg,"success","");//弹出提示框
					 $("#paraOperDiv").modal("hide");//隐藏操作窗口
					 ++ clickCount;
					 getParaJson(parameterType);
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
	getParaJson(paraType);
}

//格式化参数类型
function showParameterType(cellvalue, options, rowObject){
	return '<a href="#" onclick="javascript:showParaByType(\'' + rowObject.parameterType + '\');">' + cellvalue + '</a>';
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
	
	var validate = $("#paraTypeOperForm").validate({
        
        rules:{
        	parameterType:{
                required:true,
                byteRangeLength:[0,100]
            },
            parameterTypeCname:{
            	byteRangeLength:[0,400]
             },
            parameterTypeEname:{
            	byteRangeLength:[0,400]
	        },
	        parameterTypeTname:{
	        	byteRangeLength:[0,400]
	        },
	        flag:{
	        	byteRangeLength:[0,10]
	        },
	        remark:{
	        	byteRangeLength:[0,400]
	        } , 
            validFlag:{
            	required:true 
            } 
			                   
        }
     }); 
	//系统参数
	var validate = $("#paraOperForm").validate({
        
        rules:{
        	parameterType:{
                required:true,
                byteRangeLength:[0,100]
            },
            parameterCode:{
                required:true ,
                byteRangeLength:[0,100]
            },
            parameterCname:{
            	byteRangeLength:[0,400]
            },
            parameterEname:{
            	byteRangeLength:[0,400]
	        },
	        parameterTname:{
	        	byteRangeLength:[0,400]
	        },
	        flag:{
	        	byteRangeLength:[0,10]
	        },
	        remark:{
	        	byteRangeLength:[0,400]
	        } , 
            validFlag:{
            	required:true 
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