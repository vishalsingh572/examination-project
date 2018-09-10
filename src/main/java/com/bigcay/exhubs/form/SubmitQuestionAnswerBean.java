package com.bigcay.exhubs.form;

public class SubmitQuestionAnswerBean {

	private Integer id;

	private Integer binaryValue;

	private String shortTextValue;

	private String longTextValue;

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
