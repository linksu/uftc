<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
	<script src="https://raw.github.com/phstc/jquery-dateFormat/master/jquery.dateFormat-1.0.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$('#startTimeString').datepicker({ dateFormat: 'dd.mm.yy',defaultDate:'1' });
		
		$('#endTimeString').datepicker({ dateFormat: 'dd.mm.yy' });
		
	});
</script>

		<div id="content">
			
			<c:if test="${loggedInUser.getAuthority() == 'ROLE_CHALLENGER' || loggedInUser.getAuthority() == 'ROLE_ADMIN' }">

			<div id="column2">
				<div class="headerbox">
					<h2 class="headertext"><spring:message code="challenge.edit" text="Edit challenge"/></h2>
					<img class="headerlogo" src="/uftc/pics/star.jpg">
				</div>



				<div class="chartbox">

					<c:url var="saveUrl" value="/challenge/update" />
					<c:set var="error" value="${errors}" />
					<form:form modelAttribute="challengeInstance" method="POST"
						action="${saveUrl}">
						<br />
						<table class="challengeFormTable">
							<tr>

								<c:forEach items="${errors}" var="a">
									<c:out value="${a.toString()}" />
									<br>
								</c:forEach>



								<td><form:label path="title">
											<spring:message code="challenge.title"
												text="Challenge title" />:
									</form:label></td>
								<td><form:input class="teksti" path="title" id="title"/></td>
							</tr>
							<tr>
								<td><form:label path="startTimeString">
											<spring:message code="challenge.startTime" text="StartTime" />:
									</form:label></td>
								<td><form:input class="teksti" type="text"	path="startTimeString" value="" /></td>
							</tr>
							<tr>
								<td><form:label path="endTimeString">
											<spring:message code="challenge.endTime" text="EndTime" />:
									</form:label></td>
								<td><form:input class="teksti" type="text" path="endTimeString" value="" /></td>

							</tr>

						</table>
						<form:input type="hidden" path="version" />
						<form:input type="hidden" path="id" />
						
						<input class="nappi" type="submit" value="Save" />
						
					<a class="nappi" style="float:left;" href="/uftc/challengeSportEvent/show?challengeId=${challengeInstance.getId()}"><spring:message code="challengeSportEvent.challengeSport" text="Sports of the challenge"/></a>
					<a class="nappi" style="float:right;" href="/uftc/challenge/show?challengeId=${challengeInstance.getId()}"><spring:message code="misc.back" text="Back"/></a>
					</form:form>
					
				</div>

			</div>
			<!--End if column2-->
			</c:if>

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>