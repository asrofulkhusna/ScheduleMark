package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.SendEmailService;

@RestController
@RequestMapping("/testemail")
public class SendEmailTestController {

	@Autowired
	SendEmailService sendEmail;
	
	@GetMapping("/notif")
	public ResponseEntity<?> getNotif(){
		sendEmail.SendEmailPayroll();
		return ResponseEntity.ok("OK");
	}
}
