<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form id="form" class="form-horizontal" method="post"
	modelAttribute="questionSubjectFormBean">
	<fieldset>
		<legend> demo - create question </legend>

		<input type="hidden" name="id" value="${questionSubjectFormBean.id}" />

		<s:bind path="questionTypeId">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="questionTypeId">Question
					Type</label>
				<div class="controls">
					<form:select id="questionTypeSelector" path="questionTypeId"
						onchange="questionTypeChanged();" tabindex="1"
						autofocus="autofocus">
						<form:option value="0" label="-- Please select one --" />
						<form:options items="${questionTypes}" itemValue="id"
							itemLabel="description" />
					</form:select>
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<s:bind path="content">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="content">content</label>
				<div class="controls">
					<form:textarea rows="3" class="input-xxlarge" path="content"
						tabindex="2" />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<s:bind path="totalScore">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="totalScore">Total Score</label>
				<div class="controls">
					<form:input class="input-mini" path="totalScore" tabindex="3" />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<div id="sub_question_container"></div>

		<div class="form-actions">
			<button type="button" id="btnCreateQuestionSubject"
				class="btn btn-primary" tabindex="99">Create Question</button>
		</div>
	</fieldset>
</form:form>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		questionTypeChanged();
		
		$("#btnCreateQuestionSubject").click(function(event) {
			cleanAjaxMessage();
			
			if(validateQuestionSubjectForm()) {
				$("#form").submit();
			} else {
				event.stopPropagation();
			}
		});
	});
	

	function validateQuestionSubjectForm() {
		var successFlag = true; 
		
		var questionTypeId = $('#questionTypeSelector').val();
		if (questionTypeId == 0) {
			$('#ajax_error').html('Please choose a question type first!').show();
			successFlag = false;
		} else {
			var form = $('#form');
			$.ajax({
				url : '/rest/demo/validate_create_question_subject',
				data : form.serialize(),
				type : 'post',
				async: false,
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
		}
		
		return successFlag;
	};

	function questionTypeChanged() {
		var questionTypeId = $('#questionTypeSelector').val();
		if (questionTypeId == 0) {
			$('#sub_question_container').html('');
		} else {
			showSubQuestionArea(questionTypeId);
		}
	};

	function showSubQuestionArea(questionTypeId) {
		$.ajax({
			url : '/ajax/demo/show_sub_question_area',
			data : {
				questionTypeId : questionTypeId
			},
			type : 'get',
			cache : false,
			success : function(response, textStatus, xhr) {
				$('#sub_question_container').html(response);
			}
		});
	};
</script>
