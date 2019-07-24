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
import com.project.model.DefaultSendEmail;
import com.project.service.DefaultSendEmailService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/sendemail")
public class DefaultSendEmailController {

	@Autowired
	DefaultSendEmailService sendEmailService;
	
	@PostMapping("/")
	public ResponseEntity<?> insertDefaultSendEmail(@RequestBody DefaultSendEmail sendEmail) throws ErrorMessage{
		try {
			sendEmailService.insertDefaultSendEmail(sendEmail);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Default Send Email Successfully Added");
	}
	@PutMapping("/")
	public ResponseEntity<?> updateDefaultSendEmail(@RequestBody DefaultSendEmail sendEmail) throws ErrorMessage {
		try {
			sendEmailService.updateDefaultSendEmail(sendEmail);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Default Send Email Successfully Updated");
	}
	
	@PatchMapping("/{defaultSendEmailId}")
	public ResponseEntity<?> updateDefaultSchedulePatch(@PathVariable String defaultSendEmailId, @RequestBody DefaultSendEmail sendEmail){
		try {
			sendEmailService.updateDefaultSendEmailByPatch(defaultSendEmailId, sendEmail);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Default Send Email Successfully Update");
	}
	
	@DeleteMapping("/{defaultSendEmailId}")
	public ResponseEntity<?> deleteDefaultSendEmailId(@PathVariable String defaultSendEmailId) throws ErrorMessage{
		try {
			sendEmailService.deleteDefaultSendEmail(defaultSendEmailId);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Default Send Email Successfully Deleted");
	}
	
	@GetMapping("/id/{defaultSendEmailID}")
	public ResponseEntity<?> getDefaultSendEmailByID(@PathVariable String defaultSendEmailID){
		return ResponseEntity.ok(sendEmailService.findSendEmailByID(defaultSendEmailID));
	}
	
	@GetMapping("/code/{defaultSendEmailCode}")
	public ResponseEntity<?> getDefaultSendEmailByCode(@PathVariable String defaultSendEmailCode){
		return ResponseEntity.ok(sendEmailService.findSendEmailByCode(defaultSendEmailCode));
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllDefaultSendEmail(){
		return ResponseEntity.ok(sendEmailService.findAllDefaultSendEmail());
	}
}


