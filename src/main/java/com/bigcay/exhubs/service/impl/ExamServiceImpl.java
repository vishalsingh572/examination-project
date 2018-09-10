package com.bigcay.exhubs.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigcay.exhubs.common.GlobalManager;
import com.bigcay.exhubs.common.ResultType;
import com.bigcay.exhubs.common.ValidationResult;
import com.bigcay.exhubs.form.SubmitExamPaperFormBean;
import com.bigcay.exhubs.model.ExamCandidate;
import com.bigcay.exhubs.model.ExamEvent;
import com.bigcay.exhubs.model.ExamPaper;
import com.bigcay.exhubs.model.ExamType;
import com.bigcay.exhubs.model.QuestionSubject;
import com.bigcay.exhubs.model.SubmitQuestionHeader;
import com.bigcay.exhubs.model.User;
import com.bigcay.exhubs.repository.ExamCandidateRepository;
import com.bigcay.exhubs.repository.ExamEventRepository;
import com.bigcay.exhubs.repository.ExamPaperRepository;
import com.bigcay.exhubs.repository.ExamTypeRepository;
import com.bigcay.exhubs.repository.QuestionSubjectRepository;
import com.bigcay.exhubs.repository.SubmitQuestionHeaderRepository;
import com.bigcay.exhubs.repository.UserRepository;
import com.bigcay.exhubs.service.ExamService;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamTypeRepository examTypeRepository;

	@Autowired
	private ExamPaperRepository examPaperRepository;

	@Autowired
	private ExamEventRepository examEventRepository;

	@Autowired
	private QuestionSubjectRepository questionSubjectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExamCandidateRepository examCandidateRepository;
	
	@Autowired
	private SubmitQuestionHeaderRepository submitQuestionHeaderRepository;

	@Override
	public Page<ExamType> findPageableExamTypes(Integer pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber, GlobalManager.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
		return examTypeRepository.findAll(pageRequest);
	}

	@Override
	public ExamType findExamTypeByName(String name) {
		return examTypeRepository.findByName(name);
	}

	@Override
	public ExamType persist(ExamType examType) {
		return examTypeRepository.save(examType);
	}

	@Override
	public ExamType findExamTypeById(Integer id) {
		return examTypeRepository.findOne(id);
	}

	@Override
	public ValidationResult validateBeforeDeleteExamType(Integer examTypeId, Locale locale) {
		// TO-DO

		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		return result;
	}

	@Override
	public void deleteExamType(Integer examTypeId) {
		examTypeRepository.delete(examTypeId);
	}

	@Override
	public ExamPaper persist(ExamPaper examPaper) {
		return examPaperRepository.save(examPaper);
	}

	@Override
	public Page<ExamPaper> findPageableExamPapers(Integer pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber, GlobalManager.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
		return examPaperRepository.findAll(pageRequest);
	}

	@Override
	public ExamPaper findExamPaperByName(String name) {
		return examPaperRepository.findByName(name);
	}

	@Override
	public List<ExamType> findAllExamTypes() {
		return examTypeRepository.findAll();
	}

	@Override
	public ExamPaper findExamPaperById(Integer id) {
		return examPaperRepository.findOne(id);
	}

	@Override
	public ValidationResult validateBeforeDeleteExamPaper(Integer examPaperId, Locale locale) {
		// TO-DO

		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		return result;
	}

	@Override
	public void deleteExamPaper(Integer examPaperId) {
		examPaperRepository.delete(examPaperId);
	}

	@Override
	public List<QuestionSubject> findQuestionSubjectsByExamPaperId(Integer examPaperId) {
		return questionSubjectRepository.findByExamPapers_Id(examPaperId);
	}

	@Override
	public Page<ExamEvent> findPageableExamEvents(Integer pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber, GlobalManager.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
		return examEventRepository.findAll(pageRequest);
	}

	@Override
	public List<ExamPaper> findAllExamPapers() {
		return examPaperRepository.findAll();
	}

	@Override
	public ExamEvent persist(ExamEvent examEvent) {
		return examEventRepository.save(examEvent);
	}

	@Override
	public ExamEvent findExamEventByName(String name) {
		return examEventRepository.findByName(name);
	}

	@Override
	public ExamEvent findExamEventById(Integer id) {
		return examEventRepository.findOne(id);
	}

	@Override
	public ValidationResult validateBeforeDeleteExamEvent(Integer examEventId, Locale locale) {
		// TO-DO

		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		return result;
	}

	@Override
	public void deleteExamEvent(Integer examEventId) {
		examEventRepository.delete(examEventId);
	}

	@Override
	public ValidationResult validateBeforeAssignQuestionSubject(Integer examPaperId, Integer questionSubjectId,
			Locale locale) {
		// TO-DO

		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		return result;
	}

	@Override
	public void assignQuestionSubject(Integer examPaperId, Integer questionSubjectId) {

		ExamPaper examPaper = examPaperRepository.findOne(examPaperId);
		QuestionSubject questionSubject = questionSubjectRepository.findOne(questionSubjectId);

		examPaper.addQuestionSubject(questionSubject, examPaper.getNextSortOrder());

		this.persist(examPaper);
	}

	@Override
	public ValidationResult validateBeforeDetachQuestionSubject(Integer examPaperId, Integer questionSubjectId,
			Locale locale) {
		// TO-DO

		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		return result;
	}

	@Override
	public void detachQuestionSubject(Integer examPaperId, Integer questionSubjectId) {

		ExamPaper examPaper = examPaperRepository.findOne(examPaperId);

		examPaper.removeQuestionSubjectById(questionSubjectId);

		this.persist(examPaper);
	}

	@Override
	public List<User> findCandidatesByExamEventId(Integer examEventId) {
		return userRepository.findByCandidateExamEvents_Id(examEventId);
	}

	@Override
	public ValidationResult validateBeforeAssignCandidate(Integer examEventId, Integer candidateId, Locale locale) {
		// TO-DO

		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		return result;
	}

	@Override
	public void assignCandidate(Integer examEventId, Integer candidateId) {

		ExamEvent examEvent = examEventRepository.findOne(examEventId);
		User user = userRepository.findOne(candidateId);

		examEvent.addCandidate(user);

		this.persist(examEvent);
	}
	
	@Override
	public ValidationResult validateBeforeDetachCandidate(Integer examEventId, Integer candidateId,
			Locale locale) {
		// TO-DO

		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		return result;
	}

	@Override
	public void detachCandidate(Integer examEventId, Integer candidateId) {

		ExamEvent examEvent = examEventRepository.findOne(examEventId);
		examEvent.removeCandidateById(candidateId);

		this.persist(examEvent);
	}

	@Override
	public ValidationResult validateBeforeAssignReviewer(Integer examEventId, Integer reviewerId, Locale locale) {
		// TO-DO

		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		return result;
	}

	@Override
	public void assignReviewer(Integer examEventId, Integer reviewerId) {
		ExamEvent examEvent = examEventRepository.findOne(examEventId);
		User user = userRepository.findOne(reviewerId);

		examEvent.addReviewer(user);

		this.persist(examEvent);
	}

	@Override
	public ValidationResult validateBeforeDetachReviewer(Integer examEventId, Integer reviewerId, Locale locale) {
		// TO-DO

		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		return result;
	}

	@Override
	public void detachReviewer(Integer examEventId, Integer reviewerId) {

		ExamEvent examEvent = examEventRepository.findOne(examEventId);
		examEvent.removeReviewerById(reviewerId);

		this.persist(examEvent);
	}

	@Override
	public List<User> findReviewersByExamEventId(Integer examEventId) {
		return userRepository.findByReviewerExamEvents_Id(examEventId);
	}

	@Override
	public List<ExamEvent> findCandidateExamEventsByUserId(Integer candidateId) {

		List<ExamEvent> examEvents = examEventRepository.findByCandidateUsers_Id(candidateId);

		// Assemble myExamCandidate
		for (ExamEvent examEvent : examEvents) {
			ExamCandidate myExamCandidate = examCandidateRepository.findByExamEventIdAndUserId(examEvent.getId(), candidateId);
			examEvent.setMyExamCandidate(myExamCandidate);
		}

		return examEvents;
	}

	@Override
	public ExamCandidate findExamCandidateByExamEventIdAndUserId(Integer examEventId, Integer userId) {
		return examCandidateRepository.findByExamEventIdAndUserId(examEventId, userId);
	}

	@Override
	public ExamCandidate persist(ExamCandidate examCandidate) {
		return examCandidateRepository.save(examCandidate);
	}

	@Override
	public ValidationResult validateBeforeSubmitExamPaper(SubmitExamPaperFormBean submitExamPaperFormBean, Locale locale) {
		// let's suppose that there is no error at the beginning
		ValidationResult result = new ValidationResult(ResultType.SUCCESS);
		
		// TO-DO
		// ADD VALIDATIONS HERE
		
		return result; 
	}

	@Override
	public SubmitQuestionHeader findSubmitQuestionHeader(Integer examEventId, Integer candidateUserId,
			Integer questionHeaderId) {
		return submitQuestionHeaderRepository.findByExamEventIdAndCandidateIdAndQuestionHeaderId(examEventId,
				candidateUserId, questionHeaderId);
	}

	@Override
	public SubmitQuestionHeader persist(SubmitQuestionHeader submitQuestionHeader) {
		return submitQuestionHeaderRepository.save(submitQuestionHeader);
	}

	@Override
	public SubmitQuestionHeader findSubmitQuestionHeaderById(Integer submitQuestionHeaderId) {
		return submitQuestionHeaderRepository.findOne(submitQuestionHeaderId);
	}

	@Override
	public List<SubmitQuestionHeader> findSubmitQuestionHeadersByExamEventIdAndUserId(Integer examEventId, Integer candidateUserId) {
		return submitQuestionHeaderRepository.findByExamEventIdAndCandidateId(examEventId, candidateUserId);
	}
	
	@Override
	public List<SubmitQuestionHeader> findSubmitQuestionHeadersByExamEventIdAndQuestionHeaderId(Integer examEventId, Integer questionHeaderId) {
		return submitQuestionHeaderRepository.findByExamEventIdAndQuestionHeaderId(examEventId, questionHeaderId);
	}
	
	@Override
	public List<SubmitQuestionHeader> findSubmitQuestionHeadersByExamEventId(Integer examEventId) {
		return submitQuestionHeaderRepository.findByExamEventId(examEventId);
	}

	@Override
	public List<ExamEvent> findReviewerExamEventsByUserId(Integer reviewerId) {
		List<ExamEvent> examEvents = examEventRepository.findByReviewerUsers_Id(reviewerId);

		return examEvents;
	}

}
