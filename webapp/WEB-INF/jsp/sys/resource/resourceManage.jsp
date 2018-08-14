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
<link href="${ctx}/common/css/bootstrap.min.css"  rel="stylesheet" type="text/css">
<link href="${ctx}/common/css/font-awesome.min.css"  rel="stylesheet" type="text/css">
<link href="${ctx}/css/cms.css" rel="stylesheet" type="text/css">
<style type="text/css">
.widget .widget-content {
  margin-left: 0px;
  padding:7px 12px;
}
.widget .widget-header {
  font-size: 13px;
	font-weight: bold;
    padding: 8px 15px;
  height: 35px;
  
  border-bottom-width: 1px;
  border-bottom-style: solid;
  border-bottom-color: lightgray;
  background-color: #eeeeee;
}

.modal-content{
	width: 600px;
}
.modal-body{
	padding-bottom : 1px;
}
form {
	padding-bottom : 1px;	
}
</style>
</head>
<body class="comp-tree">

<div class="container">
 <div class="row">
  <div class="col-md-3">
	<!-- HOVER ROWS -->
	<div class="widget widget-table">
		<div class="widget-header">
			<div class="pull-left"><s:message code="cmsResource.reourceMenuTittle" /></div>
		</div>
		<div class="widget-content">
		<%-- 	<a href="javascript:operResource('add','0');" class="btn btn-primary"><i class="fa fa-plus"></i> <s:message code="common.button.add" /></a> --%>
			<div id = "treeview-menu" class = ""></div>
		</div>
	</div>
	<!-- END HOVER ROWS -->
  </div>
  
  <div class="col-md-6">
	<!-- HOVER ROWS -->
	<div class="widget widget-table">
		<div class="widget-header">
			<div class="pull-left"><s:message code="cmsResource.reourceServiceTittle" /></div>
		</div>
		<div class="widget-content">
			<div id = "treeview-service" class = ""></div>
		</div>
	</div>
	<!-- END HOVER ROWS -->
  </div>
  <div class="col-md-3"></div>
</div>
</div>

<!--新增/修改窗口--> 
<div id="resourceOperDiv" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	 <div class="modal-dialog">
		 <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        <h4 class="modal-title"><label id="resourceOperDivTitle"><s:message code="cmsResource.reourceWindow" /></label></h4>
		      </div>
		      <div class="modal-body">
		        <form class="form-horizontal" method="post" role="form" id="resourceOperForm" action="">
					<input type="hidden" id="resourceOperType"/>
					<input type="hidden" id="parentResourceId" name="parentResourceId"/>

					<div class="form-group">
						<label class="col-lg-2 col-md-2  control-label  required"><s:message code="cmsResource.resourceId" /> </label>
                        <div class="col-lg-4 col-md-4">
                        	<input type="text" class="form-control" id="resourceId"  name="resourceId" placeholder="<s:message code='commom.input.placeholder' /> " readonly="readonly"/>
                        </div>
                        <label class="col-lg-2 col-md-2  control-label  required"><s:message code="cmsResource.resourceName" /> </label>
                        <div class="col-lg-4 col-md-4">
                        	<input type="text" class="form-control" id="resourceName" name="resourceName" placeholder="<s:message code='commom.input.placeholder' /> " />
                        </div>
                    </div>
					<div class="clearfix"></div>
                    <div class="form-group">
                        <label class="col-lg-2  col-md-2 control-label  required"><s:message code="cmsResource.resourceType" /> </label>
                        <div class="col-lg-4 col-md-4">
                            <select class="form-control" id="resourceType" name="resourceType"  value=" <s:message code='commom.input.placeholder' />">
                            </select>
                        </div>
                        <label class="col-lg-2 col-md-2 control-label  required"><s:message code="cmsResource.resourceLevel" /> </label>
                        <div class="col-lg-4 col-md-4">
                            <input type="text" class="form-control" id="resourceLevel" name="resourceLevel" placeholder=" <s:message code='commom.input.placeholder' />" readonly="readonly"/>
                        </div>
                    </div>
					<div class="clearfix"></div>
                    <div class="form-group">
                        <label class="col-lg-2  col-md-2 control-label  required"><s:message code="cmsResource.parentResourceName" /> </label>
                        <div class="col-lg-4 col-md-4">
                        	<input type="text" class="form-control" id="parentResourceName"  name="parentResourceName" placeholder=" <s:message code='commom.input.placeholder' />" readonly="readonly" />
                        </div>
                        <label class="col-lg-2  col-md-2 control-label  required"><s:message code="cmsResource.actionUrl" /> </label>
                        <div class="col-lg-4 col-md-4">
                        	<input type="text" class="form-control" id="actionUrl" name="actionUrl" placeholder=" <s:message code='commom.input.placeholder' /> " />
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="form-group">
                         <label class="col-lg-2 col-md-2 control-label  required"><s:message code="cmsResource.resourceIconClass" /> </label>
						 <div class="col-lg-4 col-md-4" >
								<input	type="text" class="form-control" id="resourceIconClass"   name="resourceIconClass" placeholder=" <s:message code='commom.input.placeholder' />" /> 
						 </div>
						 <label class="col-lg-2 col-md-2 control-label  required"><s:message code="cmsResource.displayOrder" /> </label>
						 <div class="col-lg-4 col-md-4" >
								<input	type="text" class="form-control" id="displayOrder"   name="displayOrder" placeholder="<s:message code='commom.input.placeholder' /> " /> 
						 </div>
					</div>
                    <div class="clearfix"></div>
                    <div class="form-group">
                         <label class="col-lg-2 col-md-2 control-label"><s:message code="cmsResource.endFlag" /></label>
						 <div class="col-lg-10 col-md-10" id="radiobtn">
						</div>
                    </div>
				</form>
		      </div>
		      <div class="modal-footer">
		      	<button type="button" id="btnOk" onclick="javascript:saveOrUpdateResource();" class="btn btn-primary"><s:message code="common.button.sure" /></button>
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
		        <p><label id="alertMsg" ><s:message code="common.alertMag" /></label><label id="url" class="hide"></label></p>
		      </div>
		      <div class="modal-footer">
		      	<button type="button" id="alertOk" class="btn btn-primary"><s:message code="common.button.sure" /></button>
		        <button type="button" id="alertClose" class="btn btn-default" data-dismiss="modal" aria-hidden="true"><s:message code="common.button.close" /></button>	        
		      </div>
		 </div>
	</div>
</div> 	
								
</body>
<script language="Javascript" src="${ctx}/common/js/jquery/jquery-1.11.1.js"></script>
<script language="Javascript" src="${ctx}/common/js/bootstrap/bootstrap.min.js"></script>
<script language="Javascript" src="${ctx}/common/js/plugins/tree/jstree.js"></script>
<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/jquery.validate.js" ></script>
<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/validation_zh.js" ></script>
<script language="Javascript" src="${ctx}/js/commonUtil.js"></script>
<script language="Javascript" src="${ctx}/common/js/json2.js"></script>
<script language="Javascript" src="${ctx}/common/js/yafa-restful-client.js"></script>
<script language="Javascript" src="${ctx}/js/sys/resource/resourceManage.js "></script>
<script type="text/javascript">
contextPath = '${ctx}';

var resource='<s:message code="cmsResource.reource"/>' ;
var reourceMenu='<s:message code="cmsResource.reourceMenu"/>' ;
var reourceBtn='<s:message code="cmsResource.reourceBtn"/>' ;
var reourceService='<s:message code="cmsResource.reourceService"/>' ;
var point ='<s:message code="cmsResource.point" />' ;
var addBtnTxt= '<s:message code="common.button.add"/>' ;
var viewBtnTxt= '<s:message code="common.button.view"/>' ;
var editBtnTxt= '<s:message code="common.button.edit"/>' ;
var saveBtnTxt='<s:message code="common.button.save"/>' ;
var delBtnTxt= '<s:message code="common.button.delete" />' ;
var bannedBtnTxt='<s:message code="cmsMapping.bannedBtnTxt" />' ;
var editMsgTxt= '<s:message code="common.msg.edit" />' ;
var editOneMsgTxt= '<s:message code="common.msg.editOne" />' ;
var infoMsgTxt= '<s:message code="common.msg.info" />' ;
var confirmMsgTxt= '<s:message code="common.msg.confirm" />' ;
var errorMsgTxt= '<s:message code="common.msg.error" />' ;
var successMsgTxt= '<s:message code="common.msg.success" />' ;
var warnMsgTxt= '<s:message code="common.msg.warn" />' ;
var deleteRowMsgTxt= '<s:message code="common.msg.delete" />' ;
var deleteRowMsgSureTxt= '<s:message code="common.msg.deleteSure" />' ;
var validFalgTxt0= '<s:message code="common.validFlag.v0" />' ;//无效
var validFlagTxt1= '<s:message code="common.validFlag.v1" />' ;//有效
var radioBtnY='<s:message code="common.yes" />' ;
var radioBtnN='<s:message code="common.no" />' ;
var delMsg='<s:message code="cmsResource.delMsg" />' ;
var delMsgSure='<s:message code="cmsResource.delMsgSure" />' ;
var addErrorMsg='<s:message code="cmsResource.addErrorMsg" />' ;


</script>
</html>