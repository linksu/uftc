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
						<tr><td><h3>Laji</h3></td><td class="vasen"><h3>Pisteytystyyppi</h3></td><td class="oikea"><h3>Pistearvo</h3></td></tr>
						<c:forEach items="${challengeSportEvents}" var="sportEvent">
						<tr>
						<td><a href="/uftc/challengeSportEvent/edit?id=${sportEvent.getId()}">${sportEvent.getTitle()}</a></td>
						<td class="vasen"><spring:message code="sportEvent.pointFactorType.${sportEvent.getPointFactorType()}" text="${sportEvent.getPointFactorType()}" /></td>
						<td class="oikea">${sportEvent.getPointFactor()}</td>
						</tr>
						</c:forEach>
						</table>
						<a class="nappi" style="float: left;" href="/uftc/challengeSportEvent/add?challengeId=${challengeId}">Lis‰‰ laji</a>
						<a class="nappi" style="float: right;" href="/uftc/challenge/show?challengeId=${challengeId}">Takaisin</a>
						</div>
					</div>
					<!-- NEW SPORTEVENT FORM DIV ENDS -->
		<!--End of content-->
		
		</div>
		<%@ include file="/templates/footer.jsp"%>