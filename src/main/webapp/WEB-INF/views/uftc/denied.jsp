<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Ultimate</title>
<link rel="stylesheet" type="text/css" href="style/style.css" />

</head>

<body>
	<div id="wrapper">
		<%@ include file="/templates/header.jsp"%>
		<div id="content">

			<div id="column2">
				<div class="headerbox">
					<h2 class="headertext">Pääsy evätty!</h2>
				</div>
				<div class="leipis">
					<p>Sinulla ei ole tarvittavia oikeuksia tämän sivun tarkasteluun.</p>
				</div>
			</div>
			<!--End if column2-->

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>
	</div>
</body>
</html>