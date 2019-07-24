package com.project.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.report.ReportByMonth;
import com.project.model.report.ReportCompany;
import com.project.model.report.ReportPayrollSpecialist;
import com.project.model.report.ReportTask;

@Repository
public class ReportDao extends ParentDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ReportByMonth> getReport(String yearMonth, String userCode, String companyCode) {
		StringBuilder getYearMonth = new StringBuilder();
		getYearMonth.append(" SELECT th.year_month, count(*)");
		getYearMonth.append(" FROM tbl_transaction_header AS th ");
		getYearMonth.append(" WHERE 1=1");
		if (yearMonth.trim().isEmpty() == false) {
			getYearMonth.append(" AND th.year_month = :yearMonth");
		}
		getYearMonth.append(" GROUP BY th.year_month");
		getYearMonth.append(" ORDER BY th.year_month ASC");

		Query query = super.entityManager.createNativeQuery(getYearMonth.toString());
		if (yearMonth.trim().isEmpty() == false) {
			query = query.setParameter("yearMonth", yearMonth);
		}

		ArrayList<ReportByMonth> reports = new ArrayList<ReportByMonth>();

		List<Object[]> byMonth = query.getResultList();
		for (Object[] obj : byMonth) {
			ReportByMonth month = new ReportByMonth();
			month.setYearMonth(obj[0].toString());
			month.setPayrolls(getPayrollSpesialists(month.getYearMonth(), userCode, companyCode));
			reports.add(month);

		}

		return reports;

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ReportPayrollSpecialist> getPayrollSpesialists(String yearMonth, String userCode, String companyCode) {
		StringBuilder getPayroll = new StringBuilder();
		getPayroll.append(" SELECT th.year_month, u.user_code,u.user_name,u.email");
		getPayroll.append(" FROM tbl_transaction_header AS th ");
		getPayroll.append(" INNER JOIN tbl_transaction_detail AS td ");
		getPayroll.append(" ON th.hdr_trx_id = td.hdr_trx_id");
		getPayroll.append(" INNER JOIN tbl_task AS t ");
		getPayroll.append(" ON td.task_id = t.task_id");
		getPayroll.append(" INNER JOIN tbl_schedule AS s ");
		getPayroll.append(" ON t.schedule_id = s.schedule_id");
		getPayroll.append(" INNER JOIN tbl_company AS c ");
		getPayroll.append(" ON s.company_id = c.company_id");
		getPayroll.append(" INNER JOIN tbl_payroll_mapping AS pm ");
		getPayroll.append(" ON c.company_id = pm.company_id");
		getPayroll.append(" INNER JOIN tbl_user_account AS u ");
		getPayroll.append(" ON pm.user_id = u.user_id");
		getPayroll.append(" WHERE 1=1");
		if (yearMonth.trim().isEmpty() == false) {
			getPayroll.append(" AND th.year_month = :yearMonth");
		}
		if (userCode.trim().isEmpty() == false) {
			getPayroll.append(" AND u.user_code = :userCode");
		}
		getPayroll.append(" GROUP BY th.year_month, u.user_code, u.user_name, u.email");
		getPayroll.append(" ORDER BY th.year_month, u.user_code ASC");

		ArrayList<ReportPayrollSpecialist> payrolls = new ArrayList<ReportPayrollSpecialist>();

		Query query = super.entityManager.createNativeQuery(getPayroll.toString());
		if (yearMonth.trim().isEmpty() == false) {
			query = query.setParameter("yearMonth", yearMonth);
		}
		if (userCode.trim().isEmpty() == false) {
			query = query.setParameter("userCode", userCode);
		}

		List<Object[]> byPayroll = query.getResultList();

		for (Object[] obj1 : byPayroll) {
			ReportPayrollSpecialist reportPS = new ReportPayrollSpecialist();
			reportPS.setUserCode(obj1[1].toString());
			reportPS.setUserName(obj1[2].toString());
			reportPS.setUserEmail(obj1[3].toString());
			reportPS.setCompanys(getCompanys(yearMonth, reportPS.getUserCode(), companyCode));
			payrolls.add(reportPS);
		}

		return payrolls;

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ReportCompany> getCompanys(String yearMonth, String userCode, String companyCode) {
		StringBuilder getCompany = new StringBuilder();
		getCompany.append(" SELECT th.year_month, u.user_code, c.company_code , c.company_name");
		getCompany.append(" FROM tbl_transaction_header AS th ");
		getCompany.append(" INNER JOIN tbl_transaction_detail AS td ");
		getCompany.append(" ON th.hdr_trx_id = td.hdr_trx_id");
		getCompany.append(" INNER JOIN tbl_task AS t ");
		getCompany.append(" ON td.task_id = t.task_id");
		getCompany.append(" INNER JOIN tbl_schedule AS s ");
		getCompany.append(" ON t.schedule_id = s.schedule_id");
		getCompany.append(" INNER JOIN tbl_company AS c ");
		getCompany.append(" ON s.company_id = c.company_id");
		getCompany.append(" INNER JOIN tbl_payroll_mapping AS pm ");
		getCompany.append(" ON c.company_id = pm.company_id");
		getCompany.append(" INNER JOIN tbl_user_account AS u ");
		getCompany.append(" ON pm.user_id = u.user_id");
		getCompany.append(" WHERE 1=1");
		if (yearMonth.trim().isEmpty() == false) {
			getCompany.append(" AND th.year_month = :yearMonth");
		}
		if (userCode.trim().isEmpty() == false) {
			getCompany.append(" AND u.user_code = :userCode");
		}
		if (companyCode.trim().isEmpty() == false) {
			getCompany.append(" AND c.company_code = :companyCode");
		}
		getCompany.append(" GROUP BY th.year_month, u.user_code, c.company_code , c.company_name");
		getCompany.append(" ORDER BY th.year_month, u.user_code, c.company_code ASC");

		ArrayList<ReportCompany> companys = new ArrayList<ReportCompany>();

		Query query = super.entityManager.createNativeQuery(getCompany.toString());
		if (yearMonth.trim().isEmpty() == false) {
			query = query.setParameter("yearMonth", yearMonth);
		}
		if (userCode.trim().isEmpty() == false) {
			query = query.setParameter("userCode", userCode);
		}
		if (companyCode.trim().isEmpty() == false) {
			query = query.setParameter("companyCode", companyCode);
		}

		List<Object[]> byCompany = query.getResultList();
		ReportCompany comp = new ReportCompany();
		for (Object[] obj2 : byCompany) {
			comp = new ReportCompany();
			comp.setCompanyCode(obj2[2].toString());
			comp.setCompanyName(obj2[3].toString());
			comp.setTasks(getTasks(yearMonth, userCode, comp.getCompanyCode()));
			companys.add(comp);
		}

		return companys;

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ReportTask> getTasks(String yearMonth, String userCode, String companyCode) {
		StringBuilder getTask = new StringBuilder();
		getTask.append(
				" SELECT th.year_month, u.user_code ,c.company_code, t.task_code, t.task_description, t.start_date, t.finish_date, td.date_realization, td.notes, td.status");
		getTask.append(" FROM tbl_transaction_header AS th ");
		getTask.append(" INNER JOIN tbl_transaction_detail AS td ");
		getTask.append(" ON th.hdr_trx_id = td.hdr_trx_id");
		getTask.append(" INNER JOIN tbl_task AS t ");
		getTask.append(" ON td.task_id = t.task_id");
		getTask.append(" INNER JOIN tbl_schedule AS s ");
		getTask.append(" ON t.schedule_id = s.schedule_id");
		getTask.append(" INNER JOIN tbl_company AS c ");
		getTask.append(" ON s.company_id = c.company_id");
		getTask.append(" INNER JOIN tbl_payroll_mapping AS pm ");
		getTask.append(" ON c.company_id = pm.company_id");
		getTask.append(" INNER JOIN tbl_user_account AS u ");
		getTask.append(" ON pm.user_id = u.user_id");
		getTask.append(" WHERE 1=1");
		if (yearMonth.trim().isEmpty() == false) {
			getTask.append(" AND th.year_month = :yearMonth");
		}
		if (userCode.trim().isEmpty() == false) {
			getTask.append(" AND u.user_code = :userCode");
		}
		if (companyCode.trim().isEmpty() == false) {
			getTask.append(" AND c.company_code = :companyCode");
		}
		getTask.append(
				" GROUP BY th.year_month, u.user_code, c.company_code, t.task_code, t.task_description, t.start_date, t.finish_date, td.date_realization, td.notes, td.status");
		getTask.append(" ORDER BY th.year_month, u.user_code, c.company_code, t.task_code ASC");

		ArrayList<ReportTask> tasks = new ArrayList<ReportTask>();

		Query query = super.entityManager.createNativeQuery(getTask.toString());

		if (yearMonth.trim().isEmpty() == false) {
			query = query.setParameter("yearMonth", yearMonth);
		}
		if (userCode.trim().isEmpty() == false) {
			query = query.setParameter("userCode", userCode);
		}
		if (companyCode.trim().isEmpty() == false) {
			query = query.setParameter("companyCode", companyCode);
		}

		List<Object[]> byTask = query.getResultList();
		ReportTask rTask = new ReportTask();
		for (Object[] obj3 : byTask) {
			rTask = new ReportTask();
			rTask.setTaskCode(obj3[3].toString());
			rTask.setDescriptionTask(obj3[4].toString());
			rTask.setStartDate(Integer.parseInt(obj3[5].toString()));
			rTask.setFinishDate(Integer.parseInt(obj3[6].toString()));
			rTask.setDateRealization((Date) obj3[7]);
			if (obj3[8] != null) {
				rTask.setNotes(obj3[8].toString());
			}
			rTask.setStatus(obj3[9].toString());
			tasks.add(rTask);
		}

		return tasks;

	}


}
