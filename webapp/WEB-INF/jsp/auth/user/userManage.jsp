<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%> 
<!DOCTYPE html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta charset="UTF-8">
<title>易安财产保险股份有限公司</title>
<script type="text/javascript">
contextPath = '${ctx}';
</script>

<link href="${ctx}/common/css/bootstrap.min.css"  rel="stylesheet" type="text/css">
<link href="${ctx}/common/css/font-awesome.min.css"  rel="stylesheet" type="text/css">
<link href="${ctx}/css/cms.css"  rel="stylesheet" type="text/css">
<style type="text/css">
.modal-content{
	width: 650px;
}
.modal-body{
	padding-bottom : 1px;
}
form {
	padding-bottom : 1px;	
}
</style>

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
var deleteRowMsgSureTxtAppend= '<s:message code="cmsParameter.msg.deleteSure" />' ;

var gridTitleAppend= '<s:message code="commom.grid.list.titleAppend" />' ;

var validFalgTxt0= '<s:message code="common.validFlag.v0" />' ;//无效
var validFlagTxt1= '<s:message code="common.validFlag.v1" />' ;//有效

//
var newParameterTypeWinTitle= '<s:message code="cmsParameterType.newWinTitle" />' ;
var newParameterWinTitle= '<s:message code="cmsParameter.newWinTitle" />' ;
var parameterTypeIdText= '<s:message code="cmsParameterType.parameterTypeId" />' ;
var parameterIdText= '<s:message code="cmsParameter.parameterId" />' ;
var parameterTypeText= '<s:message code="cmsParameter.parameterType" />' ;
var parameterCodeText= '<s:message code="cmsParameter.parameterCode" />' ;

var cname= '<s:message code="commom.cname" />' ;
var ename= '<s:message code="commom.ename" />' ;
var tname= '<s:message code="commom.tname" />' ;

var validFlagTxt='<s:message code="common.validFlag" />';
var remarkTxt='<s:message code="common.remark" />';
var flagTxt='<s:message code="common.flag" />';


var parameterTypeAcData = '${parameterTypeList}';
</script>
</head>
<body>
<div class="container">
 <div class="row">
	  <div class="col-md-12">
		<!-- 1 start -->
			<div class="widget" >
				<div class="widget-header">查询条件</div>
				<div class="widget-content">
					<form class="form-horizontal" method="post" role="form" id="paraTypeSearchForm" action="">
						<table class="table-query">
						    <tr>
						        <td class="title"><label>appId：</label></td>
						        <td>
						        	<input type="text" class="form-control" id="appId_search"
										name="appId_search" placeholder=" <s:message code='commom.input.placeholder' /> " />	
						        </td>
						        <td class="title"><label>appSecret：</label></td>
						        <td>
						        	<input type="text" class="form-control" id="appSecret_search"
										name="appSecret_search" placeholder=" <s:message code='commom.input.placeholder' /> " /></td>
						        <td class="title"><label>状态：</label></td>
						        <td>
						        	<select class="form-control"  name="invalidFlag_search" id="invalidFlag_search">
		                                  <option value=""> <s:message code="commom.select.placeholder" /> </option>
		                                  <option value="0"> <s:message code="common.validFlag.v1" /></option>
		                                  <option value="1"> <s:message code="common.validFlag.v0" /> </option>
		                             </select>
		                        </td>
						    </tr>
						    <tr>
						        <td class="title"><label>用户名：</label></td>
						        <td>
						        	<input type="text" class="form-control" id="userName_search"
										name="userName_search" placeholder=" <s:message code='commom.input.placeholder' /> " /></td>
						        <td class="title"><label>手机号：</label></td>
						        <td>
						        	<input type="text" class="form-control" id="phoneNumber_search"
										name="phoneNumber_search" placeholder="<s:message code='commom.input.placeholder' />  " /></td>
						    </tr>
						</table>
						<div class="table-query-action">
						    <button type="button" id="btnQry" class="btn btn-primary"><i class="fa fa-search"></i><s:message code="common.button.query" /></button>
							<button type="reset" class="btn btn-primary"><i class="fa fa-retweet"></i><s:message code="common.button.reset" /></button>
						</div>
					</form>
				</div>
			</div>
			<table id="paraTypeTable">
			</table>
			<br/>
		<!-- END 1 -->
	  </div>
 </div>
 <div class="row">
 	<div id="paraDiv" class="col-md-12 hidden">
	<!-- START 2 -->
	<table id="paraTable" width="94%">
	</table>
	<!-- END 2 -->
    </div>
 </div>
</div>	

<!--系统参数新增/修改窗口--> 
<div id="paraTypeOperDiv" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	 <div class="modal-dialog">
		 <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        <h4 class="modal-title"><label id="paraOperDivTitle">第三方用户</label></h4>
		      </div>
		      <div class="modal-body">
		        <form class="form-horizontal" method="post" role="form" id="paraOperForm" action="">
		        		<input type="hidden" id="operType" name="operType"/>
						<div class="form-group">
							<label class="col-lg-2 col-md-2 control-label required">appId：</label>
	                        <div class="col-lg-4 col-md-8">
	                        	<input class="form-control"  type="text" id="appId" name=appId placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <button type="button" id="appIdCreate" onclick="javascript:createUUID(0);">+</button>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label required">appSecret：</label>
	                        <div class="col-lg-4 col-md-8">
	                        	<input class="form-control"  type="text" id="appSecret" name="appSecret" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <button type="button" id="appSecretCreate" onclick="javascript:createUUID(1);">+</button>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label required">用户名：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="userName" name="userName" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label">手机号：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="phoneNumber" name="phoneNumber" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label required">状态：</label>
	                        <div class="col-lg-10 col-md-4">
	                        	<select class="form-control"  name="invalidFlag" id="invalidFlag">
		                                  <option value="0"> <s:message code="common.validFlag.v1" /></option>
		                                  <option value="1"> <s:message code="common.validFlag.v0" /> </option>
		                        </select>
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label">回调URL：</label>
	                        <div class="col-lg-4 col-md-8">
	                        	<input class="form-control"  type="text" id="callbackUrl" name="callbackUrl" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label">创建人：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control" readonly type="text" id="createdUser" name="createdUser" />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label">更新人：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control" readonly type="text" id="updatedUser" name="updatedUser" />
	                        </div>
	                   </div>
				</form>
		      </div>
		      <div class="modal-footer">
		      	<button type="button" id="btnParaOK" onclick="javascript:saveOrUpdatePara();" class="btn btn-primary"><s:message code="common.button.sure" /></button>
		        <button type="button" id="btnClose" class="btn btn-default" data-dismiss="modal" aria-hidden="true"><s:message code="common.button.close" /></button>	        
		      </div>
		 </div>
	</div>
</div>

<!-- 系统参数类型 新增/修改窗口 -->
<div id="paraOperDiv" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	 <div class="modal-dialog">
		 <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        <h4 class="modal-title"><label id="paraTypeOperDivTitle">资源配置</label></h4>
		      </div>
		      <div class="modal-body">
		        <form class="form-horizontal" method="post" role="form" id="paraTypeOperForm" action="">
						<input type="hidden" id="operType_Res"/>
						<input type="hidden" id="id_Res" name="id_Res"/>
						
						<div class="form-group">
							<label class="col-lg-2 col-md-2 control-label required">appId：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control" readonly type="text" id="appId_Res" name="appId_Res" />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label required">状态：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<select class="form-control"  name="invalidFlag_Res" id="invalidFlag_Res">
	                                  <option value="0"> <s:message code="common.validFlag.v1" /></option>
	                                  <option value="1"> <s:message code="common.validFlag.v0" /> </option>
	                             </select>
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label">资源URL：</label>
	                        <div class="col-lg-4 col-md-10">
	                        	<select class="form-control"  name="resourceId_Res" id="resourceId_Res">
	                                  <c:forEach var="oauthTransRes" items="${oauthTransResList}">
										<option value="${oauthTransRes.id}">${oauthTransRes.id}-${oauthTransRes.requestUrl}-${oauthTransRes.interfaceName}</option>
									  </c:forEach>
	                             </select>
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                   		<label class="col-lg-2 col-md-2 control-label">创建人：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control" readonly type="text" id="createdUser_Res" name="createdUser_Res" />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label">更新人：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control" readonly type="text" id="updatedUser_Res" name="updatedUser_Res"  />
	                        </div>
	                        
	                   </div>
				</form>
		      </div>
		      <div class="modal-footer">
		      	<button type="button" id="btnParaTypeOK" onclick="javascript:saveOrUpdateParaType();" class="btn btn-primary"><s:message code="common.button.sure" /></button>
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

<div id="gridPager1"></div>
<div id="gridPager2"></div>

</body>

<script language="Javascript" src="${ctx}/common/js/jquery/jquery-1.11.1.min.js"></script>
<script language="Javascript" src="${ctx}/common/js/bootstrap/bootstrap.min.js"></script>

<script language="Javascript" src="${ctx}/common/js/json2.js"></script>
<script language="Javascript" src="${ctx}/common/js/yafa-restful-client.js"></script>

<script language="Javascript" src="${ctx}/common/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script language="Javascript" src="${ctx}/common/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>

<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/jquery.validate.js" ></script>
<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/validation_zh.js" ></script>

<script language="Javascript" src="${ctx}/common/js/plugins/jquery-autocomplete/jq-ac.js"></script>
<script language="Javascript" src="${ctx}/common/js/plugins/jquery-autocomplete/jq-ac-custom.js"></script>

<script language="Javascript" src="${ctx}/common/js/jquery-ui/jquery-ui-1.10.4.custom.min.js"></script>

<script language="Javascript" src="${ctx}/js/commonUtil.js" charset="UTF-8"></script>

<script language="Javascript" src="${ctx}/js/auth/user/userManage.js"></script>

</html>