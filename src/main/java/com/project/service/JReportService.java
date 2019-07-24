package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.LinovScheduleMark.SimpleReportExporter;
import com.project.dao.JReportDao;

@Service("JReportService")
public class JReportService {

	@Autowired
	private JReportDao jReportDao;
	
	public String getNotifPayroll(String userCode) {
		SimpleReportExporter simpleExporter = new SimpleReportExporter();
		try {
			simpleExporter.setJasperPrint(jReportDao.getNotifPayroll(userCode).getJasperPrint());
			simpleExporter.exportToHtml("notif_payroll.html");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error";
		}
		
		return "OK";
	}
	
	public String getReportByPayroll() {
		SimpleReportExporter simpleExporterPayroll = new SimpleReportExporter();
		try {
			simpleExporterPayroll.setJasperPrint(jReportDao.getReportByPayroll().getJasperPrint());
			simpleExporterPayroll.exportToPdf("report_payroll.pdf","DigitalentSolution");
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error";
		}
		
		return "OK";
	}
	
	public String getReportByCompany() {

		SimpleReportExporter simpleExporterCompany = new SimpleReportExporter();
		try {
			simpleExporterCompany.setJasperPrint(jReportDao.getReportByCompany().getJasperPrint());
			simpleExporterCompany.exportToPdf("report_company.pdf","DigitalentSolution");
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error";
		}
		
		return "OK";
	}
	
	public String getReportByPeriod() {
		SimpleReportExporter simpleExporterPeriod = new SimpleReportExporter();
		try {
			simpleExporterPeriod.setJasperPrint(jReportDao.getReportByPeriod().getJasperPrint());
			simpleExporterPeriod.exportToHtml("coba.html");
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error";
		}
		
		return "OK";
	}
}
