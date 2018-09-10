package com.bigcay.exhubs.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "question_tags")
public class QuestionTag {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "questionTag", fetch = FetchType.LAZY)
	private Set<QuestionSubjectQuestionTag> questionSubjectQuestionTags;

	@ManyToMany(mappedBy = "questionTags", fetch = FetchType.LAZY)
	private Set<QuestionSubject> questionSubjects = new HashSet<QuestionSubject>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<QuestionSubjectQuestionTag> getQuestionSubjectQuestionTags() {
		return questionSubjectQuestionTags;
	}

	public void setQuestionSubjectQuestionTags(Set<QuestionSubjectQuestionTag> questionSubjectQuestionTags) {
		this.questionSubjectQuestionTags = questionSubjectQuestionTags;
	}

	public Set<QuestionSubject> getQuestionSubjects() {
		return questionSubjects;
	}

	public void setQuestionSubjects(Set<QuestionSubject> questionSubjects) {
		this.questionSubjects = questionSubjects;
	}

}
