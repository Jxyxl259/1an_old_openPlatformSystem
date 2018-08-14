//刷新页面
function refreshPage(){
	/*window.location.href = contextPath + "/mapping/mappingManage" ;*/
	searchByName();
}

//清空值
function clearValue(){
	var queryConditionForm = document.getElementById("queryConditionForm");
	queryConditionForm.reset();
}

//按代码转换名称查询
function searchByName(){
	var searchMappingType=$("#searchMappingType").val();
	var searchMappingName=$("#searchMappingName").val();
	var searchMappingFrom=$("#searchMappingNameFrom").val();
	var searchMappingTo=$("#searchMappingNameTo").val();
	var searchValidFlag=$("#searchValidFlag").val();
	getMappingData(false,searchMappingType ,searchMappingName , searchMappingFrom ,
				searchMappingTo , searchValidFlag);
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
	var ids=$("#mappingTable").jqGrid("getGridParam","selarrrow");
	if(ids.length <= 0){
		alertMsg(bannedRowMsgTxt,"error","");
	}else{
		alertMsg(bannedRowMsgSureTxt,"confirm","bannedRowSure()");//确认后调用bannedRowSure()
	}
	
}
//确认禁用,启用
function bannedRowSure(){
	
	 var ids=$("#mappingTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 for(var k = 0;k<ids.length;k++){
		 var rowData = $("#mappingTable").jqGrid("getRowData",ids[k]);
		 arr.push(rowData.mappingId+','+rowData.validFlag);
	 }
	
	RestfulClient.post(contextPath + "/mapping/updateValidFlag", 
			 {
		 		"extend" :{
		 			"datas" : JSON.stringify(arr) 
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg, "success" , "");
				 searchByName();
				 
  		}
	);
}

//加载代码转换表格按钮
function loadTableButtons(){
	//按钮组
	$("#mappingTable")  
	.navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh:false}) 
	.navButtonAdd('#gridPager',{  
		 title:addBtnTxt, 
		 caption:'',
	  buttonicon:"fa-plus",   
	  onClickButton: function(){   
		   openWin('add' , 'mappingOperDiv' , '');
	  },   
	  position:"last"  
	})
	.navSeparatorAdd("#gridPager",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager',{  
		title:editBtnTxt, 
		caption:'',
		buttonicon:"fa-edit",   
		onClickButton: function(){   
			openWin('edit' , 'mappingOperDiv' , '');
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
function loadMappingTable(mappingList){
	 $("#mappingTable").jqGrid( {
	          datatype : "local",
	          colNames : [ mappingIDT,mappingTypeT,mappingNameT, mappingFromT,mappingToT,validFlagshowT,'validFlag', remarkT],
	          colModel : [ 
	                       {name : 'mappingId',index : 'mappingId', hidden: true}, 
	                       {name : 'mappingType',index : 'mappingType',align : "center" },
	                       {name : 'mappingName',index : 'mappingName',align : "center" }, 
	                       {name : 'mappingFrom',index : 'mappingFrom',align : "center"}, 
	                       {name : 'mappingTo',index : 'mappingTo',align : "center"}, 
	                       
	                       {name : 'validFlag',index : 'validFlag',align : "center", formatter: showValidFlag},
	                       {name : 'validFlag',index : 'validFlag', hidden: true},  
	                       
	                       {name : 'remark',index : 'remark',align : "center"}
	                     ],
	          rowNum : 10,
	          rowList : [ 10, 20, 30 ],
	          sortname : 'mappingType',
	          autowidth:true,//自动宽
	          height:300,
	          viewrecords : true,
	          sortorder : "desc",
	          multiselect : true,
	          viewrecords: true,//是否在浏览导航栏显示记录总数
	      //  emptyrecords: "no record",
	          recordpos : 'right',//定义了记录信息的位置
	     //   recordtext:"{0} - {1} 共 {2}条", 
	       // pgtext : " {0}  共 {1}页" ,
	          pager : '#gridPager'
	        });
   
   ////先清空
   $("#mappingTable").jqGrid().clearGridData();
   for ( var i = 0; i < mappingList.length; i++){
       $("#mappingTable").jqGrid('addRowData', i + 1, mappingList[i]);
     }
   //加载完数据，分页
   var rowNumTmp = jQuery("#mappingTable").jqGrid('getGridParam','rowNum');
   $("#mappingTable").setGridParam({ rowNum: rowNumTmp }).trigger("reloadGrid");
}

//查询代码转换
function getMappingData(buttonFlag,searchMappingType ,searchMappingName , searchMappingFrom ,
		searchMappingTo , searchValidFlag){
	RestfulClient.post(contextPath + "/mapping/getMappingList", 
			{
		"extend":{
			"mappingType":searchMappingType,
			"mappingName":searchMappingName,
			"mappingFrom":searchMappingFrom,
			"mappingTo":searchMappingTo,
			"validFlag":searchValidFlag
		}
		
	}, function(data) {
        var dataList = data.dataList;
        loadMappingTable(dataList);
        if(buttonFlag){
        	loadTableButtons();
        }
        
    } );
}

//删除确认提示
function delRow(){
		var ids=$("#mappingTable").jqGrid("getGridParam","selarrrow");
		if(ids.length <= 0){
			alertMsg(deleteRowMsgTxt,"error","");
		}else{
			alertMsg(deleteRowMsgSureTxt,"confirm","delRowSure()");//确认后调用delRowSure()
		}
		
}

//确认删除
function delRowSure(){
	
	 var ids=$("#mappingTable").jqGrid("getGridParam","selarrrow");
 	 var arr = [] ;
	 for(var k = 0;k<ids.length;k++){
		 var rowData = $("#mappingTable").jqGrid("getRowData",ids[k]);
		 arr.push(rowData.mappingId);
	 }
	
	RestfulClient.post(contextPath + "/mapping/deleteMapping", 
			 {
		 		"extend" :{
		 			"ids" : JSON.stringify(arr) 
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg , "success" , "");
				 searchByName();
  		}
	); 
}

//新增/修改类型
function openWin(operType , winId , rowId){
	$("#mappingOperForm").validate().resetForm();
	//显示隐藏按钮
	if($("#btnOk").hasClass("hidden")){
		$("#btnOk").removeClass("hidden");
	}
	//修改时验证，一次只能修改一条
	if(operType.toLowerCase() == "edit"){
		var ids=$("#mappingTable").jqGrid("getGridParam","selarrrow");
		if(ids.length <= 0){
			alertMsg(editMsgTxt,"error","");
			return;
		}else if(ids.length > 1){
			alertMsg(editOneMsgTxt,"error","");
			return;
		}else{
			setFormValue(ids[0]);
		}
		
		$("#mappingOperDivTitle").html(mappingTittle);
 	 	$("#btnOk").html(editBtnTxt);
		
	}else{
		$("#mappingOperDivTitle").html(addBtnTxt+mappingTittle);
 	 	$("#btnOk").html(addBtnTxt);
 	 	//表单清空
 	 	$("#mappingType").val('');
 	 	$("#mappingId").val('');
 	 	$("#mappingName").val('');
 	 	$("#mappingFrom").val('');
 	 	$("#mappingTo").val('');
 	 	$("#radiobtn").children().remove();
 	 	$("#radiobtn").html('');
		$("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' checked='checked'	value='1'>").append(radioBtnY+" ");
		$("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' value='0'>").append(radioBtnN+" ");
 	 	$("#remark").val('');
	}
	$("#operType").val(operType);//
	$("#" + winId).modal("show");
}

function setFormValue(rowId){
	var rowData = $("#mappingTable").jqGrid("getRowData",rowId);
	$("#mappingType").val(rowData.mappingType);
	$("#mappingId").val(rowData.mappingId);
	$("#mappingName").val(rowData.mappingName);
	$("#mappingFrom").val(rowData.mappingFrom);
	$("#mappingTo").val(rowData.mappingTo);
    if(rowData.validFlag =="1"){
    	 $("#radiobtn").children().remove();
		 $("#radiobtn").html('');
		 $("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' checked='checked'	value='1'>").append(radioBtnY+" ");
		 $("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' value='0'>").append(radioBtnN+" ");
    }else{
    	 $("#radiobtn").children().remove();
		 $("#radiobtn").html('');
		 $("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' value='1'>").append(radioBtnY+" ");
		 $("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' checked='checked'	value='0'>").append(radioBtnN+" ");
    }
	$("#remark").val(rowData.remark);
}

//保存\修改系统配置
function saveOrUpdateMapping(){
	
 	 //触发表单验证
	 if(!$("#mappingOperForm").valid()){
		 return;
	 }; 
	 
	var operType = $("#operType").val();
	var mappingId = $("#mappingId").val();
	var mappingOperForm = $('#mappingOperForm').serializeObject();
	RestfulClient.post(contextPath + "/mapping/saveOrUpdateMapping", 
			 {
		        "form" : mappingOperForm,
		 		"extend" :{
		 			"operType" : operType
		 		}
			 },
			 function(data) {
				 if(data.flag){
					 alertMsg(data.msg,"success","");//弹出提示框
					 $("#mappingOperDiv").modal("hide");//隐藏操作窗口
					 searchByName();
				 }else{
					 alert(data.msg);
				 }
   		}
	);
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
	//加载代码转换数据
	getMappingData(true,null,null,null,null,null);
	
    // 表单验证系统配置
	var validate = $("#mappingOperForm").validate({
        
        rules:{
        	mappingType:{
                required:true,
                byteRangeLength:[0,100]
            },
        	mappingName:{
                required:true,
                byteRangeLength:[0,400]
            },
            mappingFrom:{
            	required:true,
            	byteRangeLength:[0,100]
             },
            mappingTo:{
            	required:true,
            	byteRangeLength:[0,100]
            },
	        remark:{
	        	byteRangeLength:[0,400]
	        } 
			                   
        }
     });  

});