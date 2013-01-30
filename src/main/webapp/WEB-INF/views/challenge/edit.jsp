<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="login"></div>

		<div id="content">
			
			<c:if test="${loggedInUser.getAuthority() == 'ROLE_CHALLENGER' || loggedInUser.getAuthority() == 'ROLE_ADMIN' }">

			<div id="column2">
				<div class="headerbox">
					<h2 class="headertext">Muokkaa haastetta</h2>
					<img class="headerlogo" src="/uftc/pics/star.jpg">
				</div>



				<div class="chartbox">

					<c:url var="saveUrl" value="/challenge/update" />
					<c:set var="error" value="${errors}" />
					<form:form modelAttribute="challengeInstance" method="POST"
						action="${saveUrl}">

						<br />
						<table>
							<tr>

								<c:forEach items="${errors}" var="a">
									<c:out value="${a.toString()}" />
									<br>
								</c:forEach>



								<td><form:label path="title">
										<h3>
											<spring:message code="challenge.title"
												text="Challenge title:" />
										</h3>
									</form:label></td>
								<td><form:input path="title" id="title"/></td>
							</tr>
							<tr>
								<td><form:label path="startTimeString">
										<h3>
											<spring:message code="challenge.startTime" text="StartTime:" />
										</h3>
									</form:label></td>
								<td><form:input type="text"	path="startTimeString" value="" /></td>
							</tr>
							<tr>
								<td><form:label path="endTimeString">
										<h3>
											<spring:message code="challenge.endTime" text="EndTime:" />
										</h3>
									</form:label></td>
								<td><form:input type="text" path="endTimeString" value="" /></td>

							</tr>

						</table>
						<form:input type="hidden" path="version" />
						<form:input type="hidden" path="id" />
						
						<input type="submit" value="Save" />
					</form:form>
					<a class="nappi" href="/uftc/challengeSportEvent/show?challengeId=${challengeInstance.getId()}">Haasteen lajit</a>
				</div>

			</div>
			<!--End if column2-->
			</c:if>

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>