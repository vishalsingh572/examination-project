<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form id="form" class="form-horizontal" method="post"
	modelAttribute="submitExamPaperFormBean">
	<fieldset>
		<legend>
			<s:message code="global.info.start_exam" />
			- ${examEvent.name}
		</legend>

		<input type="hidden" name="actionType" value="" /> <input
			type="hidden" name="examEventId" value="${examEvent.id}" /> <input
			type="hidden" name="userId" value="${userId}" />

		<div id="examEventPanel" class="panel">
			<s:message code="examevents.info.examevents_user_id" />
			: ${examEvent.user.userId} <br />
			<s:message code="examevents.info.examevents_start_datetime" />
			:
			<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
				value="${examEvent.startDateTime}" />
			<br />
			<s:message code="examevents.info.examevents_end_datetime" />
			:
			<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
				value="${examEvent.endDateTime}" />
			<br />
			<s:message code="exampapers.info.question_subjects_total_score" />
			: ${examEvent.examPaper.totalScores} <br /> <br />
		</div>

		<c:set var="questionHeaderCounter" value="0" />

		<c:forEach var="questionSubject"
			items="${examEvent.examPaper.questionSubjects}"
			varStatus="question_subject_status">
			<div>
				<h5>${question_subject_status.count}.${questionSubject.content}</h5>

				<c:if test="${not empty questionSubject.imageId}">
					<img src="/image/${questionSubject.imageId}">
					<br />
				</c:if>

				<c:forEach var="questionHeader"
					items="${questionSubject.questionHeaders}"
					varStatus="question_header_status">

					<input type="hidden"
						name="submitQuestionHeaderBeans[${questionHeaderCounter}].questionHeaderId"
						value="${questionHeader.id}" />
					<input type="hidden"
						name="submitQuestionHeaderBeans[${questionHeaderCounter}].questionTypeName"
						value="${questionSubject.questionType.name}" />
					<c:if
						test="${questionHeader.questionType.name == 'SCQ'}">
					${question_header_status.index + 1}) ${questionHeader.description } (<s:message
							code="questionrepos.info.question_subject_header_score"
							arguments="${questionHeader.score}" />)<br />
						<c:forEach var="questionDetail"
							items="${questionHeader.questionDetails}"
							varStatus="question_detail_status">
							<div>
								<label class="radio"> <input type="radio"
									name='submitQuestionHeaderBeans[${questionHeaderCounter}].radioSelectedIndex'
									${questionDetail.optionCheckedValue}
									value='${question_detail_status.index}'>
									${questionChoices[question_detail_status.index]}.
									${questionDetail.content}
								</label>
							</div>
						</c:forEach>
					</c:if>
					
					<c:if
						test="${questionHeader.questionType.name == 'TFQ'}">
					${question_header_status.index + 1}) ${questionHeader.description } (<s:message
							code="questionrepos.info.question_subject_header_score"
							arguments="${questionHeader.score}" />)<br />
						<c:forEach var="questionDetail"
							items="${questionHeader.questionDetails}"
							varStatus="question_detail_status">
							<div>
								<label class="radio"> <input type="radio"
									name='submitQuestionHeaderBeans[${questionHeaderCounter}].radioSelectedIndex'
									${questionDetail.optionCheckedValue}
									value='${question_detail_status.index}'>
									${questionDetail.content}
								</label>
							</div>
						</c:forEach>
					</c:if>

					<c:if test="${questionHeader.questionType.name == 'MCQ'}">
					${question_header_status.index + 1}) ${questionHeader.description } (<s:message
							code="questionrepos.info.question_subject_header_score"
							arguments="${questionHeader.score}" />)<br />
						<c:forEach var="questionDetail"
							items="${questionHeader.questionDetails}"
							varStatus="question_detail_status">
							<div>
								<label class="checkbox"> <input type="checkbox"
									name='submitQuestionHeaderBeans[${questionHeaderCounter}].questionDetailBeans[${question_detail_status.index}].isChecked'
									${questionDetail.optionCheckedValue} value="true">
									${questionChoices[question_detail_status.index]}.
									${questionDetail.content}
								</label>
							</div>
						</c:forEach>
					</c:if>

					<c:if test="${questionHeader.questionType.name == 'BFQ'}">
						<div>
							${question_header_status.index + 1}) <input type="text"
								name='submitQuestionHeaderBeans[${questionHeaderCounter}].textAnswer'
								value="${questionHeader.candidateSubmitQuestionAnswer.shortTextValue}"
								class="input-large" /> (
							<s:message
								code="questionrepos.info.question_subject_header_score"
								arguments="${questionHeader.score}" />
							)
						</div>
					</c:if>

					<c:if test="${questionHeader.questionType.name == 'EQ'}">
						<div>
							${question_header_status.index + 1})

							<textarea
								name='submitQuestionHeaderBeans[${questionHeaderCounter}].textAnswer'
								rows="4" class="input-xxlarge">${questionHeader.candidateSubmitQuestionAnswer.longTextValue}</textarea>
							(
							<s:message
								code="questionrepos.info.question_subject_header_score"
								arguments="${questionHeader.score}" />
							)
						</div>
					</c:if>

					<c:set var="questionHeaderCounter"
						value="${questionHeaderCounter + 1}" />
					<br />
				</c:forEach>

			</div>
		</c:forEach>

		<div class="form-actions">
			<button type="button" id="btnSubmitExamPaper" class="btn btn-primary"
				tabindex="98">
				<s:message code="global.info.btn.submit" />
			</button>
			<button type="button" id="btnSaveDraftExamPaper" class="btn"
				tabindex="99">
				<s:message code="global.info.btn.save_draft" />
			</button>
		</div>
	</fieldset>
</form:form>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$("#btnSaveDraftExamPaper").click(function(event) {
			cleanAjaxMessage();
			
			$("input[name='actionType']").val('save_draft');

			if (validateSubmitExamPaperForm()) {
				$("#form").submit();
			} else {
				event.stopPropagation();
			}
		});
		
		$("#btnSubmitExamPaper").on("click", function(e) {
			var confirmsMessage = "<s:message code='joinexams.alert.are_you_sure_to_submit'/>";
			cleanAjaxMessage();

			bootbox.confirm(confirmsMessage, function(result) {
				if (result == true) {
					$("input[name='actionType']").val('submit');

					if (validateSubmitExamPaperForm()) {
						$("#form").submit();
					} else {
						event.stopPropagation();
					}
				}
			});
		});
	});

	function validateSubmitExamPaperForm() {
		var successFlag = true;

		$.ajax({
			url : '/rest/joinexams/validate_submit_exam_paper',
			data : $('#form').serialize(),
			type : 'post',
			async : false,
			cache : false,
			success : function(response, textStatus, xhr) {
				var obj = jQuery.parseJSON(xhr.responseText);
				if (obj.resultType == 'SUCCESS') {
				} else if (obj.resultType == 'ERROR') {
					$('#ajax_error').html(obj.errorMessage).show();
					successFlag = false;
				}
			}
		});

		return successFlag;
	};
</script>
