<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="login"></div>

		<div id="content">
		
		<div id="column1">
		<div class="headerbox">
		<h2 class="headertext">${challenge.getTitle()}</h2>
		</div>
		<div class="chartbox">
					
					<table class="challengeTable">
					<tr>
							<td>
									<p><spring:message code="challenge.challengeOwner" text="Challenge owner" />:</p>
								</td>

							<td><p>${challenge.getOwner().getUsername()}</p></td>
						</tr>
						<tr>
							<td>
									<p><spring:message code="challenge.totalPoints" text="Total points" />:</p>
								</td>
							<td><p>${challenge.getTotalPoints()}</p></td>
						</tr>
						<tr>
							<td>
									<p><spring:message code="challenge.startTime" text="Start time" />:</p>
								</td>
							<td><p>${challenge.getStartTimeString()}</p></td>
						</tr>
						<tr>
							<td>
							<p><spring:message code="challenge.endTime" text="End time" />:</p>
									</td>
									<td><p>${challenge.getEndTimeString()}</p></td>
						</tr>
					</table>
		
			<c:if test="${challengeOwner}"><a style="float:left;" class="nappi" href="/uftc/challenge/edit?challengeId=${challenge.getId()}"><spring:message code="misc.edit" text="Edit" /></a></c:if>
			<a class="nappi" style="float:right;" href="/uftc/challenge/list"><spring:message code="misc.back" text="Back" /></a>
			
		</div>
		</div>
		
					<c:if test="${challengeParticipant}">
		<div id="column2">
		<div class="headerbox">
		<h2 class="headertext"><spring:message code="workout.ownWorkouts" text="My workouts" /></h2>
		</div>
		<div class="chartbox">
		<table id="workoutTable">
		<c:forEach items="${workouts}" var="workout">
							<tr><td class="vasen">
								<a href="/uftc/workout/edit?workoutId=${workout.getId()}"><c:out
										value="${workout.getName()}" /></a>
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
					<c:if test="${challengeParticipant}"><a class="nappi" style="float:left;" href="/uftc/workout/add?challengeId=${challenge.getId()}"><spring:message code="workout.create" text="Add workout" /></a></c:if>
		</div>
		</div>
		
		</c:if>
		
			<div id="column3">
				<div class="headerbox">
					<h2 class="headertext"><spring:message code="challenge.challengeUsers" text="Participants" /></h2>
				</div>
				<div class="chartbox">

					<div class="challenge">
						<table class="challengeTable">
						<tr>
							<td><h3><spring:message code="user.name" text="Name" /></h3></td>
							<td class="oikea"><h3><spring:message code="workout.points" text="Points" /></h3></td>
						</tr>
						<c:forEach items="${challengeUsers}" var="cUser">
						<tr>
							<td><a href="/uftc/challenge/userWorkout?challengeId=${challenge.getId()}&userId=${cUser.getId()}">${cUser.getFirstName()}
								${cUser.getLastName()}</a></td>

						<td class="oikea">${usersWithPoints.get(cUser.getId())}</td>
						</tr>
						</c:forEach>
						</table>
						<c:if test="${challengeOwner && notApprovedUsers.size() > 0}">
						<p>&nbsp;</p>
						<table class="challengeTable">
						<tr>
						<td><h3><spring:message code="challenge.awaitingUsers" text="Awaiting" /></h3></td>
						<td class="oikea"><h3><spring:message code="misc.action" text="Action" /></h3></td>
						</tr>
						<c:forEach items="${notApprovedUsers}" var="cUser">
						<tr>
										<td>${cUser.getFirstName()} ${cUser.getLastName()}</td>
										<td class="oikea"><a href="/uftc/challenge/accept?challengeId=${challenge.getId()}&userId=${cUser.getId()}"><spring:message code="misc.accept" text="Accept" /></a></td>
							

						</tr>
						</c:forEach>
						</table>
						</c:if>
					</div>
					<c:if test="${!challengeParticipant && !awaitingParticipant || !loggedInUser.getAuthority() == 'ROLE_ADMIN'}"><a class="nappi" href="/uftc/challenge/join?challengeId=${challenge.getId()}"><spring:message code="challenge.join" text="Join" /></a></c:if>
					<c:if test="${awaitingParticipant}"><a class="nappi" style="background-color: grey;" href="#"><spring:message code="challenge.awaiting" text="Awaiting" /></a></c:if>
				</div>


			</div>
			<!--End of column3-->

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>