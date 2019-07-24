package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.JReportService;

@RestController
@RequestMapping("/jreport")
public class JReportController {

	
	@Autowired
	private JReportService jReportService;
	
	@GetMapping("/{userCode}")
	public ResponseEntity<?> notifPayroll(@PathVariable String userCode){
		String result = jReportService.getNotifPayroll(userCode);
		if (result.equals("OK")) {
			return ResponseEntity.status(HttpStatus.OK).body("Check Report Now");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Ada kesalahan dalam report");
		}
		
	}
	
	@GetMapping("/payroll")
	public ResponseEntity<?> reportPayroll(){
		String result = jReportService.getReportByPayroll();
		if (result.equals("OK")) {
			return ResponseEntity.status(HttpStatus.OK).body("Check Report Now");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Ada kesalahan dalam report");
		}
		
	}
	
	@GetMapping("/company")
	public ResponseEntity<?> reportCompany(){
		String result = jReportService.getReportByCompany();
		if (result.equals("OK")) {
			return ResponseEntity.status(HttpStatus.OK).body("Check Report Now");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Ada kesalahan dalam report");
		}
		
	}

	
}
