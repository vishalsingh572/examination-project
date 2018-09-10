<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-hover">
	<thead>
		<tr>
			<th><s:message code="users.info.users_userid" /></th>
			<th><s:message code="users.info.users_name" /></th>
			<th><s:message code="users.info.users_group" /></th>
			<th><s:message code="users.info.users_department" /></th>
			<th><s:message code="users.info.users_status" /></th>
			<th><s:message code="users.info.users_operation" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="associatedCandidate" items="${associatedCandidates}">
			<tr>
				<td>${associatedCandidate.userId}</td>
				<td>${associatedCandidate.name}</td>
				<td>${associatedCandidate.group.description}</td>
				<td>${associatedCandidate.department.name}</td>
				<td><c:if test="${associatedCandidate.activeFlag}">
						<s:message code="global.info.active" />
					</c:if> <c:if test="${not associatedCandidate.activeFlag}">
						<s:message code="global.info.inactive" />
					</c:if></td>
				<td><button data-candidate-id="${associatedCandidate.id}"
						class="btn btn-link btn-mini confirm_detach_candidate">
						<s:message code="global.info.btn.detach" />
					</button></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript" charset="utf-8">
	$(".confirm_detach_candidate").on("click", function(e) {
		var candidateId = $(this).data('candidateId');
		var examEventId = $('input[name=examEventId]').val();
		var confirmsMessage = "<s:message code='global.alert.are_you_sure'/>";

		bootbox.confirm(confirmsMessage, function(result) {
			if (result == true) {
				detachCandidate(examEventId, candidateId);
			}
		});
	});
</script>