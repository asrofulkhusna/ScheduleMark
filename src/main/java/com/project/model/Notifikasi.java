package com.project.model;

public class Notifikasi {

	private String userName;
	private String companyName;
	private String taskCode;
	private String taskDescription;
	private int finishDate;

	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(int finishDate) {
		this.finishDate = finishDate;
	}
	
	
}
