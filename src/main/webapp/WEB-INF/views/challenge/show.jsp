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
									<p><spring:message code="challenge.challengeOwner" text="Challenge owner " />:</p>
								</td>

							<td><p>${challenge.getOwner().getUsername()}</p></td>
						</tr>
						<tr>
							<td>
									<p><spring:message code="challenge.totalPoints" text="Total points " />:</p>
								</td>
							<td><p>${challenge.getTotalPoints()}</p></td>
						</tr>
						<tr>
							<td>
									<p><spring:message code="challenge.startTime" text="Start time " />:</p>
								</td>
							<td><p>${challenge.getStartTimeString()}</p></td>
						</tr>
						<tr>
							<td>
							<p><spring:message code="challenge.endTime" text="End time " />:</p>
									</td>
									<td><p>${challenge.getEndTimeString()}</p></td>
						</tr>
					</table>
		
			<c:if test="${challengeOwner}"><a class="nappi" href="/uftc/challenge/edit?challengeId=${challenge.getId()}">Muokkaa</a></c:if>
			<c:if test="${challengeParticipant}"><a class="nappi" style="float:left;" href="/uftc/workout/add?challengeId=${challenge.getId()}">Lis‰‰ suoritus</a></c:if>
			<a class="nappi" style="float:right;" href="/uftc/challenge/list">Takaisin</a>
			
		</div>
		</div>
		
					<c:if test="${challengeParticipant}">
		<div id="column2">
		<div class="headerbox">
		<h2 class="headertext">Omat suoritukset</h2>
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
							<spring:message code="sportEvent.pointFactorType.${workout.getChallengeSportEvent().getPointFactorType()}"
														text="${workout.getChallengeSportEvent().getPointFactorType()}" />
							</td>
							<td class="oikea">
							Pistett‰
							</td>
							</tr>
					</c:forEach>
					</table>
		</div>
		</div>
		
		</c:if>
		
			<div id="column3">
				<div class="headerbox">
					<h2 class="headertext">Haasteen k‰ytt‰j‰t</h2>
				</div>
				<div class="chartbox">

					<div class="challenge">
						<table class="challengeTable">
						<tr>
							<td><h3>K‰ytt‰j‰n nimi</h3></td>
							<td class="oikea"><h3>K‰ytt‰j‰n pisteet</h3></td>
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
						<td><h3>Hyv‰ksytt‰v‰t</h3></td>
						<td class="oikea"><h3>Toiminto</h3></td>
						</tr>
						<c:forEach items="${notApprovedUsers}" var="cUser">
						<tr>
										<td>${cUser.getFirstName()} ${cUser.getLastName()}</td>
										<td class="oikea"><a href="/uftc/challenge/accept?challengeId=${challenge.getId()}&userId=${cUser.getId()}">Hyv‰ksy</a></td>
							

						</tr>
						</c:forEach>
						</table>
						</c:if>
					</div>
					<c:if test="${!challengeParticipant && !awaitingParticipant || !loggedInUser.getAuthority() == 'ROLE_ADMIN'}"><a class="nappi" href="/uftc/challenge/join?challengeId=${challenge.getId()}">Liity</a></c:if>
					<c:if test="${awaitingParticipant}"><a class="nappi" style="background-color: grey;" href="#">Odottaa</a></c:if>
				</div>


			</div>
			<!--End of column3-->

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>