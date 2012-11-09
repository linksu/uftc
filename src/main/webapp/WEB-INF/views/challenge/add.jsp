<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" />

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
	<script src="https://raw.github.com/phstc/jquery-dateFormat/master/jquery.dateFormat-1.0.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$('#startTime').datepicker({ dateFormat: 'dd.mm.yy',defaultDate:'1' });
		$('#startTime').val($.format.date(new Date(),'dd.MM.yyyy'));
		
		$('#endTime').datepicker({ dateFormat: 'dd.mm.yy' });
		$('#endTime').val($.format.date(new Date(),'dd.MM.yyyy'));
		
	});
</script>

</head>

<title>Insert title here</title>



</head>
<body>
	<div class="pageBody">

		<h1><spring:message code="challenge.create" text="Create New Challenge"/></h1>

		<c:url var="saveUrl" value="/challenge/add" />
		<form:form modelAttribute="challengeInstance" method="POST"
			action="${saveUrl}">
			<%-- 		<select name="uftcIdSelector">
			<option value=""><---Valitse---></option>
			<c:forEach items="${uftcList}" var="uftc">
				<option value="${uftc}">${uftc.getId()}</option>
			</c:forEach>
		</select> --%>

			<br />
			<table>
				<tr>

					<c:forEach items="${errors}" var="a">
						<c:out value="${a.toString()}" />
						<br>
					</c:forEach>



					<td><form:label path="title"><spring:message code="challenge.title" text="Challenge title:"/></form:label></td>
					<td><form:input path="title" /></td>
				</tr>
				<tr>
					<td><form:label path="startTime"><spring:message code="challenge.startTime" text="StartTime:"/></form:label></td>
					<td><form:input type="text" name="startTime" id="startTime"
							path="startTime" value=""/></td>
				</tr>
				<tr>
					<td><form:label path="endTime"><spring:message code="challenge.endTime" text="EndTime:"/></form:label></td>
					<td><form:input type="text" name="endTime" id="endTime" path="endTime" value =""/></td>

				</tr>

			</table>

			<input type="submit" value="Save" />
		</form:form>
	</div>
</body>
</html>