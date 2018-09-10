package com.bigcay.exhubs.service;

import com.bigcay.exhubs.model.Image;

public interface ImageService {

	Image findImageById(Integer id);

	Image persist(Image image);

}