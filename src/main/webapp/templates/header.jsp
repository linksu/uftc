<%@ include file="/templates/tags.jsp"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<% String baseURL=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "" + request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ultimate</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/style/style.css" />
<link rel="stylesheet" type="text/css"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" />

</head>
<body>
<div id="wrapper">

<div id="header">

	<div id="register" class="clearfix">
		<security:authorize access="isAnonymous()" var="isAnonymous">
			<a href="/uftc/register"><h2><spring:message code="login.register" /></h2></a>
		</security:authorize>
		<security:authorize access="isAuthenticated()" var="isAuthed">
			<a href="<%=baseURL%>/j_spring_security_logout">
				<h2><spring:message code="login.logout" /></h2>
			</a>
		</security:authorize>
	</div>

	<div id="logo">
		<img src="/uftc/pics/logo.jpg">
	</div>
	<security:authorize access="isAnonymous()" var="isAnonymous">
		<div id="login">
			<c:if test="${param.login_error == 'true'}">
					<spring:message code="login.authenticationFailure" />
			</c:if>
			<form name='f' action="<c:url value='j_spring_security_check' />"
				method='POST'>
				<input style="width:170px;" type="text" class="teksti" name="j_username" placeholder="<spring:message code="login.username" />"
					maxlength="15" size="15" onfocus="this.value=''" />
					<input style="width:170px;" name="j_password" type="password"
					placeholder="<spring:message code="login.password" />" class="teksti"  />
					<input type="submit" name="submitform" class="kirjaudu" value="<spring:message code="login.login" />" />
				<p class="forget">
					<a href="#"><spring:message code="login.forgotPassword" /></a>
				</p>
			</form>
		</div>
	</security:authorize>

	<security:authorize access="isAuthenticated()" var="isAuthed">
		<div id="login">
			<div class="headerbox">
				<h2 class="headertext">
					<a href="/uftc/user/show?userId=${loggedInUser.getId()}">${loggedInUser.getUsername()}</a>
				</h2>
			</div>
		</div>

	</security:authorize>

</div>
<!--End of header-->

<div id="navi">
	<ul>
		<li><a href="/uftc"><spring:message code="header.frontPage" /></a></li>

		<security:authorize access="isAuthenticated()" var="isAuthed">
			<li><a href="/uftc/user/show?userId=${loggedInUser.getId()}"><spring:message code="header.ownPage" /></a></li>

		</security:authorize>
		<li><a href="/uftc/challenge/list"><spring:message code="header.challengePage" /></a></li>

	</ul>
</div>
<!--End of navi-->