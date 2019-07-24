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
import com.project.model.PayrollMapping;
import com.project.service.PayrollMappingService;
import com.project.service.UserAccountService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/payrollmapping")
public class PayrollMappingController {

	@Autowired
	PayrollMappingService payrollService;
	
	@Autowired
	UserAccountService userService;

	@PostMapping("/")
	public ResponseEntity<?> insertPayrollMapping(@RequestBody PayrollMapping payroll) throws ErrorMessage {
		try {
			payrollService.insertPayrollMapping(payroll);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Payroll Mapping Successfully Added");
	}

	@PatchMapping("/{companyId}")
	public ResponseEntity<?> payrollMapping(@PathVariable String companyId, @RequestBody PayrollMapping pm) {
		try {
			PayrollMapping payrollMP = payrollService.findPayrollMappingByCode(companyId);
			payrollMP.setUser(pm.getUser());
			payrollService.updatePayrollMapping(payrollMP);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Payroll Mapping Successfully Created");
	}

	@PutMapping("/")
	public ResponseEntity<?> updatePayrollMapping(@RequestBody PayrollMapping payroll) throws ErrorMessage {
		try {
			payrollService.updatePayrollMapping(payroll);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Payroll Mapping Successfully Updated");
	}

	@DeleteMapping("/{payrollMappingId}")
	public ResponseEntity<?> deletePayrollMapping(@PathVariable String payrollMappingId) throws ErrorMessage {
		try {
			payrollService.deletePayrollMapping(payrollMappingId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Payroll Mapping Successfully Deleted");
	}

	@GetMapping("/id/{payrollMappingId}")
	public ResponseEntity<?> getPayrollMappingByID(@PathVariable String payrollMappingId) {
		return ResponseEntity.ok(payrollService.findPayrollMappingByID(payrollMappingId));
	}

	@GetMapping("/code/{companyId}")
	public ResponseEntity<?> getPayrollMappingByCode(@PathVariable String companyId) {
		return ResponseEntity.ok(payrollService.findPayrollMappingByCode(companyId));
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllPayrollMapping() {
		return ResponseEntity.ok(payrollService.findAllPayrollMapping());
	}
}
