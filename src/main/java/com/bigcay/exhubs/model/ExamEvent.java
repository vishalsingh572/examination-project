package com.bigcay.exhubs.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "exam_events")
public class ExamEvent {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "exam_paper_id")
	private ExamPaper examPaper;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "start_datetime")
	private Date startDateTime;

	@Column(name = "end_datetime")
	private Date endDateTime;

	@Column(name = "duration")
	private Integer duration;

	@Column(name = "active_flg")
	private Boolean activeFlag;
	
	@OneToMany(mappedBy = "examEvent", fetch = FetchType.EAGER, orphanRemoval=true, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<ExamCandidate> examCandidates = new HashSet<ExamCandidate>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
	@OrderBy("id ASC")
	@JoinTable(name = "exam_candidates", joinColumns = { @JoinColumn(name = "exam_event_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private Set<User> candidateUsers = new HashSet<User>();
	
	@OneToMany(mappedBy = "examEvent", fetch = FetchType.EAGER, orphanRemoval=true, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<ExamReviewer> examReviewers = new HashSet<ExamReviewer>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
	@OrderBy("id ASC")
	@JoinTable(name = "exam_reviewers", joinColumns = { @JoinColumn(name = "exam_event_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private Set<User> reviewerUsers = new HashSet<User>();
	
	@Transient
	private ExamCandidate myExamCandidate;
	
	public Set<ExamReviewer> getExamReviewers() {
		return examReviewers;
	}

	public void setExamReviewers(Set<ExamReviewer> examReviewers) {
		this.examReviewers = examReviewers;
	}

	public Set<User> getReviewerUsers() {
		return reviewerUsers;
	}

	public void setReviewerUsers(Set<User> reviewerUsers) {
		this.reviewerUsers = reviewerUsers;
	}

	public Set<ExamCandidate> getExamCandidates() {
		return examCandidates;
	}

	public void setExamCandidates(Set<ExamCandidate> examCandidates) {
		this.examCandidates = examCandidates;
	}

	public Set<User> getCandidateUsers() {
		return candidateUsers;
	}

	public void setCandidateUsers(Set<User> candidateUsers) {
		this.candidateUsers = candidateUsers;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	public ExamCandidate getMyExamCandidate() {
		return myExamCandidate;
	}

	public void setMyExamCandidate(ExamCandidate myExamCandidate) {
		this.myExamCandidate = myExamCandidate;
	}
	
	public void addCandidate(User user) {
		
		ExamCandidate examCandidate = new ExamCandidate();
		examCandidate.setExamEvent(this);
		examCandidate.setUser(user);

		this.getExamCandidates().add(examCandidate);
	}
	
	public void removeCandidateById(Integer candidateId) {

		ExamCandidate deleteExamCandidate = null;

		for (ExamCandidate examCandidate : this.getExamCandidates()) {
			if (examCandidate.getUser().getId().equals(candidateId)) {
				deleteExamCandidate = examCandidate;
			}
		}

		if (deleteExamCandidate != null) {
			this.getExamCandidates().remove(deleteExamCandidate);
		}
	}
	
	public void addReviewer(User user) {
		
		ExamReviewer examReviewer = new ExamReviewer();
		examReviewer.setExamEvent(this);
		examReviewer.setUser(user);

		this.getExamReviewers().add(examReviewer);
	}
	
	public void removeReviewerById(Integer reviewerId) {

		ExamReviewer deleteExamReviewer = null;

		for (ExamReviewer examReviewer : this.getExamReviewers()) {
			if (examReviewer.getUser().getId().equals(reviewerId)) {
				deleteExamReviewer = examReviewer;
			}
		}

		if (deleteExamReviewer != null) {
			this.getExamReviewers().remove(deleteExamReviewer);
		}
	}
	
	public boolean getInProcess() {
		Date currentDateTime = new Date();
		if (currentDateTime.compareTo(this.getEndDateTime()) <= 0
				&& currentDateTime.compareTo(this.getStartDateTime()) >= 0
				&& this.getMyExamCandidate().getSubmitDateTime() == null) {
			return true;
		} else {
			return false;
		}
	}
	
}
