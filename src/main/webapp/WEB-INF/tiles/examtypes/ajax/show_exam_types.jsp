<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${fn:length(examTypes) == 0}">
	<div class="alert alert-info">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<s:message code="global.info.no_records" />
	</div>
</c:if>

<c:if test="${fn:length(examTypes) > 0}">
	<table class="table table-hover">
		<thead>
			<tr>
				<th><s:message code="examtypes.info.examtypes_name" /></th>
				<th><s:message code="examtypes.info.examtypes_description" /></th>
				<th><s:message code="examtypes.info.examtypes_operation" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="examType" items="${examTypes}">
				<tr>
					<td>${examType.name}</td>
					<td>${examType.description}</td>
					<td>
						<button class="btn btn-link btn-mini"
							onclick="location.href='/examtype/edit/${examType.id}'">
							<s:message code="global.info.btn.edit" />
						</button>
						<button data-delete-exam-type-id="${examType.id}"
							class="btn btn-link btn-mini confirm_delete_exam_type">
							<s:message code="global.info.btn.delete" />
						</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<jsp:include page="/WEB-INF/tiles/common/pagination.jsp"/>
</c:if>

<script type="text/javascript" charset="utf-8">
	$(".confirm_delete_exam_type").on("click", function(e) {
		var deleteExamTypeId = $(this).data('deleteExamTypeId');
		var confirmsMessage = "<s:message code='global.alert.are_you_sure'/>";

		bootbox.confirm(confirmsMessage, function(result) {
			if (result == true) {
				deleteExamType(deleteExamTypeId);
			}
		});
	});

	
</script>