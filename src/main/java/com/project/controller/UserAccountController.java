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
import com.project.model.UserAccount;
import com.project.service.SendEmailService;
import com.project.service.UserAccountService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/useraccount")
public class UserAccountController {

	@Autowired
	UserAccountService userService;
	
	@Autowired
	SendEmailService emailService;

	@PostMapping("/")
	public ResponseEntity<?> insertUserAccount(@RequestBody UserAccount user) throws ErrorMessage {
		try {
			userService.insertUser(user);
			emailService.SendNotifNewAccount(user.getUserName(),user.getEmail(), user.getUserRole());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("User Account Successfully Added");
	}

	@PutMapping("/")
	public ResponseEntity<?> updateUserAccount(@RequestBody UserAccount user) throws ErrorMessage {
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("User Account Successfully Updated");
	}
	
	@DeleteMapping("/{userID}")
	public ResponseEntity<?> deleteUserAccount(@PathVariable String userID) throws ErrorMessage{
		try {
			userService.deleteUser(userID);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("User Account Successfully Deleted");

	}
	
	@GetMapping("/id/{userID}")
	public ResponseEntity<?> getUserAccountByID(@PathVariable String userID){
		return ResponseEntity.ok(userService.findUserByID(userID));
	}
	
	@GetMapping("/code/{userCode}")
	public ResponseEntity<?> getUserAccountByCode(@PathVariable String userCode){
		return ResponseEntity.ok(userService.findUserByCode(userCode));
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<?> getUserAccountByEmail(@PathVariable String email){
		return ResponseEntity.ok(userService.findUserByEmail(email));
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllUserAccount(){
		return ResponseEntity.ok(userService.findAllUser());
	}
	
//	@GetMapping("/login/")
//	public ResponseEntity<?> login(){
//		return ResponseEntity.ok();
//	}
}
