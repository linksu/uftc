<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Ultimate</title>
<link rel="stylesheet" type="text/css" href="/uftc/style/style.css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		// Muutetaan java lista javascript arrayksi
		var sportEventArray = createSportEventList();

		//jQuery('#FormAddNewSportEvent').hide();
		jQuery('#FormEditSportEvent').hide();

		// Nappaa kiinni kaikkiin linkkeihin, joiden luokka on "block"
		jQuery('a.block').click(function(event) {
			// Etsitään linkin id 
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
</head>

<body>
	<div id="wrapper">
		<%@ include file="/templates/header.jsp"%>
		<div id="content">
			<div id="column1">
				<div id="new_sportevent">
				
					<!-- NEW SPORTEVENT FORM DIV -->
					<div id="FormAddNewSportEvent" class="chartbox_sportevent">
						
						<div class="headerbox">
							<h2 class="headertext">
								<spring:message code="sportEvent.create" />
							</h2>
						</div>
						<c:url var="saveUrl" value="/sportEvent/add" />
						<form:form modelAttribute="sportEventInstance" method="POST"
							action="${saveUrl}">
							<table>
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
													text="SportEvent title:" />
											</form:label>
										</p></td>
									<td><form:input path="title" class="teksti" /></td>
								</tr>

								<tr>
									<td><p>
											<form:label path="pointFactorType">
												<spring:message code="sportEvent.pointFactorType"
													text="Pointfactor type:" />
											</form:label>
										</p></td>
									<td><form:select path="pointFactorType" class="teksti">
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
													text="Pointfactor:" />
											</form:label>
										</p></td>
									<td><form:input path="pointFactor" class="teksti" /></td>
								</tr>

							</table>

							<input type="submit" class="kirjaudu"
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
							<table>
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
													text="SportEvent title:" />
											</form:label>
										</p></td>
									<td><form:input path="title" class="teksti" id="se_title" /></td>
								</tr>

								<tr>
									<td><p>
											<form:label path="pointFactorType">
												<spring:message code="sportEvent.pointFactorType"
													text="Pointfactor type:" />
											</form:label>
										</p></td>
									<td><form:select id="se_edit_select" path="pointFactorType" class="teksti">
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
													text="Pointfactor:" />
											</form:label>
										</p></td>
									<td><form:input path="pointFactor" class="teksti"
											id="se_pointFactor" /></td>
								</tr>

							</table>
							<form:hidden path="id" id="se_id"/>
							<form:hidden path="version" id="se_version"/>
							<input type="submit" class="kirjaudu"
								value="<spring:message code="misc.save" text="Save"/>" />
						</form:form>

					</div>
					<!-- EDIT SPORTEVENT FORM DIV END-->

					<div class="headerbox">
						<h2 class="headertext">
							<spring:message code="admin.sportevent.listing" />
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
				<div id="edit_user">
					<div class="headerbox">
						<h2 class="headertext">
							<spring:message code="user.listAll" />
						</h2>
					</div>
				</div>
			</div>
		</div><!--End of content-->
		
		
		
		
		</div>
		<%@ include file="/templates/footer.jsp"%>
	</div>
	

</body>
</html>