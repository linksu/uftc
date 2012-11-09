<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>Ultimate</title>
<link rel="stylesheet" type="text/css" href="/uftc/style/style.css" />
</head>

<body>
	<div id="wrapper">

		<%@ include file="/templates/header.jsp"%>
		<div id="content">
			<div id="column1">
				<div id="new_user">
					<div class="headerbox">
						<h2 class="headertext">
							<spring:message code="user.create" text="Create new user" />
						</h2>
						<img class="headerlogo" src="/uftc/pics/star.jpg">
					</div>
					<div class="chartbox">

						<c:url var="saveUrl" value="/user/add" />
						<c:set var="error" value="${errors}" />
						<form:form modelAttribute="userInstance" method="POST"
							action="${saveUrl}">
							<br />
							<table>
								<%-- 								<tr>
								
								
									<td><c:if test="${not empty errors}">


											<div class="warning">



												<c:forEach items="${errors}" var="e">
													
													<c:set var="theString" value="I am a test String"/>
													<spring:message
														code="user.validationErrors.${e.toString()}"
														text="${e.toString()}" />
													<br>

												</c:forEach>
											</div>


										</c:if></td>
								</tr>  --%>
								<tr>
									<td><p>
											<form:label path="username">
												<spring:message code="user.username" text="User name:" />
											</form:label>
										</p></td>
									<td><form:input path="username" class="teksti" /></td>

									<td><c:if test="${fn:contains(error, 'username')}">
											<div class="warning">
												<spring:message code="user.validationErrors.username" />
											</div>
										</c:if></td>
								</tr>
								<tr>
									<td><p>
											<form:label path="password">
												<spring:message code="user.password" text="Password:" />
											</form:label>
										</p></td>
									<td><form:input path="password" type="password" value=""
											onfocus="this.value=''" class="teksti" /></td>

									<td><c:if test="${fn:contains(error, 'password')}">
											<div class="warning">
												<spring:message code="user.validationErrors.password" />
											</div>
										</c:if></td>
								</tr>
								<tr>
									<td><p>
											<form:label path="retypePassword">
												<spring:message code="user.retypePassword"
													text="Retype password:" />
											</form:label>
										</p></td>
									<td><form:input path="retypePassword" type="password"
											value="" onfocus="this.value=''" class="teksti" /></td>

									<td><c:if test="${fn:contains(error, 'retypePassword')}">
											<div class="warning">
												<spring:message code="user.validationErrors.retypePassword" />
											</div>
										</c:if></td>
								</tr>

								<tr>
									<td><p>
											<form:label path="firstName">
												<spring:message code="user.firstName" text="First name:" />
											</form:label>
										</p></td>
									<td><form:input path="firstName" class="teksti" /></td>
									<td><c:if test="${fn:contains(error, 'firstName')}">
											<div class="warning">
												<spring:message code="user.validationErrors.firstName" />
											</div>
										</c:if></td>
								</tr>
								<tr>
									<td><p>
											<form:label path="lastName">
												<spring:message code="user.lastName" text="Last name:" />
											</form:label>
										</p></td>
									<td><form:input path="lastName" class="teksti" /></td>
									<td><c:if test="${fn:contains(error, 'lastName')}">
											<div class="warning">
												<spring:message code="user.validationErrors.lastName" />
											</div>
										</c:if></td>
								</tr>
								<tr>
									<td><p>
											<form:label path="isChallenger">
												<spring:message code="user.isChallenger"
													text="Is challenger:" />
											</form:label>
										</p></td>
									<td><form:select path="isChallenger">
											<form:option value="true">
												<spring:message code="misc.yes" text="Yes" />
											</form:option>
											<form:option value="false">
												<spring:message code="misc.no" text="No" />
											</form:option>
										</form:select></td>
								</tr>
							</table>

							<form:input type="hidden" value="true" path="enabled" />
							<input type="submit"
								value="<spring:message code="misc.save" text="Save"/>"
								class="kirjaudu" />
						</form:form>

					</div>

				</div>

			</div>
			<!--End of column-->

		</div>
		<!--End of content-->
		<%@ include file="/templates/footer.jsp"%>
	</div>
</body>
</html>