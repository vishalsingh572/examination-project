package com.bigcay.exhubs.form;

import java.util.ArrayList;
import java.util.List;

public class QuestionHeaderBean {

	private Integer id;

	private String description;

	private Integer score;

	private List<QuestionDetailBean> questionDetailBeans = new ArrayList<QuestionDetailBean>();
	
	private Integer radioSelectedIndex;
	
	private String textAnswer;

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

	public List<QuestionDetailBean> getQuestionDetailBeans() {
		return questionDetailBeans;
	}

	public void setQuestionDetailBeans(List<QuestionDetailBean> questionDetailBeans) {
		this.questionDetailBeans = questionDetailBeans;
	}

	public Integer getRadioSelectedIndex() {
		return radioSelectedIndex;
	}

	public void setRadioSelectedIndex(Integer radioSelectedIndex) {
		this.radioSelectedIndex = radioSelectedIndex;
	}

	public String getTextAnswer() {
		return textAnswer;
	}

	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}
}
