package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.ListEmailManagementDao;
import com.project.model.ListEmailManagement;

@Service("ListEmailManagementService")
public class ListEmailManagementService {

	@Autowired
	ListEmailManagementDao listEmailDao;
	
	@Transactional
	public void insertEmailManagement(ListEmailManagement email) throws ErrorMessage {
		if (listEmailDao.isIDExsist(email.getEmailId())) {
			throw new ErrorMessage("Email Management ID  Already Exists");
		}
		if (listEmailDao.isBKExsist(email.getEmailCode())) {
			throw new ErrorMessage("Email Management Code Already Exists");
		}
		if (email.getEmailName().isEmpty()) {
			throw new ErrorMessage("Email Cannot Be Empty");
		}
		listEmailDao.save(email);

		System.out.println(" Email Management Successfully Added");
	}

	@Transactional
	public void updateEmailManagement(ListEmailManagement email) throws ErrorMessage {
		if (!listEmailDao.isIDExsist(email.getEmailId())) {
			throw new ErrorMessage("Email Management ID Not Found");
		}
		if (!listEmailDao.isBKExsist(email.getEmailCode())) {
			throw new ErrorMessage("Email Management Code Not Found");
		}
		ListEmailManagement emailDB = listEmailDao.findByID(email.getEmailId());
		if (!email.getEmailCode().equals(emailDB.getEmailCode())) {
			throw new ErrorMessage("The Email Management Code Cannot Be Changed");
		}
		if (email.getEmailName().isEmpty()) {
			throw new ErrorMessage("Email Cannot Be Empty");
		}
		listEmailDao.save(email);
		System.out.println("Email Management Successfully Updated");
	}

	@Transactional
	public void deleteEmailManagement(String emaiID) throws ErrorMessage {
		if (!listEmailDao.isIDExsist(emaiID)) {
			throw new ErrorMessage("Default Send Email ID Not Found");
		}
		listEmailDao.delete(emaiID);
		System.out.println("Email Management Successfully Deleted");
	}

	// ================FIND BY======================
	@Transactional
	public ListEmailManagement findEmailMangementByID(String emailId) {
		return listEmailDao.findByID(emailId);
	}

	@Transactional
	public ListEmailManagement findEmailManagementByCode(String emailCode) {
		return listEmailDao.findByBK(emailCode);
	}

	@Transactional
	public List<ListEmailManagement> findAllEmailManagement() {
		return listEmailDao.getAll();
	}
}
