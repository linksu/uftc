<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
<script
	src="https://raw.github.com/phstc/jquery-dateFormat/master/jquery.dateFormat-1.0.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#startTime').datepicker({
			dateFormat : 'dd.mm.yy',
			defaultDate : '1'
		});
		$('#startTime').val($.format.date(new Date(), 'dd.MM.yyyy'));

		$('#endTime').datepicker({
			dateFormat : 'dd.mm.yy'
		});
		$('#endTime').val($.format.date(new Date(), 'dd.MM.yyyy'));

	});
</script>

		<div id="login"></div>

		<div id="content">
			<div id="column1">
				<div class="headerbox">
					<h2 class="headertext"><spring:message code="challenge.allChallenges" text="All challenges"/></h2>
				</div>
				<div class="chartbox">
					<div class="challenge">
						<table class="challengeTable">
						<tr>
							<td><h3><spring:message code="challenge.title" text="Title"/></h3></td>
							<td class="oikea"><h3><spring:message code="challenge.totalPoints" text="Total points"/></h3></td>
						</tr>
						<c:forEach items="${challenges}" var="challenge">
						<tr>

										<td><a href="/uftc/challenge/show?challengeId=${challenge.getId()}">${challenge.getTitle()}</a></td>
										<td class="oikea">${challenge.getTotalPoints()}</td>
						</tr>				
						</c:forEach>
						</table>

					</div>
					
					<c:if test="${loggedInUser.getAuthority() == 'ROLE_CHALLENGER'}">
					<a class="nappi" href="/uftc/challenge/add"><spring:message code="challenge.create" text="Create new challenge"/></a>
					</c:if>
				</div>
				
			</div>
			<!--End of column1-->
			<!-- Beginning of if column2 -->
			<c:if test="${loggedInUser.getAuthority() == 'ROLE_CHALLENGER'}">
				<div id="column2">
				<div class="headerbox">
					<h2 class="headertext"><spring:message code="challenge.ownedChallenges" text="Owned challenges"/></h2>
				</div>

				<div class="chartbox">
					<div class="challenge">
					<table class="challengeTable">
					<tr>
							<td><h3><spring:message code="challenge.title" text="Title"/></h3></td>
							<td class="oikea"><h3><spring:message code="challenge.totalPoints" text="Total points"/></h3></td>
					</tr>
						<c:forEach items="${ownedChallenges}" var="challenge">
						<tr>
										<td><a href="/uftc/challenge/show?challengeId=${challenge.getId()}">${challenge.getTitle()}</a></td>
										<td class="oikea">${challenge.getTotalPoints()}</td>
						</tr>


						</c:forEach>
						</table>
						<c:if test="${ownedChallenges.size() == 0}"><h3 class="headertext"><spring:message code="challenge.noOwnedChallenges" text="No owned challenges" /></h3></c:if>

					</div>
				</div>

			</div>
			<!--End if column2-->
			</c:if>
			
			<!--Beginning if column3 begins -->
				<c:if test="${loggedInUser.getAuthority() != 'ROLE_ADMIN'}"> 
					<div id="column3">
				<div class="headerbox">
					<h2 class="headertext"><spring:message code="challenge.mychallenges" text="My challenges"/></h2>
				</div>

				<div class="chartbox">
					<div class="challenge">
					<table class="challengeTable">
					<tr>
							<td><h3><spring:message code="challenge.title" text="Title"/></h3></td>
							<td class="oikea"><h3><spring:message code="challenge.totalPoints" text="Total points"/></h3></td>
					</tr>
						<c:forEach items="${userChallenges}" var="challenge">
						<tr>
										<td><a href="/uftc/challenge/show?challengeId=${challenge.getId()}">${challenge.getTitle()}</a></td>
										<td class="oikea">${challenge.getTotalPoints()}</td>
						</tr>


						</c:forEach>
						</table>
						
						<c:if test="${userChallenges.size() == 0}"><h3 class="headertext"><spring:message code="challenge.noMyChallenges" text="No challenges" /></h3></c:if>

					</div>
				</div>

			</div>
			<!--End if column3-->
			</c:if>
			
		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>