<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/templates/header.jsp"%>

	<h1>
		<spring:message code="sportEvent.create" text="Create New Sportevent" />
	</h1>

	<c:url var="saveUrl" value="/sportEvent/add" />
	<form:form modelAttribute="sportEventInstance" method="POST"
		action="${saveUrl}">
		<table>
			<tr>
				<c:forEach items="${errors}" var="e">
					<spring:message code="sportEvent.validationErrors.${e.toString()}"
						text="${e.toString()}" />
					<br>
				</c:forEach>

				<td><form:label path="title">
						<spring:message code="sportEvent.title" text="SportEvent title:" />
					</form:label></td>
				<td><form:input path="title" /></td>
			</tr>

			<tr>
				<td><form:label path="pointFactorType">
						<spring:message code="sportEvent.pointFactorType"
							text="Pointfactor type:" />
					</form:label></td>
				<td><form:select path="pointFactorType">
						<form:options items="${pointfactortype}" />
					</form:select></td>

			</tr>

			<tr>
				<td><form:label path="pointFactor">
						<spring:message code="sportEvent.pointFactor" text="Pointfactor:" />
					</form:label></td>
				<td><form:input path="pointFactor" /></td>
			</tr>

		</table>

		<input type="submit"
			value="<spring:message code="misc.save" text="Save"/>" />
	</form:form>
	
<%@ include file="/templates/footer.jsp"%>
