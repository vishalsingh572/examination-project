<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<table class="table table-hover">
	<thead>
		<tr>
			<th><s:message code="examevents.info.examevents_name" /></th>
			<th><s:message code="examevents.info.examevents_exam_paper_name" /></th>
			<th><s:message code="examevents.info.examevents_start_datetime" /></th>
			<th><s:message code="examevents.info.examevents_operation" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="reviewerExamEvent" items="${reviewerExamEvents}">
			<tr>
				<td>${reviewerExamEvent.name}</td>
				<td>${reviewerExamEvent.examPaper.name}</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
						value="${reviewerExamEvent.startDateTime}" /></td>
				<td>
					<button class="btn btn-info btn-mini"
						onclick="location.href='/review_exam_event/${reviewerExamEvent.id}'">
						<s:message code="examevents.info.btn.review_exam" />
					</button>
					<button data-exam-event-id="${reviewerExamEvent.id}"
						class="btn btn-info btn-mini calculate_exam_event_scores">
						<s:message code="reviewexams.info.btn.calculate_exam_event_scores" />
					</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript" charset="utf-8">
	$(".calculate_exam_event_scores").on("click", function(e) {
		var examEventId = $(this).data('examEventId');
		var confirmsMessage = "<s:message code='global.alert.are_you_sure'/>";

		bootbox.confirm(confirmsMessage, function(result) {
			if (result == true) {
				calculateExamEventScores(examEventId);
			}
		});
	});
</script>