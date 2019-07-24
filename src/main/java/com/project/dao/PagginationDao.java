package com.project.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.paggination.PagginationCompany;
import com.project.model.paggination.PagginationSchedule;
import com.project.model.paggination.PagginationUserAccount;
import com.project.model.paggination.pagginationHome;

@Repository
public class PagginationDao extends ParentDao{

	@SuppressWarnings("unchecked")
	@Transactional
	public List<PagginationSchedule> getAllScheduleByPaggination(int page, int size) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT num, schedule_id, company_code, company_name, schedule_code, status_schedule");
		sb.append(" FROM (");
		sb.append(" SELECT ROW_NUMBER() OVER (ORDER BY c.company_code,c.company_name, s.schedule_code) AS num, s.schedule_id, c.company_code, c.company_name, s.schedule_code, s.status_schedule");
		sb.append(" FROM tbl_schedule AS s");
		sb.append(" INNER JOIN tbl_company AS c");
		sb.append(" ON s.company_id = c.company_id");
		sb.append(" WHERE s.status_schedule = 'ACTIVE'");
		sb.append(" GROUP BY s.schedule_id,  c.company_code, c.company_name, s.schedule_code, s.status_schedule");
		sb.append(" ORDER BY c.company_code, c.company_name, s.schedule_code ASC ) AS page_schedule");
		sb.append(" WHERE num > :page * :size");
		sb.append(" GROUP BY  num, schedule_id, company_code, company_name, schedule_code, status_schedule");
		sb.append(" ORDER BY  num, company_code, company_name, schedule_code ASC LIMIT :size");
		List<Object []> listObj = super.entityManager
				.createNativeQuery(sb.toString())
				.setParameter("page", page) 
				.setParameter("size", size)
				.getResultList();
		
		ArrayList<PagginationSchedule> listSchedule = new ArrayList<PagginationSchedule>();
		
		for (Object[] obj : listObj) {
			PagginationSchedule pageSchedule = new PagginationSchedule();
			pageSchedule.setNo(obj[0].hashCode());
			pageSchedule.setScheduleId(obj[1].toString());
			pageSchedule.setCompanyCode(obj[2].toString());
			pageSchedule.setCompanyName(obj[3].toString());
			pageSchedule.setScheduleCode(obj[4].toString());
			pageSchedule.setStatus(obj[5].toString());
			listSchedule.add(pageSchedule);
		}
		
		if(listSchedule.size()>0) {
			return listSchedule;
		}
		else {
			return new ArrayList<PagginationSchedule>();	
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PagginationCompany> getAllCompanyByPaggination(int page, int size){
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT num, company_id, company_code, company_name, company_logo");
		sb.append(" FROM (");
		sb.append(" SELECT ROW_NUMBER() OVER (ORDER BY c.company_code,c.company_name) AS num, c.company_id, c.company_code, c.company_name, c.company_logo");
		sb.append(" FROM tbl_company AS c");
		sb.append(" GROUP BY c.company_id, c.company_code, c.company_name, c.company_logo");
		sb.append(" ORDER BY c.company_code, c.company_name ASC ) AS page_company");
		sb.append(" WHERE num > :page * :size");
		sb.append(" GROUP BY num, company_id, company_code, company_name, company_logo");
		sb.append(" ORDER BY num, company_code, company_name ASC LIMIT :size");
		
		List<Object []> listObj = super.entityManager
				.createNativeQuery(sb.toString())
				.setParameter("page", page) 
				.setParameter("size", size)
				.getResultList();
		
		ArrayList<PagginationCompany> listCompanys = new ArrayList<PagginationCompany>();
		
		for (Object[] obj : listObj) {
			PagginationCompany pageCompany = new PagginationCompany();
			pageCompany.setNo(obj[0].hashCode());
			pageCompany.setCompanyId(obj[1].toString());
			pageCompany.setCompanyCode(obj[2].toString());
			pageCompany.setCompanyName(obj[3].toString());
			pageCompany.setCompanyLogo((byte[]) obj[4]);
			listCompanys.add(pageCompany);
		}
		
		if(listCompanys.size()>0) {
			return listCompanys;
		}
		else {
			return new ArrayList<PagginationCompany>();	
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PagginationUserAccount> getAllUserByPaggination(int page, int size){
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT num, user_id, user_code, user_name, email, user_role");
		sb.append(" FROM (");
		sb.append(" SELECT ROW_NUMBER() OVER (ORDER BY u.user_code) AS num, u.user_id, u.user_code, u.user_name, u.email, u.user_role");
		sb.append(" FROM tbl_user_account AS u");
		sb.append(" GROUP BY u.user_id, u.user_code, u.user_name, u.email, u.user_role");
		sb.append(" ORDER BY u.user_code, u.user_name ASC ) AS page_user");
		sb.append(" WHERE num > :page * :size");
		sb.append(" GROUP BY  num, user_id, user_code, user_name, email, user_role");
		sb.append(" ORDER BY  num, user_code ASC LIMIT :size");
		
		List<Object []> listObj = super.entityManager
				.createNativeQuery(sb.toString())
				.setParameter("page", page) 
				.setParameter("size", size)
				.getResultList();
		
		ArrayList<PagginationUserAccount> listUsers = new ArrayList<PagginationUserAccount>();
		
		for (Object[] obj : listObj) {
			PagginationUserAccount pageUser = new PagginationUserAccount();
			pageUser.setNo(obj[0].hashCode());
			pageUser.setUserId(obj[1].toString());
			pageUser.setUserCode(obj[2].toString());
			pageUser.setUserName(obj[3].toString());
			pageUser.setEmail(obj[4].toString());
			pageUser.setUserRole(obj[5].toString());
			listUsers.add(pageUser);
		}
		
		if(listUsers.size()>0) {
			return listUsers;
		}
		else {
			return new ArrayList<PagginationUserAccount>();	
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<pagginationHome> getAllHomeByPaggination(int page, int size){
		Calendar cal = Calendar.getInstance();
		String namaBulan[] = { "JANUARI", "FEBRUARI", "MARET", "APRIL", "MEI", "JUNI", "JULI", "AGUSTUS", "SEPTEMBER",
				"OKTOBER", "NOVEMBER", "DESEMBER" };
		int bulan = cal.get(Calendar.MONTH);
		String yearMonth = cal.get(Calendar.YEAR) + "-" + namaBulan[bulan];

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT num, hdr_trx_id , trx_code, year_month, company_name, schedule_code, status");
		sb.append(" FROM (");
		sb.append(" SELECT ROW_NUMBER() OVER (ORDER BY th.trx_code, th.year_month) AS num, th.hdr_trx_id , th.trx_code, th.year_month,c.company_name,s.schedule_code, th.status");
		sb.append(" FROM tbl_transaction_header as th");
		sb.append(" INNER JOIN tbl_transaction_detail AS td ");
		sb.append(" ON th.hdr_trx_id = td.hdr_trx_id ");
		sb.append(" INNER JOIN tbl_task AS t ");
		sb.append(" ON td.task_id = t.task_id");
		sb.append(" INNER JOIN tbl_schedule AS s ");
		sb.append(" ON t.schedule_id = s.schedule_id");
		sb.append(" INNER JOIN tbl_company AS c ");
		sb.append(" ON s.company_id = c.company_id");
		sb.append(" WHERE th.status = 'ON_PROGRESS'");
		sb.append(" AND th.year_month = :yearMonth");
		sb.append(" GROUP BY th.hdr_trx_id, th.trx_code, th.year_month, c.company_name, s.schedule_code, th.status ");
		sb.append(" ORDER BY th.trx_code ASC ) AS page_home");
		sb.append(" WHERE num > :page * :size");
		sb.append(" GROUP BY num, hdr_trx_id, trx_code, year_month, company_name, schedule_code, status ");
		sb.append(" ORDER BY num, trx_code, year_month, company_name, schedule_code ASC LIMIT :size");
		List<Object []> listObj = super.entityManager
				.createNativeQuery(sb.toString())
				.setParameter("yearMonth", yearMonth)
				.setParameter("page", page) 
				.setParameter("size", size)
				.getResultList();
		ArrayList<pagginationHome> listHomes = new ArrayList<pagginationHome>();
		
		for (Object[] obj : listObj) {
			pagginationHome pageHome = new pagginationHome();
			pageHome.setNo(obj[0].hashCode());
			pageHome.setHdrTrxId(obj[1].toString());
			pageHome.setTrxCode(obj[2].toString());
			pageHome.setYearMonth(obj[3].toString());
			pageHome.setCompanyName(obj[4].toString());
			pageHome.setScheduleCode(obj[5].toString());
			pageHome.setStatus(obj[6].toString());
			listHomes.add(pageHome);
		}
		
		if(listHomes.size()>0) {
			return listHomes;
		}
		else {
			return new ArrayList<pagginationHome>();	
		}
		
	}


	
}
