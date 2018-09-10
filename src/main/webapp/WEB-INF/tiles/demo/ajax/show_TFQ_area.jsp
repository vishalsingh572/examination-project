<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="control-group">
	<label class="control-label">Question Description</label>
	<div class="controls">
		<input type="text" class="input-xlarge"
			name="questionHeaderBeans[0].description" />
		<button type="button" class="btn btn-small">Add Answer</button>
	</div>
</div>

<div class="control-group">
	<label class="control-label"><input type="radio"
		name="questionHeaderBeans[0].radioSelectedIndex" id="radio_0_0"
		value="0"></label>
	<div class="controls">
		<input type="text"
			name="questionHeaderBeans[0].questionDetailBeans[0].content"
			class="input-large" value="Y" readonly />
	</div>
</div>

<div class="control-group">
	<label class="control-label"><input type="radio"
		name="questionHeaderBeans[0].radioSelectedIndex" id="radio_0_1"
		value="1"></label>
	<div class="controls">
		<input type="text"
			name="questionHeaderBeans[0].questionDetailBeans[1].content"
			class="input-large" value="N" readonly />
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="score">Score</label>
	<div class="controls">
		<input type="text" name="questionHeaderBeans[0].score"
			class="input-mini" />
	</div>
</div>