package com.bigcay.exhubs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(ExamPaperQuestionSubjectPK.class)
@Table(name = "exam_paper_question_subject")
public class ExamPaperQuestionSubject {

	@Id
	private ExamPaper examPaper;

	@Id
	private QuestionSubject questionSubject;
	
	@Column(name = "sort_order")
	private Integer sortOrder;

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	public QuestionSubject getQuestionSubject() {
		return questionSubject;
	}

	public void setQuestionSubject(QuestionSubject questionSubject) {
		this.questionSubject = questionSubject;
	}

}
