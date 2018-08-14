var refreshPageFlag = false;//是否刷新页面控制标志
//刷新页面
function refreshPage(){
//	window.location.href = contextPath + "/role/manage" ;
	var roleId_search_key = $("#roleId_search").val();
	var roleName_search_key = $("#roleName_search").val();
	var validFlag_search_key = $("#validFlag_search").val();
	getRoleJsonData(false , roleId_search_key , roleName_search_key, validFlag_search_key );
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
function bannedRow(){
	var ids=$("#roleTable").jqGrid("getGridParam","selarrrow");
	if(ids.length <= 0){
		alertMsg(bannedRowMsgTxt,"error","");
	}else{
		alertMsg(bannedRowMsgSureTxt,"confirm","bannedRowSure");//确认后调用bannedRowSure()
	}
	
}
//确认禁用
function bannedRowSure(){
	
	 var ids=$("#roleTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 for(var k = 0;k<ids.length;k++){
		 var rowData = $("#roleTable").jqGrid("getRowData",ids[k]);
		 arr.push(rowData.roleId);
	 }
	
	RestfulClient.post(contextPath + "/role/updateRoleState", 
			 {
		 		"extend" :{
		 			"roleIds" : JSON.stringify(arr) 
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg, "success" , "");
				 refreshPage();
    		}
	);
}
//删除确认提示
function delRow(){
		var ids=$("#roleTable").jqGrid("getGridParam","selarrrow");
		var leftChk = $("input[name='chk_list']");//如果全删了
		var allChk = $("#chk_all");//选中状态
		if(ids.length <= 0){
			alertMsg(deleteRowMsgTxt,"error","");
		}else{
			alertMsg(deleteRowMsgSureTxt + "<br>" + deleteRowMsgSureTxtAppend,"confirm","delRowSure");//确认后调用delRowSure()
		}
		
}
//确认删除
function delRowSure(){
	
	var ids=$("#roleTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 for(var k = 0;k<ids.length;k++){
		 var rowData = $("#roleTable").jqGrid("getRowData",ids[k]);
		 arr.push(rowData.roleId);
		
	 }
	
	RestfulClient.post(contextPath + "/role/delRole", 
			 {
		 		"extend" :{
		 			"roleIds" : JSON.stringify(arr) 
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg , "success" , "");
				 refreshPage();
    		}
	);
}

//新增/修改角色
function operRole(operType){
	//清除表单错误验证
	$("#roleOperForm").validate().resetForm();
	
	var tmpRoleId="";//待修改的roleId
	var tmpValidFlag="";//待修改的validFlag
	var tmpRoleName="";//待修改的roleName
	//修改时验证，一次只能修改一条
	if(operType.toLowerCase() == "edit"){
		var ids=$("#roleTable").jqGrid("getGridParam","selarrrow");
 		if(ids.length <= 0){
 			alertMsg(editMsgTxt,"error","");
 			return;
 		}else if(ids.length > 1){
 			alertMsg(editOneMsgTxt,"error","");
 			return;
 		}else{
 			var rowData = $("#roleTable").jqGrid("getRowData",ids[0]);
			tmpRoleId = rowData.roleId;
			tmpValidFlag = rowData.validFlag;
 			tmpRoleName = rowData.roleName;
 		}
	}
	
	 switch(operType.toLowerCase()){
	 	case "add":	 //新增
	 	 	$("#roleOperDivTitle").html(addBtnTxt + newWinTitle);
	 	 	$("#btnOk").html(addBtnTxt);
	 	 	//清空值
	 	 	$("#validFlag").val('1');
	 	 	$("#validFlag").attr("readonly","true");//新增默认有效，不允许修改
	 	 	$("#roleId").removeAttr("readonly");//如果先点击修改，在点击新增，此时roleId只读
	 	 	$("#roleId").val('');
	 	 	$("#roleName").val('');
	 		break;
	 	case "edit":	 //修改
	 	 	$("#roleOperDivTitle").html(editBtnTxt + newWinTitle);
	 	 	$("#btnOk").html(editBtnTxt);
	 	 	//赋值
	 	 	$("#validFlag").val(tmpValidFlag);
	 	 	$("#validFlag").removeAttr("readonly");
	 	 	$("#roleId").val(tmpRoleId);
	 	 	$("#roleId").attr("readonly","true");//不允许修改角色ID
	 	 	$("#roleName").val(tmpRoleName);
	 	 	break;
	 	default:;
	 }
	$("#roleOperType").val(operType);//
	$("#roleOperDiv").modal("show");
}
//保存\修改角色
function saveOrUpdateRole(){
	 //触发表单验证
	 if(!$("#roleOperForm").valid()){
		 return;
	 };
	 
	var roleOperType = $("#roleOperType").val();
	var roleId = $("#roleId").val();
	var roleName = $("#roleName").val();
	var validFlag = $("#validFlag").val();
	
	RestfulClient.post(contextPath + "/role/saveOrUpdateRole", 
			 {
		 		"extend" :{
		 			"operType" : roleOperType,
		 			"roleId" : roleId ,
		 			"roleName": roleName ,
		 			"validFlag": validFlag
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg,"success","");//弹出提示框
				 $("#roleOperDiv").modal("hide");//隐藏操作窗口
				 refreshPage();
     		}
	);
}
//////////////////////
var roleId = "";//角色编号
//保存角色资源关系
function saveRoleResource(){
	//json.stringify之后，后台需要先JSON.parse再操作
	var arr = $('#treeview-default').jstree().get_checked();
	//将半选中状态的节点也加入到待保存数组
	var halfSelected = $(".jstree-undetermined").parent().parent();
	if(halfSelected.length > 0){
		$.each(halfSelected , function(index,node){
			arr.push(node.id);
		});
	}
	 RestfulClient.post(contextPath + "/role/saveRoleResource", 
			 {
		 		"extend" :{
		 			"roleId" : roleId ,
		 			"resourceIds": JSON.stringify(arr)
		 		}
			 },
			 function(data) {
				 alertMsg(data.msg,"success","");
     		}
	);
}
//显示角色资源树
function showRoleResource(tmpRoleId){
	
	var treeview = $('#treeview-default');
	//判断树是否存在，已经渲染好的，当再次点击时首先移除，然后再添加
	if(treeview.has('ul')){
		treeview.remove(); 
		$("div[name='jszyDiv']").append("<div id = \"treeview-default\" ></div>");
	};
	//变更右侧角色资源title
	$("div[name='jszy']").html(roleResourceTitle + "【" + tmpRoleId + "】");
	//显示div
	$("#roleResourceDiv").removeClass("hidden");
	roleId = tmpRoleId;
	RestfulClient.post(
		contextPath + "/role/getRoleRescourceListData", 
		{
	        "form" : null,
	        "extend" : {
	            "roleId" : roleId
	        },
	        "page" : null
       }, 
       function(data) {
        var roleList = data.roleResources;
        var  treeviewDefault = $('#treeview-default') ; 
        treeviewDefault.jstree({
          'core' : {
				'data' : roleList,
				'cache': false,
				'check_callback' : true
	      },
         'plugins' : ['themes', 'types', 'checkbox', 'ui']
         
        }).on('loaded.jstree', function() {
         	treeviewDefault.jstree('open_all');
        });
        
        
    });
	
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
//加载jqgrid
function loadRoleTable(parameterTypeList){
	 $("#roleTable").jqGrid( {
	          datatype : "local",
	          colNames : [ roleIdText , roleIdText , roleNameText, validFlagTxt,validFlagTxt],
	          colModel : [ 
	                       {name : 'roleId',index : 'roleId', width : 100,align : "center" , formatter: viewRow}, 
	                       {name : 'roleId',index : 'roleId', hidden: true}, 
	                       
	                       {name : 'roleName',index : 'roleName',width : 100,align : "center" }, 
	                       
	                       {name : 'validFlag',index : 'validFlagShow',width : 80,align : "center", formatter: showValidFlag},
	                       {name : 'validFlag',index : 'validFlag', hidden: true}
	                      
	                     ],
	          rowNum : 10,
	          rowList : [ 10, 20, 30 ],
	          sortname : 'roleId',
	          autowidth:true,//自动宽
	          height:'auto',
	          sortorder : "desc",
	          multiselect : true,
	          viewrecords: true,//是否在浏览导航栏显示记录总数
	          recordpos : 'right',//定义了记录信息的位置
	          pagerpos: 'left',
	          pager : '#gridPager'
	        });
  
  ////先清空
  $("#roleTable").jqGrid().clearGridData();
  for ( var i = 0; i < parameterTypeList.length; i++){
      $("#roleTable").jqGrid('addRowData', i + 1, parameterTypeList[i]);
    }
  //加载完数据，分页
  var rowNumTmp = jQuery("#roleTable").jqGrid('getGridParam','rowNum');
  $("#roleTable").setGridParam({ rowNum: rowNumTmp }).trigger("reloadGrid");
}
//格式化
function viewRow(cellvalue, options, rowObject){
	return '<a href="#" onclick="javascript:showRoleResource(\'' + rowObject.roleId + '\',\'' + rowObject.validFlag + '\');">' + cellvalue + '</a>';
} 
function getRoleJsonData(buttonFlag, roleId_search_key , roleName_search_key, validFlag_search_key){
	RestfulClient.post(contextPath + "/role/getRoleListData", {
		"extend":{
			"roleId":roleId_search_key,
			"roleName":roleName_search_key,
			"validFlag":validFlag_search_key
		}
	}, function(data) {
        var dataList = data.roleList;
        loadRoleTable(dataList);
        //
    } );
}

$(function(){
	///////加载角色列表////////
	getRoleJsonData(true , null , null , null);
	/////消息框窗口隐藏后出发事件，刷新页面
	$("#alertModal").on("hide.bs.modal", function () {
		if(refreshPageFlag){
			refreshPage();
		}
	});
	////表单验证
    var validate = $("#roleOperForm").validate({
        
        rules:{
        	roleId:{
                required:true,
                byteRangeLength:[0,32]
            },
            roleName:{
                required:true ,
                byteRangeLength:[0,400]
            }, 
            validFlag:{
            	required:true 
            } 
			                   
        }
     });  
	
	//
    $("#btnQry").on("click", function () {
    	refreshPage();
	});
	
});