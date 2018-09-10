<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<div class="page-header">
	<h4>
		<s:message code="global.info.question_type_setting" />
	</h4>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span8">
			<div id="questionTypes" class="panel"></div>
		</div>
		<div class="span4"></div>
	</div>
</div>

<script src="/resources/js/questions.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		showQuestionTypes();
	});
</script>
