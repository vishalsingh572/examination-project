<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<input type="hidden" name="examEventId" value="${examEventId}" />

<div class="page-header">
	<h4>
		<s:message code="global.info.review_exam" />
	</h4>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span11">
			<div id="reviewQuestionSubjects" class="panel"></div>
		</div>
		<div class="span1"></div>
	</div>
</div>
<br/>

<script src="/resources/js/exams.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var examEventId = $('input[name=examEventId]').val();
		
		showReviewQuestionSubjects(examEventId);
	});
</script>
