package com.bigcay.exhubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigcay.exhubs.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	Department findByName(String name);
	
}
