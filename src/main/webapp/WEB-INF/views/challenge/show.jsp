<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="login"></div>

		<div id="content">
		
		<div id="column1">
		<div class="headerbox">
		<h2 class="headertext">${challenge.getTitle()}</h2>
		</div>
		<div class="chartbox">
					
					<table>
					<tr>
							<td>
									<p><spring:message code="challenge.challengeOwner" text="Challenge owner: " /></p>
								</td>

							<td><p>${challenge.getOwner().getUsername()}</p></td>
						</tr>
						<tr>
							<td>
									<p><spring:message code="challenge.totalPoints" text="Total points: " /></p>
								</td>
							<td><p>${challenge.getTotalPoints()}</p></td>
						</tr>
						<tr>
							<td>
									<p><spring:message code="challenge.startTime" text="Start time: " /></p>
								</td>
							<td><p>${challenge.getStartTimeString()}</p></td>
						</tr>
						<tr>
							<td>
							<p><spring:message code="challenge.endTime" text="End time: " /></p>
									</td>
									<td><p>${challenge.getEndTimeString()}</p></td>
						</tr>
					</table>
		
			<c:if test="${challengeOwner}"><a class="nappi" href="/uftc/challenge/edit?challengeId=${challenge.getId()}">Muokkaa</a></c:if>
			<c:if test="${challengeParticipant}"><a class="nappi" href="/uftc/workout/add?challengeId=${challenge.getId()}">Lis‰‰ suoritus</a></c:if>
		
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


						<div class="upper-header">
							<h3 class="left">K‰ytt‰j‰n nimi</h3>
							<h3 class="right">K‰ytt‰j‰n pisteet</h3>
						</div>

						<c:forEach items="${challengeUsers}" var="cUser">
							<div class="rivi">
								<div class="riviteksti">
									<div class="nimi">
										<p><a href="/uftc/challenge/userWorkout?challengeId=${challenge.getId()}&userId=${cUser.getId()}">${cUser.getFirstName()} ${cUser.getLastName()}</a></p>
									</div>
									<div class="yht">
										<p>${usersWithPoints.get(cUser.getId())}</p>
									</div>
								</div>
							</div>


						</c:forEach>
						<c:if test="${challengeOwner}">
						<c:forEach items="${notApprovedUsers}" var="cUser">
							<div class="rivi">
								<div class="riviteksti">
									<div class="nimi">
										<p>${cUser.getFirstName()} ${cUser.getLastName()}</p>
									</div>
									<div class="yht">
										<a href="/uftc/challenge/accept?challengeId=${challenge.getId()}&userId=${cUser.getId()}">Hyv‰ksy</a>
									</div>
								</div>
							</div>


						</c:forEach>
						</c:if>
					</div>
					<c:if test="${!challengeParticipant && !awaitingParticipant}"><a class="nappi" href="/uftc/challenge/join?challengeId=${challenge.getId()}">Liity</a></c:if>
					<c:if test="${awaitingParticipant}"><a class="nappi" style="background-color: grey;" href="#">Odottaa</a></c:if>
				</div>


			</div>
			<!--End of column3-->

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>