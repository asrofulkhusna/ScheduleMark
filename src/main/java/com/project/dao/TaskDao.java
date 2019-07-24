package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.Task;

@Repository("TaskDao")
public class TaskDao extends ParentDao{

	@Transactional
	public void save(Task task) {
		super.entityManager.merge(task);
	}
	
	@Transactional
	public void delete(String taskId) {
		Task task = findByID(taskId);
		super.entityManager.remove(task);
	}
	
	@SuppressWarnings( "unchecked")
	@Transactional
	public Task findByID(String taskId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Task ");
		sb.append(" WHERE taskId = :taskId");
		
		List<Task> task = super.entityManager
				.createQuery(sb.toString())
				.setParameter("taskId", taskId)
				.getResultList();
		if(task.size()>0) {
			return (Task) task.get(0);
		}
		else {
			return new Task();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Task findByBK(String taskCode, String scheduleId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Task ");
		sb.append(" WHERE taskCode = :taskCode");
		sb.append(" AND schedule.scheduleId = : scheduleId");
		
		List<Task> task = super.entityManager
				.createQuery(sb.toString())
				.setParameter("taskCode", taskCode)
				.setParameter("scheduleId", scheduleId)
				.getResultList();
		if(task.size()>0) {
			return (Task) task.get(0);
		}
		else {
			return new Task();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Task> getAll() {
		
		List <Task> task = super.entityManager.
				createQuery("FROM Task")
				.getResultList();
		
		if(task.size()>0) {
			return task;
		}
		else {
			return new ArrayList<Task>();
		}
	}
	
	@Transactional
	public boolean isIDExsist(String taskId) {
		Task task = findByID(taskId);
		if(task.getTaskId()== null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	@Transactional
	public boolean isBKExsist(String taskCode, String scheduleId) {
		Task task = findByBK(taskCode, scheduleId);
		if(task.getTaskCode()==null) {
			return false;
		}
		else {
			return true;
		}
	}
}
