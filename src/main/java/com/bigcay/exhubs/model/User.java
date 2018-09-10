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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "userid")
	private String userId;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id")
	private Group group;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	private Department department;

	@Column(name = "active_flg")
	private Boolean activeFlag;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "update_datetime")
	private Date updateDateTime;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
	private Set<ExamCandidate> examCandidates;

	@ManyToMany(mappedBy = "candidateUsers", fetch = FetchType.LAZY)
	private Set<ExamEvent> candidateExamEvents = new HashSet<ExamEvent>();
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
	private Set<ExamReviewer> examReviewers;

	@ManyToMany(mappedBy = "reviewerUsers", fetch = FetchType.LAZY)
	private Set<ExamEvent> reviewerExamEvents = new HashSet<ExamEvent>();
	
	public Set<ExamReviewer> getExamReviewers() {
		return examReviewers;
	}

	public void setExamReviewers(Set<ExamReviewer> examReviewers) {
		this.examReviewers = examReviewers;
	}

	public Set<ExamEvent> getReviewerExamEvents() {
		return reviewerExamEvents;
	}

	public void setReviewerExamEvents(Set<ExamEvent> reviewerExamEvents) {
		this.reviewerExamEvents = reviewerExamEvents;
	}

	public Set<ExamCandidate> getExamCandidates() {
		return examCandidates;
	}

	public void setExamCandidates(Set<ExamCandidate> examCandidates) {
		this.examCandidates = examCandidates;
	}

	public Set<ExamEvent> getCandidateExamEvents() {
		return candidateExamEvents;
	}

	public void setCandidateExamEvents(Set<ExamEvent> candidateExamEvents) {
		this.candidateExamEvents = candidateExamEvents;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	// used by user report
	public String getGroupDesc() {
		return this.getGroup() != null ? this.getGroup().getDescription() : null;
	}
	
	// used by user report
	public String getDepartmentName() {
		return this.getDepartment() != null ? this.getDepartment().getName() : null;
	}
	
	// used by user report
	public String getStatus() {
		return (this.getActiveFlag() == null || this.getActiveFlag() == false) ? "N" : "Y";
	}

}
