package com.bigcay.exhubs.form;

import java.util.ArrayList;
import java.util.List;

public class SubmitQuestionHeaderBean {

	private Integer questionHeaderId;
	
	private String questionTypeName;

	private List<QuestionDetailBean> questionDetailBeans = new ArrayList<QuestionDetailBean>();
	
	private SubmitQuestionAnswerBean submitQuestionAnswerBean;

	private Integer radioSelectedIndex;
	
	private String textAnswer;

	public Integer getRadioSelectedIndex() {
		return radioSelectedIndex;
	}

	public void setRadioSelectedIndex(Integer radioSelectedIndex) {
		this.radioSelectedIndex = radioSelectedIndex;
	}

	public Integer getQuestionHeaderId() {
		return questionHeaderId;
	}

	public void setQuestionHeaderId(Integer questionHeaderId) {
		this.questionHeaderId = questionHeaderId;
	}

	public SubmitQuestionAnswerBean getSubmitQuestionAnswerBean() {
		return submitQuestionAnswerBean;
	}

	public void setSubmitQuestionAnswerBean(SubmitQuestionAnswerBean submitQuestionAnswerBean) {
		this.submitQuestionAnswerBean = submitQuestionAnswerBean;
	}

	public String getQuestionTypeName() {
		return questionTypeName;
	}

	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}

	public String getTextAnswer() {
		return textAnswer;
	}

	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}

	public List<QuestionDetailBean> getQuestionDetailBeans() {
		return questionDetailBeans;
	}

	public void setQuestionDetailBeans(List<QuestionDetailBean> questionDetailBeans) {
		this.questionDetailBeans = questionDetailBeans;
	}

}
