<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${fn:length(examEvents) == 0}">
	<div class="alert alert-info">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<s:message code="global.info.no_records" />
	</div>
</c:if>

<c:if test="${fn:length(examEvents) > 0}">
	<table class="table table-hover">
		<thead>
			<tr>
				<th><s:message code="examevents.info.examevents_name" /></th>
				<th><s:message
						code="examevents.info.examevents_exam_paper_name" /></th>
				<th><s:message code="examevents.info.examevents_start_datetime" /></th>
				<th><s:message code="examevents.info.examevents_duration" /></th>
				<th><s:message code="examevents.info.examevents_status" /></th>
				<th><s:message code="examevents.info.examevents_operation" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="examEvent" items="${examEvents}">
				<tr>
					<td>${examEvent.name}</td>
					<td>${examEvent.examPaper.name}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
							value="${examEvent.startDateTime}" /></td>
					<td>${examEvent.duration}</td>
					<td><c:if test="${examEvent.activeFlag}">
							<s:message code="global.info.active" />
						</c:if> <c:if test="${not examEvent.activeFlag}">
							<s:message code="global.info.inactive" />
						</c:if></td>
					<td>
						<button class="btn btn-info btn-mini"
							onclick="location.href='/exam_event/${examEvent.id}'">
							<s:message code="global.info.btn.arrange" />
						</button>
						<button class="btn btn-link btn-mini"
							onclick="location.href='/exam_event/edit/${examEvent.id}'">
							<s:message code="global.info.btn.edit" />
						</button>
						<button data-delete-exam-event-id="${examEvent.id}"
							class="btn btn-link btn-mini confirm_delete_exam_event">
							<s:message code="global.info.btn.delete" />
						</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<jsp:include page="/WEB-INF/tiles/common/pagination.jsp" />
</c:if>

<script type="text/javascript" charset="utf-8">
	$(".confirm_delete_exam_event").on("click", function(e) {
		var deleteExamEventId = $(this).data('deleteExamEventId');
		var confirmsMessage = "<s:message code='global.alert.are_you_sure'/>";

		bootbox.confirm(confirmsMessage, function(result) {
			if (result == true) {
				deleteExamEvent(deleteExamEventId);
			}
		});
	});

	
</script>