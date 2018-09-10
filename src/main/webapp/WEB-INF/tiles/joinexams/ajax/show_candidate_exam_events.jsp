<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<table class="table table-hover">
	<thead>
		<tr>
			<th><s:message code="examevents.info.examevents_name" /></th>
			<th><s:message code="examevents.info.examevents_start_datetime" /></th>
			<th><s:message code="examevents.info.examevents_duration" /></th>
			<th><s:message code="examevents.info.examevents_submit_datetime" /></th>
			<th><s:message code="examevents.info.examevents_final_score" /></th>
			<th><s:message code="examevents.info.examevents_operation" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="candidateExamEvent" items="${candidateExamEvents}">
			<tr>
				<td>${candidateExamEvent.name}</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
						value="${candidateExamEvent.startDateTime}" /></td>
				<td>${candidateExamEvent.duration}</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
						value="${candidateExamEvent.myExamCandidate.submitDateTime}" /></td>
				<td>${candidateExamEvent.myExamCandidate.finalScore}</td>
				<td><c:if test="${candidateExamEvent.inProcess}">
						<button class="btn btn-info btn-mini"
							onclick="location.href='/answer_exam/${candidateExamEvent.id}'">
							<s:message code="examevents.info.btn.answer_exam" />
						</button>
					</c:if></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
