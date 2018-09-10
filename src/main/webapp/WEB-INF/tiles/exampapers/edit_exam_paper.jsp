<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="please_select">
	<s:message code="global.info.please_select" />
</c:set>

<form:form id="form" class="form-horizontal" method="post"
	modelAttribute="examPaperFormBean">
	<fieldset>
		<legend>
			<s:message code="exampapers.info.edit_exam_paper" />
		</legend>

		<input type="hidden" name="id" value="${examPaperFormBean.id}" />

		<s:bind path="name">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="name"><s:message
						code="exampapers.info.exampapers_name" /></label>
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
						code="exampapers.info.exampapers_description" /></label>
				<div class="controls">
					<form:input class="input-xlarge" path="description" tabindex="2" />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<s:bind path="examTypeId">
			<div class="control-group ${status.error ? 'error' : '' }">
				<label class="control-label" for="examTypeId"><s:message
						code="exampapers.info.exam_type" /></label>
				<div class="controls">
					<form:select path="examTypeId" tabindex="3">
						<form:option value="0" label="${please_select}" />
						<form:options items="${examTypes}" itemValue="id"
							itemLabel="description" />
					</form:select>
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
		</s:bind>

		<div class="form-actions">
			<button type="submit" class="btn btn-primary" tabindex="4">
				<s:message code="global.info.btn.update" />
			</button>
			<button type="button" class="btn" tabindex="5"
				onclick="history.go(-1);">
				<s:message code="global.info.btn.cancel" />
			</button>
		</div>
	</fieldset>
</form:form>