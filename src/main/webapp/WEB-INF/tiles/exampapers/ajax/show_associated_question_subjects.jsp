<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-hover">
	<thead>
		<tr>
			<th><s:message code="exampapers.info.question_subjects_id" /></th>
			<th><s:message
					code="exampapers.info.question_subjects_question_type" /></th>
			<th><s:message code="exampapers.info.question_subjects_content" /></th>
			<th><s:message
					code="exampapers.info.question_subjects_total_score" /></th>
			<th><s:message
					code="exampapers.info.question_subjects_operation" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="associatedQuestionSubject"
			items="${associatedQuestionSubjects}">
			<tr>
				<td>${associatedQuestionSubject.id}</td>
				<td>${associatedQuestionSubject.questionType.description}</td>
				<td>${fn:substring(associatedQuestionSubject.content, 0,
					25)}...</td>
				<td>${associatedQuestionSubject.totalScore}</td>
				<td><button
						data-question-subject-id="${associatedQuestionSubject.id}"
						class="btn btn-link btn-mini confirm_detach_question_subject">
						<s:message code="global.info.btn.detach" />
					</button></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript" charset="utf-8">
	$(".confirm_detach_question_subject").on("click", function(e) {
		var questionSubjectId = $(this).data('questionSubjectId');
		var examPaperId = $('input[name=examPaperId]').val();
		var confirmsMessage = "<s:message code='global.alert.are_you_sure'/>";

		bootbox.confirm(confirmsMessage, function(result) {
			if (result == true) {
				detachQuestionSubject(examPaperId, questionSubjectId);
			}
		});
	});
</script>