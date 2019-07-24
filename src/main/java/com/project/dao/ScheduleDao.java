package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.Schedule;
import com.project.model.Task;
import com.project.model.enumerated.StatusSchedule;
import com.project.model.paggination.PagginationSchedule;

@Repository("ScheduleDao")
public class ScheduleDao extends ParentDao{

	@Transactional
	public void save(Schedule schedule) {
		super.entityManager.merge(schedule);
	}
	
	@Transactional
	public void delete(String scheduleId) {
		Schedule schedule = findByID(scheduleId);
		super.entityManager.remove(schedule);
	}
	
	@SuppressWarnings( "unchecked")
	@Transactional
	public Schedule findByID(String scheduleId) {
		StringBuilder sb = new StringBuilder();
//		sb.append(" SELECT s.schedule_id, s.schedule_code, c.company_code, s.status");
//		sb.append(" FROM tbl_schedule AS s");
//		sb.append(" INNER JOIN tbl_company AS c");
//		sb.append(" on s.company_id = c.company_id");
		sb.append("FROM Schedule");
		sb.append(" WHERE schedule_id = :scheduleId");
		
		List<Schedule> schedule = super.entityManager
				.createQuery(sb.toString())
				.setParameter("scheduleId", scheduleId)
				.getResultList();
		if(schedule.size()>0) {
			return (Schedule) schedule.get(0);
		}
		else {
			Schedule sch = new Schedule();
			sch.setStatusSchedule(StatusSchedule.ACTIVE);
			return sch;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Schedule findByBK(String scheduleCode, String companyId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Schedule ");
		sb.append(" WHERE scheduleCode = :scheduleCode");
		sb.append(" AND company.companyId = :companyId");
		
		List<Schedule> schedule = super.entityManager
				.createQuery(sb.toString())
				.setParameter("scheduleCode", scheduleCode)
				.setParameter("companyId", companyId)
				.getResultList();
		if(schedule.size()>0) {
			return (Schedule) schedule.get(0);
		}
		else {
			return new Schedule();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Schedule> findByCompany(String companyId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Schedule ");
		sb.append(" WHERE company.companyId = :companyId");
		
		List<Schedule> schedules = super.entityManager
				.createQuery(sb.toString())
				.setParameter("companyId", companyId)
				.getResultList();
		if(schedules.size()>0) {
			return schedules;
		}
		else {
			return new ArrayList<Schedule>();
		}
	}
	
//	@SuppressWarnings("unchecked")
//	@Transactional
//	public List<Schedule> findByStatus(String status) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("FROM Schedule ");
//		sb.append(" WHERE status = :status");
//		
//		List<Schedule> schedules = super.entityManager
//				.createQuery(sb.toString())
//				.setParameter("status", status)
//				.getResultList();
//		if(schedules.size()>0) {
//			return schedules;
//		}
//		else {
//			return new ArrayList<Schedule>();
//		}
//	}
	
//	@SuppressWarnings("unchecked")
//	@Transactional
//	public List<Schedule> findByFilter(String companyId, String status) {
//		StringBuilder hql = new StringBuilder();
//		hql.append("FROM Schedule Where 1=1");
//		if (!companyId.trim().isEmpty())
//			hql.append(" AND company.companyId = :companyId");
//		if (!status.isEmpty())
//			hql.append(" AND status = :status");
//		
//		Query query = super.entityManager
//                .createQuery(hql.toString());
//		
//		if (!companyId.trim().isEmpty())
//			query.setParameter("companyId", companyId);
//		if (!status.isEmpty())
//			query.setParameter("status", status);
//		
//		List<Schedule> schedules = query.getResultList();
//		
//		if(schedules.size()>0) {
//			return schedules;
//		}
//		else {
//			return new ArrayList<Schedule>();
//		}
//	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Task> getTask(String scheduleId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Task ");
		sb.append(" WHERE schedule.scheduleId = : scheduleId");
		
		List<Task> task =  super.entityManager
				.createQuery(sb.toString())
				.setParameter("scheduleId", scheduleId)
				.getResultList();
		
		if(task.size()>0) {
			return task;
		}
		else {
			return new ArrayList<Task>();
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Schedule> getAll() {
		
		List <Schedule> schedules = super.entityManager.
				createQuery("FROM Schedule")
				.getResultList();
		
		if(schedules.size()>0) {
			return schedules;
		}
		else {
			return new ArrayList<Schedule>();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PagginationSchedule> getAllByPaggination(int page, int size) {
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
	
	@Transactional
	public boolean isIDExsist(String scheduleID) {
		Schedule schedule = findByID(scheduleID);
		if(schedule.getScheduleId() == null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	@Transactional
	public boolean isBKExsist(String scheduleCode, String companyId) {
		Schedule schedule = findByBK(scheduleCode, companyId);
		if(schedule.getScheduleCode()== null) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
