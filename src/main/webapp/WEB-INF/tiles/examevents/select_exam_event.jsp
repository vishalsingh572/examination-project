<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<input type="hidden" name="examEventId" value="${examEventId}" />

<div class="page-header">
	<h4>
		<s:message code="global.info.exam_event_setting" />
	</h4>
</div>

<div class="tab_content_main">
	<ul id="myTab" class="nav nav-tabs">
		<li class="active"><a href="#candidate_container"
			data-toggle="tab"><s:message
					code="examevents.info.tab.assign_candidates" /></a></li>
		<li><a href="#reviewer_container" data-toggle="tab"><s:message
					code="examevents.info.tab.assign_reviewers" /></a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="candidate_container">
			<h4>
				<s:message code="examevents.info.potential_candidates" />
			</h4>

			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span11">
						<div id="potentialCandidates" class="panel"></div>
					</div>
					<div class="span1"></div>
				</div>
			</div>

			<h4>
				<s:message code="examevents.info.associated_candidates" />
			</h4>

			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span11">
						<div id="associatedCandidates" class="panel"></div>
					</div>
					<div class="span1"></div>
				</div>
			</div>
		</div>
		<div class="tab-pane fade" id="reviewer_container">
			<h4>
				<s:message code="examevents.info.potential_reviewers" />
			</h4>

			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span11">
						<div id="potentialReviewers" class="panel"></div>
					</div>
					<div class="span1"></div>
				</div>
			</div>

			<h4>
				<s:message code="examevents.info.associated_reviewers" />
			</h4>

			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span11">
						<div id="associatedReviewers" class="panel"></div>
					</div>
					<div class="span1"></div>
				</div>
			</div>
		</div>
	</div>
</div>


<script src="/resources/js/exams.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var examEventId = $('input[name=examEventId]').val();
		showPotentialCandidates();
		showAssociatedCandidates(examEventId);
		showPotentialReviewers();
		showAssociatedReviewers(examEventId);
	});
</script>
