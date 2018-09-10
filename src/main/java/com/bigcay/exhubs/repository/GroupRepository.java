package com.bigcay.exhubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigcay.exhubs.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

	Group findByName(String name);
	
}
