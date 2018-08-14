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
						        <td class="title"><label><s:message code="cmsParameter.parameterType" />：</label></td>
						        <td><input type="text" class="form-control" id="parameterType_search" name="parameterType_search"
										data-ac='{"dataFn":dataFn,"valueKey":"value","labelFn":labelFn,"matchLabel":true,"minChars": 0}'
										placeholder=" <s:message code='commom.input.placeholder' />  " /></td>
						        <td class="title"><label><s:message code="commom.cname" />：</label></td>
						        <td><input type="text" class="form-control" id="parameterCname_search"
										name="parameterCname_search" placeholder=" <s:message code='commom.input.placeholder' /> " /></td>
						        <td class="title"><label><s:message code="common.flag" />：</label></td>
						        <td><select class="form-control"  name="validFlag_search" id="validFlag_search">
		                                  <option value=""> <s:message code="commom.select.placeholder" /> </option>
		                                  <option value="1"> <s:message code="common.validFlag.v1" /></option>
		                                  <option value="0"> <s:message code="common.validFlag.v0" /> </option>
		                             </select></td>
						    </tr>
						    <tr>
						        <td class="title"><label><s:message code="commom.ename" />：</label></td>
						        <td><input type="text" class="form-control" id="parameterEname_search"
										name="parameterEname_search" placeholder=" <s:message code='commom.input.placeholder' /> " /></td>
						        <td class="title"><label><s:message code="commom.tname" />：</label></td>
						        <td><input type="text" class="form-control" id="parameterTname_search"
										name="parameterTname_search" placeholder="<s:message code='commom.input.placeholder' />  " /></td>
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
<div id="paraOperDiv" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	 <div class="modal-dialog">
		 <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        <h4 class="modal-title"><label id="paraOperDivTitle"><s:message code="cmsParameter.newWinTitle" /></label></h4>
		      </div>
		      <div class="modal-body">
		        <form class="form-horizontal" method="post" role="form" id="paraOperForm" action="">
						<input type="hidden" id="operType"/>
						<input type="hidden" id="parameterId" name="parameterId"/>
						
						<div class="form-group">
							<label class="col-lg-2 col-md-2 control-label required"><s:message code="cmsParameter.parameterType" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="parameterType" name=parameterType placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label required"><s:message code="cmsParameter.parameterCode" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="parameterCode" name="parameterCode" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="commom.cname" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="parameterCname" name="parameterCname" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="commom.ename" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="parameterEname" name="parameterEname" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="commom.tname" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="parameterTname" name="parameterTname" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label required"><s:message code="common.validFlag" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<select class="form-control"  name="validFlag" id="validFlag">
	                                  <option value=""> <s:message code="commom.select.placeholder" /> </option>
	                                  <option value="1"> <s:message code="common.validFlag.v1" /></option>
	                                  <option value="0"> <s:message code="common.validFlag.v0" /> </option>
	                             </select>
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="common.flag" />：</label>
	                        <div class="col-lg-10 col-md-10">
	                        	<input class="form-control"  type="text" id="flag" name="flag" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="common.remark" />：</label>
	                        <div class="col-lg-10 col-md-10">
	                        	<textarea class="form-control"  id="remark" name="remark" rows="3" cols="65" onpropertychange= "this.style.posHeight=this.scrollHeight " placeholder="<s:message code='commom.input.placeholder' /> "></textarea>
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
<div id="paraTypeOperDiv" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	 <div class="modal-dialog">
		 <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        <h4 class="modal-title"><label id="paraTypeOperDivTitle"><s:message code="cmsParameterType.newWinTitle" /></label></h4>
		      </div>
		      <div class="modal-body">
		        <form class="form-horizontal" method="post" role="form" id="paraTypeOperForm" action="">
						<input type="hidden" id="operTypeT"/>
						<input type="hidden" id="parameterTypeId" name="parameterTypeId"/>
						
						<div class="form-group">
							<label class="col-lg-2 col-md-2 control-label required"><s:message code="cmsParameter.parameterType" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="parameterTypeT" name=parameterType placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="commom.cname" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="parameterTypeCname" name=parameterTypeCname placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="commom.ename" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="parameterTypeEname" name="parameterTypeEname" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="commom.tname" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="parameterTypeTname" name="parameterTypeTname" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label required"><s:message code="common.validFlag" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<select class="form-control"  name="validFlag" id="validFlagT">
	                                  <option value=""> <s:message code="commom.select.placeholder" /> </option>
	                                  <option value="1"> <s:message code="common.validFlag.v1" /></option>
	                                  <option value="0"> <s:message code="common.validFlag.v0" /> </option>
	                             </select>
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="common.flag" />：</label>
	                        <div class="col-lg-10 col-md-10">
	                        	<input class="form-control"  type="text" id="flagT" name="flag" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="common.remark" />：</label>
	                        <div class="col-lg-10 col-md-10">
	                        	<textarea id="remarkT" class="form-control"  name="remark" rows="3" cols="65" onpropertychange="this.style.posHeight=this.scrollHeight" placeholder="<s:message code='commom.input.placeholder' /> "></textarea>
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

<script language="Javascript" src="${ctx}/js/commonUtil.js" charset="UTF-8"></script>

<script language="Javascript" src="${ctx}/js/sys/para/paraManage.js"></script>

</html>