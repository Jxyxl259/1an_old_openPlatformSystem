//新增/修改资源
function operCompany(operType,arr){
	 $("#companyOperForm").validate().resetForm();
	 switch(operType.toLowerCase()){
	 	case "add":	 //新增
	 	 	$("#companyOperDivTitle").html(addBtnTxt+company);
	 	 	$("#btnOk").html(addBtnTxt);
	 	 /*	$("#companyId").attr("readonly",false);
	 	 	$("#parentCompanyName").attr("readonly",true);
	 	 	$("#companyLevel").attr("readonly",true);*/
	 	 	$("#radiobtn").children().remove();
	 	 	$("#radiobtn").html('');
			$("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' checked='checked'	value='1'>").append(radioBtnY+" ");
			$("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' value='0'>").append(radioBtnN+" ");
	 	 	//清空值
	 	 	if(arr==0){
	 	 		$("#companyLevel").val('');
				$("#parentCompanyId").val('0000');
				$("#parentCompanyName").val('0000');
				$("#companyLevel").val('1');
	 	 	}
			RestfulClient.post(contextPath+"/company/createCompanyId", 
					{
				        "form" : null,
				        "extend" : null,
				        "page" : null
			       }, 
			       function(data) {
			    	   $("#companyId").val(data.extend.companyId);
			       });
	 		$("#companyEname").val('');
			$("#companyTname").val('');
			$("#companyCname").val('');
			$("#remark").val('');	
	 		break;
	 	case "edit":	 //修改
	 	 	$("#companyOperDivTitle").html(editBtnTxt+company);
	 	 	$("#btnOk").html(saveBtnTxt);
	 	 	$("#companyId").attr("readonly","true");
		 	$("#parentCompanyName").attr("readonly",true);
	 	 	$("#companyLevel").attr("readonly",true);
	 	 	//赋值
            $("#companyId").val(arr[0]);
			$("#companyCname").val(arr[1]);
			$("#companyEname").val(arr[2]);
			$("#companyTname").val(arr[3]);
			$("#companyLevel").val(arr[4]);
			$("#parentCompanyId").val(arr[5]);
			var parentId=arr[5];
			$("#remark").val(arr[7]);
			if(arr[4]=="1"){
				 $("#parentCompanyName").val("0000");
				 $("#parentCompanyId").val("0000");
			}else{
				RestfulClient.post(
						contextPath+"/company/findParentCompanyName", 
						{
					        "form" : null,
					        "extend" : {
					        	"parentId":parentId
					        },
					        "page" : null
				       }, 
				       function(data) {
				    	   $("#parentCompanyName").val(data.extend.parentCompanyName);
				       });
			}
			if(arr[6]=="1"){
				 $("#radiobtn").children().remove();
				 $("#radiobtn").html('');
				 $("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' checked='checked'	value='1'>").append(radioBtnY+" ");
				 $("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' value='0'>").append(radioBtnN+" ");
			}else{
				 $("#radiobtn").children().remove();
				 $("#radiobtn").html('');
				 $("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' value='1'>").appeng(radioBtnY+" ");
				 $("#radiobtn").append("<input	type='radio' id='validFlag'  name='validFlag' checked='checked'	value='0'>").appeng(radioBtnN+" ");
			}
	 	 	break;
	 	default:;
	 }
	$("#companyOperType").val(operType);
	$("#companyOperDiv").modal("show");
}


//保存\修改角色
function saveOrUpdateCompany(){
	  //校验表单
	  if(!$("#companyOperForm").valid()){
			 return;
		 };
	  var companyOperType = $("#companyOperType").val();
	  var companyOperForm = $('#companyOperForm').serializeObject();

	    	RestfulClient.post(
	    			contextPath+"/company/addOrModifyCompany", 
	    			{
	    	            "form" : companyOperForm,
	    	            "extend" : {
	    	                "operType" : companyOperType,
	    	            }
	    	       }, 
	    	       function(data) {
	    				if(data.extend.flag==1){
	    					alertMsg(data.extend.msg,"error","");
	    				}else{
	    					alertMsg(data.extend.msg,"success","refresh()");
	    				}
	    			
	    	    });
	    		$("#companyOperDiv").modal("hide");
}


//删除资源
function delCompanySure(id){
	RestfulClient.post(
			contextPath+ "/company/deleteCompany", 
			{
		        "extend" : {
	                "companyId" : id
	            }
	       }, 
	       function(data) {
	    	   alertMsg(data.extend.msg,"success","refresh()");
	         
	    });
		
}
//刷新页面
function refresh(){
	location.href=contextPath+"/company/companyManage";
}

//警告消息
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
	 		break;
	 	case "warn":		//warn
	 		$("#alertInfo").html(warnMsgTxt);
	 		$("#alertOk").attr("class","hide");
	 		break;
	 	case "success":		//success
	 		$("#alertInfo").html(successMsgTxt);
	 		$("#alertOk").attr("class","hide");
	 	 	if($("#alertClose").has("onclick")){
		 		$("#alertClose").removeAttr("onclick");
		 		$("#alertClose").attr("onclick","javascript:" + fn + ";");
		 	}else{
		 		$("#alertClose").attr("onclick","javascript:" + fn + ";");
		 	}
	 		break;
	 	case "info":		//info
	 		$("#alertInfo").html(infoMsgTxt);
	 		$("#alertOk").attr("class","hide");
	 		break;
	 	default:;
	 }
	 $("#alertModal").modal("show");
}


$(function(){
	
	//自定义右键菜单
    var contextmenu = {
           	'items':{
	        		'新增子机构':{
	     			"separator_before"	: false,
					"separator_after"	: true,
					"_disabled"			: false, //(this.check("create_node", data.reference, {}, "last")),
					"label"				: addBtnTxt+company,
					"action"			: function (data) {
						var inst = $.jstree.reference(data.reference),
							obj = inst.get_node(data.reference);
					        var parentId =obj.id;
					        var parentCompanyName = obj.original.companyCname;
						    var companyLevel = parseInt(obj.original.level)+1;
						    if(obj.original.validFlag=="0"){
						    	alert(addErrorMsg);
						    }else{
						    	$("#parentCompanyId").val(parentId);
						    	$("#parentCompanyName").val(parentCompanyName);
						    	$("#companyLevel").val(companyLevel);
						    	operCompany("add","1");
						    }   
						
				  }
	        		},
	       		'编辑机构':{
	     			"separator_before"	: false,
					"separator_after"	: true,
					"_disabled"			: false, //(this.check("create_node", data.reference, {}, "last")),
					"label"				: editBtnTxt+company,
					"action"			: function (data) {
					var inst = $.jstree.reference(data.reference),
						obj = inst.get_node(data.reference);
					    var arrObj =[];
					    arrObj[0] =obj.id;
					    arrObj[1] =obj.original.companyCname;
					    arrObj[2] =obj.original.companyEname;
					    arrObj[3] =obj.original.companyTname;
					    arrObj[4] =obj.original.level;
					    arrObj[5] =obj.original.parentId;
					    arrObj[6] =obj.original.validFlag;
					    arrObj[7] =obj.original.remark;
					    operCompany("edit",arrObj);
				  }
	        		},
	       		'删除机构':{
	     			"separator_before"	: false,
					"separator_after"	: true,
					"_disabled"			: false, //(this.check("create_node", data.reference, {}, "last")),
					"label"				: delBtnTxt+company,
					"action"			: function (data) {
					var inst = $.jstree.reference(data.reference),
						obj = inst.get_node(data.reference);
					    var id =  obj.id;
				        if(obj.children !=null && obj.children !=""){
				        	alert(delErrorMsg);
				        }else{
				        	alertMsg(delMsgSure,"confirm","delCompanySure(\'"+id+"\')");
				        }
				  }
	        		}
	        	}
        }
	
	RestfulClient.post(
			contextPath+"/company/getCompanyListData", 
			{
		        "form" : null,
		        "extend" : null,
		        "page" : null
	       }, 
	       function(data) {
	           var companyListTree = data.companyListTree;
	           var  treeviewCompany = $('#treeview-company') ; 


	           treeviewCompany.jstree({
	             'core' : {
	   				'data' : companyListTree,
	   				'cache': false,
	   				'check_callback' : true,
	   	      },
	            'plugins' : ['themes', 'types', 'contextmenu', 'ui'],
	            'contextmenu':contextmenu
	            
	           }).on('loaded.jstree', function() {
	        	   treeviewCompany.jstree('open_all');
	           }); 
	         
	    });
    
	//表单验证
    var validate = $('#companyOperForm').validate({
        rules:{
        	companyId:{
                required:true,
                byteRangeLength:[0,32]
            },
            companyCname:{
                 required:true ,
                 byteRangeLength:[0,400]
              },
            companyEname:{
                 required:true ,
                 byteRangeLength:[0,400]
              },
            companyTname:{
                 required:true ,
                 byteRangeLength:[0,400]
              },
            remark:{
            	byteRangeLength:[0,400]
            }
        }
     });  
	
  });