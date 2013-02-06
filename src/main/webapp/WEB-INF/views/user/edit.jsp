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
							<td><form:input class="teksti"
									value="${userInstance.getFirstName()}" path="firstName" /></td>
						</tr>
						<tr>
							<td><p><form:label path="lastName">
									<spring:message code="user.lastName" text="Last name:" />
								</form:label></p></td>
							<td><form:input class="teksti"
									value="${userInstance.getLastName()}" path="lastName" /></td>
						</tr>
						
					</table>

					<form:input type="hidden" value="${userInstance.getId()}" path="id" />
					<form:input type="hidden" value="${userInstance.getVersion()}"
						path="version" />
					<input type="submit" value="Save" />
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

			<div id="column3">
				<div class="headerbox">
					<h2 class="headertext">TAPAHTUMAT</h2>
					<img class="headerlogo" src="/uftc/pics/runner.jpg">
				</div>
				<div class="chartbox">
				<div class="tapahtumat">
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>

				</div>

			</div>
			</div>
			<!--End of column3-->

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>