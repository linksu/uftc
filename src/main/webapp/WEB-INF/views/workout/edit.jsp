<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/templates/header.jsp"%>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#repetition').change(function() {
		countPoints();
	});
	$('#challengeSportEventId').change(function() {
		countPoints();
	});
	countPoints();
});

function countPoints() {
	//var sportEventList = "<c:out value="${challengeSportEventsList}"/>";
	var sportEventList = createSportEventList();
	var sportEventId = $('#challengeSportEventId').val();
	var repetition = $('#repetition').val();
	var sportEvent = null;

	for (var i = 0; i < sportEventList.length;i++) {
		  
		  if(sportEventId == sportEventList[i].id){
			  sportEvent = sportEventList[i];
			  showSportEventMachingLabel(sportEvent.pointFactorType);
		  }
		}
	var points = sportEvent.pointFactor * repetition;
	$('#points').text(points);
	
};

function showSportEventMachingLabel(pointFactorType){
	$('.pointFactorTypeLabel').hide();
	$('#'+pointFactorType).show();
}


function createSportEventList(){
	 var sportEvents = new Array();
	 <c:forEach items="${challengeSportEventsList}" var="sportEvent">
	   mySportEvent = new Object();
	   mySportEvent.id = ${sportEvent.id}; 
	   mySportEvent.pointFactor = ${sportEvent.pointFactor}; 
	   var myPointFactorType = "${sportEvent.pointFactorType.toString()}";
	   mySportEvent.pointFactorType = myPointFactorType;
	   sportEvents.push(mySportEvent);
	 </c:forEach> 
	 return sportEvents;
	  
};
</script>
		
		<div id="content">
			<div id="column1">
				<div id="new_user">
					<div class="headerbox">
						<h2 class="headertext">
							<spring:message code="workout.edit" text="Editworkout" />
						</h2>
						<img class="headerlogo" src="/uftc/pics/star.jpg">
					</div>
					<div class="chartbox">

						<c:set var="error" value="${errors}" />


						<c:if test="${optimisticLockingError}">
							<br />
						</c:if>

						<c:url var="editUrl"
							value="/workout/edit?workoutId=${workoutInstance.getId()}" />
						<form:form modelAttribute="workoutInstance" method="POST"
							action="${editUrl}">
							<br />
							<table>

								<tr>
									<td><p>
											<c:forEach items="${pointFactorTypeEnum}" var="type">
												<form:label id="${type}" class="pointFactorTypeLabel"
													path="repetition">
													<spring:message code="sportEvent.pointFactorType.${type}"
														text="${type}" />
												</form:label>
											</c:forEach>


										</p></td>
									<td><form:select id="repetition" path="repetition"
											class="teksti">

											<c:forEach var="i" begin="1" end="200" step="1">

												<form:option value="${i}">
													<spring:message text="${i}" />
												</form:option>
											</c:forEach>

										</form:select></td>

									<td><c:if test="${fn:contains(error, 'repetition')}">
											<div class="warning">
												<spring:message code="workout.validationErrors.repetition" />
											</div>
										</c:if></td>
								</tr>

								<tr>
									<td><p>
											<form:label path="challengeSportEventId">
												<spring:message code="workout.sportEventTitles"
													text="SportEvent: " />
											</form:label>
										</p></td>
									<td><form:select id="challengeSportEventId"
											path="challengeSportEventId">
											<form:options items="${challengeSportEventsList}" itemValue="id" itemLabel="title" />
										</form:select>
								<tr>
									<td><p>
											<form:label path="points">
												<spring:message code="workout.points" text="Points: " />
											</form:label>
										</p></td>
									<td><p>
											<form:label id="points" path="points">
												<spring:message text="" />
											</form:label>
										</p></td>

								</tr>
							</table>
						<form:hidden path="id" value="${workout.getId() }"/>
						<form:hidden path="name" value="${workout.getName() }"/>
						
						<form:hidden path="version" value="${workout.getVersion() }"/>

							<input type="submit"
								value="<spring:message code="misc.save" text="Save"/>"
								class="kirjaudu" />
								<a class="nappi" style="float:right;" href="/uftc/challenge/show?challengeId=${challengeInstance.getId()}">Takaisin</a>
						</form:form>

					</div>

				</div>

			</div>
			<!--End of column-->

		</div>
		<!--End of content-->
		<%@ include file="/templates/footer.jsp"%>