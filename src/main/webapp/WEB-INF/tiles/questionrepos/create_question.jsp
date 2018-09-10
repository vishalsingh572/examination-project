<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="please_select">
	<s:message code="global.info.please_select" />
</c:set>

<form:form id="form" class="form-horizontal" method="post"
	modelAttribute="questionSubjectFormBean" enctype="multipart/form-data">
	<fieldset>
		<legend>
			<s:message code="questionrepos.info.add_question" />
		</legend>

		<input type="hidden" name="id" value="${questionSubjectFormBean.id}" />

		<s:bind path="questionTypeId">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="questionTypeId"><s:message
						code="questionrepos.info.label_question_type" /></label>
				<div class="controls">
					<form:select id="questionTypeSelector" path="questionTypeId"
						onchange="questionTypeChanged();" tabindex="1"
						autofocus="autofocus">
						<form:option value="0" label="${please_select}" />
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
				<label class="control-label" for="content"><s:message
						code="questionrepos.info.label_content" /></label>
				<div class="controls">
					<form:textarea rows="3" class="input-xxlarge" path="content"
						tabindex="2" />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<div class="control-group">
			<label class="control-label" for="content"><s:message
					code="questionrepos.info.label_image" /></label>
			<div class="controls">
				<div class="fileupload fileupload-new" data-provides="fileupload">
					<div class="fileupload-preview thumbnail"
						style="width: 400px; height: 240px;"></div>
					<div>
						<span class="btn btn-file"><span class="fileupload-new"><s:message
									code="global.info.btn.select_image" /></span><span
							class="fileupload-exists"><s:message
									code="global.info.btn.change" /></span><input type="file" name="file"
							tabindex="3" /></span> <a href="#" class="btn fileupload-exists"
							data-dismiss="fileupload"><s:message
								code="global.info.btn.remove" /></a>
					</div>
				</div>
			</div>
		</div>

		<s:bind path="tag">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="tag"><s:message
						code="questionrepos.info.label_tag" /></label>
				<div class="controls">
					<form:input class="typeahead input-xlarge" autocomplete="off"
						path="tag" tabindex="4" data-source='${tagNames}' />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<s:bind path="totalScore">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="totalScore"><s:message
						code="questionrepos.info.label_total_score" /></label>
				<div class="controls">
					<form:input class="input-mini" path="totalScore" tabindex="5" />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<div id="sub_question_container"></div>

		<div class="form-actions">
			<button type="button" id="btnCreateQuestionSubject"
				class="btn btn-primary" tabindex="99">
				<s:message code="questionrepos.info.btn.add_question" />
			</button>
		</div>
	</fieldset>
</form:form>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		questionTypeChanged();

		$('.typeahead').bindCustomTypeAhead();

		$("#btnCreateQuestionSubject").click(function(event) {
			cleanAjaxMessage();

			if (validateQuestionSubjectForm()) {
				$("#form").submit();
			} else {
				event.stopPropagation();
			}
		});
	});

	function validateQuestionSubjectForm() {
		var successFlag = true;

		$.ajax({
			url : '/rest/questionrepos/validate_create_question_subject',
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
			url : '/ajax/questionrepos/show_sub_question_area',
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
