package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ErrorHandling.ErrorMessage;
import com.project.model.DefaultSchedule;
import com.project.service.DefaultScheduleService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/defaultsch")
public class DefaultScheduleController {

	@Autowired
	DefaultScheduleService defaultService;
	
	@PostMapping("/")
	public ResponseEntity<?> insertDefaultSchedule(@RequestBody DefaultSchedule defaultSchedule) throws ErrorMessage{
		try {
			defaultService.insertDefaultSchedule(defaultSchedule);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Default Schedule Successfully Added");
	}
	@PutMapping("/")
	public ResponseEntity<?> updateDefaultSchedule(@RequestBody DefaultSchedule schedule) throws ErrorMessage {
		try {
			defaultService.updateDefaultSchedule(schedule);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Default Schedule Successfully Updated");
	}
	
	@PatchMapping("/{defaultScheduleId}")
	public ResponseEntity<?> updateDefaultSchedulePatch(@PathVariable String defaultScheduleId, @RequestBody DefaultSchedule schedule){
		try {
			defaultService.updateDefaultScheduleByPatch(defaultScheduleId,schedule);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Default Schedule Successfully Update");
	}
	
	@DeleteMapping("/{defaultScheduleId}")
	public ResponseEntity<?> deleteDefaultSchedule(@PathVariable String defaultScheduleId) throws ErrorMessage{
		try {
			defaultService.deleteDefaultSchedule(defaultScheduleId);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Default Schedule Successfully Deleted");
	}
	
	@GetMapping("/id/{defaultScheduleId}")
	public ResponseEntity<?> getDefaultScheduleByID(@PathVariable String defaultScheduleId){
		return ResponseEntity.ok(defaultService.findScheduleByID(defaultScheduleId));
	}
	
	@GetMapping("/companyId/{companyId}")
	public ResponseEntity<?> getScheduleByCode(@PathVariable String companyId){
		return ResponseEntity.ok(defaultService.findScheduleByCode(companyId));
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllSchedule(){
		return ResponseEntity.ok(defaultService.findAllDefaultSchedule());
	}
	@GetMapping("/all/")
	public ResponseEntity<?> getAllDefaultSchedule(){
		return ResponseEntity.ok(defaultService.findAllDefaultScheduleNative());
	}
	
}
