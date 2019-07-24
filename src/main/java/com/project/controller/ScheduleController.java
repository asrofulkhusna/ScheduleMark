package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.TransactionDao;
import com.project.model.Company;
import com.project.model.InsertScheduleTask;
import com.project.model.Schedule;
import com.project.model.Task;
import com.project.model.enumerated.StatusSchedule;
import com.project.service.CompanyService;
import com.project.service.ScheduleService;
import com.project.service.TaskService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	TaskService taskService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	TransactionDao transactionDao;
	
	@PostMapping("/add/")
	public ResponseEntity<?> insertScheduleTask(@RequestBody InsertScheduleTask scheduleTask) throws ErrorMessage{
		try {
			Company comp = companyService.findCompanyByID(scheduleTask.getCompanyId());
			Schedule sch = new Schedule();
			sch.setScheduleId("uuid");
			sch.setScheduleCode(scheduleTask.getScheduleCode());
			sch.setCompany(comp);
			sch.setStatusSchedule(StatusSchedule.ACTIVE);
			System.out.println(sch.getCompany().getCompanyId());
			scheduleService.insertSchedule(sch);
			System.out.println(sch.getScheduleCode());
			System.out.println(sch.getCompany().getCompanyId());
			transactionDao.flush();
			
			Schedule schedule = scheduleService.findScheduleByCode(sch.getScheduleCode(),sch.getCompany().getCompanyId());
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
				taskService.insertTask(task);
			}

		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Schedule & Task Successfully Added");

	}
	
	@PostMapping("/")
	public ResponseEntity<?> insertSchedule(@RequestBody Schedule schedule) throws ErrorMessage {
		try {
			scheduleService.insertSchedule(schedule);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Schedule Successfully Added");
	}

	@PutMapping("/")
	public ResponseEntity<?> updateSchedule(@RequestBody Schedule schedule) throws ErrorMessage {
		try {
			scheduleService.updateSchedule(schedule);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Schedule Successfully Updated");
	}
	
	@DeleteMapping("/{scheduleId}")
	public ResponseEntity<?> deleteSchedule(@PathVariable String scheduleId) throws ErrorMessage{
		try {
			scheduleService.deleteSchedule(scheduleId);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Schedule Successfully Deleted");

	}
	
	@GetMapping("/id/{scheduleId}")
	public ResponseEntity<?> getScheduleByID(@PathVariable String scheduleId){
		return ResponseEntity.ok(scheduleService.findScheduleByID(scheduleId));
	}
	
	@GetMapping("/code/{scheduleCode}/{companyId}")
	public ResponseEntity<?> getScheduleByCode(@PathVariable String scheduleCode, @PathVariable String companyId){
		return ResponseEntity.ok(scheduleService.findScheduleByCode(scheduleCode,companyId));
	}
	
//	@GetMapping("/companyId/{companyId}/status/{status}")
//	public ResponseEntity<?> getScheduleByFilter(@PathVariable String companyId, @PathVariable String status){
//		return ResponseEntity.ok(scheduleService.findByFilter(companyId, status));
//	}
	
	@GetMapping("/company/{companyId}")
	public ResponseEntity<?> getScheduleByCompany(@PathVariable String companyId){
		return ResponseEntity.ok(scheduleService.findByCompany(companyId));
	}
	
	@GetMapping("/task/{scheduleId}")
	public ResponseEntity<?> getTask(@PathVariable String scheduleId){
		return ResponseEntity.ok(scheduleService.getTask(scheduleId));
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllSchedule(){
		return ResponseEntity.ok(scheduleService.findAllSchedule());
	}
	
	
	@GetMapping("/{page}/{size}")
	public ResponseEntity<?> getAllScheduleByPaggination(@PathVariable int page,@PathVariable int size){
		return ResponseEntity.ok(scheduleService.findAllScheduleByPaggination(page, size));
	}
	
}
