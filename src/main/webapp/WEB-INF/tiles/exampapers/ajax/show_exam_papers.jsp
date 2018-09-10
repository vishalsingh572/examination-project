<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${fn:length(examPapers) == 0}">
	<div class="alert alert-info">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<s:message code="global.info.no_records" />
	</div>
</c:if>

<c:if test="${fn:length(examPapers) > 0}">
	<table class="table table-hover">
		<thead>
			<tr>
				<th><s:message code="exampapers.info.exampapers_exam_type" /></th>
				<th><s:message code="exampapers.info.exampapers_name" /></th>
				<th><s:message code="exampapers.info.exampapers_creator" /></th>
				<th><s:message code="exampapers.info.exampapers_create_date" /></th>
				<th><s:message code="exampapers.info.exampapers_status" /></th>
				<th><s:message code="exampapers.info.exampapers_operation" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="examPaper" items="${examPapers}">
				<tr>
					<td>${examPaper.examType.name}</td>
					<td>${examPaper.name}</td>
					<td>${examPaper.user.userId}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd"
							value="${examPaper.createDate}" /></td>
					<td><c:if test="${examPaper.activeFlag}">
							<s:message code="global.info.active" />
						</c:if> <c:if test="${not examPaper.activeFlag}">
							<s:message code="global.info.inactive" />
						</c:if></td>
					<td>
						<button class="btn btn-info btn-mini"
							onclick="location.href='/exam_paper/${examPaper.id}'">
							<s:message code="global.info.btn.arrange" />
						</button>
						<button class="btn btn-link btn-mini"
							onclick="location.href='/exam_paper/edit/${examPaper.id}'">
							<s:message code="global.info.btn.edit" />
						</button>
						<button data-delete-exam-paper-id="${examPaper.id}"
							class="btn btn-link btn-mini confirm_delete_exam_paper">
							<s:message code="global.info.btn.delete" />
						</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<jsp:include page="/WEB-INF/tiles/common/pagination.jsp" />
</c:if>

<script type="text/javascript" charset="utf-8">
	$(".confirm_delete_exam_paper").on("click", function(e) {
		var deleteExamPaperId = $(this).data('deleteExamPaperId');
		var confirmsMessage = "<s:message code='global.alert.are_you_sure'/>";

		bootbox.confirm(confirmsMessage, function(result) {
			if (result == true) {
				deleteExamPaper(deleteExamPaperId);
			}
		});
	});

	
</script>