<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="content">
			<div id="column1">
				<div class="headerbox">
					<h2 class="headertext"><spring:message code="user.info" text="User info" /></h2>
				</div>
				<div class="chartbox">
				<c:url var="editUrl"
					value="/user/edit?userId=${userInstance.getId()}" />
				<form:form modelAttribute="userInstance" method="POST"
					action="${editUrl}">
					<br />
					<table class="userTable">
						<tr>
							<c:if test="${optimisticLockingError}">
					Entity lukittu
					<br />
							</c:if>
							<c:forEach items="${errors}" var="e">
								<spring:message code="user.validationErrors.${e.toString()}"
									text="${e.toString()}" />
								<br>
							</c:forEach>
						</tr>
						<tr>
							<td><form:label path="firstName">
									<spring:message code="user.firstName" text="First name" />
								</form:label></td>
							<td class="oikea"><form:input class="teksti" path="firstName" /></td>
						</tr>
						<tr>
							<td><form:label path="lastName">
									<spring:message code="user.lastName" text="Last name" />
								</form:label></td>
							<td class="oikea"><form:input class="teksti" path="lastName" /></td>
						</tr>
						
					</table>

					<form:input type="hidden" path="id" />
					<form:input type="hidden" path="version" />
					<input class="nappi" style="float:left;" type="submit" value="Save" />
					<a class="nappi" style="float:right;" href="/uftc/user/show?userId=${userInstance.getId()}"><spring:message code="misc.back" text="Back" /></a>
				</form:form>
			</div>
			</div>

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>