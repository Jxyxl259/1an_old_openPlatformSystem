//刷新页面
function refreshPage(){
//	window.location.href = contextPath + "/job/jobManage" ;
	searchByName();
}
var jobStatusDate ;
//清空值
function clearValue(){
	var queryConditionForm = document.getElementById("queryConditionForm");
	queryConditionForm.reset();
}

//条件查询
function searchByName(){
	var searchJobName=$("#searchJobName").val();
	var searchJobGroupName=$("#searchJobGroupName").val();
	var searchJobStatus=$("#searchJobStatus").val();
	getJobData(false,searchJobName,searchJobGroupName,searchJobStatus);
/*	RestfulClient.post(contextPath + "/job/query", 
			 {
		 		"extend" :{
		 			"jobName":searchJobName,
		 			"jobGroupName" : searchJobGroupName,
		 			"jobStatus" :searchJobStatus
		 		}
			 },
			 function(data) {
				 loadJobTable(data.dataList);
 		}
	);*/
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


//加载代码转换表格按钮
function loadJobTableButtons(){
	//按钮组
	$("#jobTable")  
	.navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh:false}) 
	.navButtonAdd('#gridPager',{  
		 title:addBtnTxt, 
		 caption:'',
	  buttonicon:"fa-plus",   
	  onClickButton: function(){   
		  openWin('add' , 'jobOperDiv' , '');
	  },   
	  position:"last"  
	})
	.navSeparatorAdd("#gridPager",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager',{  
		title:editBtnTxt, 
		caption:'',
		buttonicon:"fa-edit",   
		onClickButton: function(){   
			openWin('edit' , 'jobOperDiv' , '');
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
		title:jobStatusL, 
		caption:'',
		buttonicon:"fa-lock",   
		onClickButton: function(){   
			bannedRow('lock');
		},   
		position:"last"  
	})	
	.navSeparatorAdd("#gridPager",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager',{  
		title:jobStatusU, 
		caption:'',
		buttonicon:"fa-unlock",   
		onClickButton: function(){   
			bannedRow('unlock');
		},   
		position:"last"  
	}); 
}
//加载或刷新表格
function loadJobTable(dataList){
	 $("#jobTable").jqGrid( {
	          datatype : "local",
	          colNames : [ jobIdT,jobNameT,jobGroupNameT, cronExpressionT,jobStatusT,'jobStatus',remarkT,operT,'','','','',''],
	          colModel : [ 
	                       {name : 'jobId',index : 'jobId', hidden: true}, 
	                       {name : 'jobName',index : 'jobName',width : 80,align : "center" },
	                       {name : 'jobGroupName',index : 'jobGroupName',width : 80,align : "center" }, 
	                       {name : 'cronExpression',index : 'cronExpression',width : 100,align : "center"}, 
	                     
	                       {name : 'jobStatus',index : 'jobStatus',width : 100,align : "center",formatter: showJobStatus}, 
	                       {name : 'jobStatus',index : 'jobStatus', hidden: true}, 
	                       
	                       {name : 'remark',index : 'remark',width : 100,align : "center"},
	                       {name : 'handle',index : 'handle',width : 100,align : "center",formatter:showHandle},
	                       {name : 'cronExpression',index : 'cronExpression', hidden: true}, 
	                       {name : 'jobClass',index : 'jobClass', hidden: true}, 
	                       {name : 'springId',index : 'springId', hidden: true}, 
	                       {name : 'methodName',index : 'methodName', hidden: true}, 
	                       {name : 'restfulUrl',index : 'restfulUrl', hidden: true}
	                     ],
	          rowNum : 10,
	          rowList : [ 10, 20, 30 ],
	          sortname : 'jobGroupName',
	          autowidth:true,//自动宽
	          height:'auto',
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
   $("#jobTable").jqGrid().clearGridData();
   for ( var i = 0; i < dataList.length; i++){
       $("#jobTable").jqGrid('addRowData', i + 1, dataList[i]);
     }
   //加载完数据，分页
   var rowNumTmp = jQuery("#jobTable").jqGrid('getGridParam','rowNum');
   $("#jobTable").setGridParam({ rowNum: rowNumTmp }).trigger("reloadGrid");
}

//查询定时任务列表
function getJobData(buttonFlag,searchJobName,searchJobGroupName,searchJobStatus){
	RestfulClient.post(contextPath + "/job/list", {
		"extend" :{
 			"jobName":searchJobName,
 			"jobGroupName" : searchJobGroupName,
 			"jobStatus" :searchJobStatus
		}
	}, function(data) {
        var dataList = data.dataList;
        loadJobTable(dataList);
        if(buttonFlag){
        	loadJobTableButtons();
        }
        
    } );
}

//删除确认提示
function delRow(){
		var ids=$("#jobTable").jqGrid("getGridParam","selarrrow");
		if(ids.length <= 0){
			alertMsg(deleteRowMsgTxt,"error","");
		}else{
			alertMsg(deleteRowMsgSureTxt,"confirm","delRowSure()");//确认后调用delRowSure()
		}
		
}

//确认删除
function delRowSure(){
	
	 var ids=$("#jobTable").jqGrid("getGridParam","selarrrow");
 	 var arr = [] ;
	 for(var k = 0;k<ids.length;k++){
		 var rowData = $("#jobTable").jqGrid("getRowData",ids[k]);
		 arr.push(rowData.jobId);
	 }
	
	RestfulClient.post(contextPath + "/job/deleteJob", 
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
	$("#jobOperForm").validate().resetForm();
	//显示隐藏按钮
	if($("#btnOk").hasClass("hidden")){
		$("#btnOk").removeClass("hidden");
	}
	
	 $("#jobStatus").children("option").remove();
	 for(var i=0;i<jobStatusDate.length;i++){			
		 $('#jobStatus').append("<option value=\""+jobStatusDate[i].parameterCode+"\">"+jobStatusDate[i].parameterName+"</option>");
	 }
	 
	
	//修改时验证，一次只能修改一条
	if(operType.toLowerCase() == "edit"){
		var ids=$("#jobTable").jqGrid("getGridParam","selarrrow");
		if(ids.length <= 0){
			alertMsg(editMsgTxt,"error","");
			return;
		}else if(ids.length > 1){
			alertMsg(editOneMsgTxt,"error","");
			return;
		}else{
			setFormValue(ids[0]);
		}
		
		$("#jobOperDivTitle").html(editBtnTxt+jobT);
 	 	$("#btnOk").html(editBtnTxt);
		
	}else{
		$("#jobOperDivTitle").html(addBtnTxt+jobT);
 	 	$("#btnOk").html(addBtnTxt);
 	 	//表单清空
 	 	clearFrom();
 	 	
 	 	$("#jobStatus").val('0');

	}
	var options = $('#jobStatus').children('option');
	for(var i=0;i<options.length;i++){
		if(options[i].selected==true){
			options.not(options.eq(i)).remove();
		}
	}
	$("#operType").val(operType);//
	$("#" + winId).modal("show");
}

//清空表单
function clearFrom(){
	document.getElementById("jobOperForm").reset();
}

//修改前表单赋值
function setFormValue(rowId){
	var rowData = $("#jobTable").jqGrid("getRowData",rowId);
	$("#jobId").val(rowData.jobId);
	$("#jobName").val(rowData.jobName);
	$("#jobGroupName").val(rowData.jobGroupName);
	$("#jobStatus").val(rowData.jobStatus);
	$("#cronExpression").val(rowData.cronExpression);
	$("#jobClass").val(rowData.jobClass);
	$("#springId").val(rowData.springId);
	$("#methodName").val(rowData.methodName);
	$("#restfulUrl").val(rowData.restfulUrl);
	$("#remark").val(rowData.remark);
}

//保存\修改任务
function saveOrUpdateJob(){	
 	 //触发表单验证
	 if(!$("#jobOperForm").valid()){
		 return;
	 }; 
	 
	 var jobClass = $('#jobClass').val();
	 var springId =  $('#springId').val();
	 var restfulUrl =  $('#restfulUrl').val();
	 var methodName = $('#methodName').val();
	 if((jobClass==""||jobClass==null)&&(springId==""||springId==null)&&(restfulUrl==""||restfulUrl==null)){
		 alert(errorConf);
		 return;
	 }
	 
	 if(jobClass!=""&&jobClass!=null){
		 if(springId!=""&&springId!=null||restfulUrl!=""&&restfulUrl!=null){
			 alert(errorConf);
			 return;
		 }
	 }
	 
	 if(springId!=""&&springId!=null){
		 if(jobClass!=""&&jobClass!=null||restfulUrl!=""&&restfulUrl!=null){
			 alert(errorConf);
			 return;
		 }
	 }
	 
	 if(restfulUrl!=""&&restfulUrl!=null){
		 if(springId!=""&&springId!=null||jobClass!=""&&jobClass!=null){
			 alert(errorConf);
			 return;
		 }
	 }
	 
	 if(jobClass!=""&&jobClass!=null||springId!=""&&springId!=null){
		 if(methodName==""||methodName==null){
			 alert(errorNull);
			 return;
		 }
	 }
	 
	var operType = $("#operType").val();
	var jobId = $("#jobId").val();
	if('add'==operType){
		saveJob();
	}else{
		update();
	}
}

//修改定时任务
function update(){
	var jobOperForm = $('#jobOperForm').serializeObject();
	RestfulClient.post(contextPath + "/job/updateJob", 
			 {
		        "form" : jobOperForm
			 },
			 function(data) {
				 alertMsg(data.msg,"success","");//弹出提示框
				 $("#jobOperDiv").modal("hide");//隐藏操作窗口
				 searchByName();
   		}
	);
}

//新增定时任务
function saveJob(){
	var jobOperForm = $('#jobOperForm').serializeObject();
	RestfulClient.post(contextPath + "/job/saveJob", 
			 {
		        "form" : jobOperForm
			 },
			 function(data) {
				 alertMsg(data.msg,"success","");//弹出提示框
				 $("#jobOperDiv").modal("hide");//隐藏操作窗口
				 searchByName();
   		}
	);
}

//开关
function showHandle(cellvalue, options, rowObject){
	var status =  rowObject.jobStatus;
	var handle = '-';
	if(status==0||status==2){
		handle = '<button type="button" onclick="runningJob(\''+rowObject.jobId+'\')"  class="btn btn-primary">'+btnR+'</button>';
	}else if(status==1){
		handle = '<button type="button" onclick="stopJob(\''+rowObject.jobId+'\')"  class="btn btn-primary">'+btnS+'</button>';;
	}else{
		handle = '-';
	}
	
	return handle;
}

//运行任务状态
function runningJob(jobId,jobStatus){
	RestfulClient.post(contextPath + "/job/runningJob", 
			 {
		 		"extend" :{
		 			"jobId" : jobId
		 		}
			 },
			 function(data) {
				 searchByName();
 		}
	);
}

//停止任务状态
function stopJob(jobId,jobStatus){
	RestfulClient.post(contextPath + "/job/stopJob", 
			 {
		 		"extend" :{
		 			"jobId" : jobId
		 		}
			 },
			 function(data) {
				 searchByName();
 		}
	);
}

//获取页面参数
function getPagePara(){
	RestfulClient.post(contextPath + "/job/getJobStatusList", 
			 {
		 		"extend" :null
			 },
			 function(data) {
				 $("#searchJobStatus").children("option").not($("#searchJobStatus").children("option").eq(0)).remove();
				 $("#jobStatus").children("option").not($("#jobStatus").children("option").eq(0)).remove();
				 var dataList = data.dataList;
				 jobStatusDate= data.dataList;
				 for(var i=0;i<dataList.length;i++){			
					 $('#searchJobStatus').append("<option value=\""+dataList[i].parameterCode+"\">"+dataList[i].parameterName+"</option>");
					 $('#jobStatus').append("<option value=\""+dataList[i].parameterCode+"\">"+dataList[i].parameterName+"</option>");
				 }
		}
	);
}

//禁用
function bannedRow(flag){
	var ids=$("#jobTable").jqGrid("getGridParam","selarrrow");
	if(ids.length <= 0){
		alertMsg(bannedRowMsgTxt,"error","");
	}else{
		 for(var k = 0;k<ids.length;k++){
			 var rowData = $("#jobTable").jqGrid("getRowData",ids[k]);
			 if(rowData.jobStatus=="1"){
				 var msg = rowData.jobName + msgBanned;
				 alertMsg(msg, "error", "");
				 return;
			 }
		 }
		alertMsg(bannedRowMsgSureTxt,"confirm","bannedRowSure(\'"+flag+"\')");//确认后调用bannedRowSure()
	}
	
}
//确认禁用,启用
function bannedRowSure(flag){
	if('lock'==flag){
		lock();
	}else{
		unlock();
	}
}

//禁用
function lock(){
	 var ids=$("#jobTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 for(var k = 0;k<ids.length;k++){
		 var rowData = $("#jobTable").jqGrid("getRowData",ids[k]);
		 if(rowData.jobStatus=='3'){
			 var msg = rowData.jobName+errorLockT;
			 alert(msg);
			 $('#alertModal').modal('hide');
			 return;
		 }
		 arr.push(rowData.jobId);
	 }
	
	RestfulClient.post(contextPath + "/job/lockJob", 
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

//启用
function unlock(){
	 var ids=$("#jobTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 for(var k = 0;k<ids.length;k++){
		 var rowData = $("#jobTable").jqGrid("getRowData",ids[k]);
		 if(rowData.jobStatus!='3'){
			 var msg = rowData.jobName+errorUnlockT;
			 alert(msg);
			 $('#alertModal').modal('hide');
			 return;
		 }
		 arr.push(rowData.jobId);
	 }
	
	RestfulClient.post(contextPath + "/job/unlockJob", 
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

function showJobStatus(cellvalue, options, rowObject){
	var ret = '-';
	if(cellvalue == '0'){
		ret = jobStatusN;
	}else if(cellvalue == '1'){
		ret = jobStatusR;
	}else if(cellvalue == '2'){
		ret = jobStatusS;
	}else if(cellvalue == '3'){
		ret = jobStatusL;
	}
	return ret;
} 

$(function(){
	//加载代码转换数据
	getJobData(true,null,null,null);
    
	getPagePara();
    // 表单验证系统配置
	var validate = $("#jobOperForm").validate({
        rules:{
        	jobName:{
                required:true,
                byteRangeLength:[0,400]
            },
            jobGroupName:{
            	required:true,
            	byteRangeLength:[0,400]
             },
             jobStatus:{
            	required:true,
            	byteRangeLength:[0,3]
            },
            cronExpression:{
            	required:true,
	        	byteRangeLength:[0,100]
	        } 
			                   
        }
     }); 

});