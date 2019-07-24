package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tbl_task", uniqueConstraints=@UniqueConstraint(columnNames= {"task_code","schedule_id"}))
public class Task {

	@Id
	@Column(name = "task_id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String taskId;
	
	@Column(name="task_code")
	private String taskCode;
	
	@OneToOne
	@JoinColumn(name="schedule_id",referencedColumnName="schedule_id")
	private Schedule schedule;
	
	@Column(name="start_date")
	private int startDate;
	
	@Column(name="finish_date")
	private int finishDate;
	
	@Column(name="task_description")
	private String taskDescription;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}


	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}



	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public int getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(int finishDate) {
		this.finishDate = finishDate;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	
}
