//刷新页面
function refreshPage(){
//	window.location.href = contextPath + "/config/manage" ;
	var configCode_search_key = $("#configCode_search").val();
	var configName_search_key = $("#configName_search").val();
	var configValue_search_key = $("#configValue_search").val();
	var validFlag_search_key = $("#validFlag_search").val();
	getConfigJsonData(false , configCode_search_key , configName_search_key,
			configValue_search_key , validFlag_search_key );
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

//禁用
function bannedRow(){
	var ids=$("#configTable").jqGrid("getGridParam","selarrrow");
	if(ids.length <= 0){
		alertMsg(bannedRowMsgTxt,"error","");
	}else{
		alertMsg(bannedRowMsgSureTxt,"confirm","bannedRowSure()");//确认后调用bannedRowSure()
	}
	
}
//确认禁用
function bannedRowSure(){
	
	 var ids=$("#configTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 for(var k = 0;k<ids.length;k++){
		 var rowData = $("#configTable").jqGrid("getRowData",ids[k]);
		 arr.push(rowData.configId);
	 }
	
	RestfulClient.post(contextPath + "/config/updateStates", 
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

//加载系统配置表格按钮
function loadConfigTableButtons(){
	//按钮组
	$("#configTable")  
	.navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh:false}) 
	.navButtonAdd('#gridPager',{  
		 title:addBtnTxt, 
		 caption:'',
	  buttonicon:"fa-plus",   
	  onClickButton: function(){   
		   openWin('add' , 'configOperDiv' , '');
	  },   
	  position:"last"  
	})
	.navSeparatorAdd("#gridPager",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager',{  
		title:editBtnTxt, 
		caption:'',
		buttonicon:"fa-edit",   
		onClickButton: function(){   
			openWin('edit' , 'configOperDiv' , '');
		},   
		position:"last"  
	})
	.navSeparatorAdd("#gridPager",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager',{  
		title:delBtnTxt, 
		caption:'',
		buttonicon:"fa-trash",   
		onClickButton: function(){   
			delRow();
		},   
		position:"last"  
	})
	.navSeparatorAdd("#gridPager",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager',{  
		title:bannedBtnTxt, 
		caption:'',
		buttonicon:"fa-lock",   
		onClickButton: function(){   
			bannedRow();
		},   
		position:"last"  
	}); 
}
//加载或刷新表格
function loadConfitTable(parameterTypeList){
	 $("#configTable").jqGrid( {
	          datatype : "local",
	          colNames : [ configIDTxt, configCodeTxt,configCodeTxt,configNameTxt,condition1Txt, 
	                       condition2Txt, condition3Txt,condition4Txt,condition5Txt,configValueTxt, 
	                       validFlagTxt,validFlagTxt, flagTxt,remarkTxt ],
	          colModel : [ 
	                       {name : 'configId',index : 'configId', hidden: true}, 
	                       
	                       {name : 'configCode',index : 'configCodeShow',width : 70,align : "center" , formatter: viewRow}, 
	                       {name : 'configCode',index : 'configCode', hidden: true}, 
	                       
	                       {name : 'configName',index : 'configName',width : 100,align : "center" }, 
	                       {name : 'condition1',index : 'condition1',width : 100,align : "center"}, 
	                       {name : 'condition2',index : 'condition2',width : 100,align : "center"}, 
	                       {name : 'condition3',index : 'condition3',width : 100,align : "center"}, 
	                       {name : 'condition4',index : 'condition4',width : 100,align : "center"}, 
	                       {name : 'condition5',index : 'condition5',width : 100,align : "center"}, 
	                       {name : 'configValue',index : 'configValue',width : 100,align : "center"}, 
	                       
	                       {name : 'validFlag',index : 'validFlagShow',width : 80,align : "center", formatter: showValidFlag},
	                       {name : 'validFlag',index : 'validFlag', hidden: true}, 
	                       
	                       {name : 'flag',index : 'flag',  width : 80,align : "center" ,sortable : false} ,
	                       {name : 'remark',index : 'remark',width : 150,align : "center"}
	                      
	                     ],
	          rowNum : 10,
	          rowList : [ 10, 20, 30 ],
	          sortname : 'configCode',
	          autowidth:true,//自动宽
	          height : 'auto',
	          viewrecords : true,
	          sortorder : "desc",
	          multiselect : true,
	          viewrecords: true,//是否在浏览导航栏显示记录总数
//	          emptyrecords: "没有数据！",
	          recordpos : 'right',//定义了记录信息的位置
//	          recordtext:"{0} - {1} 共 {2}条", 
//	          pgtext : " {0}  共 {1}页" ,
	          pager : '#gridPager'
	        });
   
   ////先清空
   $("#configTable").jqGrid().clearGridData();
   for ( var i = 0; i < parameterTypeList.length; i++){
       $("#configTable").jqGrid('addRowData', i + 1, parameterTypeList[i]);
     }
   //加载完数据，分页
   var rowNumTmp = jQuery("#configTable").jqGrid('getGridParam','rowNum');
   $("#configTable").setGridParam({ rowNum: rowNumTmp }).trigger("reloadGrid");
}
//系统配置
function getConfigJsonData(buttonFlag , configCode_search_key , configName_search_key,
		configValue_search_key , validFlag_search_key ){
	RestfulClient.post(contextPath + "/config/getCfgList", {
		"extend":{
			"configCode":configCode_search_key,
			"configName":configName_search_key,
			"configValue":configValue_search_key,
			"validFlag":validFlag_search_key
		}
	}, function(data) {
        var dataList = data.dataList;
        loadConfitTable(dataList);
        if(buttonFlag){
        	loadConfigTableButtons();
        }
        //
    } );
}
//删除确认提示
function delRow(){
	
		var ids=$("#configTable").jqGrid("getGridParam","selarrrow");
		if(ids.length <= 0){
			alertMsg(deleteRowMsgTxt,"error","");
		}else{
			alertMsg(deleteRowMsgSureTxt,"confirm","delRowSure()");//确认后调用delRowSure()
		}
		
}
//确认删除
function delRowSure(){
	
	 var ids=$("#configTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 for(var k = 0;k<ids.length;k++){
		 var rowData = $("#configTable").jqGrid("getRowData",ids[k]);
		 arr.push(rowData.configId);
	 }
	
	RestfulClient.post(contextPath + "/config/delCfg", 
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

//新增/修改类型
function openWin(operType , winId , rowId){
	
	//清除表单错误验证
	$("#configOperForm").validate().resetForm();
	//显示隐藏按钮
	if($("#btnOK").hasClass("hidden")){
		$("#btnOK").removeClass("hidden");
	}
	//修改时验证，一次只能修改一条
	if(operType.toLowerCase() == "edit"){
		
		var ids=$("#configTable").jqGrid("getGridParam","selarrrow");
		
		if(ids.length <= 0){
			alertMsg(editMsgTxt,"error","");
			return;
		}else if(ids.length > 1){
			alertMsg(editOneMsgTxt,"error","");
			return;
		}else{
			setFormValue(ids[0]);
		}
		
		$("#configOperDivTitle").html(editBtnTxt + newWinTitle);
 	 	$("#btnOK").html(editBtnTxt);
 	 	removeReadOnlyPro();
		
	}else if(operType.toLowerCase() == "add"){
		$("#configOperDivTitle").html(addBtnTxt + newWinTitle);
 	 	$("#btnOK").html(addBtnTxt);
 	 	//表单清空
 	 	$("#configId").val('');
 	 	$("#configCode").val('');
 	 	$("#configName").val('');
 	 	$("#condition1").val('');
 	 	$("#condition2").val('');
 	 	$("#condition3").val('');
 	 	$("#condition4").val('');
 	 	$("#condition5").val('');
 	 	$("#configValue").val('');
 	 	$("#validFlag").val('1');
 	 	$("#flag").val('');
 	 	$("#remark").val('');
 	 	removeReadOnlyPro();
 	 	
	}else{
		setFormValue(rowId);
		//
		addReadOnlyPro();

		$("#configOperDivTitle").html(viewBtnTxt + newWinTitle);
	 	$("#btnOK").addClass("hidden");//隐藏按钮
	}
	$("#operType").val(operType);//
	$("#" + winId).modal("show");
}
function setFormValue(rowId){
	
	var rowData = $("#configTable").jqGrid("getRowData",rowId);
	
	$("#configId").val(rowData.configId);
	$("#configCode").val(rowData.configCode);
	$("#configName").val(rowData.configName);
	$("#condition1").val(rowData.condition1);
	$("#condition2").val(rowData.condition2);
	$("#condition3").val(rowData.condition3);
	$("#condition4").val(rowData.condition4);
	$("#condition5").val(rowData.condition5);
	$("#configValue").val(rowData.configValue);
	$("#validFlag").val(rowData.validFlag);
	$("#flag").val(rowData.flag);
	$("#remark").val(rowData.remark);
}
//查看时添加只读属性
function addReadOnlyPro(){
	$("#configCode").attr("readOnly","true");
	$("#configName").attr("readOnly","true");
	$("#condition1").attr("readOnly","true");
	$("#condition2").attr("readOnly","true");
	$("#condition3").attr("readOnly","true");
	$("#condition4").attr("readOnly","true");
	$("#condition5").attr("readOnly","true");
	$("#configValue").attr("readOnly","true");
	$("#validFlag").attr("readOnly","true");
	$("#flag").attr("readOnly","true");
	$("#remark").attr("readOnly","true");
}
//移除只读属性
function removeReadOnlyPro(){
	$("#configCode").removeAttr("readOnly","true");
	$("#configName").removeAttr("readOnly","true");
	$("#condition1").removeAttr("readOnly","true");
	$("#condition2").removeAttr("readOnly","true");
	$("#condition3").removeAttr("readOnly","true");
	$("#condition4").removeAttr("readOnly","true");
	$("#condition5").removeAttr("readOnly","true");
	$("#configValue").removeAttr("readOnly","true");
	$("#validFlag").removeAttr("readOnly","true");
	$("#flag").removeAttr("readOnly","true");
	$("#remark").removeAttr("readOnly","true");
}
//保存\修改系统配置
function saveOrUpdateConfig(){
	
	 //触发表单验证
	 if(!$("#configOperForm").valid()){
		 return;
	 };
	 
	var operType = $("#operType").val();
	var configId = $("#configId").val();
	var configCode = $("#configCode").val();
	var configName = $("#configName").val();
	var condition1 = $("#condition1").val();
	var condition2 = $("#condition2").val();
	var condition3 = $("#condition3").val();
	var condition4 = $("#condition4").val();
	var condition5 = $("#condition5").val();
	var configValue = $("#configValue").val();
	var validFlag = $("#validFlag").val();
	var flag = $("#flag").val();
	var remark = $("#remark").val();
	 
	RestfulClient.post(contextPath + "/config/saveOrUpdCfg", 
			 {
		 		"extend" :{
		 			"operType" : operType,
		 			"configId" : configId ,
		 			"configCode" : configCode ,
		 			"configName": configName ,
		 			"condition1": condition1 ,
		 			"condition2": condition2 ,
		 			"condition3": condition3 ,
		 			"condition4": condition4 ,
		 			"condition5": condition5 ,
		 			"configValue": configValue ,
		 			"validFlag": validFlag ,
		 			"flag": flag ,
		 			"remark": remark
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg,"success","");//弹出提示框
				 $("#configOperDiv").modal("hide");//隐藏操作窗口
				 refreshPage();
   		}
	);
}


//格式化系统配置
function viewRow(cellvalue, options, rowObject){
	return '<a href="#" onclick="javascript:openWin(\'view\',\'configOperDiv\',\'' + options.rowId + '\');">' + cellvalue + '</a>';
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
	
	///////加载系统配置列表////////
	getConfigJsonData(true , null , null , null , null);
   /////// 表单验证系统配置
	
	var validate = $("#configOperForm").validate({
        
        rules:{
        	configCode:{
                required:true,
                byteRangeLength:[0,100]
            },
            configValue:{
            	required:true,
            	byteRangeLength:[0,100]
             }, 
             validFlag:{
             	required:true 
             },
             configName:{
            	 byteRangeLength:[0,400]
	        },
	        condition1:{
	        	byteRangeLength:[0,100]
	        },
	        condition2:{
	        	byteRangeLength:[0,100]
	        },
	        condition3:{
	        	byteRangeLength:[0,100]
	        },
	        condition4:{
	        	byteRangeLength:[0,100]
	        },
	        condition5:{
	        	byteRangeLength:[0,100]
	        },
	        flag:{
	        	byteRangeLength:[0,10]
	        },
	        remark:{
	        	byteRangeLength:[0,400]
	        } 
			                   
        }
     }); 
	
	$("#btnQry").on("click", function () {
		refreshPage();
	});

});