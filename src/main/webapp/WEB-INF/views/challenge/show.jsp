<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="login"></div>

		<div id="content">
			<div id="column1">
				<div class="headerbox">
					<h2 class="headertext">K‰ytt‰j‰t</h2>
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
										<p>${cUser.getFirstName()} ${cUser.getLastName()}</p>
									</div>
									<div class="yht">
										<p>1</p>
									</div>
								</div>
							</div>


						</c:forEach>
					</div>
					<a class="nappi" href="/uftc/challenge/join?challengeId=${challengeInstance.getId()}">Liity</a>
				</div>


			</div>
			<!--End of column1-->

			<div id="column2">
				<div class="headerbox">
					<h2 class="headertext">Muokkaa haastetta</h2>
					<img class="headerlogo" src="/uftc/pics/star.jpg">
				</div>



				<div class="chartbox">

					<c:url var="saveUrl" value="/challenge/update" />
					<c:set var="error" value="${errors}" />
					<form:form modelAttribute="challengeInstance" method="POST"
						action="${saveUrl}">

						<br />
						<table>
							<tr>

								<c:forEach items="${errors}" var="a">
									<c:out value="${a.toString()}" />
									<br>
								</c:forEach>



								<td><form:label path="title">
										<h3>
											<spring:message code="challenge.title"
												text="Challenge title:" />
										</h3>
									</form:label></td>
								<td><form:input path="title" id="title"/></td>
							</tr>
							<tr>
								<td><form:label path="startTimeString">
										<h3>
											<spring:message code="challenge.startTime" text="StartTime:" />
										</h3>
									</form:label></td>
								<td><form:input type="text"	path="startTimeString" value="" /></td>
							</tr>
							<tr>
								<td><form:label path="endTimeString">
										<h3>
											<spring:message code="challenge.endTime" text="EndTime:" />
										</h3>
									</form:label></td>
								<td><form:input type="text" path="endTimeString" value="" /></td>

							</tr>

						</table>
						<form:input type="hidden" path="version" />
						<form:input type="hidden" path="id" />
						
						<input type="submit" value="Save" />
					</form:form>
					<c:if test="${challengeParticipant}"><a class="nappi" href="/uftc/workout/add?challengeId=${challengeInstance.getId()}">Lis‰‰ suoritus</a></c:if>
					<c:if test="${challengeOwner}"><a class="nappi" href="/uftc/challengeSportEvent/show?challengeId=${challengeInstance.getId()}">Haasteen lajit</a></c:if>
				</div>

			</div>
			<!--End if column2-->

			<div id="column3">
				<div class="headerbox">
					<h2 class="headertext">TAPAHTUMAT</h2>
					<img class="headerlogo" src="/uftc/pics/runner.jpg">
				</div>
				<div class="chartbox">

					<div class="tapahtumat">
						<div class="single">
							<div class="date">
								<p>23.7.2012</p>
							</div>
							<div class="action">
								<p>Janne teki 20 punnerrusta</p>
							</div>
							<div class="like">
								<a href="#"><img src="/uftc/pics/like.jpeg"
									style="width: 15%;"></a>
							</div>
						</div>
						<div class="single">
							<div class="date">
								<p>23.7.2012</p>
							</div>
							<div class="action">
								<p>Janne teki 20 punnerrusta</p>
							</div>
							<div class="like">
								<a href="#"><img src="/uftc/pics/like.jpeg"
									style="width: 15%;"></a>
							</div>
						</div>
						<div class="single">
							<div class="date">
								<p>23.7.2012</p>
							</div>
							<div class="action">
								<p>Janne teki 20 punnerrusta</p>
							</div>
							<div class="like">
								<a href="#"><img src="/uftc/pics/like.jpeg"
									style="width: 15%;"></a>
							</div>
						</div>
						<div class="single">
							<div class="date">
								<p>23.7.2012</p>
							</div>
							<div class="action">
								<p>Janne teki 20 punnerrusta</p>
							</div>
							<div class="like">
								<a href="#"><img src="uftc/pics/like.jpeg"
									style="width: 15%;"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--End of column3-->

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>