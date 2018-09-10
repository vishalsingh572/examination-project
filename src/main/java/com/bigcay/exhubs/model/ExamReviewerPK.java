package com.bigcay.exhubs.model;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ExamReviewerPK implements Serializable {

	private static final long serialVersionUID = -2764073181258971803L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_event_id", referencedColumnName = "id")
	private ExamEvent examEvent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

}
