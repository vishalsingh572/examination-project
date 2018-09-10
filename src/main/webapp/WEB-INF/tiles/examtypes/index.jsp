<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<div class="page-header">
	<h4>
		<s:message code="global.info.exam_type_setting" />
	</h4>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span11">
			<div id="examTypes" class="panel"></div>
		</div>
		<div class="span1"></div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<button type="button" class="btn btn-primary"
				onclick="location.href='/examtypes/create'">
				<s:message code="global.info.btn.add" />
			</button>
		</div>
		<div class="span6"></div>
	</div>
</div>
<br />

<script src="/resources/js/exams.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		showExamTypes();
	});
</script>
