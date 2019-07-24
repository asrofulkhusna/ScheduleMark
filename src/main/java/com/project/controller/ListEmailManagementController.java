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
import com.project.model.ListEmailManagement;
import com.project.service.ListEmailManagementService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/emailmanagement")
public class ListEmailManagementController {

	@Autowired
	ListEmailManagementService emailManagementService;
	
	@PostMapping("/")
	public ResponseEntity<?> insertEmailManagement(@RequestBody ListEmailManagement email) throws ErrorMessage{
		try {
			emailManagementService.insertEmailManagement(email);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Email Management Successfully Added");
	}
	@PutMapping("/")
	public ResponseEntity<?> updateEmailManagement(@RequestBody ListEmailManagement email) throws ErrorMessage {
		try {
			emailManagementService.updateEmailManagement(email);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Email Management Successfully Updated");
	}
	
	@DeleteMapping("/{emailId}")
	public ResponseEntity<?> deleteDefaultSendEmailId(@PathVariable String emailId) throws ErrorMessage{
		try {
			emailManagementService.deleteEmailManagement(emailId);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Email Management Successfully Deleted");
	}
	
	@GetMapping("/id/{emailId}")
	public ResponseEntity<?> getEmailManagementByID(@PathVariable String emailId){
		return ResponseEntity.ok(emailManagementService.findEmailMangementByID(emailId));
	}
	
	@GetMapping("/code/{emailCode}")
	public ResponseEntity<?> getEmailManagementByCode(@PathVariable String emailCode){
		return ResponseEntity.ok(emailManagementService.findEmailManagementByCode(emailCode));
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllDefaultSendEmail(){
		return ResponseEntity.ok(emailManagementService.findAllEmailManagement());
	}
}
