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

var newWinTitle= '<s:message code="cmsConfig.newWinTitle" />' ;

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

var validFalgTxt0= '<s:message code="common.validFlag.v0" />' ;//无效
var validFlagTxt1= '<s:message code="common.validFlag.v1" />' ;//有效

var configIDTxt='<s:message code="cmsConfig.configId"/>';
var configCodeTxt='<s:message code="cmsConfig.configCode"/>';
var configNameTxt='<s:message code="cmsConfig.configName"/>'; 
var configValueTxt='<s:message code="cmsConfig.configValue"/>'; 
var condition1Txt='<s:message code="cmsConfig.condition1" />';              
var condition2Txt='<s:message code="cmsConfig.condition2" />';              
var condition3Txt='<s:message code="cmsConfig.condition3" />';              
var condition4Txt='<s:message code="cmsConfig.condition4" />';              
var condition5Txt='<s:message code="cmsConfig.condition5" />';

var validFlagTxt='<s:message code="common.validFlag" />';
var remarkTxt='<s:message code="common.remark" />';
var flagTxt='<s:message code="common.flag" />';
</script>
</head>
<body>

<div class="container">
<!-- 条件区域 -->
 <div class="row">
  <div class="col-md-12">
	<!-- 1 start -->
		<div class="widget" >
			<div class="widget-header">
				<div class="pull-left"> <s:message code="common.button.query" /><s:message code="commom.grid.query.titleAppend" /></div>
				<div class="clearfix"></div>
			</div>
			<div class="widget-content">
				<form class="form-horizontal" method="post" role="form" id="configSearchForm" action="">
					<table class="table-query">
							<tr>
								<td class="title">
									<s:message code="cmsConfig.configCode" />：
								</td>
								<td>
									<input class="form-control" type="text" id="configCode_search" name="configCode_search" 
	                    				placeholder=" <s:message code='commom.input.placeholder' /> " />
								</td>
								<td class="title">
									<s:message code="cmsConfig.configName" />：
								</td>
								<td>
									<input class="form-control" type="text" id="configName_search" name="configName_search" 
	                    				placeholder=" <s:message code='commom.input.placeholder' /> " />
								</td>
								<td class="title">
									<s:message code="cmsConfig.configValue" />：
								</td>
								<td>
									<input class="form-control" type="text" id="configValue_search" name="configValue_search" 
	                    				placeholder=" <s:message code='commom.input.placeholder' /> " />
								</td>
							</tr>
							<tr>
								<td class="title">
									<s:message code="common.validFlag" />：
								</td>
								<td>
									<select class="form-control"  name="validFlag_search" id="validFlag_search">
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
  </div>
 </div>
  <!-- 列表区域 -->
 <div class="row">
  <div class="col-md-12">
	<!-- 1 start -->
		<div class="widget" >
			<div class="widget-header">
				<div class="pull-left"> <s:message code="cmsConfig.newWinTitle" /><s:message code="commom.grid.list.titleAppend" /></div>
				<div class="clearfix"></div>
			</div>
			<div class="widget-content">
				<table id="configTable">
					
				</table>
			</div>
		</div>
	
	<!-- END 1 -->
  </div>
  
 </div>

<!--系统配置新增/修改窗口--> 
<div id="configOperDiv" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	 <div class="modal-dialog">
		 <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        <h4 class="modal-title"><label id="configOperDivTitle"> <s:message code="cmsConfig.newWinTitle" /> </label></h4>
		      </div>
		      <div class="modal-body">
		        <form class="form-horizontal" method="post" role="form" id="configOperForm" action="">
						<input type="hidden" id="operType"/>
						<input type="hidden" id="configId" name="configId"/>
						
						<div class="form-group">
							<label class="col-lg-2 col-md-2 control-label required"><s:message code="cmsConfig.configCode" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="configCode" name=configCode placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="cmsConfig.configName"/>：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="configName" name="configName" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                   		<label class="col-lg-2 col-md-2 control-label required"><s:message code="cmsConfig.configValue"/>：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="configValue" name="configValue" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="cmsConfig.condition1" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="condition1" name="condition1" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                   		<label class="col-lg-2 col-md-2 control-label"><s:message code="cmsConfig.condition2" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="condition2" name="condition2" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="cmsConfig.condition3" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="condition3" name="condition3" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                   		<label class="col-lg-2 col-md-2 control-label"><s:message code="cmsConfig.condition4" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="condition4" name="condition4" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="cmsConfig.condition5" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<input class="form-control"  type="text" id="condition5" name="condition5" placeholder=" <s:message code='commom.input.placeholder' /> " />
	                        </div>
	                        
	                   </div>
	                   <div class="clearfix"></div>
	                   <div class="form-group">
	                        <label class="col-lg-2 col-md-2 control-label required"><s:message code="common.validFlag" />：</label>
	                        <div class="col-lg-4 col-md-4">
	                        	<select class="form-control"  name="validFlag" id="validFlag">
	                                  <option value=""> <s:message code="commom.select.placeholder" /> </option>
	                                  <option value="1"> <s:message code="common.validFlag.v1" /></option>
	                                  <option value="0"> <s:message code="common.validFlag.v0" /> </option>
	                             </select>
	                        </div>
	                        <label class="col-lg-2 col-md-2 control-label"><s:message code="common.flag" />：</label>
	                        <div class="col-lg-4 col-md-4">
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
		      	<button type="button" id="btnOK" onclick="javascript:saveOrUpdateConfig();" class="btn btn-primary"><s:message code="common.button.sure" /></button>
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


<script language="Javascript" src="${ctx}/common/js/jquery/jquery-1.11.1.min.js"></script>
<script language="Javascript" src="${ctx}/common/js/bootstrap/bootstrap.min.js"></script>

<script language="Javascript" src="${ctx}/common/js/json2.js"></script>
<script language="Javascript" src="${ctx}/common/js/yafa-restful-client.js"></script>

<script language="Javascript" src="${ctx}/common/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script language="Javascript" src="${ctx}/common/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>

<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/jquery.validate.js" ></script>
<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/validation_zh.js" ></script>

<script language="Javascript" src="${ctx}/js/commonUtil.js" charset="UTF-8"></script>

<script language="Javascript" src="${ctx}/js/sys/config/configManage.js"></script>

</html>