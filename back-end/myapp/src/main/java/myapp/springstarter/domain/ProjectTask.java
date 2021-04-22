package myapp.springstarter.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GenerationType;

@Entity
public class ProjectTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable = false, unique = true)
	private String projectSequence; // we want something like projectIdentifier + projectSequence, ex: ID01-1
	@NotBlank(message = "Please include a project summary")
	private String summary;
	private String acceptanceCriteria;
	private String status;
	private Integer priority;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date dueDate;
	//ManyToOne with Backlog
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="backlog_id", updatable = false, nullable = false)
	@JsonIgnore
	private Backlog backlog;
	
	@Column(updatable = false)
	private String projectIdentifier;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date create_at;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date update_at;
	
	
	public ProjectTask() {		
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getProjectSequence() {
		return projectSequence;
	}


	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}


	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getPriority() {
		return priority;
	}


	public void setPriority(Integer priority) {
		this.priority = priority;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public String getProjectIdentifier() {
		return projectIdentifier;
	}


	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}


	public Date getCreate_at() {
		return create_at;
	}


	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}


	public Date getUpdate_at() {
		return update_at;
	}


	public void setUpdate_at(Date update_at) {
		this.update_at = update_at;
	}
	
	
	


	public Backlog getBacklog() {
		return backlog;
	}


	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}


	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", projectIdentifier=" + projectIdentifier + ", create_at=" + create_at
				+ ", update_at=" + update_at + "]";
	}


	@PrePersist
	protected void onCreate() {
		this.create_at = new Date();
		
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.update_at = new Date();
	}
	
}
