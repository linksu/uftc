<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/templates/header.jsp"%>

<div id="content">
	<div id="column1">
		<div id="new_user">
			<div class="headerbox">
				<h2 class="headertext">
					<spring:message code="user.create" text="Create new user" />
				</h2>
			</div>
			<div class="chartbox">

				<c:url var="saveUrl" value="/admin/userAdd" />
				<c:set var="error" value="${errors}" />
				<form:form modelAttribute="userInstance" method="POST"
					action="${saveUrl}">
					<br />
					<table class="userAddFormTable">
						<tr>
						
						<td><p>
											<form:label path="authority">
												<spring:message code="user.accountType"
													text="Account type" />
											</form:label>
										</p></td>
									<td class="oikea"><form:select id="authority" class="teksti"
											path="authority">
												<form:option value="ROLE_CHALLENGER">
													<spring:message code="user.ROLE_CHALLENGER" text="Challenger" />
												</form:option>
												<form:option value="ROLE_USER">
													<spring:message code="user.ROLE_USER" text="User" />
												</form:option>

										</form:select>
										</tr>
										<tr>
						
							<td><p>
									<form:label path="username">
										<spring:message code="user.userName" text="User name" />
									</form:label>
								</p></td>
							<td class="oikea"><form:input path="username" class="teksti" /></td>

							<td><c:if test="${fn:contains(error, 'username')}">
									<div class="warning">
										<spring:message code="user.validationErrors.userName" />
									</div>
								</c:if></td>
						</tr>
						<tr>
							<td><p>
									<form:label path="password">
										<spring:message code="user.password" text="Password" />
									</form:label>
								</p></td>
							<td class="oikea"><form:input path="password" type="password" value=""
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
											text="Retype password" />
									</form:label>
								</p></td>
							<td class="oikea"><form:input path="retypePassword" type="password"
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
										<spring:message code="user.firstName" text="First name" />
									</form:label>
								</p></td>
							<td class="oikea"><form:input path="firstName" class="teksti" /></td>
							<td><c:if test="${fn:contains(error, 'firstName')}">
									<div class="warning">
										<spring:message code="user.validationErrors.firstName" />
									</div>
								</c:if></td>
						</tr>
						<tr>
							<td><p>
									<form:label path="lastName">
										<spring:message code="user.lastName" text="Last name" />
									</form:label>
								</p></td>
							<td class="oikea"><form:input path="lastName" class="teksti" /></td>
							<td><c:if test="${fn:contains(error, 'lastName')}">
									<div class="warning">
										<spring:message code="user.validationErrors.lastName" />
									</div>
								</c:if></td>
						</tr>
					</table>

					<form:input type="hidden" value="true" path="enabled" />
					<input type="submit"
						value="<spring:message code="misc.save" text="Save"/>"
						class="nappi" style="float:left;"/>
						<a class="nappi" style="float:right;" href="/uftc/admin"><spring:message code="misc.back" text="Back" /></a>
				</form:form>

			</div>

		</div>

	</div>
	<!--End of column-->

</div>
<!--End of content-->
<%@ include file="/templates/footer.jsp"%>