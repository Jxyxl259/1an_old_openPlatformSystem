<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>  
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <script type="text/javascript">
		var contextPath = "${ctx}" ;
	</script>
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<!-- CSS -->
	<link href="${ctx}/common/css/bootstrap.min.css"  rel="stylesheet" type="text/css">
	<link href="${ctx}/common/css/font-awesome.min.css"  rel="stylesheet" type="text/css">
    <link href="${ctx}/css/cms.css"  rel="stylesheet" type="text/css">
    <script language="Javascript" src="${ctx}/common/js/jquery/jquery-1.11.1.min.js"></script>
    <script language="Javascript" src="${ctx}/common/js/json2.js"></script>
    <script language="Javascript" src="${ctx}/common/js/yafa-restful-client.js"></script>
    <script language="Javascript" src="${ctx}/common/js/plugins/jquery-validation/jquery.validate.js"></script>
    <script language="Javascript" src="${ctx}/common/js/plugins/jquery-validation/validation_zh.js"></script>
    <script language="Javascript" src="${ctx}/common/js/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script>
 	<script language="Javascript" src="${ctx}/common/js/bootstrap/bootstrap.min.js"  ></script>
 	<script language="Javascript" src="${ctx}/common/js/plugins/tree/jstree.js"></script>
 	<script language="Javascript" src="${ctx}/common/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
    <script language="Javascript" src="${ctx}/common/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
    <script language="Javascript" src="${ctx}/common/js/plugins/jquery-autocomplete/jq-ac.js"></script>
    <script language="Javascript" src="${ctx}/common/js/plugins/jquery-autocomplete/jq-ac-custom.js"></script>
	<style type="text/css">
		label.error {
			color: red; /*错误信息的颜色*/
		}
		input.error {
			border: 1px dotted red; /*输入错误的输入框的边框样式*/
		}
		select.error {
			border: 1px dotted red; /*输入错误的输入框的边框样式*/
		}
	</style>
</head>
<body>
	<!-- WRAPPER -->
	  <input type="hidden"  name= "alertMsg1" value="<s:message code="cmsUser.userSelectedOne"/>" /> 
	  <input type="hidden"  name= "alertMsg2" value="<s:message code="cmsUser.userSelectedAtListOne"/>" /> 
	  <div class="row">
		 <div class="col-md-12">
			 <div class="widget">
	  	          <div class="container">
				 	<!-- main -->
					    <div class="widget-header">
						   <div class="form-group">
							<div class="pull-left">
								<s:message code="cmsUser.userManager" />
							</div>
						</div>
					   </div>
					
                         <div class="widget-content">
                           <form class="form-horizontal" method="post" role="form"
							id="queryConditionForm" action="">
							<table class="table-query">
								<tr>
									<td class="title"><label><s:message code="cmsUser.userCode" />：</label></td>
									<td><input type="text" class="form-control"
										id="searchUserCode" name="searchUserCode"
										placeholder=" <s:message code='commom.input.placeholder' />  " />
									</td>
									<td class="title"><label><s:message code="cmsUser.userCname" />：</label></td>
									<td><input type="text" class="form-control"
										id="searchUserCName" name="searchUserCName"
										placeholder=" <s:message code='commom.input.placeholder' />  " />
									</td>
									<td class="title"><label><s:message code="cmsUser.userTname" />：</label></td>
									<td><input type="text" class="form-control"
										id="searchUserTName" name="searchUserTName"
										placeholder=" <s:message code='commom.input.placeholder' />  " />
									</td>
								</tr>
							</table>
							<div class="table-query-action">
								<button type="button" onclick="javascript:query();"
									id="btnQry" class="btn btn-primary">
									<i class="fa fa-search"></i><s:message code="common.button.query" />
								</button>
								<button type="button" onclick="javascript:resetValue();"
									id="btnQry" class="btn btn-primary">
									<i class="fa fa-retweet"></i><s:message code="common.button.reset" />
								</button>
							</div>
						</form>	
					 
                  <!--start userModal DIALOG --> 	
							   <div class="modal fade" id="userModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-dialog" style="display: inline-block; width: auto;">
									 <div class="modal-content">
									   <div class="modal-header">
																<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
																<h4 class="modal-title" id="myModalLabel"><i class="fa fa-pencil-square-o"><font color='blue'><s:message code="cmsUser.userEdit" /></font></i></h4>
															</div>
									   <div class="modal-body">
										  <!--  user form  -->
										      <input type="hidden" id="actionType" name="actionType" value="${actionType}" />
											  <form id="userForm" action="">
	                                                  <div  class="widget">
										                 <div class="widget-content">
			 									         <div class="form-horizontal">
													      <div class="form-group">
														    <label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.userCode" /> <font color='red'>*</font></label>
														     <div class="col-md-2">
															   <input class="form-control input-sm" id="userCode"  name="userCode" type="text"  value=""  placeholder=" <s:message code='commom.input.placeholder' /> " >
														  </div>
														  <label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.userCname" /><font color='red'>*</font></label>
														  <div class="col-md-2">
															 <input class="form-control input-sm" id="userCname"  name="userCname"  type="text" value="" placeholder=" <s:message code='commom.input.placeholder' /> " >
														  </div>
														  <label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.userEname" /></label>
														  <div class="col-md-2">
															 <input class="form-control input-sm" id="userEname"  name="userEname"  type="text" value="" placeholder=" <s:message code='commom.input.placeholder' /> ">
														   </div>
														      <label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.userTname" /></label>
														   <div class="col-md-2">
														     	<input class="form-control input-sm" id="userTname"  name="userTname"  type="text" value="" placeholder=" <s:message code='commom.input.placeholder' /> ">
														   </div>
													    </div>
													<div class="form-group">
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.password" /><font color='red'>*</font></label>
														<div class="col-md-2">
															<input class="form-control" type="password" id="password" name="password"  placeholder="">
														</div>
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.passwordSetDate" /></label>
														<div class="col-md-2">
															<input  class="form-control" class="datepicker"  type="text" id="passwordSetDate"  name="passwordSetDate"  placeholder=" <s:message code='commom.input.placeholder' />" data-date-format="yyyy-mm-dd"  >
														</div>
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.passwordExpireDate" /><font color='red'>*</font></label>
														<div class="col-md-2">
															<input  class="form-control" class="datepicker" id="passwordExpireDate" name="passwordExpireDate"  placeholder=" <s:message code='commom.input.placeholder' />"   data-date-format="yyyy-mm-dd"      >
														</div>
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.seal" /></label>
														<div class="col-md-2">
															<input class="form-control" type="password" id="seal"  name="seal"  value="" placeholder=" <s:message code='commom.input.placeholder' /> ">
														</div>
													</div>
													 <div class="form-group">
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.mobile" /></label>
														<div class="col-md-2">
															<input class="form-control" type="text" id="mobile"  name="mobile" value="" placeholder=" <s:message code='commom.input.placeholder' /> ">
														</div>
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.phone" /></label>
														<div class="col-md-2">
															<input class="form-control" type="text"  id="phone" name="phone"  value="" placeholder=" <s:message code='commom.input.placeholder' /> ">
														</div>
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.postCode" /></label>
														<div class="col-md-2">
															<input class="form-control" type="text"  id="postCode" name="postCode"  value="" placeholder=" <s:message code='commom.input.placeholder' /> ">
														</div>
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.email" /></label>
														<div class="col-md-2">
															<input class="form-control" type="text"  id="email" name="email" value=""  	placeholder=" <s:message code='commom.input.placeholder' /> ">
														</div>
													</div>
													<div class="form-group">
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.companyCode" /><font color='red'>*</font></label>
														<div class="col-md-2">
															<input class="form-control" type="text" id="companyCode" name="companyCode" 
                        	                                      data-ac='{"dataFn":dataFn1,"valueKey":"value","labelFn":labelFn,"matchLabel":true,"minChars": 0}'
                                                              	placeholder=" <s:message code='commom.input.placeholder' /> " />
                        	                         	</div>
													 	<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="common.remark" /></label>
														<div class="col-md-2">
															<input class="form-control" type="text" id="remark"  name="remark" value="" placeholder=" <s:message code='commom.input.placeholder' /> ">
														</div>
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="common.flag" /></label>
														<div class="col-md-2">
															<input class="form-control" type="text" id="flag" name="flag" value="" placeholder=" <s:message code='commom.input.placeholder' /> ">
														</div>
													   <label class="col-md-1 control-label"><s:message code="common.validFlag" /><font color='red'>*</font></label>
													   <div class="col-md-2">
															<select class="form-control" name="validFlag" id="validFlag" >
														    </select>
														  </div>
														
													</div>
													<!-- TEXTAREA -->
										           <div class="widget-content">
												       <p><s:message code="cmsUser.address" /></p>
												       <textarea class="form-control" rows="5" cols="30"  id="address"  name="address" ></textarea>
												     </div>
													
						                    		<div class="form-group">
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="common.createdBy" /></label>
														<div class="col-md-2">
															<input class="form-control" type="text" id="createdBy"   name="createdBy"  value="" READONLY >
														</div>
													 	<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="common.createdDate" /></label>
														<div class="col-md-2">
															<input class="form-control" class="datepicker" type="text" id="createdDate"   name="createdDate" data-date-format="yyyy-mm-dd"  value="" READONLY >
														</div>
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="common.updatedBy" /></label>
														<div class="col-md-2">
															<input class="form-control" type="text" id="updatedBy"   name="updatedBy" value=""   READONLY >
														</div>
															<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="common.updatedDate"  /></label>
														<div class="col-md-2">
															<input class="form-control" class="datepicker" type="text" id="updatedDate"   name="updatedDate"  data-date-format="yyyy-mm-dd"   value=""  READONLY >
														</div>
													</div>
													<div class="form-group" style="display:none">
														<label for="exampleInputFile" class="col-md-1 control-label"><s:message code="cmsUser.userId" /></label>
														<div class="col-md-2">
															<input class="form-control" type="text" id="userId"  name="userId" value="" READONLY  >
														</div>
													 </div>
										   </div>
										 </div>
										</div>
					                  
					                     <div class="modal-footer">
													<input id="buttonSave"  type="submit"  class="btn btn-primary" value="<s:message code="common.button.save" />"   >
									            	<button type="button" class="btn btn-default" data-dismiss="modal"  onclick = "closeEdit()"><s:message code="common.button.close" /></button>
								    	 </div>
					                   </form>				
									<!-- end userform  -->							
								  </div>
								  </div>
							      </div>
								  </div>
					  <!-- END userModal DIALOG -->
					 				
					   </div>
					  <table id="userTable">
					 </table>
					 <div id="gridPager"></div>
				   <!-- end user list -->   
			 	  </div>
				<!-- /conten -->
			</div>
			<!-- /container -->
		   </div>
		 </div>
  <!-- start roleModal  -->
  <div class="modal fade" id="roleModal" tabindex="-1" role="dialog" aria-labelledby="roleModalLabel" aria-hidden="true">
	  <div class="modal-dialog"  style="width: auto;">
	   <div class="modal-content">
		  <div class="modal-body">			
		      <div class="row">
                      <div class="col-md-4">
	                     <div class="list-group" >
			                      <a href="#" class="list-group-item active"><span id="rowCountId" class="badge"></span><s:message code="cmsUser.roleList" />(<s:message code="cmsUser.userCode" />: <label id="userCodeDisplayLable"></label>)</a>
			                      <input type="hidden" id="userCodeOpr" name="userCodeOpr"  >
			                      <div class="list-group-item">
				                         <table id="roleTable" class="table table-hover table-bordered  ">
					                            <thead>
						                           <tr>
							                             <th><input type="checkbox" name="selectBox_all" id="selectBox_all" onClick="boundCheckBox(this, 'selectCheckboxRole');loadResourceTree()" ></th>
							                             <th><s:message code="cmsRole.roleId" /></th>
							                             <th><s:message code="cmsRole.roleName" /></th>
						                            </tr>
					                             </thead>
					                             <tbody id="roleTbody">
				                                 </tbody>
				                             </table>
			                        </div>
		                   </div>
	                    </div>
                       <div id="userExludeResourceDiv" class="col-md-8">
	                         <!-- userExludeResource start -->
	                        <div class="list-group"  id="resourceDiv" name="resourceDiv" >
		                             <a href="#" class="list-group-item active" name="excludeResource"><s:message code="cmsUser.excludeResource" /></a>
	                                 <div id = "resourceTree" ></div>
	                                 </div>
                           </div>	
                      <!-- END userExludeResource  -->
                </div>
    
	            <div class="modal-footer">
	            	  <input id="buttonSave"  type="submit"  class="btn btn-primary" value="<s:message code="common.button.save" />"  onclick = "saveUserExcludeResource()" >
			          <button type="button" class="btn btn-default" data-dismiss="modal"><s:message code="common.button.close" /></button>   
				 </div>
	     </div>	
       </div>
    </div>
 </div>
 <!-- END roleModal DAIOG -->
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
 <script type="text/javascript">
     contextPath = '${ctx}';
     var companyAcData = '${companyList}';
     var userIdT='<s:message code="cmsUser.userId" />';
     var userCodeT='<s:message code="cmsUser.userCode" />';
     var userCnameT='<s:message code="cmsUser.userCname" />';
     var userTnameT='<s:message code="cmsUser.userTname" />';
     var companyCodeT='<s:message code="cmsUser.companyCode" />';
     var phoneT='<s:message code="cmsUser.phone" />';
     var addressT='<s:message code="cmsUser.address" />';
     var validFlagshowT='<s:message code="common.validFlag" />';
     var addBtnTxt= '<s:message code="common.button.add"/>' ;
     var viewBtnTxt= '<s:message code="common.button.view"/>' ;
     var editBtnTxt= '<s:message code="common.button.edit"/>' ;
     var delBtnTxt= '<s:message code="common.button.delete" />' ;
     var userGrantBtnTxt= '<s:message code="cmsUser.userGrant" />' ;
     var editMsgTxt= '<s:message code="common.msg.edit" />' ;
     var infoMsgTxt= '<s:message code="common.msg.info" />' ;
     var confirmMsgTxt= '<s:message code="common.msg.confirm" />' ;
     var errorMsgTxt= '<s:message code="common.msg.error" />' ;
     var successMsgTxt= '<s:message code="common.msg.success" />' ;
     var warnMsgTxt= '<s:message code="common.msg.warn" />' ;
     var deleteRowMsgTxt= '<s:message code="common.msg.delete" />' ;
     var deleteRowMsgSureTxt= '<s:message code="common.msg.deleteSure" />' ;
     var validFalgTxt0= '<s:message code="common.validFlag.v0" />' ; 
     var validFlagTxt1= '<s:message code="common.validFlag.v1" />' ; 
     var userUnlockBtnTxt= '<s:message code="cmsUser.userUnlock" />' ;
     var unlockUserRowMsgSureTxt= '<s:message code="common.msg.unlockUserSure" />' ;

  </script>
  <script language="Javascript"  src="${ctx}/js/sys/user/userManage.js"  ></script>
</body>
</html>