package com.bigcay.exhubs.model;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ExamCandidatePK implements Serializable {

	private static final long serialVersionUID = 6079585760155941151L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_event_id", referencedColumnName = "id")
	private ExamEvent examEvent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

}
