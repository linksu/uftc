<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="login"></div>

		<div id="content">
		
		<div id="column2">
		<div class="headerbox">
		<h2 class="headertext">[${challengeInstance.getTitle()}] ${user.getFirstName()} ${user.getLastName()}</h2>
		</div>
		<div class="chartbox">
		<table id="workoutTable">
		<c:if test="${workouts.size() == 0}"><h3 class="headertext"><spring:message code="workout.noWorkouts" text="No workouts added" /></h3></c:if>
		<c:forEach items="${workouts}" var="workout">
							<tr><td class="vasen">
								<c:out value="${workout.getName()}" />
							</td>
							<td class="vasen">
							${workout.getRepetition()} 
							</td>
							<td class="oikea">
							${workout.getPoints()}
							</td>
							</tr>
							<tr class="viiva">
							<td class="vasen">
							${workout.getTime()}
							</td>
							<td class="vasen">
							<spring:message code="workout.pointFactorType.${workout.getChallengeSportEvent().getPointFactorType()}"
														text="${workout.getChallengeSportEvent().getPointFactorType()}" />
							</td>
							<td class="oikea">
							<spring:message code="workout.points" text="Points" />
							</td>
							</tr>
					</c:forEach>
					</table>
					<a class="nappi" style="float:right;" href="/uftc/challenge/show?challengeId=${challengeInstance.getId()}"><spring:message code="misc.back" text="Back" /></a>
		</div>
		</div>

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>