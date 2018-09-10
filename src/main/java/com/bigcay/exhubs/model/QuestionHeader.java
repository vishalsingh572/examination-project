package com.bigcay.exhubs.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bigcay.exhubs.util.QuestionUtil;

@Entity
@Table(name = "question_headers")
public class QuestionHeader {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "description")
	private String description;

	@Column(name = "score")
	private Integer score;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_subject_id")
	private QuestionSubject questionSubject;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_type_id")
	private QuestionType questionType;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "question_answer_id")
	private QuestionAnswer questionAnswer;
	
	@Transient
	private SubmitQuestionAnswer candidateSubmitQuestionAnswer;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "questionHeader")
	@OrderBy("sortOrder ASC")
	private Set<QuestionDetail> questionDetails = new HashSet<QuestionDetail>();

	public Set<QuestionDetail> getQuestionDetails() {
		return questionDetails;
	}

	public void setQuestionDetails(Set<QuestionDetail> questionDetails) {
		this.questionDetails = questionDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public QuestionAnswer getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(QuestionAnswer questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getRightQuestionAnswer() {
		return QuestionUtil.getRightQuestionAnswer(this.questionType.getName(), this.getQuestionAnswer());
	}

	public QuestionSubject getQuestionSubject() {
		return questionSubject;
	}

	public void setQuestionSubject(QuestionSubject questionSubject) {
		this.questionSubject = questionSubject;
	}

	public SubmitQuestionAnswer getCandidateSubmitQuestionAnswer() {
		return candidateSubmitQuestionAnswer;
	}

	public void setCandidateSubmitQuestionAnswer(SubmitQuestionAnswer candidateSubmitQuestionAnswer) {
		this.candidateSubmitQuestionAnswer = candidateSubmitQuestionAnswer;
	}
}
