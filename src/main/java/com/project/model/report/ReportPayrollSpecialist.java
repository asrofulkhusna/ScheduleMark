package com.project.model.report;

import java.util.List;


public class ReportPayrollSpecialist {

	private String userCode;
	private String userName;
	private String userEmail;
	private List<ReportCompany> companys;
	
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<ReportCompany> getCompanys() {
		return companys;
	}
	public void setCompanys(List<ReportCompany> companys) {
		this.companys = companys;
	}
	
}
