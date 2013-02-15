<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		// Muutetaan java lista javascript arrayksi
		var sportEventArray = createSportEventList();

		//jQuery('#FormAddNewSportEvent').hide();
		jQuery('#FormEditSportEvent').hide();

		// Nappaa kiinni kaikkiin linkkeihin, joiden luokka on "block"
		jQuery('a.block').click(function(event) {
			// Etsit‰‰n linkin id 
			var id = jQuery(this).attr("id");

			jQuery(sportEventArray).each(function(index, value) {
				if (value.id == id) {
					jQuery('#se_id').attr("value", value.id);
					jQuery('#se_version').attr("value", value.version);
					jQuery('#se_title').attr("value", value.title);
					jQuery('#se_edit_select').val(value.pointFactorType).attr('selected',true);
					jQuery('#se_pointFactor').attr("value", value.pointFactor);
				}
			});

			jQuery('#FormEditSportEvent').show();
			jQuery('#FormAddNewSportEvent').hide();

			//alert("${saveUrl}");

		});

	});

	function createSportEventList() {
		var sportEvents = new Array();
		<c:forEach items="${sportEventList}" var="sportEvent">
			mySportEvent = new Object();
			mySportEvent.id = ${sportEvent.id};
			mySportEvent.version =  ${sportEvent.version};
			mySportEvent.title = "${sportEvent.title}";
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
				<div id="new_sportevent">
				
					<!-- NEW SPORTEVENT FORM DIV -->
					<div id="FormAddNewSportEvent" class="chartbox_sportevent">
						
						<div class="headerbox">
							<h2 class="headertext">
								<spring:message code="sportEvent.create" text="Create new sport event" />
							</h2>
						</div>
						<c:url var="saveUrl" value="/sportEvent/add" />
						<form:form modelAttribute="sportEventInstance" method="POST"
							action="${saveUrl}">
							<table class="sportEventFormTable">
								<tr>
									<c:forEach items="${errors}" var="e">
										<spring:message
											code="sportEvent.validationErrors.${e.toString()}"
											text="${e.toString()}" />
										<br>
									</c:forEach>

									<td><p>
											<form:label path="title">
												<spring:message code="sportEvent.title"
													text="Sport event title" />
											</form:label>
										</p></td>
									<td class="oikea"><form:input path="title" class="teksti" /></td>
								</tr>

								<tr>
									<td><p>
											<form:label path="pointFactorType">
												<spring:message code="sportEvent.pointFactorType"
													text="Pointfactor type" />
											</form:label>
										</p></td>
									<td class="oikea"><form:select path="pointFactorType" class="teksti">
											<c:forEach items="${pointFactorTypeEnum}" var="type">
												<form:option value="${type}">
													<spring:message code="sportEvent.pointFactorType.${type}"
														text="${type}" />
												</form:option>
											</c:forEach>
										</form:select></td>



								</tr>

								<tr>
									<td><p>
											<form:label path="pointFactor">
												<spring:message code="sportEvent.pointFactor"
													text="Pointfactor" />
											</form:label>
										</p></td>
									<td class="oikea"><form:input path="pointFactor" class="teksti" /></td>
								</tr>

							</table>

							<input style="float:left;" type="submit" class="nappi"
								value="<spring:message code="misc.save" text="Save"/>" />
						</form:form>
					</div>
					<!-- NEW SPORTEVENT FORM DIV ENDS -->

					<!-- EDIT SPORTEVENT FORM DIV -->
					<div id="FormEditSportEvent" class="chartbox_sportevent">
						<div class="headerbox">
							<h2 class="headertext">
								<spring:message code="sportEvent.edit" />
							</h2>
						</div>
						<c:url var="editUrl" value="/sportEvent/edit" />
						<form:form modelAttribute="sportEventInstance" method="POST"
							action="${editUrl}">
							<table class="sportEventFormTable">
								<tr>
									<c:forEach items="${errors}" var="e">
										<spring:message
											code="sportEvent.validationErrors.${e.toString()}"
											text="${e.toString()}" />
										<br>
									</c:forEach>

									<td><p>
											<form:label path="title">
												<spring:message code="sportEvent.title"
													text="SportEvent title" />
											</form:label>
										</p></td>
									<td class="oikea"><form:input path="title" class="teksti" id="se_title" /></td>
								</tr>

								<tr>
									<td><p>
											<form:label path="pointFactorType">
												<spring:message code="sportEvent.pointFactorType"
													text="Pointfactor type" />
											</form:label>
										</p></td>
									<td class="oikea"><form:select id="se_edit_select" path="pointFactorType" class="teksti">
											<%--  											<form:options items="${pointfactortype}" /> --%>

											<c:forEach items="${pointFactorTypeEnum}" var="type">
												<form:option value="${type}">
													<spring:message code="sportEvent.pointFactorType.${type}"
														text="${type}" />
												</form:option>
											</c:forEach>


										</form:select></td>

								</tr>

								<tr>
									<td><p>
											<form:label path="pointFactor">
												<spring:message code="sportEvent.pointFactor"
													text="Pointfactor" />
											</form:label>
										</p></td>
									<td class="oikea"><form:input path="pointFactor" class="teksti"
											id="se_pointFactor" /></td>
								</tr>

							</table>
							<form:hidden path="id" id="se_id"/>
							<form:hidden path="version" id="se_version"/>
							<input type="submit" class="nappi" style="float:left;"
								value="<spring:message code="misc.save" text="Save"/>" />
								<a class="nappi" style="float:right;" href="/uftc/admin"><spring:message code="misc.back" text="Back" /></a>
						</form:form>

					</div>
					</div>
					<!-- EDIT SPORTEVENT FORM DIV END-->
					<div class="allDefaultSports">
					<div class="headerbox" style="margin-top:5px;">
						<h2 class="headertext">
							<spring:message code="sportEvent.allDefaultSportEvents" text="All default sport events" />
						</h2>
					</div>
					<div class="top5_content">
						<c:forEach items="${sportEventList}" var="sportEvent"
							varStatus="status">
							<div class="top5_content_box">
								<a class="block" href="#" id="${sportEvent.getId()}"><c:out
										value="${sportEvent.getTitle()}" /></a>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div id="column2">
					<div class="headerbox">
						<h2 class="headertext">
							<spring:message code="user.listAll" />
						</h2>
				</div>
				
				<div class="chartbox" style="height:auto;">
				<table class="userTable">
				<tr><td><h3><spring:message code="user.name" text="Name" /></h3></td><td style="width:100px;"><h3><spring:message code="user.role" text="Role" /></h3></td><td><h3><spring:message code="admin.activate.action" text="Activate" /></h3></td></tr>
					<c:forEach items="${userList}" var="user">
							<tr><td><a style="border-bottom: 1px; " href="/uftc/admin/userShow?userId=${user.getId()}" id="${user.getId()}">
							<c:out value="${user.getFirstName()}" />
							<c:out value="${user.getLastName()}" /></a></td>
									<td><p><spring:message code="user.${user.getAuthority()}" text="Unknown" /></p></td>
									<td><a href="/uftc/admin/userActivate?userId=${user.getId()}"><c:choose><c:when test="${!user.isEnabled()}"><spring:message code="admin.activate" text="Activate" /></c:when><c:otherwise><spring:message code="admin.deactivate" text="Deactivate" /></c:otherwise></c:choose></a></td>
									</tr>
					</c:forEach>
					<tr>
					<td><a class="nappi" style="float:left;" href="/uftc/admin/userAdd"><spring:message code="admin.addUser" text="Add new" /></a></td>
					</tr>
					</table>
				</div>
			</div>
		<!--End of content-->
		
		
		
		
		</div>
		<%@ include file="/templates/footer.jsp"%>