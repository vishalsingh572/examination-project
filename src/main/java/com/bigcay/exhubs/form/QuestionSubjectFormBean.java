package com.bigcay.exhubs.form;

import java.util.List;

public class QuestionSubjectFormBean {

	private Integer id;

	private String content;

	private String tag;

	private Integer totalScore;

	private Integer questionTypeId;

	private List<QuestionHeaderBean> questionHeaderBeans;

	public List<QuestionHeaderBean> getQuestionHeaderBeans() {
		return questionHeaderBeans;
	}

	public void setQuestionHeaderBeans(List<QuestionHeaderBean> questionHeaderBeans) {
		this.questionHeaderBeans = questionHeaderBeans;
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

	public Integer getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Integer questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String[] splitTagsToArray() {
		return this.tag.split(",");
	}
	
}
