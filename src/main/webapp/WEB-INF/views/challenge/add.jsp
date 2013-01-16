<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
	<script src="https://raw.github.com/phstc/jquery-dateFormat/master/jquery.dateFormat-1.0.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$('#startTime').datepicker({ dateFormat: 'dd.mm.yy',defaultDate:'1' });
		$('#startTime').val($.format.date(new Date(),'dd.MM.yyyy'));
		
		$('#endTime').datepicker({ dateFormat: 'dd.mm.yy' });
		$('#endTime').val($.format.date(new Date(),'dd.MM.yyyy'));
		
	});
</script>

	<div class="pageBody">

					<div id="column2">
				<div class="headerbox">
					<h2 class="headertext"><spring:message code="challenge.create" text="Create New Challenge"/></h2>
					<img class="headerlogo" src="/uftc/pics/star.jpg">
				</div>



				<div class="chartbox">

					<c:url var="saveUrl" value="/challenge/add" />
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

						<input class="kirjaudu" type="submit" value="Save" />
					</form:form>
				</div>

			</div>
			<!--End if column2-->
	</div>
	<%@ include file="/templates/footer.jsp"%>