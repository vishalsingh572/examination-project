package com.bigcay.exhubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigcay.exhubs.model.ExamCandidate;

public interface ExamCandidateRepository extends JpaRepository<ExamCandidate, Integer> {

	ExamCandidate findByExamEventIdAndUserId(Integer examEventId, Integer userId);

}
