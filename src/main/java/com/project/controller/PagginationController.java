package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.PagginationService;

@RestController
@RequestMapping("/paggination")
public class PagginationController {

	@Autowired
	PagginationService pageService;
	
	
	@GetMapping("/schedule/{page}/{size}/")
	public ResponseEntity<?> pageSchedule(@PathVariable int page, @PathVariable int size) {
		return ResponseEntity.ok(pageService.findAllScheduleByPaggination(page, size));
	}
	
	@GetMapping("/company/{page}/{size}/")
	public ResponseEntity<?> pageCompany(@PathVariable int page, @PathVariable int size) {
		return ResponseEntity.ok(pageService.findAllCompanyByPaggination(page, size));
	}
	
	@GetMapping("/user/{page}/{size}/")
	public ResponseEntity<?> pageUserAccount(@PathVariable int page, @PathVariable int size) {
		return ResponseEntity.ok(pageService.findAllUserByPaggination(page, size));
	}
	
	@GetMapping("/home/{page}/{size}/")
	public ResponseEntity<?> pageHome(@PathVariable int page, @PathVariable int size) {
		return ResponseEntity.ok(pageService.findAllHomeByPaggination(page, size));
	}



}
