package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.TaskDao;
import com.project.dao.TransactionDao;
import com.project.model.Task;

@Service("TaskService")
public class TaskService {

	@Autowired
	TaskDao taskDao;
	
	@Autowired
	TransactionDao transactionDao;

	// =================CRUD=================
	@Transactional
	public void insertTask(Task task) throws ErrorMessage {
		if (taskDao.isIDExsist(task.getTaskId())) {
			throw new ErrorMessage("Task ID  Already Exists");
		}
		if (taskDao.isBKExsist(task.getTaskCode(),task.getSchedule().getScheduleId())) {
			throw new ErrorMessage("Task Code Already Exists");
		}
		if (task.getStartDate()== 0) {
			throw new ErrorMessage("Start Date Can't Be Empty");
		}
		if (task.getFinishDate() == 0) {
			throw new ErrorMessage("Finish Date Can't Be Empty");
		}
		if (task.getTaskDescription().isEmpty()) {
			throw new ErrorMessage("Task Description Can't Be Empty");
		}
		taskDao.save(task);

		System.out.println("Task Successfully Added");
	}

	@Transactional
	public void updateTask(Task task) throws ErrorMessage {
		if (!taskDao.isIDExsist(task.getTaskId())) {
			throw new ErrorMessage("Task ID Not Found");
		}
		if (!taskDao.isBKExsist(task.getTaskCode(),task.getSchedule().getScheduleId())) {
			throw new ErrorMessage("Task Code Not Found");
		}
		Task taskDB = taskDao.findByID(task.getTaskId());
		if (!task.getTaskCode().equals(taskDB.getTaskCode())) {
			throw new ErrorMessage("The Task Code Cannot Be Changed");
		}
		if (task.getStartDate()==0) {
			throw new ErrorMessage("Start Date Can't Be Empty");
		}
		if (task.getFinishDate()==0) {
			throw new ErrorMessage("Finish Date Can't Be Empty");
		}
		if (task.getTaskDescription().isEmpty()) {
			throw new ErrorMessage("Task Description Can't Be Empty");
		}

		taskDao.save(task);
		System.out.println("Task Successfully Updated");
	}

	@Transactional
	public void deleteTask(String taskId) throws ErrorMessage {
		if (!taskDao.isIDExsist(taskId)) {
			throw new ErrorMessage("Task ID Not Found");
		}
		taskDao.delete(taskId);
		System.out.println("Schedule Successfully Deleted");
	}

	// ================FIND BY======================
	@Transactional
	public Task findTaskByID(String taskId) {
		return taskDao.findByID(taskId);
	}

	@Transactional
	public Task findTaskByCode(String taskCode, String scheduleId) {
		return taskDao.findByBK(taskCode, scheduleId);
	}

	@Transactional
	public List<Task> findAllTask() {
		return taskDao.getAll();
	}
}
