package com.bigcay.exhubs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigcay.exhubs.model.Image;
import com.bigcay.exhubs.repository.ImageRepository;
import com.bigcay.exhubs.service.ImageService;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;

	@Override
	public Image findImageById(Integer id) {
		return imageRepository.findOne(id);
	}

	@Override
	public Image persist(Image image) {
		return imageRepository.save(image);
	}

}
