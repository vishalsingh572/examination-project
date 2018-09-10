package com.bigcay.exhubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigcay.exhubs.model.ExamPaper;

public interface ExamPaperRepository extends JpaRepository<ExamPaper, Integer> {

	ExamPaper findByName(String name);
	
}
