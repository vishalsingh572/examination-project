package com.bigcay.exhubs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(ExamReviewerPK.class)
@Table(name = "exam_reviewers")
public class ExamReviewer {

	@Id
	private ExamEvent examEvent;

	@Id
	private User user;

	public ExamEvent getExamEvent() {
		return examEvent;
	}

	public void setExamEvent(ExamEvent examEvent) {
		this.examEvent = examEvent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
