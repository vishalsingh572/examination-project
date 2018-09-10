package com.bigcay.exhubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigcay.exhubs.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
