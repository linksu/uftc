<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/templates/header.jsp"%>
		
<h1>SportEvent list</h1>
	<table style="border: 1px solid; width: 500px; text-align: center">
		<thead style="background: #fcf">
			<tr>
				<th>SportEvent</th>
				<th colspan="3"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${sportEvents}" var="sportEvent">

				<tr>
					<td><a href="/uftc/sportEvent/edit?sportEventId=${sportEvent.getTitle()}"><c:out value="${sportEvent.title}" /></a></td>
				</tr>
			</c:forEach>
			</tbody>
	</table>
<%@ include file="/templates/footer.jsp"%>