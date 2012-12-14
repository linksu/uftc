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
					<h2 class="headertext">Haasteet</h2>
				</div>
				<div class="chartbox">
					<div class="challenge">
						<div class="upper-header">
							<h3 class="left">Haasteen nimi</h3>
							<h3 class="right">Yhteispisteet</h3>
						</div>


						<!-- 								<div class="rivi_selected">
							<div class="riviteksti">
								<div class="nimi">
									<p>1.UFTC</p>
								</div>
								<div class="yht">
									<p>30</p>
								</div>
							</div>
						</div>
						 -->
						<c:forEach items="${challenges}" var="challenge">
							<div class="rivi">
								<div class="riviteksti">
									<div class="nimi">
										<p><a href="/uftc/challenge/show?challengeId=${challenge.getId()}">${challenge.getTitle()}</a></p>
									</div>
									<div class="yht">
										<p>1</p>
									</div>
								</div>
							</div>


						</c:forEach>

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