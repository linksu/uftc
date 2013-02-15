<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="content">
			<div id="column1">				
						<div class="headerbox" style="width: 350px;">
							<h2 class="headertext">
								[${challenge.getTitle()}]: <spring:message code="challengeSportEvent.challengeSport" text="Sports" />
							</h2>
						</div>
						<div class="chartbox" style="width: 350px;">
						<table id="sportTable">
						<tr>
						<td><h3><spring:message code="sportEvent.title"	text="SportEvent title" /></h3></td>
						<td class="vasen"><h3><spring:message code="sportEvent.pointFactorType" text="Pointfactor type" /></h3></td>
						<td class="oikea"><h3><spring:message code="sportEvent.pointFactor" text="Pointfactor" /></h3></td></tr>
						<c:forEach items="${challengeSportEvents}" var="sportEvent">
						<tr>
						<td><a href="/uftc/challengeSportEvent/edit?id=${sportEvent.getId()}">${sportEvent.getTitle()}</a></td>
						<td class="vasen"><spring:message code="sportEvent.pointFactorType.${sportEvent.getPointFactorType()}" text="${sportEvent.getPointFactorType()}" /></td>
						<td class="oikea">${sportEvent.getPointFactor()}</td>
						</tr>
						</c:forEach>
						</table>
						<c:if test="${loggedInUser.getAuthority() != 'ROLE_ADMIN'}"><a class="nappi" style="float: left;" href="/uftc/challengeSportEvent/add?challengeId=${challenge.getId()}"><spring:message code="challengeSportEvent.create" text="Add new sports" /></a></c:if>
						<a class="nappi" style="float: right;" href="/uftc/challenge/show?challengeId=${challenge.getId()}"><spring:message code="misc.back" text="Back" /></a>
						</div>
					</div>
					<!-- NEW SPORTEVENT FORM DIV ENDS -->
		<!--End of content-->
		
		</div>
		<%@ include file="/templates/footer.jsp"%>