<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="content">
			<div id="column1">				
						<div class="headerbox">
							<h2 class="headertext">
								[${challengeSportEventInstance.getChallenge().getTitle()}]: <spring:message code="challengeSportEvent.challengeSport" text="Sports" />
							</h2>
						</div>
						<div class="chartbox">
												<c:url var="saveUrl" value="/challengeSportEvent/edit" />
						<form:form modelAttribute="challengeSportEventInstance" method="POST"
							action="${saveUrl}">
							<table class="challengeSportEventFormTable">
								<tr>
									<c:forEach items="${errors}" var="e">
										<spring:message
											code="sportEvent.validationErrors.${e.toString()}"
											text="${e.toString()}" />
										<br>
									</c:forEach>

									<td><p>
											<form:label path="title">
												<spring:message code="sportEvent.title"
													text="SportEvent title" />
											</form:label>
										</p></td>
									<td><form:input path="title" class="teksti" /></td>
								</tr>

								<tr>
									<td><p>
											<form:label path="pointFactorType">
												<spring:message code="sportEvent.pointFactorType"
													text="Pointfactor type" />
											</form:label>
										</p></td>
									<td><form:select path="pointFactorType" class="teksti">
											<c:forEach items="${pointFactorTypeEnum}" var="type">
												<form:option value="${type}">
													<spring:message code="sportEvent.pointFactorType.${type}"
														text="${type}" />
												</form:option>
											</c:forEach>
										</form:select></td>



								</tr>

								<tr>
									<td><p>
											<form:label path="pointFactor">
												<spring:message code="sportEvent.pointFactor"
													text="Pointfactor" />
											</form:label>
										</p></td>
									<td><form:input path="pointFactor" class="teksti" /></td>
								</tr>

							</table>
							<form:hidden path="id"/>
							<form:hidden path="version"/>
							<input class="nappi" style="float:left;" type="submit" 
								value="<spring:message code="misc.save" text="Save"/>" />
							<a class="nappi" style="float:right;" href="/uftc/challengeSportEvent/show?challengeId=${challengeSportEventInstance.getChallenge().getId()}"><spring:message code="misc.back" text="Back" /></a>
						</form:form>
						</div>

					</div>
					<!-- NEW SPORTEVENT FORM DIV ENDS -->
		<!--End of content-->
		
		</div>
		<%@ include file="/templates/footer.jsp"%>