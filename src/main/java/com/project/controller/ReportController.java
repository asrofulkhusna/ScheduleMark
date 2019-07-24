package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.ReportService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@GetMapping("/getreport/{yearMonth}/{userCode}/{companyCode}")
	public ResponseEntity<?> getreport(@PathVariable String yearMonth, @PathVariable String userCode, @PathVariable String companyCode){
		return ResponseEntity.ok(reportService.getReport(yearMonth, userCode, companyCode));
	}
	@GetMapping("/getuser/{yearMonth}/{userCode}/{companyCode}")
	public ResponseEntity<?> getUser(@PathVariable String yearMonth, @PathVariable String userCode, @PathVariable String companyCode){
		return ResponseEntity.ok(reportService.getPayrollSepesialist(yearMonth, userCode, companyCode));
	}
	
	@GetMapping("/getcompany/{yearMonth}/{userCode}/{companyCode}")
	public ResponseEntity<?> getCompany(@PathVariable String yearMonth, @PathVariable String userCode, @PathVariable String companyCode){
		return ResponseEntity.ok(reportService.getCompanys(yearMonth, userCode, companyCode));
	}
	
	@GetMapping("/gettask/{yearMonth}/{userCode}/{companyCode}")
	public ResponseEntity<?> getTask(@PathVariable String yearMonth, @PathVariable String userCode, @PathVariable String companyCode){
		return ResponseEntity.ok(reportService.getTaks(yearMonth, userCode, companyCode));
	}
	
//	@GetMapping("/listuser/{yearMonth}")
//	public ResponseEntity<?> listUser(@PathVariable String yearMonth){
//		return ResponseEntity.ok(reportService.listPS(yearMonth));
//	}
//	
//	@GetMapping("/listcompany/{yearMonth}/{userCode}")
//	public ResponseEntity<?> listCompany(@PathVariable String yearMonth, @PathVariable String userCode){
//		return ResponseEntity.ok(reportService.listCompany(yearMonth, userCode));
//	}
//	
//	@GetMapping("/listtask/{yearMonth}/{userCode}/{companyCode}")
//	public ResponseEntity<?> listTask(@PathVariable String yearMonth, @PathVariable String userCode, @PathVariable String companyCode){
//		return ResponseEntity.ok(reportService.listTask(yearMonth, userCode, companyCode));
//	}
}
