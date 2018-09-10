<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="please_select">
	<s:message code="global.info.please_select" />
</c:set>

<form:form id="form" class="form-horizontal" method="post"
	modelAttribute="examEventFormBean">
	<fieldset>
		<legend>
			<s:message code="examevents.info.add_exam_event" />
		</legend>

		<s:bind path="name">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="name"><s:message
						code="examevents.info.examevents_name" /></label>
				<div class="controls">
					<form:input class="input-xlarge" path="name" tabindex="1"
						autofocus="autofocus" />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<s:bind path="description">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="description"><s:message
						code="examevents.info.examevents_description" /></label>
				<div class="controls">
					<form:input class="input-xlarge" path="description" tabindex="2" />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<s:bind path="examPaperId">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="examPaperId"><s:message
						code="examevents.info.exam_paper" /></label>
				<div class="controls">
					<form:select path="examPaperId" tabindex="3">
						<form:option value="0" label="${please_select}" />
						<form:options items="${examPapers}" itemValue="id"
							itemLabel="name" />
					</form:select>
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<s:bind path="startDateTime">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="startDateTime"><s:message
						code="examevents.info.examevents_start_datetime" /></label>
				<div class="controls">
					<div class="input-append date form_datetime"
						data-link-field="startDateTime">
						<input type="text" class="input-medium"
							value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
							value="${examEventFormBean.startDateTime}" />'
							readonly> <span class="add-on"><i
							class="icon-remove"></i></span> <span class="add-on"><i
							class="icon-th"></i></span>
					</div>
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
				<form:input type="hidden" class="input-xlarge" path="startDateTime" />
			</div>
		</s:bind>

		<s:bind path="duration">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="duration"><s:message
						code="examevents.info.examevents_duration" /></label>
				<div class="controls">
					<form:input class="input-mini" path="duration" tabindex="5" />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<div class="form-actions">
			<button type="submit" class="btn btn-primary" tabindex="6">
				<s:message code="global.info.btn.add" />
			</button>
		</div>
	</fieldset>
</form:form>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		bindDateTimePicker();
	});
</script>
