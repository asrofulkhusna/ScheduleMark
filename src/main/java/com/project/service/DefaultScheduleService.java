package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.DefaultScheduleDao;
import com.project.model.DefaultSchedule;

@Service("DefaultScheduleService")
public class DefaultScheduleService {

	@Autowired
	DefaultScheduleDao defaultScheduleDao;

	// =================CRUD=================
	@Transactional
	public void insertDefaultSchedule(DefaultSchedule schedule) throws ErrorMessage {
		if (defaultScheduleDao.isIDExsist(schedule.getDefaultScheduleId())) {
			throw new ErrorMessage("Default Schedule ID  Already Exists");
		}
		if (defaultScheduleDao.isBKExsist(schedule.getCompany().getCompanyId())) {
			throw new ErrorMessage("Company Already Exists");
		}
		
		defaultScheduleDao.save(schedule);

		System.out.println("Default Schedule Successfully Added");
	}

	@Transactional
	public void updateDefaultSchedule(DefaultSchedule schedule) throws ErrorMessage {
		if (!defaultScheduleDao.isIDExsist(schedule.getDefaultScheduleId())) {
			throw new ErrorMessage("Default Schedule ID Not Found");
		}
		if (!defaultScheduleDao.isBKExsist(schedule.getCompany().getCompanyId())) {
			throw new ErrorMessage("Company Not Found");
		}
		DefaultSchedule defaultScheduleDB = defaultScheduleDao.findByID(schedule.getDefaultScheduleId());
		if (!schedule.getCompany().getCompanyId().equals(defaultScheduleDB.getCompany().getCompanyId())) {
			throw new ErrorMessage("The Company Cannot Be Changed");
		}
		if (schedule.getSchedule().getScheduleId().isEmpty()) {
			throw new ErrorMessage("Schedule Can't Be Empty");
		}
		defaultScheduleDao.save(schedule);
		System.out.println("Default Schedule Successfully Updated");
	}

	@Transactional
	public void updateDefaultScheduleByPatch(String defaultScheduleID, DefaultSchedule defaultSchedule) throws ErrorMessage {
		DefaultSchedule sch = findScheduleByID(defaultScheduleID);
		sch.setSchedule(defaultSchedule.getSchedule());
		defaultScheduleDao.save(sch);
	}

	@Transactional
	public void deleteDefaultSchedule(String defaultScheduleID) throws ErrorMessage {
		if (!defaultScheduleDao.isIDExsist(defaultScheduleID)) {
			throw new ErrorMessage("Default Schedule ID Not Found");
		}
		defaultScheduleDao.delete(defaultScheduleID);
		System.out.println("Default Schedule Successfully Deleted");
	}

	// ================FIND BY======================
	@Transactional
	public DefaultSchedule findScheduleByID(String defaultScheduleID) {
		return defaultScheduleDao.findByID(defaultScheduleID);
	}

	@Transactional
	public DefaultSchedule findScheduleByCode(String companyId) {
		return defaultScheduleDao.findByBK(companyId);
	}

	@Transactional
	public List<DefaultSchedule> findAllDefaultSchedule() {
		return defaultScheduleDao.getAll();
	}
	
	@Transactional
	public List<DefaultSchedule> findAllDefaultScheduleNative() {
		return defaultScheduleDao.getAllDefaultSchedule();
	}

}
