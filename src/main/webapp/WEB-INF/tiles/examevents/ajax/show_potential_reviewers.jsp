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
		<c:forEach var="potentialReviewer" items="${potentialReviewers}">
			<tr>
				<td>${potentialReviewer.userId}</td>
				<td>${potentialReviewer.name}</td>
				<td>${potentialReviewer.group.description}</td>
				<td>${potentialReviewer.department.name}</td>
				<td><c:if test="${potentialReviewer.activeFlag}">
						<s:message code="global.info.active" />
					</c:if> <c:if test="${not potentialReviewer.activeFlag}">
						<s:message code="global.info.inactive" />
					</c:if></td>
				<td><button
						data-reviewer-id="${potentialReviewer.id}"
						class="btn btn-link btn-mini confirm_assign_reviewer">
						<s:message code="global.info.btn.assign" />
					</button></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="/WEB-INF/tiles/common/pagination.jsp" />

<script type="text/javascript" charset="utf-8">
	$(".confirm_assign_reviewer").on("click", function(e) {
		var reviewerId = $(this).data('reviewerId');
		var examEventId = $('input[name=examEventId]').val();
		var confirmsMessage = "<s:message code='global.alert.are_you_sure'/>";

		bootbox.confirm(confirmsMessage, function(result) {
			if (result == true) {
				assignReviewer(examEventId, reviewerId);
			}
		});
	});
</script>