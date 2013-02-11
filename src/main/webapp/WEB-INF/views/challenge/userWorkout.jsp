<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="login"></div>

		<div id="content">
		
		<div id="column2">
		<div class="headerbox">
		<h2 class="headertext">${user.getFirstName()} ${user.getLastName()}</h2>
		</div>
		<div class="chartbox">
		<table id="workoutTable">
		<c:forEach items="${workouts}" var="workout">
							<tr><td>
								<a href="/uftc/workout/edit?workoutId=${workout.getId()}"><c:out
										value="${workout.getName()}" /></a>
							</td>
							<td>
							${workout.getRepetition()} 
							</td>
							<td class="oikea">
							${workout.getPoints()}
							</td>
							</tr>
							<tr>
							<td style="border-bottom: thin solid white;">
							${workout.getTime()}
							</td>
							<td style="border-bottom: thin solid white;">
							<spring:message code="sportEvent.pointFactorType.${workout.getChallengeSportEvent().getPointFactorType()}"
														text="${workout.getChallengeSportEvent().getPointFactorType()}" />
							</td>
							<td class="oikea" style="border-bottom: thin solid white;">
							Pistettä
							</td>
							</tr>
					</c:forEach>
					</table>
					<a class="nappi" style="float:right;" href="/uftc/challenge/show?challengeId=${challengeInstance.getId()}">Takaisin</a>
		</div>
		</div>

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>