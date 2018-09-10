package com.bigcay.exhubs.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bigcay.exhubs.model.ExamPaper;
import com.bigcay.exhubs.service.ExamService;

@Component
public class ExamPaperFormBeanValidator implements Validator {

	@Autowired
	private ExamService examService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ExamPaperFormBean.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		// 1. Preparations
		ExamPaperFormBean examPaperFormBean = (ExamPaperFormBean) target;

		// 2. Bean validations
		ValidationUtils.rejectIfEmpty(errors, "name", "ExamPaperFormBean.name.NotEmpty");
		ValidationUtils.rejectIfEmpty(errors, "description", "ExamPaperFormBean.description.NotEmpty");

		if (examPaperFormBean.getExamTypeId() == null || examPaperFormBean.getExamTypeId() == 0) {
			errors.rejectValue("examTypeId", "ExamPaperFormBean.examTypeId.IsRequired");
		}

		// 3. Business validations
		ExamPaper examPaperFoundByName = examService.findExamPaperByName(examPaperFormBean.getName());

		if (examPaperFoundByName != null && !examPaperFoundByName.getId().equals(examPaperFormBean.getId())) {
			errors.rejectValue("name", "ExamPaperFormBean.name.AlreadyExist");
		}
	}

}
