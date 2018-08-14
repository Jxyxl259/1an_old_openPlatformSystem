<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%> 
<!DOCTYPE html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
var contextPath = "${ctx}" ;
</script>
<meta charset="UTF-8">
<title>易安财产保险股份有限公司</title>

<link href="${ctx}/common/css/bootstrap.min.css"  rel="stylesheet" type="text/css">
<link href="${ctx}/common/css/font-awesome.min.css"  rel="stylesheet" type="text/css">
<link href="${ctx}/css/cms.css"  rel="stylesheet" type="text/css">

<script type="text/javascript">
var addBtnTxt= '<s:message code="common.button.add"/>' ;
var viewBtnTxt= '<s:message code="common.button.view"/>' ;
var editBtnTxt= '<s:message code="common.button.edit"/>' ;
var delBtnTxt= '<s:message code="common.button.delete" />' ;
var bannedBtnTxt= '<s:message code="common.button.banned" />' ;

var editMsgTxt= '<s:message code="common.msg.edit" />' ;
var editOneMsgTxt= '<s:message code="common.msg.editOne" />' ;

var infoMsgTxt= '<s:message code="common.msg.info" />' ;
var confirmMsgTxt= '<s:message code="common.msg.confirm" />' ;
var errorMsgTxt= '<s:message code="common.msg.error" />' ;
var successMsgTxt= '<s:message code="common.msg.success" />' ;
var warnMsgTxt= '<s:message code="common.msg.warn" />' ;

var bannedRowMsgTxt= '<s:message code="common.msg.banned" />' ;
var bannedRowMsgSureTxt= '<s:message code="common.msg.bannedSure" />' ;

var deleteRowMsgTxt= '<s:message code="common.msg.delete" />' ;
var deleteRowMsgSureTxt= '<s:message code="common.msg.deleteSure" />' ;
var deleteRowMsgSureTxtAppend= '<s:message code="cmsRole.msg.deleteSure" />' ;

var gridTitleAppend= '<s:message code="commom.grid.list.titleAppend" />' ;

//
var newWinTitle= '<s:message code="cmsRole.newWinTitle" />' ;
var roleResourceTitle= '<s:message code="cmsRole.roleResourceTitle" />' ;

var roleIdText = '<s:message code="cmsRole.roleId" /> ' ;
var roleNameText = '<s:message code="cmsRole.roleName" /> ' ;

var validFlagTxt='<s:message code="common.validFlag" />';
var validFalgTxt0= '<s:message code="common.validFlag.v0" />' ;//无效
var validFlagTxt1= '<s:message code="common.validFlag.v1" />' ;//有效

</script>

</head>
<body class="comp-tree">

<div class="container">

<!-- 条件区域 -->
 <div class="row">
  <div class="col-md-5 col-lg-5">
	<!-- 1 start -->
		<div class="widget" >
			<div class="widget-header">
				<div class="pull-left"> <s:message code="common.button.query" /><s:message code="commom.grid.query.titleAppend" /></div>
				<div class="clearfix"></div>
			</div>
			<div class="widget-content">
				<form class="form-horizontal" method="post" role="form" id="configSearchForm" action="">
				<table class="table-query small">
					<tr>
					    <td class="title"><label><s:message code="cmsRole.roleId" />：</label></td>
					    <td><input class="form-control" type="text" id="roleId_search" name="roleId_search" 
	                    	placeholder=" <s:message code='commom.input.placeholder' /> " /></td>
					    <td class="title"><label><s:message code="cmsRole.roleName" />：</label></td>
					    <td><input class="form-control" type="text" id="roleName_search" name="roleName_search" 
	                    	placeholder=" <s:message code='commom.input.placeholder' /> " /></td>
					</tr>
					<tr>
					    <td class="title"><label><s:message code="common.validFlag" />：</label></td>
					    <td><select class="form-control"  name="validFlag_search" id="validFlag_search">
	                            <option value=""> <s:message code="commom.select.placeholder" /> </option>
	                            <option value="1"> <s:message code="common.validFlag.v1" /> </option>
	                            <option value="0"> <s:message code="common.validFlag.v0" /> </option>
					 		</select>
					 	</td>
					</tr>
				</table>
				<div class="table-query-action">
				    <button type="button" id="btnQry" class="btn btn-primary"><i class="fa fa-search"></i><s:message code="common.button.query" /></button>
				    <button type="reset" class="btn btn-primary"><i class="fa fa-retweet"></i><s:message code="common.button.reset" /></button>
				</div>
              </form>
			</div>
		</div>
	
	<!-- END 1 -->
		 <!-- 列表区域 -->
		<div class="widget widget-table" >
			<div class="widget-header">
				<div class="pull-left"><s:message code="cmsRole.newWinTitle" /><s:message code="commom.grid.list.titleAppend" />  </div>
				<div class="clearfix"></div>
			</div>
			<div class="widget-content">
				<a href="javascript:operRole('add');" class="btn btn-primary"><i class="fa fa-plus"></i> <s:message code="common.button.add"/></a>
				<a href="javascript:operRole('edit');" class="btn btn-primary"><i class="fa fa-edit"></i> <s:message code="common.button.edit"/></a>
				<a href="javascript:delRow();" class="btn btn-primary"><i class="fa fa-trash"></i> <s:message code="common.button.delete" /></a>
				<a href="javascript:bannedRow();" class="btn btn-primary"><i class="fa fa-lock"></i> <s:message code="common.button.banned" /></a>
			</div>
			<div class="widget-content">
				<table id="roleTable">
					
					</table>
				</div>
		</div>
				
				<!-- END 1 -->
  </div>
  
   <!-- 资源树 -->
  <div id="roleResourceDiv" class="col-md-5 col-lg-5 hidden">
	<!-- 2 start -->
	<div class="widget widget-table" >
		<div class="widget-header">
			<div class="pull-left" name="jszy"> <s:message code="cmsRole.roleResourceTitle" /></div>
			<div class="clearfix"></div>
		</div>
		<div class="widget-content" name="jszyDiv">
			<a href="javascript:saveRoleResource()" class="btn btn-primary " > <i class="fa fa-save"></i><s:message code="common.button.save" /> </a>
		</div>
		<div id = "treeview-default" ></div>
	</div>
	<!-- END 2 -->
  </div>
 </div>
 
</div>	

<!--新增/修改窗口--> 
<div id="roleOperDiv" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	 <div class="modal-dialog">
		 <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        <h4 class="modal-title"><label id="roleOperDivTitle"> <s:message code="cmsRole.newWinTitle" /></label></h4>
		      </div>
		      <div class="modal-body">
		        <form class="form-horizontal" method="post" role="form" id="roleOperForm" action="">
					
					<input type="hidden" id="roleOperType"/>
					
					<div class="form-group">
						<label class="col-lg-2 control-label required"><s:message code="cmsRole.roleId" /> </label>
                        <div class="col-lg-4">
                        	<input type="text" class="form-control" id="roleId" name="roleId" placeholder=" <s:message code='commom.input.placeholder' /> " />
                        </div>
                        <label class="col-lg-2 control-label required"><s:message code="cmsRole.roleName" /> </label>
                        <div class="col-lg-4">
                        	<input type="text" class="form-control" id="roleName" name="roleName" placeholder=" <s:message code='commom.input.placeholder' /> " />
                        </div>
                        <label class="col-lg-2 control-label required"><s:message code="common.validFlag" /></label>
                        <div class="col-lg-4">
                        	<select class="form-control" name="validFlag" id="validFlag">
                                  <option value=""> <s:message code="commom.select.placeholder" /> </option>
                                  <option value="1"> <s:message code="common.validFlag.v1" /></option>
                                  <option value="0"> <s:message code="common.validFlag.v0" /> </option>
                             </select>
                        </div>
                    </div>
				</form>
		      </div>
		      <div class="modal-footer">
		      	<button type="button" id="btnOk" onclick="javascript:saveOrUpdateRole();" class="btn btn-primary"><s:message code="common.button.sure" /></button>
		        <button type="button" id="btnClose" class="btn btn-default" data-dismiss="modal" aria-hidden="true"><s:message code="common.button.close" /></button>	        
		      </div>
		 </div>
	</div>
</div>


<!-- 消息窗口 -->
<div id="alertModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	 <div class="modal-dialog">
		 <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        <h4 class="modal-title"><label id="alertInfo"><s:message code="common.msg.info" /></label></h4>
		      </div>
		      <div class="modal-body">
		        <p><label id="alertMsg" ><s:message code="common.alertMag" /></label></p>
		      </div>
		      <div class="modal-footer">
		      	<button type="button" id="alertOk" class="btn btn-primary"><s:message code="common.button.sure" /></button>
		        <button type="button" id="alertClose" class="btn btn-default" data-dismiss="modal" aria-hidden="true"><s:message code="common.button.close" /></button>	        
		      </div>
		 </div>
	</div>
</div> 		

<div id="gridPager"></div>

</body>

<script language="Javascript" src="${ctx}/common/js/jquery/jquery-2.1.0.min.js"></script>
<script language="Javascript" src="${ctx}/common/js/bootstrap/bootstrap.min.js"></script>

<script language="Javascript" src="${ctx}/common/js/json2.js"></script>
<script language="Javascript" src="${ctx}/common/js/yafa-restful-client.js"></script>
<script language="Javascript" src="${ctx}/common/js/plugins/tree/jstree.js"></script>

<script language="Javascript" src="${ctx}/common/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script language="Javascript" src="${ctx}/common/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>

<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/jquery.validate.js" ></script>
<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/validation_zh.js" ></script>

<script language="Javascript" src="${ctx}/js/commonUtil.js" charset="UTF-8"></script>

<script language="Javascript" src="${ctx}/js/sys/role/roleManage.js"></script>

</html>