package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.UserAccountDao;
import com.project.model.UserAccount;

@Service("UserAccountService")
public class UserAccountService {

	@Autowired
	UserAccountDao userDao;
	
		// =================CRUD=================
		@Transactional
		public void insertUser(UserAccount user) throws ErrorMessage {
			if (userDao.isIDExsist(user.getUserId())) {
				throw new ErrorMessage("ID User Account Already Exists");
			}
			if(userDao.isBKExsist(user.getUserCode())) {
				throw new ErrorMessage("Code User Account Already Exists");
			}
			if (user.getEmail().isEmpty()) {
				throw new ErrorMessage("Email Can't Be Empty");
			}
			userDao.save(user);
			
			System.out.println("User Account Successfully Added");
		}

		@Transactional
		public void updateUser(UserAccount user) throws ErrorMessage {
			if (!userDao.isIDExsist(user.getUserId())) {
				throw new ErrorMessage("User Account ID Not Found");
			}
			if (!userDao.isBKExsist(user.getUserCode())) {
				throw new ErrorMessage("User Account Code Not Found");
			}
			UserAccount userDB = userDao.findByID(user.getUserId());
			if (!user.getUserCode().equals(userDB.getUserCode())) {
				throw new ErrorMessage("The User Account Code Cannot Be Changed");
			}
			if(user.getEmail().isEmpty()) {
				throw new ErrorMessage("Email Can't Be Empty");
			}
			userDao.save(user);
			System.out.println("User Account Successfully Updated");
		}

		@Transactional
		public void deleteUser(String userID) throws ErrorMessage {
			if (!userDao.isIDExsist(userID)) {
				throw new ErrorMessage("User Account ID Not Found");
			}
			userDao.delete(userID);
			System.out.println("User Account Successfully Deleted");
		}

		// ================FIND BY======================
		@Transactional
		public UserAccount findUserByID(String userID) {
			return userDao.findByID(userID);
		}
		@Transactional
		public UserAccount findUserByCode(String UserCode) {
			return userDao.findByBK(UserCode);
		}
		@Transactional
		public UserAccount findUserByEmail(String email) {
			return userDao.findByEmail(email);
		}
		@Transactional
		public List<UserAccount> findAllUser() {
			return userDao.getAll();
		}
		
}
