package com.bigcay.exhubs.model;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class QuestionSubjectQuestionTagPK implements Serializable {

	private static final long serialVersionUID = -564790373808623660L;

	@ManyToOne
	@JoinColumn(name = "question_subject_id", referencedColumnName = "id")
	private QuestionSubject questionSubject;

	@ManyToOne
	@JoinColumn(name = "question_tag_id", referencedColumnName = "id")
	private QuestionTag questionTag;

}