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
import com.project.model.Task;
import com.project.service.TaskService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@PostMapping("/")
	public ResponseEntity<?> insertTask(@RequestBody Task task) throws ErrorMessage {
		try {
			taskService.insertTask(task);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Task Successfully Added");
	}

	@PutMapping("/")
	public ResponseEntity<?> updateTask(@RequestBody Task task) throws ErrorMessage {
		try {
			taskService.updateTask(task);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Task Successfully Updated");
	}
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity<?> deleteTask(@PathVariable String taskId) throws ErrorMessage{
		try {
			taskService.deleteTask(taskId);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Task Successfully Deleted");

	}
	
	@GetMapping("/id/{taskId}")
	public ResponseEntity<?> getTaskByID(@PathVariable String taskId){
		return ResponseEntity.ok(taskService.findTaskByID(taskId));
	}
	
	@GetMapping("/code/{taskCode}/{scheduleId}")
	public ResponseEntity<?> getTaskByCode(@PathVariable String taskCode,@PathVariable String scheduleId){
		return ResponseEntity.ok(taskService.findTaskByCode(taskCode,scheduleId));
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllTask(){
		return ResponseEntity.ok(taskService.findAllTask());
	}
}
