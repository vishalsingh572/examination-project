package com.bigcay.exhubs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigcay.exhubs.model.ExamEvent;

public interface ExamEventRepository extends JpaRepository<ExamEvent, Integer> {

	ExamEvent findByName(String name);
	
	List<ExamEvent> findByCandidateUsers_Id(Integer candidateId);
	
	List<ExamEvent> findByReviewerUsers_Id(Integer reviewerId);
	
}
