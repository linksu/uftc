<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="content">
			<div id="column1">				
						<div class="headerbox" style="width: 350px;">
							<h2 class="headertext">
								<spring:message code="challengeSportEvent.challengeSport" text="Sports of the challenge" />
							</h2>
						</div>
						<div class="chartbox" style="width: 350px;">
						<table id="sportTable">
						<tr><td>Laji</td><td>Pisteytystyyppi</td><td>Pistearvo</td></tr>
						<c:forEach items="${challengeSportEvents}" var="sportEvent">
						<tr>
						<td><a style="border-bottom: 1 px;" href="/uftc/challengeSportEvent/edit?id=${sportEvent.getId()}">${sportEvent.getTitle()}</a></td>
						<td><spring:message code="sportEvent.pointFactorType.${sportEvent.getPointFactorType()}" text="${sportEvent.getPointFactorType()}" /></td>
						<td>${sportEvent.getPointFactor()}</td>
						</tr>
						</c:forEach>
						</table>
						<a class="nappi" style="float: left;" href="/uftc/challengeSportEvent/add?challengeId=${challengeId}">Lis‰‰ laji</a>
						<a class="nappi" style="float: left;" href="/uftc/challenge/show?challengeId=${challengeId}">Takaisin</a>
						</div>
					</div>
					<!-- NEW SPORTEVENT FORM DIV ENDS -->
		<!--End of content-->
		
		</div>
		<%@ include file="/templates/footer.jsp"%>