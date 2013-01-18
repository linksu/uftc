<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="content">
			<div id="column1">				
						<div class="headerbox">
							<h2 class="headertext">
								<spring:message code="challengeSportEvent.challengeSport" text="Sports of the challenge" />
							</h2>
						</div>
						<div class="chartbox">
						<table>
						<c:forEach items="${challengeSportEvents}" var="sportEvent">
						<tr>
						<td><a style="border-bottom: 1 px;" href="/uftc/challengeSportEvent/edit?id=${sportEvent.getId()}">${sportEvent.getTitle()}</a></td><td>${sportEvent.getPointFactorType()}</td>
						<td>${sportEvent.getPointFactor()}</td>
						</tr>
						</c:forEach>
						</table>
						</div>
					</div>
					<!-- NEW SPORTEVENT FORM DIV ENDS -->
		<!--End of content-->
		
		</div>
		<%@ include file="/templates/footer.jsp"%>