<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

		<div id="login"></div>

		<div id="content">
			<div id="column1">
				<div class="headerbox">
					<h2 class="headertext">Käyttäjät</h2>
				</div>
				<div class="chartbox">

					<div class="challenge">


						<div class="upper-header">
							<h3 class="left">Käyttäjän nimi</h3>
							<h3 class="right">Käyttäjän pisteet</h3>
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
						<c:url var="joinUrl"
							value="/challenge/join?userId=${userInstance.getId()}&challengeId=${challengeInstance.getId() }" />
						<form:form modelAttribute="challengeInstance" method="POST"
							action="${joinUrl}">
							<input type="submit" value="Save" name="submitform"
								class="kirjaudu" value="Kirjaudu" />
						</form:form>
					</div>
				</div>


			</div>
			<!--End of column1-->

			<div id="column2">
				<div class="headerbox">
					<h2 class="headertext">Lis&auml;&auml; haaste</h2>
					<img class="headerlogo" src="pics/star.jpg">
				</div>



				<div class="chartbox">

					<c:url var="saveUrl" value="/challenge/list" />
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
								<td><form:input path="title" /></td>
							</tr>
							<tr>
								<td><form:label path="startTime">
										<h3>
											<spring:message code="challenge.startTime" text="StartTime:" />
										</h3>
									</form:label></td>
								<td><form:input type="text" name="startTime" id="startTime"
										path="startTime" value="" /></td>
							</tr>
							<tr>
								<td><form:label path="endTime">
										<h3>
											<spring:message code="challenge.endTime" text="EndTime:" />
										</h3>
									</form:label></td>
								<td><form:input type="text" name="endTime" id="endTime"
										path="endTime" value="" /></td>

							</tr>

						</table>

						<input type="submit" value="Save" />
					</form:form>
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