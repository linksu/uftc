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
			<a href="/uftc/register"><h2>REKISTER&Ouml;IDY</h2></a>
		</security:authorize>
		<security:authorize access="isAuthenticated()" var="isAuthed">
			<a href="<c:url value="/uftc/j_spring_security_logout" />">
				<h2>KIRJAUDU ULOS</h2>
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
				<input type="text" class="teksti" name="j_username" value="Tunnus"
					maxlength="15" size="15" onfocus="this.value=''" />
					<input name="j_password" type="text"
					value="Salasana" class="teksti"
					onfocus="this.type='password'; this.value=''" /> <input
					type="submit" name="submitform" class="kirjaudu" value="Kirjaudu" />
				<p class="forget">
					<a href="#">Unohditko salasanan?</a>
				</p>
			</form>
		</div>
	</security:authorize>

	<security:authorize access="isAuthenticated()" var="isAuthed">
		<div id="login">
			<div class="headerbox">
				<h2 class="headertext">
					<a href="/uftc/user/edit?userId=${userInstance.getId()}">${userInstance.getUsername()}</a>
				</h2>
			</div>
		</div>

	</security:authorize>

</div>
<!--End of header-->

<div id="navi">
	<ul>
		<li><a href="/uftc">ETUSIVU</a></li>

		<security:authorize access="isAuthenticated()" var="isAuthed">
			<li><a href="/uftc/user/show?userId=${userInstance.getId()}">OMA
					SIVU</a></li>

		</security:authorize>
		<li><a href="/uftc/challenge/list">HAASTEET</a></li>

	</ul>
</div>
<!--End of navi-->