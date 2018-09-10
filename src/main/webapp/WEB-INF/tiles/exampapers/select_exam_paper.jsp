<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<input type="hidden" name="examPaperId" value="${examPaperId}" />

<div class="page-header">
	<h4>
		<s:message code="global.info.exam_paper_setting" />
	</h4>
</div>

<h4>
	<s:message code="exampapers.info.potential_question_subjects" />
</h4>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span11">
			<div id="potentialQuestionSubjects" class="panel"></div>
		</div>
		<div class="span1"></div>
	</div>
</div>

<h4>
	<s:message code="exampapers.info.associated_question_subjects" />
</h4>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span11">
			<div id="associatedQuestionSubjects" class="panel"></div>
		</div>
		<div class="span1"></div>
	</div>
</div>

<script src="/resources/js/exams.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var examPaperId = $('input[name=examPaperId]').val();
		showPotentialQuestionSubjects();
		showAssociatedQuestionSubjects(examPaperId);
	});
</script>
