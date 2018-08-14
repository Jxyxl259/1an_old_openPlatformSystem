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
								<s:message code='cmsJob.manageTable' />
							</div>
						</div>
					</div>
					<div class="widget-content">
						<form class="form-horizontal" method="post" role="form"
							id="queryConditionForm" action="">
							<table class="table-query">
								<tr>
									<td class="title"><label><s:message code='cmsJob.jobName' />：</label></td>
									<td><input type="text" class="form-control"
										id="searchJobName" name="searchJobName"
										placeholder=" <s:message code='commom.input.placeholder' />  " />
									</td>
									<td class="title"><label><s:message code='cmsJob.jobGroupName' />：</label></td>
									<td><input type="text" class="form-control"
										id="searchJobGroupName" name="searchJobGroupName"
										placeholder=" <s:message code='commom.input.placeholder' />  " />
									</td>
									<td class="title"><label><s:message code='cmsJob.jobStatus' />：</label></td>
									<td>	
									<select class="form-control" id="searchJobStatus" name="searchJobStatus">
									     <option value=""><s:message code='commom.select.placeholder' /></option>
							        </select>
									</td>
								</tr>
							</table>
							<div class="table-query-action">
								<button type="button" onclick="javascript:searchByName();"
									id="btnQry" class="btn btn-primary">
									<i class="fa fa-search"></i><s:message code="common.button.query" />
								</button>
								<button type="button" onclick="javascript:clearValue();"
									id="btnQry" class="btn btn-primary">
									<i class="fa fa-retweet"></i><s:message code="common.button.reset" />
								</button>
							</div>
						</form>
					</div>
					<table id="jobTable">
					</table>
					<br />
				</div>

				<!-- END 1 -->
			</div>
		</div>
	</div>

	<!--新增/修改窗口-->
	<div id="jobOperDiv" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">
						<label id="jobOperDivTitle"><s:message code='cmsJob.jobOperDivTitle' /></label>
					</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" method="post" role="form"
						id="jobOperForm" action="">
						<input type="hidden" id="operType" />
						<input type="hidden" id="jobId"  name="jobId"/> 
						
						<div class="form-group">
							<label class="col-lg-2 col-md-2 control-label  required"><s:message code='cmsJob.jobName' /> </label>
							<div class="col-lg-4 col-md-4">
								<input type="text" class="form-control" id="jobName"
									name="jobName" placeholder=" <s:message code='commom.input.placeholder' /> " />
							</div>
							<label class="col-lg-2 col-md-2 control-label  required"><s:message code='cmsJob.jobGroupName' /> </label>
							<div class="col-lg-4 col-md-4">
								<input type="text" class="form-control" id="jobGroupName"
									name="jobGroupName" placeholder=" <s:message code='commom.input.placeholder' /> " />
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="form-group">
							<label class="col-lg-2 col-md-2 control-label  required"><s:message code='cmsJob.jobStatus' /> </label>
							<div class="col-lg-4 col-md-4">
								<%-- <input type="text" class="form-control" id="jobStatus" value="0" readonly="readonly"
									name="jobStatus" placeholder=" <s:message code='commom.input.placeholder' /> " /> --%>
								<select class="form-control" readonly="readonly" id="jobStatus" name="jobStatus">
							    </select>
							</div>
							<label class="col-lg-2 col-md-2 control-label  required"><s:message code='cmsJob.cronExpression' /> </label>
							<div class="col-lg-4 col-md-4">
								<input type="text" class="form-control" id="cronExpression"
									name="cronExpression" placeholder=" <s:message code='commom.input.placeholder' /> " />
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="form-group">
							<label class="col-lg-2 col-md-2 control-label"><s:message code='cmsJob.jobClass' /></label>
							<div class="col-lg-4 col-md-4">
								<input type="text" class="form-control" id="jobClass"
									name="jobClass" placeholder=" <s:message code='commom.input.placeholder' /> " />
							</div>
							<label class="col-lg-2 col-md-2 control-label"><s:message code='cmsJob.springId' /></label>
							<div class="col-lg-4 col-md-4">
								<input type="text" class="form-control" id="springId"
									name="springId" placeholder=" <s:message code='commom.input.placeholder' /> " />
							</div>
						</div>
			            <div class="clearfix"></div>
						<div class="form-group">
							<label class="col-lg-2 col-md-2 control-label"><s:message code='cmsJob.methodName' /></label>
							<div class="col-lg-4 col-md-4">
								<input type="text" class="form-control" id="methodName"
									name="methodName" placeholder=" <s:message code='commom.input.placeholder' /> " />
							</div>
							<label class="col-lg-2 col-md-2 control-label"><s:message code='cmsJob.restfulUrl' /></label>
							<div class="col-lg-4 col-md-4">
								<input type="text" class="form-control" id="restfulUrl"
									name="restfulUrl" placeholder=" <s:message code='commom.input.placeholder' /> " />
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="form-group">
							<label class="col-lg-2 col-md-2 control-label"><s:message code='cmsJob.remark' /></label>
							<div class="col-lg-10 col-md-10 ">
								<textarea id="remark" name="remark" class="form-control"
									rows="2" cols="30" maxlength="99"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="btnOk"
						onclick="javascript:saveOrUpdateJob();"
						class="btn btn-primary"><s:message code="common.button.sure" /></button>
					<button type="button" id="btnClose" class="btn btn-default"
						data-dismiss="modal" aria-hidden="true"><s:message code="common.button.close" /></button>
				</div>
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
					<button type="button" id="alertClose" class="btn btn-default"
						data-dismiss="modal" aria-hidden="true"><s:message code="common.button.close" /></button>
				</div>
			</div>
		</div>
	</div>
	
	<div id="gridPager"></div>

</body>
<script language="Javascript" src="${ctx}/common/js/jquery/jquery-1.11.1.js"></script>
<script language="Javascript" src="${ctx}/common/js/bootstrap/bootstrap.min.js"></script>
<script language="Javascript" src="${ctx}/common/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script language="Javascript" src="${ctx}/common/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/jquery.validate.js" ></script>
<script language="Javascript"  src="${ctx}/common/js/plugins/jquery-validation/validation_zh.js" ></script>
<script language="Javascript" src="${ctx}/js/commonUtil.js"></script>
<script language="Javascript" src="${ctx}/common/js/json2.js"></script>
<script language="Javascript" src="${ctx}/common/js/yafa-restful-client.js"></script>
<script language="Javascript" src="${ctx}/js/sys/job/jobManage.js" ></script>
<script type="text/javascript">
contextPath = '${ctx}';

var jobStatusN='<s:message code="cmsJob.jobStatus.notRun"/>';
var jobStatusR='<s:message code="cmsJob.jobStatus.running"/>';
var jobStatusS='<s:message code="cmsJob.jobStatus.stop"/>';
var jobStatusL='<s:message code="cmsJob.jobStatus.lock"/>';
var jobStatusU='<s:message code="cmsJob.jobStatus.unlock"/>';
var msgBanned='<s:message code="cmsJob.msg.errorbanned"/>';
var errorNull='<s:message code="cmsJob.msg.errorNull"/>';
var errorConf='<s:message code="cmsJob.msg.errorConf"/>';
var btnR='<s:message code="cmsJob.btn.run"/>';
var btnS='<s:message code="cmsJob.btn.stop"/>';
var jobT = '<s:message code="cmsJob.job"/>';
var jobIdT = '<s:message code="cmsJob.jobId"/>';
var jobNameT = '<s:message code="cmsJob.jobName"/>';
var jobGroupNameT = '<s:message code="cmsJob.jobGroupName"/>';
var cronExpressionT = '<s:message code="cmsJob.cronExpression"/>';
var jobStatusT = '<s:message code="cmsJob.jobStatus"/>';
var remarkT = '<s:message code="cmsJob.remark"/>';
var operT = '<s:message code="cmsJob.oper"/>';
var errorLockT = '<s:message code="cmsJob.msg.errorLock"/>';
var errorUnlockT = '<s:message code="cmsJob.msg.errorUnlock"/>';
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

</script>
</html>