<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="content">
			<div id="column1">
				<div class="headerbox">
					<h2 class="headertext">Tiedot</h2>
				</div>
				<div class="chartbox">
				<c:url var="editUrl"
					value="/user/edit?userId=${userInstance.getId()}" />
				<form:form modelAttribute="userInstance" method="POST"
					action="${editUrl}">
					<br />
					<table>
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
						<!--  <tr>
							<td><form:label path="username">
									<spring:message code="user.username" text="User name:" />
								</form:label></td>

							<td><form:input class="teksti"
									value="${userInstance.getUsername()}" path="username" /></td>
						</tr>
						-->
						<tr>
							<td><p><form:label path="firstName">
									<spring:message code="user.firstName" text="First name:" />
								</form:label></p></td>
							<td><form:input class="teksti" path="firstName" /></td>
						</tr>
						<tr>
							<td><p><form:label path="lastName">
									<spring:message code="user.lastName" text="Last name:" />
								</form:label></p></td>
							<td><form:input class="teksti" path="lastName" /></td>
						</tr>
						
					</table>

					<form:input type="hidden" path="id" />
					<form:input type="hidden" path="version" />
					<input class="nappi" style="float:left;" type="submit" value="Save" />
					<a class="nappi" style="float:right;" href="/uftc/user/show?userId=${userInstance.getId()}">Takaisin</a>
				</form:form>
			</div>
			</div>
			<!--End of column1-->
			<%--
			<div id="column2">
				<div class="headerbox">
					<h2 class="headertext">OMA TULOS</h2>
					<img class="headerlogo" src="/uftc/pics/star.jpg">
				</div>
				<div class="chartbox">
					<p></p>
				</div>

			</div>
			--%>
			<!--End if column2-->

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>