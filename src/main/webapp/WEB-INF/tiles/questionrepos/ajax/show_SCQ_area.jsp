<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="control-group">
	<label class="control-label"><s:message
			code="questionrepos.info.label_question_description" /></label>
	<div class="controls">
		<input type="text" class="input-xlarge"
			name="questionHeaderBeans[0].description" />
		<button type="button" class="btn btn-small">
			<s:message code="questionrepos.info.btn.add_answer" />
		</button>
	</div>
</div>

<div class="control-group">
	<label class="control-label"><input type="radio"
		name="questionHeaderBeans[0].radioSelectedIndex" id="radio_0_0"
		value="0"></label>
	<div class="controls">
		<input type="text"
			name="questionHeaderBeans[0].questionDetailBeans[0].content"
			class="input-large" />
		<button type="button" class="btn btn-small btn-link">
			<s:message code="questionrepos.info.btn.remove" />
		</button>
	</div>
</div>

<div class="control-group">
	<label class="control-label"><input type="radio"
		name="questionHeaderBeans[0].radioSelectedIndex" id="radio_0_1"
		value="1"></label>
	<div class="controls">
		<input type="text"
			name="questionHeaderBeans[0].questionDetailBeans[1].content"
			class="input-large" />
		<button type="button" class="btn btn-small btn-link">
			<s:message code="questionrepos.info.btn.remove" />
		</button>
	</div>
</div>

<div class="control-group">
	<label class="control-label"><input type="radio"
		name="questionHeaderBeans[0].radioSelectedIndex" id="radio_0_2"
		value="2"></label>
	<div class="controls">
		<input type="text"
			name="questionHeaderBeans[0].questionDetailBeans[2].content"
			class="input-large" />
		<button type="button" class="btn btn-small btn-link">
			<s:message code="questionrepos.info.btn.remove" />
		</button>
	</div>
</div>

<div class="control-group">
	<label class="control-label"><input type="radio"
		name="questionHeaderBeans[0].radioSelectedIndex" id="radio_0_3"
		value="3"></label>
	<div class="controls">
		<input type="text"
			name="questionHeaderBeans[0].questionDetailBeans[3].content"
			class="input-large" />
		<button type="button" class="btn btn-small btn-link">
			<s:message code="questionrepos.info.btn.remove" />
		</button>
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