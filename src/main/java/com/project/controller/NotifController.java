package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.NotifikasiService;

@RestController
@RequestMapping("/notif")
public class NotifController {

	@Autowired
	NotifikasiService notifService;
	
	@GetMapping("/{userCode}")
	public ResponseEntity<?> getNotifikai(@PathVariable String userCode) {
		return ResponseEntity.ok(notifService.getNotif(userCode));
	}
}
