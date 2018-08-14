<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta charset="UTF-8">
<title>易安财产保险股份有限公司</title>
<link href="${ctx}/common/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/common/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/cms.css" rel="stylesheet" type="text/css">
<style type="text/css">
.widget .widget-content {
	margin-left: 0px;
	padding: 7px 12px;
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
			<div class="col-md-12">
				<!-- 1 start -->
				<div class="widget">
					<div class="widget-header">
						<div class="form-group">
							<div class="pull-left">
								<s:message code='cmsUser.modifyPassword.modify' /> 
							</div>
						</div>
					</div>
					<div class="widget-content">
						<form class="form-horizontal" method="post" role="form"
							id="modifyForm" action="">
							<table class="table-query">
								<tr>
								    <td class="title"></td>
									<td class="title"><label><font color="red">*</font><s:message code='cmsUser.modifyPassword.passwordOld' />：</label></td>
									<td><input type="password" class="form-control"
										id="passwordOld" name="passwordOld"
										placeholder=" <s:message code='commom.input.placeholder' />  " />
									</td>
									<td class="title" style="text-align:left"><label id="passwordOldMsg"></label></td>
									<td class="title"></td>
								</tr>
								<tr>
									<td class="title"></td>
									<td class="title"><label><font color="red">*</font><s:message code='cmsUser.modifyPassword.passwordNew' />：</label></td>
									<td><input type="password" class="form-control"
										id="passwordNew" name="passwordNew"
										placeholder=" <s:message code='commom.input.placeholder' />  " />
									</td>
									<td class="title" style="text-align:left"><label id="passwordNewMsg"></label></td>
									<td class="title"></td>
								</tr>
								<tr>
									<td class="title"></td>
									<td class="title"><label><font color="red">*</font><s:message code='cmsUser.modifyPassword.passwordNewSec' />：</label></td>
									<td><input type="password" class="form-control"
										id="passwordNewSec" name="passwordNewSec"
										placeholder=" <s:message code='commom.input.placeholder' />  " />
									</td>
									<td class="title" style="text-align:left"><label id="passwordNewSecMsg"></label></td>
									<td class="title"></td>
								</tr>
							</table>
							<div class="table-query-action">
								<button type="button" onclick="javascript:modifyPassword();"
									id="btnQry" class="btn btn-primary">
									<i class="fa fa-plus"></i><s:message code="common.button.save" />
								</button>
								<button type="button" 
									id="btnQry" class="btn btn-primary" onclick="closeModifyTab();">
									<i class="fa fa-retweet"></i><s:message code="common.button.close" />
								</button>
							</div>
						</form>
					</div>
				</div>
				<!-- END 1 -->
			</div>
		</div>
	</div>
	<!-- 消息窗口 -->
	<div id="alertModal" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">
						<label id="alertInfo"><s:message code="common.msg.info" /></label>
					</h4>
				</div>
				<div class="modal-body">
					<p>
						<label id="alertMsg"><s:message code="common.alertMag" /></label><label id="url" class="hide"></label>
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" id="alertOk" class="btn btn-primary"><s:message code="common.button.sure" /></button>
					<button type="button" id="alertClose" class="btn btn-default" onclick="javascript:closeModifyTab();"
						data-dismiss="modal" aria-hidden="true"><s:message code="common.button.close" /></button>
				</div>
			</div>
		</div>
	</div>
</body>
<script language="Javascript" src="${ctx}/common/js/jquery/jquery-1.11.1.js"></script>
<script language="Javascript" src="${ctx}/common/js/bootstrap/bootstrap.min.js"></script>
<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/jquery.validate.js" ></script>
<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/validation_zh.js" ></script>
<script language="Javascript" src="${ctx}/js/commonUtil.js" charset="UTF-8"></script>
<script language="Javascript" src="${ctx}/common/js/json2.js"></script>
<script language="Javascript" src="${ctx}/common/js/yafa-restful-client.js"></script>
<script language="Javascript" src="${ctx}/js/sys/user/preModifyPassword.js"></script>
<script type="text/javascript">

contextPath = '${ctx}';
var passwordOldMsg='<s:message code="cmsUser.modifyPassword.error.passwordOld" />';
var passwordNewMsg='<s:message code="cmsUser.modifyPassword.error.passwordNew" />';
var passwordNewSecMsg='<s:message code="cmsUser.modifyPassword.error.passwordNewSec" />';
var errorPasswordOldMsg='<s:message code="cmsUser.modifyPassword.error.errorPasswordOld" />';
var errorPasswordNewMsg='<s:message code="cmsUser.modifyPassword.error.errorPasswordNew" />';
var errorPasswordNewSecMsg='<s:message code="cmsUser.modifyPassword.error.errorPasswordNewSec" />';

var tabId='${tabId}';
var addBtnTxt= '<s:message code="common.button.add"/>' ;
var viewBtnTxt= '<s:message code="common.button.view"/>' ;
var editBtnTxt= '<s:message code="common.button.edit"/>' ;
var delBtnTxt= '<s:message code="common.button.delete" />' ;
var bannedBtnTxt='<s:message code="cmsMapping.bannedBtnTxt" />' ;
var editMsgTxt= '<s:message code="common.msg.edit" />' ;
var editOneMsgTxt= '<s:message code="common.msg.editOne" />' ;
var infoMsgTxt= '<s:message code="common.msg.info" />' ;
var confirmMsgTxt= '<s:message code="common.msg.confirm" />' ;
var errorMsgTxt= '<s:message code="common.msg.error" />' ;
var successMsgTxt= '<s:message code="common.msg.success" />' ;
var warnMsgTxt= '<s:message code="common.msg.warn" />' ;
var bannedRowMsgTxt= '<s:message code="cmsMapping.msg.banned" />' ;
var bannedRowMsgSureTxt= '<s:message code="cmsMapping.msg.bannedSure" />' ;
var deleteRowMsgTxt= '<s:message code="common.msg.delete" />' ;
var deleteRowMsgSureTxt= '<s:message code="common.msg.deleteSure" />' ;
var validFalgTxt0= '<s:message code="common.validFlag.v0" />' ;//无效
var validFlagTxt1= '<s:message code="common.validFlag.v1" />' ;//有效
var radioBtnY='<s:message code="common.yes" />' ;
var radioBtnN='<s:message code="common.no" />' ; 


</script>
</html>