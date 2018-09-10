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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "question_subjects")
public class QuestionSubject {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "content")
	private String content;
	
	@Column(name = "image_id")
	private Integer imageId; 

	@Column(name = "total_score")
	private Integer totalScore;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_type_id")
	private QuestionType questionType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "questionSubject")
	@OrderBy("id ASC")
	private Set<QuestionHeader> questionHeaders = new HashSet<QuestionHeader>();

	@OneToMany(mappedBy = "questionSubject", fetch = FetchType.LAZY)
	private Set<QuestionSubjectQuestionTag> questionSubjectQuestionTags;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@OrderBy("id ASC")
	@JoinTable(name = "question_subject_question_tag", joinColumns = { @JoinColumn(name = "question_subject_id") }, inverseJoinColumns = { @JoinColumn(name = "question_tag_id") })
	private Set<QuestionTag> questionTags = new HashSet<QuestionTag>();

	@OneToMany(mappedBy = "questionSubject", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
	private Set<ExamPaperQuestionSubject> examPaperQuestionSubjects;

	@ManyToMany(mappedBy = "questionSubjects", fetch = FetchType.LAZY)
	private Set<ExamPaper> examPapers = new HashSet<ExamPaper>();

	public Set<QuestionHeader> getQuestionHeaders() {
		return questionHeaders;
	}

	public void setQuestionHeaders(Set<QuestionHeader> questionHeaders) {
		this.questionHeaders = questionHeaders;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<QuestionSubjectQuestionTag> getQuestionSubjectQuestionTags() {
		return questionSubjectQuestionTags;
	}

	public void setQuestionSubjectQuestionTags(Set<QuestionSubjectQuestionTag> questionSubjectQuestionTags) {
		this.questionSubjectQuestionTags = questionSubjectQuestionTags;
	}

	public Set<QuestionTag> getQuestionTags() {
		return questionTags;
	}

	public void setQuestionTags(Set<QuestionTag> questionTags) {
		this.questionTags = questionTags;
	}

	public String getAssembledTags() {
		if (this.getQuestionTags() != null && this.getQuestionTags().size() > 0) {
			StringBuilder sb = new StringBuilder(100);

			for (QuestionTag questionTag : this.getQuestionTags()) {
				sb.append(questionTag.getName());
				sb.append(",");
			}

			return sb.substring(0, sb.length() - 1);
		} else {
			return null;
		}
	}

	public Set<ExamPaperQuestionSubject> getExamPaperQuestionSubjects() {
		return examPaperQuestionSubjects;
	}

	public void setExamPaperQuestionSubjects(Set<ExamPaperQuestionSubject> examPaperQuestionSubjects) {
		this.examPaperQuestionSubjects = examPaperQuestionSubjects;
	}

	public Set<ExamPaper> getExamPapers() {
		return examPapers;
	}

	public void setExamPapers(Set<ExamPaper> examPapers) {
		this.examPapers = examPapers;
	}
	
	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

}
