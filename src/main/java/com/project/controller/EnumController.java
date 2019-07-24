package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.EnumService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/enum")
public class EnumController {

	@Autowired
	EnumService enumService;
	
	@GetMapping("/userrole/")
	public ResponseEntity<?> getEnumUserRole(){
		return ResponseEntity.ok(enumService.getEnumserRole());
	}
	
	@GetMapping("/schedule/")
	public ResponseEntity<?> getEnumStatusSchedule(){
		return ResponseEntity.ok(enumService.getEnumStatusSchedule());
	}
	
	@GetMapping("/header/")
	public ResponseEntity<?> getEnumStatusTrxHdr(){
		return ResponseEntity.ok(enumService.getEnumStatusTrxHdr());
	}
	
	@GetMapping("/detail/")
	public ResponseEntity<?> getEnumStatusTrxDtl(){
		return ResponseEntity.ok(enumService.getEnumStatusTrxDtl());
	}
}
