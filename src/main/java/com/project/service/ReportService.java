package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.ReportDao;
import com.project.model.report.ReportByMonth;
import com.project.model.report.ReportCompany;
import com.project.model.report.ReportPayrollSpecialist;
import com.project.model.report.ReportTask;

@Service("ReportService")
public class ReportService {

	@Autowired
	ReportDao reportDao;
	
	@Transactional
	public List<ReportByMonth> getReport(String yearMonth, String userCode, String companyCode) {
		return reportDao.getReport(yearMonth, userCode, companyCode);
	}
	
	@Transactional
	public List<ReportPayrollSpecialist> getPayrollSepesialist(String yearMonth, String userCode, String companyCode) {
		return reportDao.getPayrollSpesialists(yearMonth, userCode, companyCode);
	}
	
	@Transactional
	public List<ReportCompany> getCompanys(String yearMonth, String userCode, String companyCode) {
		return reportDao.getCompanys(yearMonth, userCode, companyCode);
	}
	
	@Transactional
	public List<ReportTask> getTaks(String yearMonth, String userCode, String companyCode) {
		return reportDao.getTasks(yearMonth, userCode, companyCode);
	}
	
//	@Transactional
//	public List<ReportPayrollSpecialist> listPS(String yearMonth) {
//		return reportDao.listPayrollSpesialists(yearMonth);
//	}
//	
//	@Transactional
//	public List<ReportCompany> listCompany(String yearMonth, String userCode) {
//		return reportDao.listCompanys(yearMonth, userCode);
//	}
//	
//	@Transactional
//	public List<ReportTask> listTask(String yearMonth, String userCode, String companyCode) {
//		return reportDao.listTasks(yearMonth, userCode, companyCode);
//	}

}
