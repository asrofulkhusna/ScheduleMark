package com.project.model.report;

import java.util.List;


public class ReportCompany {

	private String companyCode;
	private String CompanyName;
	private List<ReportTask> Tasks;
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public List<ReportTask> getTasks() {
		return Tasks;
	}
	public void setTasks(List<ReportTask> tasks) {
		Tasks = tasks;
	}
}
