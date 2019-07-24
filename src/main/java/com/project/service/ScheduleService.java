package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.CompanyDao;
import com.project.dao.ScheduleDao;
import com.project.dao.TaskDao;
import com.project.dao.TransactionDao;
import com.project.model.Company;
import com.project.model.InsertScheduleTask;
import com.project.model.Schedule;
import com.project.model.Task;
import com.project.model.enumerated.StatusSchedule;
import com.project.model.paggination.PagginationSchedule;

@Service("ScheduleService")
public class ScheduleService {

	@Autowired
	ScheduleDao scheduleDao;
	
	@Autowired
	TransactionDao transactionDao;

	@Autowired
	TaskDao taskDao;

	
	@Autowired
	CompanyDao companyDao;
	
	@Transactional
	public void insertScheduleTask(InsertScheduleTask scheduleTask) throws ErrorMessage {
		Company comp = companyDao.findByID(scheduleTask.getCompanyId());
		Schedule sch = new Schedule();
		sch.setScheduleId("uuid");
		sch.setScheduleCode(scheduleTask.getScheduleCode());
		sch.setCompany(comp);
		sch.setStatusSchedule(StatusSchedule.ACTIVE);
		scheduleDao.save(sch);
		System.out.println(sch.getScheduleCode());
		System.out.println(sch.getCompany().getCompanyId());
		transactionDao.flush();
		
		Schedule schedule = scheduleDao.findByBK(sch.getScheduleCode(),sch.getCompany().getCompanyId());
		System.out.println(schedule.getScheduleCode());
		for (Task t : scheduleTask.getTasks()) {
			System.out.println(schedule.getScheduleId());
			Task task = new Task();
			task.setTaskId("uuid");
			task.setTaskCode(t.getTaskCode());
			task.setSchedule(schedule);
			task.setStartDate(t.getStartDate());
			task.setFinishDate(t.getFinishDate());
			task.setTaskDescription(t.getTaskDescription());
			taskDao.save(task);
		}
//			System.out.println(task.getTaskCode());
//			System.out.println(task.getSchedule());
//			System.out.println(task.getStartDate());
//			System.out.println(task.getFinishDate());
//			System.out.println(task.getTaskDescription());
//			transactionDao.flush();
	}
	
	// =================CRUD=================
	@Transactional
	public void insertSchedule(Schedule schedule) throws ErrorMessage {
		if (scheduleDao.isIDExsist(schedule.getScheduleId())) {
			throw new ErrorMessage("Schedule ID  Already Exists");
		}
		if (scheduleDao.isBKExsist(schedule.getScheduleCode(),schedule.getCompany().getCompanyId())) {
			throw new ErrorMessage("Schedule Code Already Exists");
		}
		if (schedule.getCompany().getCompanyId().isEmpty()) {
			throw new ErrorMessage("Company Can't Be Empty");
		}
		scheduleDao.save(schedule);
		System.out.println("Schedule Successfully Added");
	}

	@Transactional
	public void updateSchedule(Schedule schedule) throws ErrorMessage {
		if (!scheduleDao.isIDExsist(schedule.getScheduleId())) {
			throw new ErrorMessage("Schedule ID Not Found");
		}
		if (!scheduleDao.isBKExsist(schedule.getScheduleCode(),schedule.getCompany().getCompanyId())) {
			throw new ErrorMessage("Schedule Code Not Found");
		}
		Schedule scheduleDB = scheduleDao.findByID(schedule.getScheduleId());
		if (!schedule.getScheduleCode().equals(scheduleDB.getScheduleCode())) {
			throw new ErrorMessage("The Schedule Code Cannot Be Changed");
		}
		if (schedule.getCompany().getCompanyId().isEmpty()) {
			throw new ErrorMessage("Company Can't Be Empty");
		}
		scheduleDao.save(schedule);
		System.out.println("Schedule Successfully Updated");
	}

	@Transactional
	public void deleteSchedule(String scheduleId) throws ErrorMessage {
		if (!scheduleDao.isIDExsist(scheduleId)) {
			throw new ErrorMessage("Schedule ID Not Found");
		}
		scheduleDao.delete(scheduleId);
		System.out.println("Schedule Successfully Deleted");
	}

	// ================FIND BY======================
	@Transactional
	public Schedule findScheduleByID(String scheduleId) {
		return scheduleDao.findByID(scheduleId);
	}

	@Transactional
	public Schedule findScheduleByCode(String scheduleCode, String companyId) {
		return scheduleDao.findByBK(scheduleCode, companyId);
	}
	
	@Transactional
	public List<Schedule> findByCompany(String companyId){
		return scheduleDao.findByCompany(companyId);
	}

//	@Transactional
//	public List<Schedule> findByFilter(String companyId, String status) {
//		return scheduleDao.findByFilter(companyId, status);
//	}

	@Transactional
	public List<Schedule> findAllSchedule() {
		return scheduleDao.getAll();
	}
	
	@Transactional
	public List<PagginationSchedule> findAllScheduleByPaggination(int page, int size) {
		return scheduleDao.getAllByPaggination(page, size);
	}
	
	
	@Transactional
	public List<Task> getTask(String ScheduleId) {
		return scheduleDao.getTask(ScheduleId);
	}
}
