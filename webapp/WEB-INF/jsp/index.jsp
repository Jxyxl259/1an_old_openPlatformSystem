<%@page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if IE 9 ]>
<html class="ie ie9" lang="en" class="no-js"> 
<![endif]-->
<!--[if !(IE)]>
<html lang="en" class="no-js">
<![endif]-->
<head>
<jsp:include page="header.jsp"></jsp:include>
</head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<body>
	<div class="wrapper">
		<div class="top-bar">
			<div class="container">
				<div class="row">
                    <jsp:include page="top.jsp"></jsp:include>
        		</div>
			</div>
		</div>
		<div class="bottom">
			<div class="container">
				<div class="row">
                    <jsp:include page="menu.jsp"></jsp:include>
					<jsp:include page="content.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="switcher.jsp"></jsp:include>
</body>
<jsp:include page="footer.jsp"></jsp:include>
<script language="Javascript" src="${ctx}/js/commonUtil.js"></script>
<script language="Javascript" src="${ctx}/js/common/index.js"></script>
<script type="text/javascript">
contextPath = '${ctx}';
var tabId = '${user.userCode}';
</script>
</html>