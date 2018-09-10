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
		<c:forEach var="potentialCandidate" items="${potentialCandidates}">
			<tr>
				<td>${potentialCandidate.userId}</td>
				<td>${potentialCandidate.name}</td>
				<td>${potentialCandidate.group.description}</td>
				<td>${potentialCandidate.department.name}</td>
				<td><c:if test="${potentialCandidate.activeFlag}">
						<s:message code="global.info.active" />
					</c:if> <c:if test="${not potentialCandidate.activeFlag}">
						<s:message code="global.info.inactive" />
					</c:if></td>
				<td><button
						data-candidate-id="${potentialCandidate.id}"
						class="btn btn-link btn-mini confirm_assign_candidate">
						<s:message code="global.info.btn.assign" />
					</button></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="/WEB-INF/tiles/common/pagination.jsp" />

<script type="text/javascript" charset="utf-8">
	$(".confirm_assign_candidate").on("click", function(e) {
		var candidateId = $(this).data('candidateId');
		var examEventId = $('input[name=examEventId]').val();
		var confirmsMessage = "<s:message code='global.alert.are_you_sure'/>";

		bootbox.confirm(confirmsMessage, function(result) {
			if (result == true) {
				assignCandidate(examEventId, candidateId);
			}
		});
	});
</script>