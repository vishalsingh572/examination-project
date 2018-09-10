package com.bigcay.exhubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigcay.exhubs.model.QuestionTag;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Integer> {

	QuestionTag findByName(String name);

}
