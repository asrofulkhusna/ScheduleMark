package com.project.model.report;

import java.util.List;


public class ReportByMonth {

	private String yearMonth;
	private List<ReportPayrollSpecialist> payrolls;
	
	
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public List<ReportPayrollSpecialist> getPayrolls() {
		return payrolls;
	}
	public void setPayrolls(List<ReportPayrollSpecialist> payrolls) {
		this.payrolls = payrolls;
	}
}
