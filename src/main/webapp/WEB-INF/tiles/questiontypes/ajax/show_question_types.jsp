<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<table class="table table-hover">
	<thead>
		<tr>
			<th><s:message code="questiontypes.info.question_types_name" /></th>
			<th><s:message
					code="questiontypes.info.question_types_description" /></th>
			<th><s:message
					code="questiontypes.info.question_types_operation" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="questionType" items="${questionTypes}">
			<tr>
				<td>${questionType.name}</td>
				<td>${questionType.description}</td>
				<td>...</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
