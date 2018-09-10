<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<div class="page-header">
	<h4>
		<s:message code="global.info.question_repository_setting" />
	</h4>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span11">
			<div id="questionSubject" class="panel">
				<s:message code="questionrepos.info.question_subject_id" />
				: ${questionSubject.id} <br />
				<s:message code="questionrepos.info.question_subjects_question_type" />
				: ${questionSubject.questionType.description} <br />
				<s:message code="questionrepos.info.question_subjects_creator" />
				: ${questionSubject.user.userId} <br /> <br />
				<s:message code="questionrepos.info.question_subjects_content" />
				:<br /> ${questionSubject.content} (
				<s:message code="questionrepos.info.question_subject_total_score"
					arguments="${questionSubject.totalScore}" />
				) <br />
				<c:if test="${not empty questionSubject.imageId}">
					<img src="/image/${questionSubject.imageId}">
					<br />
				</c:if>
				<s:message code="questionrepos.info.question_subject_tag" />: ${questionSubject.assembledTags}
				<br /><br />
				<c:forEach var="questionHeader"
					items="${questionSubject.questionHeaders}"
					varStatus="question_header_status">
					<c:if
						test="${questionHeader.questionType.name == 'SCQ' or questionHeader.questionType.name == 'MCQ'}">
					${question_header_status.index + 1}. ${questionHeader.description }<br />
						<c:forEach var="questionDetail"
							items="${questionHeader.questionDetails}"
							varStatus="question_detail_status">
						${questionChoices[question_detail_status.index]}. ${questionDetail.content}<br />
						</c:forEach>
						<s:message code="questionrepos.info.question_subject_answer" />: ${questionHeader.rightQuestionAnswer} (<s:message
							code="questionrepos.info.question_subject_header_score"
							arguments="${questionHeader.score}" />)<br />
					</c:if>
					<c:if test="${questionHeader.questionType.name == 'TFQ'}">
					${question_header_status.index + 1}. ${questionHeader.description }<br />
						<s:message code="questionrepos.info.question_subject_answer" />: ${questionHeader.rightQuestionAnswer} (<s:message
							code="questionrepos.info.question_subject_header_score"
							arguments="${questionHeader.score}" />)<br />
					</c:if>
					<c:if test="${questionHeader.questionType.name == 'BFQ'}">
					${question_header_status.index + 1}. ${questionHeader.description }<br />
						<s:message code="questionrepos.info.question_subject_answer" />: ${questionHeader.rightQuestionAnswer} (<s:message
							code="questionrepos.info.question_subject_header_score"
							arguments="${questionHeader.score}" />)<br />
					</c:if>
					<c:if test="${questionHeader.questionType.name == 'EQ'}">
					${question_header_status.index + 1}. ${questionHeader.description }<br />
						<s:message code="questionrepos.info.question_subject_answer" />: ${questionHeader.rightQuestionAnswer} (<s:message
							code="questionrepos.info.question_subject_header_score"
							arguments="${questionHeader.score}" />)<br />
					</c:if>
					<br />
				</c:forEach>
			</div>
		</div>
		<div class="span1"></div>
	</div>
</div>

<script src="/resources/js/questions.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
	});
</script>
