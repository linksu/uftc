<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/templates/header.jsp"%>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
	<script src="https://raw.github.com/phstc/jquery-dateFormat/master/jquery.dateFormat-1.0.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$('#startTimeString').datepicker({ dateFormat: 'dd.mm.yy',defaultDate:'1' });
		$('#startTimeString').val($.format.date(new Date(),'dd.MM.yyyy'));
		
		$('#endTimeString').datepicker({ dateFormat: 'dd.mm.yy' });
		$('#endTimeString').val($.format.date(new Date(),'dd.MM.yyyy'));
		
	});
</script>

	<div class="pageBody">

					<div id="column2">
				<div class="headerbox">
					<h2 class="headertext"><spring:message code="challenge.create" text="Create New Challenge"/></h2>
				</div>



				<div class="chartbox">

					<c:url var="saveUrl" value="/challenge/add" />
					<c:set var="error" value="${errors}" />
					<form:form modelAttribute="challengeInstance" method="POST"
						action="${saveUrl}">

						<br />
						<table class="challengeFormTable">
							<tr>


								<td><form:label path="title">
											<spring:message code="challenge.title"
												text="Challenge title" />
									</form:label></td>
								<td><form:input path="title" class="teksti" /></td>
								<td><c:if test="${fn:contains(error, 'title')}">
									<div class="warning">
										<spring:message code="challenge.validationErrors.title" />
									</div>
								</c:if></td>
							</tr>
							<tr>
								<td><form:label path="startTimeString">
											<spring:message code="challenge.startTime" text="StartTime" />
									</form:label></td>
								<td><form:input type="text" class="teksti"	path="startTimeString" value="" /></td>
								<td><c:if test="${fn:contains(error, 'startTime')}">
									<div class="warning">
										<spring:message code="challenge.validationErrors.startTime" />
									</div>
								</c:if></td>
							</tr>
							<tr>
								<td><form:label path="endTimeString">
											<spring:message code="challenge.endTime" text="EndTime" />
									</form:label></td>
								<td><form:input type="text" class="teksti" path="endTimeString" value="" /></td>
								<td><c:if test="${fn:contains(error, 'endTime')}">
									<div class="warning">
										<spring:message code="challenge.validationErrors.endTime" />
									</div>
								</c:if></td>
							</tr>

						</table>

						<input class="nappi" style="float:left;" type="submit" value="<spring:message code="misc.save" text="Save" />" />
						<a class="nappi" style="float:right;" href="/uftc/challenge/list"><spring:message code="misc.back" text="Back" /></a>
					</form:form>
					
				</div>

			</div>
			<!--End if column2-->
	</div>
	<%@ include file="/templates/footer.jsp"%>