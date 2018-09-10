package com.bigcay.exhubs.form;

import java.util.List;

public class SubmitExamPaperFormBean {

	private Integer examEventId;

	private Integer userId;

	private List<SubmitQuestionHeaderBean> submitQuestionHeaderBeans;

	public List<SubmitQuestionHeaderBean> getSubmitQuestionHeaderBeans() {
		return submitQuestionHeaderBeans;
	}

	public void setSubmitQuestionHeaderBeans(List<SubmitQuestionHeaderBean> submitQuestionHeaderBeans) {
		this.submitQuestionHeaderBeans = submitQuestionHeaderBeans;
	}

	public Integer getExamEventId() {
		return examEventId;
	}

	public void setExamEventId(Integer examEventId) {
		this.examEventId = examEventId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
