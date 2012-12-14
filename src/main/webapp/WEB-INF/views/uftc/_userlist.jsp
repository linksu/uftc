<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Ultimate</title>
<link rel="stylesheet" type="text/css" href="/uftc/style/style.css" />
</head>

<body>
	<div id="wrapper">
		<%@ include file="/templates/header.jsp"%>

		<c:forEach items="${userList}" var="user">
			<a href="">${user.getFirstName()}</a>
			<br/>
		</c:forEach>
		
		<a href="/uftc/admin/userlist?count=${count}&from=${from}&to=${to}&r=true"><--</a>|<a href="/uftc/admin/userlist?count=${count}&from=${from}&to=${to}">--></a>
		<%@ include file="/templates/footer.jsp"%>
	</div>
	

</body>
</html>