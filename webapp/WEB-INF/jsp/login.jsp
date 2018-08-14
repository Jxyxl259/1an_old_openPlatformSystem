<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie ie9" lang="en" class="no-js"> <![endif]-->
<!--[if !(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->

<head>
	<title><s:message code="common.login"/> | <s:message code="common.system.name"/></title>
	<script type="text/javascript">
		contextPath = '${ctx}';
	</script>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="description" content="cms">
	<meta name="author" content="hzhihui">

	<!-- CSS -->
	<link href="${ctx}/common/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${ctx}/common/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="${ctx}/common/css/main.css" rel="stylesheet" type="text/css">
	<style>.error{color:red;}</style>
	<!--[if lte IE 9]>
		<link href="common/css/main-ie.css" rel="stylesheet" type="text/css" />
		<link href="common/css/main-ie-part2.css" rel="stylesheet" type="text/css" />
	<![endif]-->
</head>

<body style="overflow:hidden;">
	<div class="wrapper full-page-wrapper page-auth page-login text-center">
		<div class="inner-page">
			<div class="logo">
				<a href="login"><img src="${ctx}/common/img/logo.png" height="60" width="170" alt="" /></a>
			</div>
			<button type="button" class="btn btn-auth-facebook"><span class="system-name"><s:message code="common.system.shortname"/></span></button>

			<div class="separator"><span><s:message code="common.login.title"/></span></div>

			<div class="login-box center-block">
				<form class="form-horizontal" role="form" method="post" action="">
					<div class="form-group">
						<label for="username" class="control-label sr-only"><s:message code="common.login.username"/></label>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" placeholder="<s:message code="common.login.username"/>" id="username"  name="username" class="form-control" value="${userCode}">
								<span class="input-group-addon"><i class="fa fa-user"></i></span>
							</div>
						</div>
					</div>
					<label for="password" class="control-label sr-only"><s:message code="common.login.password"/></label>
					<div class="form-group">
						<div class="col-sm-12">
							<div class="input-group">
								<input type="password" placeholder="<s:message code="common.login.password"/>" id="password" name="password" class="form-control">
								<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							</div>
						</div>
					</div>
					<br/>
					<label class="fancy-checkbox" style="display:none">
						<input type="checkbox" name="rememberMe">
						<span><s:message code="common.login.rememberme"/></span>
					</label>
					<button class="btn btn-custom-primary btn-lg btn-block btn-auth"><i class="fa fa-arrow-circle-o-right"></i><s:message code="common.login"/></button>
					<div class="error">${error}</div>
				</form>
			</div>
		</div>
	</div>
	<footer class="footer">&copy; 2014-2015 1an.com</footer>
	<!-- Javascript -->
	<script src="common/js/jquery/jquery-2.1.0.min.js"></script>
	<script src="common/js/bootstrap/bootstrap.min.js"></script>
	<script src="common/js/plugins/modernizr/modernizr.js"></script>
</body>
</html>