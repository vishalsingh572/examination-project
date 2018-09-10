function showExamTypes(pageNumber) {
	$.ajax({
		url : '/ajax/examtypes/show_exam_types',
		data : {
			pageNumber : pageNumber == null ? 1 : pageNumber
		},
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#examTypes').html(response);
		}
	});
};

function deleteExamType(deleteId) {
	$.ajax({
		url : '/rest/examtypes/delete_exam_type',
		data : {
			deleteId : deleteId
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				showExamTypes();
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};

function showExamPapers(pageNumber) {
	$.ajax({
		url : '/ajax/exampapers/show_exam_papers',
		data : {
			pageNumber : pageNumber == null ? 1 : pageNumber
		},
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#examPapers').html(response);
		}
	});
};

function deleteExamPaper(deleteId) {
	$.ajax({
		url : '/rest/exampapers/delete_exam_paper',
		data : {
			deleteId : deleteId
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				showExamPapers();
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};


function showAssociatedQuestionSubjects(examPaperId) {
	$.ajax({
		url : '/ajax/exampapers/show_associated_question_subjects',
		data : {
			examPaperId: examPaperId
		},
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#associatedQuestionSubjects').html(response);
		}
	});
};

function showPotentialQuestionSubjects(pageNumber) {
	$.ajax({
		url : '/ajax/exampapers/show_potential_question_subjects',
		data : {
			pageNumber : pageNumber == null ? 1 : pageNumber
		},
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#potentialQuestionSubjects').html(response);
		}
	});
};

function assignQuestionSubject(examPaperId, questionSubjectId) {
	$.ajax({
		url : '/rest/exampapers/assign_question_subject',
		data : {
			examPaperId : examPaperId,
			questionSubjectId : questionSubjectId
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				showAssociatedQuestionSubjects(examPaperId);
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};

function detachQuestionSubject(examPaperId, questionSubjectId) {
	$.ajax({
		url : '/rest/exampapers/detach_question_subject',
		data : {
			examPaperId : examPaperId,
			questionSubjectId : questionSubjectId
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				showAssociatedQuestionSubjects(examPaperId);
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};

function showExamEvents(pageNumber) {
	$.ajax({
		url : '/ajax/examevents/show_exam_events',
		data : {
			pageNumber : pageNumber == null ? 1 : pageNumber
		},
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#examEvents').html(response);
		}
	});
};

function deleteExamEvent(deleteId) {
	$.ajax({
		url : '/rest/examevents/delete_exam_event',
		data : {
			deleteId : deleteId
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				showExamEvents();
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};

function showAssociatedCandidates(examEventId) {
	$.ajax({
		url : '/ajax/examevents/show_associated_candidates',
		data : {
			examEventId: examEventId
		},
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#associatedCandidates').html(response);
		}
	});
};

function showPotentialCandidates(pageNumber) {
	$.ajax({
		url : '/ajax/examevents/show_potential_candidates',
		data : {
			pageNumber : pageNumber == null ? 1 : pageNumber
		},
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#potentialCandidates').html(response);
		}
	});
};

function assignCandidate(examEventId, candidateId) {
	$.ajax({
		url : '/rest/examevents/assign_candidate',
		data : {
			examEventId : examEventId,
			candidateId : candidateId
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				showAssociatedCandidates(examEventId);
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};

function detachCandidate(examEventId, candidateId) {
	$.ajax({
		url : '/rest/examevents/detach_candidate',
		data : {
			examEventId : examEventId,
			candidateId : candidateId
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				showAssociatedCandidates(examEventId);
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};

function showAssociatedReviewers(examEventId) {
	$.ajax({
		url : '/ajax/examevents/show_associated_reviewers',
		data : {
			examEventId: examEventId
		},
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#associatedReviewers').html(response);
		}
	});
};

function showPotentialReviewers(pageNumber) {
	$.ajax({
		url : '/ajax/examevents/show_potential_reviewers',
		data : {
			pageNumber : pageNumber == null ? 1 : pageNumber
		},
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#potentialReviewers').html(response);
		}
	});
};

function assignReviewer(examEventId, reviewerId) {
	$.ajax({
		url : '/rest/examevents/assign_reviewer',
		data : {
			examEventId : examEventId,
			reviewerId : reviewerId
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				showAssociatedReviewers(examEventId);
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};

function detachReviewer(examEventId, reviewerId) {
	$.ajax({
		url : '/rest/examevents/detach_reviewer',
		data : {
			examEventId : examEventId,
			reviewerId : reviewerId
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				showAssociatedReviewers(examEventId);
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};

function showCandidateExamEvents() {
	$.ajax({
		url : '/ajax/joinexams/show_candidate_exam_events',
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#candidateExamEvents').html(response);
		}
	});
};

function showReviewerExamEvents() {
	$.ajax({
		url : '/ajax/reviewexams/show_reviewer_exam_events',
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#reviewerExamEvents').html(response);
		}
	});
};

function showReviewQuestionSubjects(examEventId) {
	$.ajax({
		url : '/ajax/reviewexams/show_review_question_subjects',
		data : {
			examEventId: examEventId
		},
		type : 'get',
		cache : false,
		success : function(response, textStatus, xhr) {
			$('#reviewQuestionSubjects').html(response);
		}
	});
};

function autoReviewQuestionSubject(examEventId, questionSubjectId) {
	$.ajax({
		url : '/rest/reviewexams/auto_review_question_subject',
		data : {
			examEventId : examEventId,
			questionSubjectId : questionSubjectId
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				$('#ajax_success').html(obj.successMessage).show();
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};

function calculateExamEventScores(examEventId) {
	$.ajax({
		url : '/rest/reviewexams/calculate_exam_event_scores',
		data : {
			examEventId : examEventId,
		},
		type : 'post',
		cache : false,
		success : function(response, textStatus, xhr) {
			var obj = jQuery.parseJSON(xhr.responseText);
			if (obj.resultType == 'SUCCESS') {
				$('#ajax_success').html(obj.successMessage).show();
			} else if (obj.resultType == 'ERROR') {
				$('#ajax_error').html(obj.errorMessage).show();
			}
		}
	});
};

