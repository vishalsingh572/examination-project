package com.bigcay.exhubs.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bigcay.exhubs.model.ExamEvent;
import com.bigcay.exhubs.service.ExamService;

@Component
public class ExamEventFormBeanValidator implements Validator {

	@Autowired
	private ExamService examService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ExamEventFormBean.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		// 1. Preparations
		ExamEventFormBean examEventFormBean = (ExamEventFormBean) target;

		// 2. Bean validations
		ValidationUtils.rejectIfEmpty(errors, "name", "ExamEventFormBean.name.NotEmpty");
		ValidationUtils.rejectIfEmpty(errors, "description", "ExamEventFormBean.description.NotEmpty");
		ValidationUtils.rejectIfEmpty(errors, "startDateTime", "ExamEventFormBean.startDateTime.NotEmpty");
		ValidationUtils.rejectIfEmpty(errors, "duration", "ExamEventFormBean.duration.NotEmpty");

		if (examEventFormBean.getExamPaperId() == null || examEventFormBean.getExamPaperId() == 0) {
			errors.rejectValue("examPaperId", "ExamEventFormBean.examPaperId.IsRequired");
		}

		// 3. Business validations
		ExamEvent examEventFoundByName = examService.findExamEventByName(examEventFormBean.getName());

		if (examEventFoundByName != null && !examEventFoundByName.getId().equals(examEventFormBean.getId())) {
			errors.rejectValue("name", "ExamEventFormBean.name.AlreadyExist");
		}
	}
}
