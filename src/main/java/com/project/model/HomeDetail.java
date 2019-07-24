package com.project.model;

import java.util.Date;

public class HomeDetail {

	private String dtlTRxId;
	private String trxCode;
	private String scheduleCode;
	private String taskCode;
	private String taskDescription;
	private int startDate;
	private int finishDate;
	private Date dateRealized;
	private String notes;
	private String status;
	public String getDtlTRxId() {
		return dtlTRxId;
	}
	public void setDtlTRxId(String dtlTRxId) {
		this.dtlTRxId = dtlTRxId;
	}
	public String getTrxCode() {
		return trxCode;
	}
	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}
	public String getScheduleCode() {
		return scheduleCode;
	}
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
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
	public Date getDateRealized() {
		return dateRealized;
	}
	public void setDateRealized(Date dateRealized) {
		this.dateRealized = dateRealized;
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
