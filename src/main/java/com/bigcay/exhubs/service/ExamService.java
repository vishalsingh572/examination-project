package com.bigcay.exhubs.service;

import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;

import com.bigcay.exhubs.common.ValidationResult;
import com.bigcay.exhubs.form.SubmitExamPaperFormBean;
import com.bigcay.exhubs.model.ExamCandidate;
import com.bigcay.exhubs.model.ExamEvent;
import com.bigcay.exhubs.model.ExamPaper;
import com.bigcay.exhubs.model.ExamType;
import com.bigcay.exhubs.model.QuestionSubject;
import com.bigcay.exhubs.model.SubmitQuestionHeader;
import com.bigcay.exhubs.model.User;

public interface ExamService {

	Page<ExamType> findPageableExamTypes(Integer pageNumber);

	ExamType findExamTypeById(Integer id);
	
	ExamType findExamTypeByName(String name);

	ExamType persist(ExamType examType);
	
	List<ExamType> findAllExamTypes();
	
	ValidationResult validateBeforeDeleteExamType(Integer examTypeId, Locale locale);
	
	void deleteExamType(Integer examTypeId);
	
	List<ExamPaper> findAllExamPapers();
	
	Page<ExamPaper> findPageableExamPapers(Integer pageNumber);
	
	ExamPaper findExamPaperById(Integer id);
	
	ExamPaper findExamPaperByName(String name);
	
	ExamPaper persist(ExamPaper examPaper);
	
	ValidationResult validateBeforeDeleteExamPaper(Integer examPaperId, Locale locale);
	
	void deleteExamPaper(Integer examPaperId);
	
	List<QuestionSubject> findQuestionSubjectsByExamPaperId(Integer examPaperId);
	
	Page<ExamEvent> findPageableExamEvents(Integer pageNumber);
	
	ExamEvent findExamEventById(Integer id);
	
	ExamEvent findExamEventByName(String name);
	
	ExamEvent persist(ExamEvent examEvent);
	
	ValidationResult validateBeforeDeleteExamEvent(Integer examEventId, Locale locale);
	
	void deleteExamEvent(Integer examEventId);
	
	List<User> findCandidatesByExamEventId(Integer examEventId);
	
	List<User> findReviewersByExamEventId(Integer examEventId);
	
	ValidationResult validateBeforeAssignQuestionSubject(Integer examPaperId, Integer questionSubjectId, Locale locale);
	
	void assignQuestionSubject(Integer examPaperId, Integer questionSubjectId);
	
	ValidationResult validateBeforeDetachQuestionSubject(Integer examPaperId, Integer questionSubjectId, Locale locale);
	
	void detachQuestionSubject(Integer examPaperId, Integer questionSubjectId);
	
	ValidationResult validateBeforeAssignCandidate(Integer examEventId, Integer candidateId, Locale locale);
	
	void assignCandidate(Integer examEventId, Integer candidateId);
	
	ValidationResult validateBeforeDetachCandidate(Integer examEventId, Integer candidateId, Locale locale);
	
	void detachCandidate(Integer examEventId, Integer candidateId);
	
	ValidationResult validateBeforeAssignReviewer(Integer examEventId, Integer reviewerId, Locale locale);
	
	void assignReviewer(Integer examEventId, Integer reviewerId);
	
	ValidationResult validateBeforeDetachReviewer(Integer examEventId, Integer reviewerId, Locale locale);
	
	void detachReviewer(Integer examEventId, Integer reviewerId);
	
	List<ExamEvent> findCandidateExamEventsByUserId(Integer candidateId);
	
	ExamCandidate findExamCandidateByExamEventIdAndUserId(Integer examEventId, Integer userId);
	
	ExamCandidate persist(ExamCandidate examCandidate);
	
	SubmitQuestionHeader findSubmitQuestionHeader(Integer examEventId, Integer candidateUserId, Integer questionHeaderId);
	
	List<SubmitQuestionHeader> findSubmitQuestionHeadersByExamEventIdAndUserId(Integer examEventId, Integer candidateUserId);
	
	List<SubmitQuestionHeader> findSubmitQuestionHeadersByExamEventIdAndQuestionHeaderId(Integer examEventId, Integer questionHeaderId);
	
	List<SubmitQuestionHeader> findSubmitQuestionHeadersByExamEventId(Integer examEventId);
	
	SubmitQuestionHeader findSubmitQuestionHeaderById(Integer submitQuestionHeaderId);
	
	SubmitQuestionHeader persist(SubmitQuestionHeader submitQuestionHeader);
	
	ValidationResult validateBeforeSubmitExamPaper(SubmitExamPaperFormBean submitExamPaperFormBean, Locale locale);
	
	List<ExamEvent> findReviewerExamEventsByUserId(Integer reviewerId);
	
}
