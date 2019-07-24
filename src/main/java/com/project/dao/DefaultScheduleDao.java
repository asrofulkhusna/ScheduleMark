package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.model.Company;
import com.project.model.DefaultSchedule;
import com.project.model.Schedule;

@Repository("DefaultScheduleDao")
public class DefaultScheduleDao extends ParentDao {

	@Autowired
	CompanyDao compDao;
	
	@Autowired
	ScheduleDao schDao;
	
	
	@Transactional
	public void save(DefaultSchedule defaultSchedule) {
		super.entityManager.merge(defaultSchedule);
	}

	@Transactional
	public void delete(String defaultScheduleId) {
		DefaultSchedule schedule = findByID(defaultScheduleId);
		super.entityManager.remove(schedule);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public DefaultSchedule findByID(String defaultScheduleId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM DefaultSchedule ");
		sb.append(" WHERE defaultScheduleId = :defaultScheduleId");

		List<DefaultSchedule> schedule = super.entityManager.createQuery(sb.toString())
				.setParameter("defaultScheduleId", defaultScheduleId).getResultList();
		if (schedule.size() > 0) {
			return (DefaultSchedule) schedule.get(0);
		} else {
			return new DefaultSchedule();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public DefaultSchedule findByBK(String companyId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM DefaultSchedule ");
		sb.append(" WHERE company.companyId = :companyId");

		List<DefaultSchedule> schedule = super.entityManager.createQuery(sb.toString())
				.setParameter("companyId", companyId).getResultList();
		if (schedule.size() > 0) {
			return (DefaultSchedule) schedule.get(0);
		} else {
			return new DefaultSchedule();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<DefaultSchedule> getAll() {

		List<DefaultSchedule> schedules = super.entityManager
				.createQuery("FROM DefaultSchedule")
				.getResultList();

		if (schedules.size() > 0) {
			return schedules;
		} else {
			return new ArrayList<DefaultSchedule>();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<DefaultSchedule> getAllDefaultSchedule(){
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ds.default_schedule_id, ds.company_id, ds.schedule_id");
		sb.append(" FROM tbl_default_schedule AS ds ");
		sb.append(" WHERE ds.schedule_id IS NOT NULL ");
		
		List<Object[]> listObj = super.entityManager
				.createNativeQuery(sb.toString())
				.getResultList();
		
		ArrayList<DefaultSchedule> listDefaultSchedule = new ArrayList<DefaultSchedule>();
		for (Object[] obj : listObj) {
			DefaultSchedule dflt = new DefaultSchedule();
			Company comp = compDao.findByID(obj[1].toString());
			Schedule sch = schDao.findByID(obj[2].toString());
			dflt.setDefaultScheduleId(obj[0].toString());
			dflt.setCompany(comp);
			dflt.setSchedule(sch);
			listDefaultSchedule.add(dflt);			
		}
		if(listDefaultSchedule.size()>0) {
			return listDefaultSchedule;
		}else {
			return new ArrayList<DefaultSchedule>();	
		}		
	}
	

	@Transactional
	public boolean isIDExsist(String defaultScheduleId) {
		DefaultSchedule schedule = findByID(defaultScheduleId);
		if (schedule.getDefaultScheduleId()==null) {
			return false;
		} else {
			return true;
		}

	}

	@Transactional
	public boolean isBKExsist(String companyId) {
		DefaultSchedule schedule = findByBK(companyId);
		if (schedule.getCompany() == null) {
			return false;
		} else {
			return true;
		}
	}
}
