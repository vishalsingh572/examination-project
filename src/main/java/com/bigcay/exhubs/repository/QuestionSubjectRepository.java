package com.bigcay.exhubs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigcay.exhubs.model.QuestionSubject;

public interface QuestionSubjectRepository extends JpaRepository<QuestionSubject, Integer> {

	List<QuestionSubject> findByExamPapers_Id(Integer examPaperId);
	
}
