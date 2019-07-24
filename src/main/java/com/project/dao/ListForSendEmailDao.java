package com.project.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.SendEmailPayroll;

@Repository("ListForSendEmailDo")
public class ListForSendEmailDao extends ParentDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<SendEmailPayroll> getListPayroll() {
		Calendar cal = Calendar.getInstance();
		String namaBulan[] = { "JANUARI", "FEBRUARI", "MARET", "APRIL", "MEI", "JUNI", "JULI", "AGUSTUS", "SEPTEMBER",
				"OKTOBER", "NOVEMBER", "DESEMBER" };
		int bulan = cal.get(Calendar.MONTH);
		String yearMonth = cal.get(Calendar.YEAR) + "-" + namaBulan[bulan];

		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT u.user_id, u.user_code, u.user_name, u.email, u.user_role, th.year_month, c.company_name, s.schedule_code, t.task_code, t.task_description, t.start_date, t.finish_date");
		sb.append(" FROM tbl_transaction_detail AS td ");
		sb.append(" INNER JOIN tbl_transaction_header AS th ");
		sb.append(" ON td.hdr_trx_id = th.hdr_trx_id");
		sb.append(" INNER JOIN tbl_task AS t ");
		sb.append(" ON td.task_id = t.task_id");
		sb.append(" INNER JOIN tbl_schedule AS s ");
		sb.append(" ON t.schedule_id = s.schedule_id");
		sb.append(" INNER JOIN tbl_company AS c ");
		sb.append(" ON s.company_id = c.company_id");
		sb.append(" INNER JOIN tbl_payroll_mapping AS pm ");
		sb.append(" ON c.company_id = pm.company_id");
		sb.append(" INNER JOIN tbl_user_account AS u ");
		sb.append(" ON pm.user_id = u.user_id");
		sb.append(" WHERE 1=1");
		sb.append(" AND td.status ='PENDING'");
		sb.append(" AND u.user_role ='PAYROLL_SPECIALLIST'");
		sb.append(" AND th.year_month= :yearMonth");
		sb.append(
				" GROUP BY u.user_id, u.user_code, u.user_name, u.email, u.user_role, th.year_month, c.company_name, s.schedule_code, t.task_code, t.task_description, t.start_date, t.finish_date");
		sb.append(" ORDER BY  u.user_code, u.user_role, c.company_name, s.schedule_code, t.task_code");

		List<Object[]> listObject = super.entityManager.createNativeQuery(sb.toString())
				.setParameter("yearMonth", yearMonth).getResultList();
		ArrayList<SendEmailPayroll> payrolls = new ArrayList<SendEmailPayroll>();

		for (Object[] obj : listObject) {
			SendEmailPayroll payroll = new SendEmailPayroll();
			payroll.setUserId(obj[0].toString());
			payroll.setUserCode(obj[1].toString());
			payroll.setUserName(obj[2].toString());
			payroll.setEmail(obj[3].toString());
			payroll.setUserRole(obj[4].toString());
			payroll.setYearMonth(obj[5].toString());
			payroll.setCompanyName(obj[6].toString());
			payroll.setScheduleCode(obj[7].toString());
			payroll.setTaskCode(obj[8].toString());
			payroll.setTaskDescription(obj[9].toString());
			payroll.setStartDate(((BigDecimal) obj[10]).intValue());
			payroll.setFinishDate(((BigDecimal) obj[11]).intValue());
			payrolls.add(payroll);
		}

		if (payrolls.size() > 0) {
			return payrolls;
		} else {
			return new ArrayList<SendEmailPayroll>();
		}

	}

}
