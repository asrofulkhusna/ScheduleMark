package com.project.model.report;

import java.util.Date;


public class ReportTask {

	private String taskCode;
	private String descriptionTask;
	private int startDate;
	private int finishDate;
	private Date dateRealization;
	private String notes;
	private String status;
	
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getDescriptionTask() {
		return descriptionTask;
	}
	public void setDescriptionTask(String descriptionTask) {
		this.descriptionTask = descriptionTask;
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
	public Date getDateRealization() {
		return dateRealization;
	}
	public void setDateRealization(Date dateRealization) {
		this.dateRealization = dateRealization;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
