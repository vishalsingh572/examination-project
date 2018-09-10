<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="control-group">
	<label class="control-label">Question Description</label>
	<div class="controls">
		<input type="text" class="input-xlarge"
			name="questionHeaderBeans[0].description" value="___" />
		<button type="button" class="btn btn-small">Add Answer</button>
	</div>
</div>

<div class="control-group">
	<label class="control-label">Right Answer</label>
	<div class="controls">
		<input type="text"
			name="questionHeaderBeans[0].textAnswer"
			class="input-large"  />
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="score">Score</label>
	<div class="controls">
		<input type="text" name="questionHeaderBeans[0].score"
			class="input-mini" />
	</div>
</div>