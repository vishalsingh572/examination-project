package com.bigcay.exhubs.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "submit_question_headers")
public class SubmitQuestionHeader {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_event_id")
	private ExamEvent examEvent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User candidate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_header_id")
	private QuestionHeader questionHeader;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "submit_question_answer_id")
	private SubmitQuestionAnswer submitQuestionAnswer;

	@Column(name = "comment")
	private String comment;

	@Column(name = "obtain_score")
	private Integer obtainScore;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_user_id")
	private User reviewer;

	@Column(name = "review_datetime")
	private Date reviewDateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ExamEvent getExamEvent() {
		return examEvent;
	}

	public void setExamEvent(ExamEvent examEvent) {
		this.examEvent = examEvent;
	}

	public User getCandidate() {
		return candidate;
	}

	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}

	public QuestionHeader getQuestionHeader() {
		return questionHeader;
	}

	public void setQuestionHeader(QuestionHeader questionHeader) {
		this.questionHeader = questionHeader;
	}

	public SubmitQuestionAnswer getSubmitQuestionAnswer() {
		return submitQuestionAnswer;
	}

	public void setSubmitQuestionAnswer(SubmitQuestionAnswer submitQuestionAnswer) {
		this.submitQuestionAnswer = submitQuestionAnswer;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getObtainScore() {
		return obtainScore;
	}

	public void setObtainScore(Integer obtainScore) {
		this.obtainScore = obtainScore;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	public Date getReviewDateTime() {
		return reviewDateTime;
	}

	public void setReviewDateTime(Date reviewDateTime) {
		this.reviewDateTime = reviewDateTime;
	}

}
