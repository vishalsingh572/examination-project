<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="control-group">
	<label class="control-label"><s:message
			code="questionrepos.info.label_question_description" /></label>
	<div class="controls">
		<input type="text" class="input-xlarge"
			name="questionHeaderBeans[0].description" />
	</div>
</div>

<div class="control-group">
	<label class="control-label"><s:message
			code="questionrepos.info.label_right_answer" /></label>
	<div class="controls">
		<textarea name="questionHeaderBeans[0].textAnswer" rows="4"
			class="input-xxlarge"></textarea>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="score"><s:message
			code="questionrepos.info.label_score" /></label>
	<div class="controls">
		<input type="text" name="questionHeaderBeans[0].score"
			class="input-mini" />
	</div>
</div>