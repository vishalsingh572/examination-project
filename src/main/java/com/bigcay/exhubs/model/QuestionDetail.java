package com.bigcay.exhubs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "question_details")
public class QuestionDetail {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "content")
	private String content;

	@Column(name = "sort_order")
	private Integer sortOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_header_id")
	private QuestionHeader questionHeader;

	@Transient
	private Boolean isChecked;
	
	public QuestionHeader getQuestionHeader() {
		return questionHeader;
	}

	public void setQuestionHeader(QuestionHeader questionHeader) {
		this.questionHeader = questionHeader;
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

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public String getOptionCheckedValue() {
		if (this.getIsChecked() != null && this.getIsChecked().booleanValue() == true) {
			return "checked";
		} else {
			return "";
		}
	}

}
