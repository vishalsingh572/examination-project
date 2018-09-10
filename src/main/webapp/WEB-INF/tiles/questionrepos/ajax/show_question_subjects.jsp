<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-hover">
	<thead>
		<tr>
			<th><s:message code="questionrepos.info.question_subjects_id" /></th>
			<th><s:message
					code="questionrepos.info.question_subjects_question_type" /></th>
			<th><s:message
					code="questionrepos.info.question_subjects_content" /></th>
			<th><s:message
					code="questionrepos.info.question_subjects_total_score" /></th>
			<th><s:message
					code="questionrepos.info.question_subjects_creator" /></th>
			<th><s:message
					code="questionrepos.info.question_subjects_operation" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="questionSubject" items="${questionSubjects}">
			<tr>
				<td>${questionSubject.id}</td>
				<td>${questionSubject.questionType.description}</td>
				<td>${fn:substring(questionSubject.content, 0, 25)}...</td>
				<td>${questionSubject.totalScore}</td>
				<td>${questionSubject.user.userId}</td>
				<td><button class="btn btn-link btn-mini"
						onclick="location.href='/question_subject/${questionSubject.id}'">
						<s:message code="global.info.btn.view" />
					</button>
					<button data-delete-question-subject-id="${questionSubject.id}"
						class="btn btn-link btn-mini confirm_delete_question_subject">
						<s:message code="global.info.btn.delete" />
					</button></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="/WEB-INF/tiles/common/pagination.jsp" />

<script type="text/javascript" charset="utf-8">
	$(".confirm_delete_question_subject").on("click", function(e) {
		var deleteQuestionSubjectId = $(this).data('deleteQuestionSubjectId');
		var confirmsMessage = "<s:message code='global.alert.are_you_sure'/>";

		bootbox.confirm(confirmsMessage, function(result) {
			if (result == true) {
				deleteQuestionSubject(deleteQuestionSubjectId);
			}
		});
	});
	
</script>