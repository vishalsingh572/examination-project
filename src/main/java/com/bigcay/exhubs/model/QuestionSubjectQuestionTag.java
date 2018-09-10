package com.bigcay.exhubs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(QuestionSubjectQuestionTagPK.class)
@Table(name = "question_subject_question_tag")
public class QuestionSubjectQuestionTag {

	@Id
	private QuestionSubject questionSubject;

	@Id
	private QuestionTag questionTag;

	public QuestionSubject getQuestionSubject() {
		return questionSubject;
	}

	public void setQuestionSubject(QuestionSubject questionSubject) {
		this.questionSubject = questionSubject;
	}

	public QuestionTag getQuestionTag() {
		return questionTag;
	}

	public void setQuestionTag(QuestionTag questionTag) {
		this.questionTag = questionTag;
	}

}
