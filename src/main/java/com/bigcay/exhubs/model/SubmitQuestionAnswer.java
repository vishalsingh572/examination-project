package com.bigcay.exhubs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "submit_question_answers")
public class SubmitQuestionAnswer {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "binary_value")
	private Integer binaryValue;

	@Column(name = "short_text_value")
	private String shortTextValue;

	@Column(name = "long_text_value")
	private String longTextValue;

	@Column(name = "comment")
	private String comment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBinaryValue() {
		return binaryValue;
	}

	public void setBinaryValue(Integer binaryValue) {
		this.binaryValue = binaryValue;
	}

	public String getShortTextValue() {
		return shortTextValue;
	}

	public void setShortTextValue(String shortTextValue) {
		this.shortTextValue = shortTextValue;
	}

	public String getLongTextValue() {
		return longTextValue;
	}

	public void setLongTextValue(String longTextValue) {
		this.longTextValue = longTextValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
