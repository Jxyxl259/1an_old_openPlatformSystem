var attribute = new Array("userCode", "userCname", "userTname", "companyCode",
		"phone", "address");
$('#passwordSetDate').datepicker( {});
$('#passwordExpireDate').datepicker( {});
var alertMsg1 = "请选择记录";
var validator  ;
$(function() {
	    validator = $("#userForm").validate( {
		focusInvalid : false,
		onkeyup : false,
		submitHandler : function(form) {
			saveUser(); //保存用户
	},

	rules : {
		userCode : {
			required : true,
		    maxlength:50  
		},
		userCname : {
			required : true,
			maxlength:400 
		},
		userTname : {
			maxlength:400 
		},
		userEname : {
			maxlength:400 
		},
		email : {
			email : true
		},
		companyCode : {
			required : true,
			maxlength:10
		},
		passwordExpireDate : {
			required : true
		},
		validFlag : {
			required : true
		},
		phone : {
			maxlength:18,
			isTel:true
		}, 
		mobile : {
			maxlength:18,
			isMobile : true
		} ,
		postCode : {
			maxlength:6,
			isPostCode:true
		} 
	} 
	});
});  
var data;
function refreshPage() {
	window.location.href = contextPath + "/user/preQuery";
}
/**
  查询结果集
 **/
function query() {
	   	  $("#userTable").setGridParam({ postData: {
	 		    "searchUserCode":$("#searchUserCode").val(),
				"searchUserTName":$("#searchUserTName").val(), 
				"searchUserCName":$("#searchUserCName").val() 
	 	   } }).trigger("reloadGrid", [{ page: 1}]);  
}

//删除确认提示
function deleteUser(){
		var ids=$("#userTable").jqGrid("getGridParam","selarrrow");
		if(ids.length <= 0){
			alertMsg(alertMsg2.value,"error","");
		}else{
			alertMsg(deleteRowMsgSureTxt,"confirm","deleteUserSure()");//确认后调用delRowSure()
		}
}

//确认删除
function deleteUserSure(){
	var list = new Array();
	var ids=$("#userTable").jqGrid("getGridParam","selarrrow");
	for(var k = 0;k < ids.length;k++){
		 var rowData = $("#userTable").jqGrid("getRowData",ids[k]);
	 	 list.push( {
				"userId" : rowData.userId,
				"userCode" : rowData.userCode 
		 });
    }
   RestfulClient.post(contextPath + "/user/delete", {
		"extend" : {
			"userList" : JSON.stringify(list)
		}
	}, function(data) {
		alertMsg(data.msg + data.deleteCount, "success", "");
		getData(false);
	});
}


/**
 * 进入用户编辑
 * 
 * @param param
 * @return
 */
function preEdit(param) {
 	$("#actionType").val(param);
	var userId = null;
	if (param == 'edit') {
	 	var ids=$("#userTable").jqGrid("getGridParam","selarrrow");
		if(ids.length <= 0){
			alert(alertMsg1);
			return;
		}else if(ids.length > 1){
			alert(alertMsg1);
			return;
		}else{
		    var rowData = $("#userTable").jqGrid("getRowData",ids[0]);
		    userId = rowData.userId ;
			$("#userCode").attr("readOnly","true");
			$("#password").attr("readOnly","true");
			$("#password").removeAttr("required");
		}
		 $("#buttonSave").val(editBtnTxt);
	 }else{
		 $("#buttonSave").val(addBtnTxt);
		 $("#password").attr("required","true");
		 $("#userCode").removeAttr("readonly");
		 $("#password").removeAttr("readonly");
	 }
	
	RestfulClient
			.post(
					contextPath + "/user/preEdit?actionType=" + param + '&userId='
							+ userId,
					{},
					function(data) {
						var userDto = data.userDto;
						for (attr in userDto) {
							$("#" + attr).val(userDto[attr]);
						}
						// 设置值
						$("#userModal").modal("show");
						var validFlagList = data.validFlagList;

						$("#validFlag").children("option").remove();
						var selected;
						for ( var i = validFlagList.length-1; i >= 0 ; i--) {
							selected = "";
							if (userDto["validFlag"] == validFlagList[i].parameterCode) {
								selected = "selected";
							}
							$('#validFlag').append(
									"<option value=\""
											+ validFlagList[i].parameterCode
											+ "\"" + selected + ">"
											+ validFlagList[i].parameterCode
											+ "-"
											+ validFlagList[i].parameterName
											+ "</option>");
						}
					});
}

/**
 * 角色权限配置
 */
function setUserRole() {
    var userId = null;
    var userCode = null ;
	var ids=$("#userTable").jqGrid("getGridParam","selarrrow");
	if(ids.length <= 0){
			alert(alertMsg1);
			return;
	}else if(ids.length > 1){
			alert(alertMsg1);
			return;
	}else{
		    var rowData = $("#userTable").jqGrid("getRowData",ids[0]);
		    userId = rowData.userId ;
		    userCode = rowData.userCode ;
	}
 	$('#userCodeDisplayLable').html(userCode);
	$('#userCodeOpr').val(userCode);
	loadRoleAndExludeResource(userCode); //加载用户角色,排除资源
}

/**
 * 保存用户
 */
function saveUser() {
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	
	//校验用户数据

	var data = $('#userForm').serializeObject();
	var actionType = document.getElementById("actionType");
	var url = contextPath + "/user/update?actionType=" + actionType.value ;
	RestfulClient.post(url , 
			 {
 		       "extend" :{
 		        	"userDto" : JSON.stringify(data) 
 		           }
	         },
			function(data) {
				if (data.dealStatus == "-1") {
					alertMsg(data.msg, "error", "");
				} else {
					alertMsg(data.msg, "success", "");// 弹出提示框
			$("#userModal").modal("hide");// 隐藏操作窗口
			getData(false);
			validator.resetForm();
		}
	}); 
	
}

/**
 * 加载角色和排除资源
 */
function loadRoleAndExludeResource(userCode) {
	RestfulClient.post(contextPath + "/userRole/edit?userCode=" + userCode, {},
			function(data)  {
				var userRoleList = data.userRoleList;
				$("#roleTbody").remove();
				var tbody = $("<tbody id=\"roleTbody\"></tbody>");
				tbody.appendTo("#roleTable");
				for ( var i in userRoleList) {
					var index = parseInt(i) + 1;
					var tr = $("<tr></tr>");
					var checked = (userRoleList[i].congfigFlag == 'true' ? 'checked'
							: '');
					var td = $(" <td><input type='checkbox'  id=\"selectCheckboxRole\" name=\"selectCheckboxRole\""
							+ checked
							+ " onclick='loadResourceTree()' >&nbsp;"
							+ index
							+ "&nbsp;&nbsp;"
							+ "<input type='hidden' id=\"roleIdHidden\" name=\"roleIdHidden\"  value=\""
							+ userRoleList[i].roleId
							+ "\""
							+ "/>"
							+ "</td>");
					tr.append(td);
					td = $("<td>" + userRoleList[i].roleId + "</td>");
					tr.append(td);
					td = $("<td>" + userRoleList[i].roleName + "</td>");
					tr.append(td);
					tr.appendTo("#roleTbody");
				}

				var resourceList = data.resourceTree;
				var resourceTrees = $('#resourceTree');
			  
				if(resourceTrees.has('ul')){
					resourceTrees.remove(); 
					$("div[name='resourceDiv']").append("<div id = \"resourceTree\" ></div>");
				};
			    resourceTrees = $('#resourceTree');
			  	resourceTrees.jstree( {
					'core' : {
						'data' : resourceList,
						'cache' : false,
						'check_callback' : true
					},
					'plugins' : [ 'themes', 'types', 'checkbox', 'ui' ]
				}).on('loaded.jstree', function() {
					resourceTrees.jstree('open_all');
				});

				$("#roleModal").modal("show");

			});
}
/**
 * 加载可排除资源
 * @return
 */
function loadResourceTree(){
	var userCode = $('#userCodeOpr').val();
	var selectCheckbox = document.getElementsByName("selectCheckboxRole");
	var roleIdHiddens = document.getElementsByName("roleIdHidden");
	var roleList = new Array();
	for ( var i = 0; i < selectCheckbox.length; i++) {
		if (selectCheckbox[i].checked) {
			roleList.push(roleIdHiddens[i].value);
		}
	}
	RestfulClient.post(contextPath + "/userRole/loadRoleResource" , 
			{
	      	  "extend" : {
	               	"userCode" : userCode,
		            "roleList" : JSON.stringify(roleList) 
	          }
		     },
			function(data)  {
				var resourceList = data.resourceTree;
				var resourceTrees = $('#resourceTree');
			  
				if(resourceTrees.has('ul')){
					resourceTrees.remove(); 
					$("div[name='resourceDiv']").append("<div id = \"resourceTree\" ></div>");
				};
			    resourceTrees = $('#resourceTree');
			  	resourceTrees.jstree( {
					'core' : {
						'data' : resourceList,
						'cache' : false,
						'check_callback' : true
					},
					'plugins' : [ 'themes', 'types', 'checkbox', 'ui' ]
				}).on('loaded.jstree', function() {
					resourceTrees.jstree('open_all');
				});
			});
}

/**
 * 保存用户排除资源和用户角色
 * @return
 */
function saveUserExcludeResource() {
	var resourceTree = $('#resourceTree').jstree().get_checked();
	var userCode = $('#userCodeOpr').val();
	var selectCheckbox = document.getElementsByName("selectCheckboxRole");
	var roleIdHiddens = document.getElementsByName("roleIdHidden");
	var userRoleList = new Array();
	for ( var i = 0; i < selectCheckbox.length; i++) {
		if (selectCheckbox[i].checked) {
			var userRole = {
				"userId" : userCode,
				"roleId" : roleIdHiddens[i].value
			};
			userRoleList.push(userRole);
		}
	}
	var data = {
		"extend" : {
			"userId" : userCode,
			"userRoleList" : JSON.stringify(userRoleList),
			"resourceIds" : JSON.stringify(resourceTree)
		}
	};
	RestfulClient.post(contextPath + "/userRole/update", data, function(data) {
        alertMsg(data.msg,"success","");//弹出提示框
	    $("#roleModal").modal("hide");
	 });
}


 
function boundCheckBox(controlField, checkBoxField) {
    var count = 0;
	var checkBoxList = document.getElementsByName(checkBoxField);
	if (checkBoxList != 'undefined') {
		count = checkBoxList.length;
		if (isNaN(count)) {
			if (checkBoxList.className != "readonlyCheckBox") {
				checkBoxList.checked = checkBoxList.checked;
			}
		} else {
			for ( var i = 0; i < count; i++) {
				if (checkBoxList[i].className != "readonlyCheckBox") {
					checkBoxList[i].checked = controlField.checked;
				}
			}
		}
	}
}


function loadUserTableButtons(){
	//按钮组
	$("#userTable")  
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
		   deleteUser();
		},   
		position:"last"  
	})
	.navSeparatorAdd("#gridPager",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager',{  
		title:userGrantBtnTxt, 
		caption:'',
		buttonicon:"fa-user-plus",   
		onClickButton: function(){   
	    	setUserRole();
		},   
		position:"last"  
	})
	.navSeparatorAdd("#gridPager",{sepclass : 'ui-separator',sepcontent: ''})
	.navButtonAdd('#gridPager',{
		title:userUnlockBtnTxt,
		caption:'',
		buttonicon:"fa-unlock-alt",
		onClickButton: function(){
			unlockUser();
		},
		position:"last"
	})
	; 
}

function loadUserTable(){
	 $("#userTable").jqGrid( {
	          datatype : "json",
	          url: contextPath + "/user/query",
	          mtype: 'post', 
	          colNames : [ userIdT,userCodeT,userCnameT,userTnameT, companyCodeT,phoneT,addressT,validFlagshowT, 'validFlag'],
	          colModel : [ 
	                       {name : 'userId',index : 'userId' , hidden: true}, 
	                       {name : 'userCode',index : 'userCode'}, 
	                       {name : 'userCname',index : 'userCname',width : 100,align : "center" },
	                       {name : 'userTname',index : 'userTname',width : 100,align : "center" }, 
	                       {name : 'companyCode',index : 'companyCode',width : 100,align : "center"}, 
	                       {name : 'phone',index : 'phone',width : 100,align : "center"}, 
	                       {name : 'address',index : 'address',width : 150,align : "center"}, 
	                       {name : 'validFlag',index : 'validFlag',width : 100,align : "center", formatter: showValidFlag},
	                       {name : 'validFlag',index : 'validFlag', hidden: true}  
	                     ],
	          rowNum : 10,
	          rowList : [ 10, 20, 30 ],
	          sortname : 'updatedDate',
	          autowidth:true,//自动宽
	          height:'auto',
	          viewrecords : true,
	          sortorder : "desc",
	          multiselect : true,
	          multiselectWidth : 29,
	          viewrecords: true,//是否在浏览导航栏显示记录总数
	          recordpos : 'right',//定义了记录信息的位置
	          pager : '#gridPager',
	          rownumbers:true  /*,
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
			   "searchUserCode":$("#searchUserCode").val(),
			   "searchUserTName":$("#searchUserTName").val(), 
			   "searchUserCName":$("#searchUserCName").val()
              }
	        });
}
//查询代码转换
function getData(buttonFlag){
	if(flag){
		 loadUserTable();
	     if(buttonFlag){
	        	loadUserTableButtons();
	     }
	}else{
		  $("#userTable").setGridParam({ postData: {
	 		    "searchUserCode":$("#searchUserCode").val(),
				"searchUserTName":$("#searchUserTName").val(), 
				"searchUserCName":$("#searchUserCName").val() 
	 	   } }).trigger("reloadGrid"); 
	}
	flag = false ;  
}

$(function(){
  	//加载代码转换数据
	getData(true);
});

var flag = true ;  

function showValidFlag(cellvalue, options, rowObject){
	var ret = '-';
	if(cellvalue == '1'){
		ret = validFlagTxt1;
	}else if(cellvalue == '0'){
		ret = '<font color=red>' + validFalgTxt0 + '</font>';
	}
	return ret;
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
/**
 * 重置数据
 * @return
 */
function resetValue(){
	var queryConditionForm = document.getElementById("queryConditionForm");
	queryConditionForm.reset();
	
}
var data1 = dataFn1(companyAcData);
function dataFn1(input) {
	var data = JSON.parse(companyAcData);
	return data;
};
function valueFn(item) {
    return item["value"];
}

function labelFn(item) {
    return item["value"] + "-" + item["yyy"];
}

function closeEdit(){
	validator.resetForm(); //清空验证
}

/**
 * 用户解锁
 */
function unlockUser() {
	var ids = $("#userTable").jqGrid("getGridParam","selarrrow");
	
	if(ids.length <= 0){
		alert(alertMsg1);
		return;
	}
	
	alertMsg(unlockUserRowMsgSureTxt,"confirm","unlockUsers()");//确认后调用unlockUsers()
}

//确认解锁
function unlockUsers(){
	var ids=$("#userTable").jqGrid("getGridParam","selarrrow");
	var len = ids.length;
	
	var list = new Array(len);
	for(var k = 0; k < len; k++){
		var rowData = $("#userTable").jqGrid("getRowData",ids[k]);
		list.push( {
			"userId" : rowData.userId,
			"userCode" : rowData.userCode 
		});
	}
	
	RestfulClient.post(contextPath + "/user/unlock", {
		"extend" : {
			"userList" : JSON.stringify(list)
		}
	}, function(data) {
		if(data.status==200){
			alertMsg(data.msg, "success", "");
		} else {
			alertMsg(data.msg, "error", "");
		}
		getData(false);
	});
}
