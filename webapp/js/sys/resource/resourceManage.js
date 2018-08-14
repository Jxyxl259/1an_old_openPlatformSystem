//新增、修改
function operResource(operType,arr){
	 $("#resourceOperForm").validate().resetForm();
	 switch(operType.toLowerCase()){
	 	case "add":	 
	 	 	$("#resourceOperDivTitle").html(addBtnTxt+resource);
	 	 	$("#btnOk").html(addBtnTxt);
	 	 	/*$("#resourceId").attr("readonly",false);*/
	 	 	/*$("#parentResourceName").attr("readonly",true);
	 	 	$("#resourceLevel").attr("readonly",true);*/
	 		$("#radiobtn").children().remove();
			$("#radiobtn").html('');
			$("#radiobtn").append("<input	type='hidden'id='endFlag'  name='endFlag' value='0'>")
			$("#radiobtn").append("<input	type='radio' id='endFlag'  name='endFlag' value='1'>").append(radioBtnY+" ");
			$("#radiobtn").append("<input	type='radio' id='endFlag'  name='endFlag' value='0' checked='checked'>").append(radioBtnN+" ");
	 	 	if(arr==0){
	 	 	 	$("#resourceType").attr("disabled",false);
		    	$("#parentResourceId").val('0000');
		    	$("#parentResourceName").val('0000');
		    	$("#actionUrl").val('demo/demo');
		    	$("#resourceLevel").val('1');
		    	$("#resourceType").val('');
		    	$("#resourceType").children("option").not($("#resourceType").children("option").eq(0)).remove();
	    		$("#resourceType").append("<option value='menu'>"+reourceMenu+"</option>");
	    		$("#resourceType").append("<option value='button'>"+reourceBtn+"</option>");
		    	$("#resourceType").append("<option value='service'>"+reourceService+"</option>");
		       	$("#resourceIconClass").val('fa fa-home fa-fw');
		    	$("#displayOrder").val('1');
	 	 	}
		    var resourceId = "";
			RestfulClient.post(contextPath+"/resource/createResourceId", 
					{
				        "form" : null,
				        "extend" : null,
				        "page" : null
			       }, 
			       function(data) {
			    	   resourceId =  data.extend.resourceId;
			    	   $("#resourceId").val(resourceId);
			       });
			$("#resourceName").val('');
			$("#endFlag").val('');
	 		break;
	 	case "edit":	 
	 	 	$("#resourceOperDivTitle").html(editBtnTxt+resource);
	 	 	$("#btnOk").html(saveBtnTxt);
	 	 	$("#resourceId").attr("readonly","true");
            if(arr[2]=='service'){
            	$("#resourceType").children("option").remove();
		    	$("#resourceType").append("<option value='service'>"+reourceService+"</option>");
            }else{
            	$("#resourceType").children("option").remove();
	    		$("#resourceType").append("<option value='menu'>"+reourceMenu+"</option>");
	    		$("#resourceType").append("<option value='button'>"+reourceBtn+"</option>");
            }	 	    
	 	 
            $("#resourceId").val(arr[0]);
			$("#resourceName").val(arr[1]);
			$("#resourceType").val(arr[2]);
			$("#resourceLevel").val(arr[3]);
			$("#parentResourceId").val(arr[4]);
			$("#actionUrl").val(arr[5]);
			$("#resourceIconClass").val(arr[7]);
			$("#displayOrder").val(arr[8]);
			var parentId = arr[4];
			if(arr[3]=="1"){
				$("#parentResourceId").val('0000');
				$("#parentResourceName").val('0000');
			}else{
				RestfulClient.post(contextPath+"/resource/findParentResourceName", 
						{
					        "form" : null,
					        "extend" : {
					        	"parentId":parentId
					        },
					        "page" : null
				       }, 
				       function(data) {
				            $("#parentResourceName").val(data.extend.parentResourceName);
				       });
			}
			if(arr[6]=="1"){
				 $("#radiobtn").children().remove();
				 $("#radiobtn").html('');
				 $("#radiobtn").append("<input	type='radio' id='endFlag'  name='endFlag' checked='checked'	value='1'> ").append(radioBtnY+" ");
				 $("#radiobtn").append("<input	type='radio' id='endFlag'  name='endFlag' value='0'>").append(radioBtnN+" ");
			}else{
				 $("#radiobtn").children().remove();
				 $("#radiobtn").html('');
				 $("#radiobtn").append("<input	type='radio' id='endFlag'  name='endFlag' value='1'>").append(radioBtnY+" ");
				 $("#radiobtn").append("<input	type='radio' id='endFlag'  name='endFlag' checked='checked' value='0'>").append(radioBtnN+" ");
			}
	 	 	break;
	 	default:;
	 }
	$("#resourceOperType").val(operType);
	$("#resourceOperDiv").modal("show");
}



function saveOrUpdateResource(){

	  if(!$("#resourceOperForm").valid()){
			 return;
		 };
	  var resourceOperType = $("#resourceOperType").val();
	  var resourceOperForm = $('#resourceOperForm').serializeObject();
	    	RestfulClient.post(
	    			contextPath+"/resource/addOrModifyMenu", 
	    			{
	    	            "form" : resourceOperForm,
	    	            "extend" : {
	    	                "operType" : resourceOperType,
	    	            }
	    	       }, 
	    	       function(data) {
	    	    		if(data.extend.flag==1){
	    					alertMsg(data.extend.msg,"error","");
	    				}else{
	    					alertMsg(data.extend.msg,"success","refresh()");
	    				}
	    			
	    	    });
	    		$("#resourceOperDiv").modal("hide");
}



function delResourceSure(id){
	RestfulClient.post(
			contextPath+"/resource/deleteResource", 
			{
				 "extend" : {
		                "resourceId" : id
		            }
	       }, 
	       function(data) {
	    	   alertMsg(data.extend.msg,"success","refresh()");
	    });	
}

function refresh(){
	location.href=contextPath+"/resource/resourceManage";
}


function alertMsg(msg,type,fn){
	 $("#alertMsg").html(msg);
	 
	 switch(type.toLowerCase()){
	 	case "confirm":		//confirm
	 		$("#alertInfo").html(confirmMsgTxt);
		 	if($("#alertOk").hasClass("hide")){
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
	
    var contextmenu = {
           	'items':{
	        		'新增资源':{
	     			"separator_before"	: false,
					"separator_after"	: true,
					"_disabled"			: false, //(this.check("create_node", data.reference, {}, "last")),
					"label"				: addBtnTxt+resource,
					"action"			: function (data) {
						var inst = $.jstree.reference(data.reference),
							obj = inst.get_node(data.reference);
						    var parentId =obj.id;
						    var type = obj.original.type;
						    var resourceLevel = parseInt(obj.original.level)+1;
						    var parentResourceName = obj.text;
						    var option =$("#resourceType").children("option").eq(3).val();
						    var actionUrl=obj.original.actionUrl;
						    var resourceIconClass=obj.icon;
						    var displayOrder =obj.original.displayOrder;
						    if(obj.original.endFlag=="1"){
						    	alert(addErrorMsg);
						    }else{
						    	if(type =='service'){
						    		$("#resourceType").children("option").remove();
						    		$("#resourceType").append("<option value='service'>"+reourceService+"</option>");
						    		$("#resourceType").val(type);
						    	}else{
						    		$("#resourceType").children("option").remove();
						    		$("#resourceType").append("<option value='menu'>"+reourceMenu+"</option>");
						    		$("#resourceType").append("<option value='button'>"+reourceBtn+"</option>");
						    		$("#resourceType").children("option").eq(3).remove();
						    	}
						    	$("#parentResourceId").val(parentId);
						    	$("#parentResourceName").val(parentResourceName);
						    	$("#actionUrl").val(actionUrl);
						    	$("#resourceLevel").val(resourceLevel);
						    	$("#resourceIconClass").val(resourceIconClass);
						    	$("#displayOrder").val(displayOrder);
						    	operResource("add","1");
						    	
						    }    
						
				  }
	        		},
	       		'修改资源':{
	     			"separator_before"	: false,
					"separator_after"	: true,
					"_disabled"			: false, //(this.check("create_node", data.reference, {}, "last")),
					"label"				: editBtnTxt+resource,
					"action"			: function (data) {
					var inst = $.jstree.reference(data.reference),
						obj = inst.get_node(data.reference);
					    var arrObj =[];
					    arrObj[0] =obj.id;
					    arrObj[1] =obj.text; 
					    arrObj[2] =obj.original.type;
					    arrObj[3] =obj.original.level;
					    arrObj[4] =obj.original.parentId;
					    arrObj[5] =obj.original.actionUrl;
					    arrObj[6] =obj.original.endFlag;
					    arrObj[7] =obj.icon;
					    arrObj[8] =obj.original.displayOrder;
					    operResource("edit",arrObj);
				  }
	        		},
	       		'删除资源':{
	     			"separator_before"	: false,
					"separator_after"	: true,
					"_disabled"			: false, //(this.check("create_node", data.reference, {}, "last")),
					"label"				: delBtnTxt+resource,
					"action"			: function (data) {
					var inst = $.jstree.reference(data.reference),
						obj = inst.get_node(data.reference);
				        var id =  obj.id;
				        if(obj.children !=null && obj.children !=""){
				        	alert(delMsg);
				        }else{
				            alertMsg(delMsgSure,"confirm","delResourceSure(\'"+id+"\')");
				        }
				  }
	        		}
	        	}
        }
	
	RestfulClient.post(contextPath+"/resource/getResourceListData", 
			{
		        "form" : null,
		        "extend" : null,
		        "page" : null
	       }, 
	       function(data) {
	           var menuResourceList = data.menuResourceTree;
	           var serviceResourceList = data.serviceResourceTree;
	           var  treeviewMenu = $('#treeview-menu') ; 
	           var  treeviewService = $('#treeview-service') ; 

	           treeviewMenu.jstree({
	             'core' : {
	   				'data' : menuResourceList,
	   				'cache': false,
	   				'check_callback' : true,
	   	      },
	            'plugins' : ['themes', 'types', 'contextmenu', 'ui'],
	            'contextmenu':contextmenu
	            
	           }).on('loaded.jstree', function() {
                  /* treeviewMenu.jstree('open_all');*/
	           });

	           treeviewService.jstree({
		             'core' : {
		   				'data' : serviceResourceList,
		   				'cache': false,
		   				'check_callback' : true,
		   	      },
		            'plugins' : ['themes', 'types', 'contextmenu', 'ui'],
		   	        'contextmenu':contextmenu
		           }).on('loaded.jstree', function() {
		        	  /* treeviewService.jstree('close_all');*/
		           });
	        
	    });
    
    var validate = $('#resourceOperForm').validate({
        rules:{
        	reourceId:{
                required:true,
                byteRangeLength:[0,32]
            },resourceName:{
                required:true ,
                byteRangeLength:[0,400]
             },
            actionUrl:{
                required:true ,
                byteRangeLength:[0,32]
             },
            resourceIconClass:{
                required:true ,
                byteRangeLength:[0,100]
              },
            displayOrder:{
                required:true ,
                byteRangeLength:[0,100]
              }
        }
     });  
	
  });