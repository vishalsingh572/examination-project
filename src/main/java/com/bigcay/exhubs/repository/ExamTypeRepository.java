package com.bigcay.exhubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigcay.exhubs.model.ExamType;

public interface ExamTypeRepository extends JpaRepository<ExamType, Integer> {

	ExamType findByName(String name);
	
}