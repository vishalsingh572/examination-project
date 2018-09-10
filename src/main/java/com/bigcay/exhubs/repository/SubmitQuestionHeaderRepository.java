package com.bigcay.exhubs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigcay.exhubs.model.SubmitQuestionHeader;

public interface SubmitQuestionHeaderRepository extends JpaRepository<SubmitQuestionHeader, Integer> {

	SubmitQuestionHeader findByExamEventIdAndCandidateIdAndQuestionHeaderId(Integer examEventId, Integer candidateUserId, Integer questionHeaderId);
	
	List<SubmitQuestionHeader> findByExamEventIdAndQuestionHeaderId(Integer examEventId, Integer questionHeaderId);
	
	List<SubmitQuestionHeader> findByExamEventId(Integer examEventId);
	
	List<SubmitQuestionHeader> findByExamEventIdAndCandidateId(Integer examEventId, Integer candidateUserId);
}
