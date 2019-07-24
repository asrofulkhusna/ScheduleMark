package com.project.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.model.Notifikasi;

@Repository("NotifikasiDao")
public class NotifikasiDao extends ParentDao {

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Notifikasi> getNotifikasi(String userCode) {
		Calendar cal = Calendar.getInstance();
		int tanggal = cal.get(Calendar.DATE);
		System.out.println(tanggal);
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT u.user_name, c.company_name, t.task_code, t.task_description, t.finish_date");
		sb.append(" FROM tbl_transaction_detail AS td");
		sb.append(" INNER JOIN tbl_task AS t");
		sb.append(" ON td.task_id = t.task_id");
		sb.append(" INNER JOIN tbl_schedule AS s");
		sb.append(" ON t.schedule_id = s.schedule_id");
		sb.append(" INNER JOIN tbl_company AS c");
		sb.append(" ON s.company_id = c.company_id");
		sb.append(" INNER JOIN tbl_payroll_mapping AS pm");
		sb.append(" ON c.company_id = pm.company_id");
		sb.append(" INNER JOIN tbl_user_account AS u");
		sb.append(" ON pm.user_id = u.user_id");
		sb.append(" WHERE u.user_code = :userCode");
		sb.append(" AND td.status ='PENDING'");
		sb.append(" AND t.start_date < :tanggal");
		sb.append(" GROUP BY u.user_name, c.company_name, t.task_code, t.task_description, t.finish_date");
		sb.append(" ORDER BY u.user_name, c.company_name, t.task_code");

		List<Object[]> listObj = super.entityManager.createNativeQuery(sb.toString()).setParameter("userCode", userCode)
				.setParameter("tanggal", tanggal).getResultList();
		ArrayList<Notifikasi> listNotif = new ArrayList<Notifikasi>();
		for (Object[] obj : listObj) {
			Notifikasi notif = new Notifikasi();
			notif.setUserName(obj[0].toString());
			notif.setCompanyName(obj[1].toString());
			notif.setTaskCode(obj[2].toString());
			notif.setTaskDescription(obj[3].toString());
			notif.setFinishDate(((BigDecimal) obj[4]).intValue());
			listNotif.add(notif);
		}
		if (listNotif.size() > 0) {
			return listNotif;
		}
		return new ArrayList<Notifikasi>();

	}
}
