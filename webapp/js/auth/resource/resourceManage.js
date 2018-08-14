
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

function showInvalidFlag(cellvalue, options, rowObject){
	return cellvalue == "0" ? "有效":"无效";
} 
function showStatus(cellvalue, options, rowObject){
	return cellvalue == "1" ? "是":"否";
} 

function loadUserTable(){
	 $("#resourceTable").jqGrid( {
	          datatype : "json",
	          url: contextPath + "/transRes/query",
	          mtype: 'post', 
	          colNames : [ "资源ID","接口名称","请求资源URL","转换资源URL","模糊匹配","需要鉴权","带参数", "备注描述","创建人","更新人","状态","","","","","","","","","","","","","状态"],
	          colModel : [ 
	                       {name : 'idStr',index : 'idStr',width : 50,align : "center" },
	                       {name : 'interfaceName',index : 'interfaceName',width : 100,align : "center"}, 
	                       {name : 'requestUrl',index : 'requestUrl',width : 100,align : "center" },
	                       {name : 'transUrl',index : 'transUrl',width : 300,align : "center" }, 
	                       {name : 'fuzzy', index : 'fuzzyShow',width : 100,align : "center", formatter: showStatus},
	                       {name : 'isAuth',index : 'isAuthShow',width : 100,align : "center", formatter: showStatus},
	                       {name : 'isPair',index : 'isPairShow',width : 100, align : "center", formatter: showStatus},
	                       {name : 'remark',index : 'remark',width : 100,align : "center"}, 
	                       {name : 'createdUser',index : 'createdUser',width : 100,align : "center"}, 
	                       {name : 'updatedUser',index : 'updatedUser',width : 150,align : "center"}, 
	                       {name : 'invalidFlag',index : 'invalidFlagShow', width : 80,align : "center", formatter: showInvalidFlag},
	                       {name : 'orgId',index : 'orgId',width : 80, hidden: true}, 
	                       {name : 'agrtCode',index : 'agrtCode',width : 80, hidden: true}, 
	                       {name : 'userId',index : 'userId',width : 80, hidden: true},
	                       {name : 'isEncrypt',index : 'isEncrypt', hidden: true},
	                       {name : 'encryptType',index : 'encryptType', hidden: true},
	                       {name : 'encryptKey',index : 'encryptKey', hidden: true},
	                       {name : 'allowIp',index : 'allowIp', hidden: true},
	                       {name : 'encodingType',index : 'encodingType', hidden: true},
	                       {name : 'accept',index : 'accept', hidden: true},
	                       {name : 'fuzzy',index : 'fuzzy', hidden: true},
	                       {name : 'isAuth',index : 'isAuth', hidden: true},
	                       {name : 'isPair',index : 'isPair', hidden: true},
	                       {name : 'invalidFlag',index : 'invalidFlag', hidden: true}
	                     ],
	          rowNum : 10,
	          rowList : [ 10, 20, 30 ],
	          sortname : 'updatedDate',
	          autowidth:true,//自动宽
	          multiboxonly :true,
	          multiselectWidth : 29,
	          height:'auto',
	          viewrecords : true,
	          sortorder : "desc",
	          multiselect : true,
	          viewrecords: true,//是否在浏览导航栏显示记录总数
	          recordpos : 'right',//定义了记录信息的位置
	          pager : '#gridPager'/*,
	          rownumbers:true  ,
	          jsonReader:{
	        	  root:'datas',
	        	  cell:'',
	        	  page:"page.page",
	        	  total:"page.total",
	        	  records:"page.records",
	        	  repeatitems:false//该属性设置可以不必严格按照后台返回的数据顺序读取
	         }*/
	          ,
	          postData:{
	        	    id_search : $("#id_search").val(),
	        	  	interfaceName_search : $("#interfaceName_search").val(),
	        	  	requestUrl_search : $("#requestUrl_search").val(),
	        	  	transUrl_search : $("#transUrl_search").val(),
	        	  	remark_search : $("#remark_search").val(),
	        	  	invalidFlag_search : $("#invalidFlag_search").val()
             }
	        });
}

/**
 * 进入用户编辑
 * 
 * @param param
 * @return
 */
function preEdit(param) {
	var userId = null;
	if (param == 'edit') {
		$("#operType").val("edit");
	 	var ids=$("#resourceTable").jqGrid("getGridParam","selarrrow");
		if(ids.length <= 0){
			alert("请选择记录！");
			return;
		}else if(ids.length > 1){
			alert("请选择单条记录！");
			return;
		}else{
		    var rowData = $("#resourceTable").jqGrid("getRowData",ids[0]);
		    $("#id").val(rowData.idStr);
			$("#interfaceName").val(rowData.interfaceName);
			$("#requestUrl").val(rowData.requestUrl);
			$("#transUrl").val(rowData.transUrl);
			$("#orgId").val(rowData.orgId);
			$("#agrtCode").val(rowData.agrtCode);
			$("#userId").val(rowData.userId);
			$("#fuzzy").val(rowData.fuzzy);
			$("#isAuth").val(rowData.isAuth);
			$("#isPair").val(rowData.isPair);
			$("#remark").val(rowData.remark);
			$("#encodingType").val(rowData.encodingType);
			$("#accept").val(rowData.accept);
			$("#allowIp").val(rowData.allowIp);
			$("#isEncrypt").val(rowData.isEncrypt);
			$("#encryptType").val(rowData.encryptType);
			$("#encryptKey").val(rowData.encryptKey);
			$("#createdUser").val(rowData.createdUser);
			$("#updatedUser").val(rowData.updatedUser);
			$("#invalidFlag").val(rowData.invalidFlag);
		}
		 $("#buttonSave").val(editBtnTxt);
	 }else{
		 $("#operType").val("add");
		 $("#buttonSave").val(addBtnTxt);
		 $("#id").val('');
		 $("#interfaceName").val('');
		 $("#requestUrl").val('');
		 $("#transUrl").val('');
		 $("#remark").val('');
		 $("#encodingType").val('');
		 $("#accept").val('');
		 $("#orgId").val('');
		 $("#agrtCode").val('');
		 $("#userId").val('');
		 $("#fuzzy").val('0');
		 $("#isAuth").val('1');
		 $("#isPair").val('0');
		 $("#allowIp").val('');
		 $("#isEncrypt").val('0');
         $("#encryptType").val('');
         $("#encryptKey").val('');
		 $("#createdUser").val('');
		 $("#updatedUser").val('');
		 $("#invalidFlag").val('0');
	 }
	$("#paraTypeOperDiv").modal("show");
}


function loadUserTableButtons(){
	//按钮组
	$("#resourceTable")  
	.navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh:false}) 
	.navButtonAdd('#gridPager',{  
		 title:addBtnTxt, 
		 caption:'',
	  buttonicon:"fa-plus",   
	  onClickButton: function(){   
		preEdit('insert');
	  },   
	  position:"last"  
	})
	.navSeparatorAdd("#gridPager",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager',{  
		title:editBtnTxt, 
		caption:'',
		buttonicon:"fa-edit",   
		onClickButton: function(){   
		   preEdit('edit');
		},   
		position:"last"  
	})
	.navSeparatorAdd("#gridPager",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager',{  
		title:delBtnTxt, 
		caption:'',
		buttonicon:"fa-trash",   
		onClickButton: function(){   
			delPara();
		},   
		position:"last"  
	})
 	; 
}


//删除确认提示
function delPara(){
	
		var paraTypeIds=$("#resourceTable").jqGrid("getGridParam","selarrrow");
		if(paraTypeIds.length <= 0){
			alertMsg(deleteRowMsgTxt,"error","");
		}else{
			alertMsg("确定要删除所选记录？","confirm","delParaSure");//确认后调用delParaSure()
		}
		
}
//确认删除
function delParaSure(){	 
	 var paraTypeIds=$("#resourceTable").jqGrid("getGridParam","selarrrow");
	 var arr = [] ;
	 var parameterType ;
	 for(var k = 0;k<paraTypeIds.length;k++){
		 var rowData = $("#resourceTable").jqGrid("getRowData",paraTypeIds[k]);
		arr.push(rowData.idStr);
		 parameterType = rowData.idStr;
	 }
	
	RestfulClient.post(contextPath + "/transRes/del", 
			 {
		 		"extend" :{
		 			"ids" : JSON.stringify(arr) 
		 		}
			 },
			 function(data) {
				 if(data.flag){
					 alertMsg(data.msg,"success","");//弹出提示框
					 $("#paraTypeOperDiv").modal("hide");//隐藏操作窗口
					 $("#resourceTable").setGridParam({ postData: {
				 	   } }).trigger("reloadGrid", [{ page: 1}]);  
				 }else{
					 alert(data.msg);
				 }
  		}
	);
}


var flag = true ;  
function getData(buttonFlag){
	if(flag){
		 loadUserTable();
	     if(buttonFlag){
	        loadUserTableButtons();
	     }
	}else{
		 
	}
	flag = false ;  
}

//保存\修改参数
function saveOrUpdatePara(){
	 if(!$("#paraOperForm").valid()){
		 return;
	 };
	 
	 if($("#isEncrypt").val() == '1' && $("#encryptType").val() == "") {
	     alertMsg("加密,请选择加密方式","success","");//弹出提示框
	     return;
	 }
	 
	 RestfulClient.post(contextPath + "/transRes/saveOrUpdate", 
			 {
		 		"extend" :{
		 			"operType" : $("#operType").val(),
		 			"id" : $("#id").val() ,
		 			"interfaceName" : $("#interfaceName").val() ,
		 			"requestUrl" : $("#requestUrl").val() ,
		 			"transUrl" : $("#transUrl").val() ,
		 			"encodingType" : $("#encodingType").val() ,
		 			"accept" : $("#accept").val() ,
		 			"orgId" : $("#orgId").val() ,
		 			"agrtCode" : $("#agrtCode").val() ,
		 			"userId" : $("#userId").val() ,
		 			"fuzzy" : $("#fuzzy").val() ,
		 			"isAuth" : $("#isAuth").val(),
		 			"isPair" : $("#isPair").val(),
		 			"allowIp" : $("#allowIp").val(),
		 			"isEncrypt" : $("#isEncrypt").val(),
		 			"encryptType" : $("#encryptType").val(),
		 			"encryptKey" : $("#encryptKey").val(),
		 			"remark" : $("#remark").val() ,
		 			"invalidFlag": $("#invalidFlag").val()
		 		}
			 },
			 function(data) {
				 if(data.flag){
					 alertMsg(data.msg,"success","");//弹出提示框
					 $("#paraTypeOperDiv").modal("hide");//隐藏操作窗口
					 $("#resourceTable").setGridParam({ postData: {
				 	   } }).trigger("reloadGrid", [{ page: 1}]);  
				 }else{
					 alert(data.msg);
				 }
   		}
	);
}

function refreshPage(){
	var id_search = $("#id_search").val();
	var interfaceName_search = $("#interfaceName_search").val();
	var requestUrl_search = $("#requestUrl_search").val();
	var transUrl_search = $("#transUrl_search").val();
	var remark_search = $("#remark_search").val();
	var invalidFlag_search = $("#invalidFlag_search").val();
	getParaTypeJson(false , id_search , interfaceName_search , requestUrl_search , transUrl_search , remark_search , invalidFlag_search );
	
}

function getParaTypeJson(buttonFlag , id_search , interfaceName_search , 
		requestUrl_search , transUrl_search , remark_search , invalidFlag_search){
	RestfulClient.post(contextPath + "/transRes/query", {
		"extend":{
			"id_search":id_search,
			"interfaceName_search":interfaceName_search,
			"requestUrl_search":requestUrl_search,
			"transUrl_search":transUrl_search,
			"remark_search":remark_search,
			"invalidFlag_search":invalidFlag_search
		}
	}, function(data) {
        var parameterTypeList = data.dataList;
        loadUserTable(parameterTypeList);
    } );
}

$(function(){
	
	getData(true);
	
	
	var validate = $("#paraOperForm").validate({
        
        rules:{
        	interfaceName:{
                required:true,
                byteRangeLength:[0,100]
            },
            requestUrl:{
            	required:true,
            	byteRangeLength:[0,400]
	        },
	        transUrl:{
	        	required:true,
	        	byteRangeLength:[0,400]
	        },
	        remark:{
	        	required:true,
	        	byteRangeLength:[0,400]
	        },
	        invalidFlag:{
            	required:true 
            },
            isAuth:{
                required:true 
            },
            isPair:{
                required:true 
            }         
        }
     }); 
	
	
	$("#btnQry").on("click", function () {
		$("#resourceTable").setGridParam({ postData: {
					"id_search" : $("#id_search").val(),
	        	  	"interfaceName_search" : $("#interfaceName_search").val(),
	        	  	"requestUrl_search" : $("#requestUrl_search").val(),
	        	  	"transUrl_search" : $("#transUrl_search").val(),
	        	  	"remark_search" : $("#remark_search").val(),
	        	  	"invalidFlag_search" : $("#invalidFlag_search").val()
 	   	} }).trigger("reloadGrid", [{ page: 1}]);  
	});
	
	$(function() {
        $(document).scroll(function() {
            $("#resourceTable").setGridWidth($(window).width()-10);
        });
        $(window).resize(function() {
            $("#resourceTable").setGridWidth($(window).width()-10);
        });
    });
	
	$("select#encryptType").change(function(){
		if($(this).val()=='RSA'){
			$("#keyTipLabel").text("公钥KEY：");
		}else{
			$("#keyTipLabel").text("加密KEY：");
		}
	});
	
});

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