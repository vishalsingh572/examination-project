package com.bigcay.exhubs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigcay.exhubs.model.QuestionAnswer;
import com.bigcay.exhubs.model.SubmitQuestionAnswer;

public class QuestionUtil {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(QuestionUtil.class);

	private static int MAX_CHOICES_NUM = 8;

	public static String[] getQuestionChoices() {
		return new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };
	}

	public static String getRightQuestionAnswer(String questionTypeName, QuestionAnswer questionAnswer) {

		String rightQuestionAnswer = "Unknown";

		if ("SCQ".equalsIgnoreCase(questionTypeName) || "MCQ".equalsIgnoreCase(questionTypeName)) {
			String tempAnswer = "";
			int tempValue = questionAnswer.getBinaryValue();
			for (int i = MAX_CHOICES_NUM - 1; i >= 0; i--) {
				if (tempValue >= Math.pow(2, i)) {
					tempValue -= Math.pow(2, i);
					tempAnswer = QuestionUtil.getQuestionChoices()[i] + tempAnswer;
				}
			}
			rightQuestionAnswer = tempAnswer;
		} else if ("TFQ".equalsIgnoreCase(questionTypeName)) {
			if (questionAnswer.getBinaryValue().intValue() == 1) {
				rightQuestionAnswer = "Y";
			} else if (questionAnswer.getBinaryValue().intValue() == 2) {
				rightQuestionAnswer = "N";
			}
		} else if ("BFQ".equalsIgnoreCase(questionTypeName)) {
			rightQuestionAnswer = questionAnswer.getShortTextValue();
		} else if ("EQ".equalsIgnoreCase(questionTypeName)) {
			rightQuestionAnswer = questionAnswer.getLongTextValue();
		}

		return rightQuestionAnswer;
	}
	
	public static boolean reviewSubmittAnswer(String questionTypeName, QuestionAnswer questionAnswer,
			SubmitQuestionAnswer submitQuestionAnswer) {

		if ("SCQ".equalsIgnoreCase(questionTypeName) || "MCQ".equalsIgnoreCase(questionTypeName)
				|| "TFQ".equalsIgnoreCase(questionTypeName)) {
			if (questionAnswer.getBinaryValue().equals(submitQuestionAnswer.getBinaryValue())) {
				return true;
			} else {
				return false;
			}
		} else if ("BFQ".equalsIgnoreCase(questionTypeName)) {
			if (submitQuestionAnswer.getShortTextValue() != null
					&& questionAnswer.getShortTextValue().trim()
							.equalsIgnoreCase(submitQuestionAnswer.getShortTextValue().trim())) {
				return true;
			} else {
				return false;
			}
		} else if ("EQ".equalsIgnoreCase(questionTypeName)) {
			if (submitQuestionAnswer.getLongTextValue() != null
					&& questionAnswer.getLongTextValue().trim()
							.equalsIgnoreCase(submitQuestionAnswer.getLongTextValue().trim())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
