package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.DefaultSendEmailDao;
import com.project.model.DefaultSendEmail;

@Service("DefaultSendEmailService")
public class DefaultSendEmailService {

	@Autowired
	DefaultSendEmailDao sendEmailDao;
	
	// =================CRUD=================
		@Transactional
		public void insertDefaultSendEmail(DefaultSendEmail sendEmail) throws ErrorMessage {
			if (sendEmailDao.isIDExsist(sendEmail.getDefaultSendEmailId())) {
				throw new ErrorMessage("Default Send Email ID  Already Exists");
			}
			if (sendEmailDao.isBKExsist(sendEmail.getDefaultSendEmailCode())) {
				throw new ErrorMessage("Default Send Email Code Already Exists");
			}
			if (sendEmail.getDefaultSendEmailDate()<= 0) {
				throw new ErrorMessage("date cannot be changed below 1");
			}
			sendEmailDao.save(sendEmail);

			System.out.println("Default Send Email Successfully Added");
		}

		@Transactional
		public void updateDefaultSendEmail(DefaultSendEmail sendEmail) throws ErrorMessage {
			if (!sendEmailDao.isIDExsist(sendEmail.getDefaultSendEmailId())) {
				throw new ErrorMessage("Default Send Email ID Not Found");
			}
			if (!sendEmailDao.isBKExsist(sendEmail.getDefaultSendEmailCode())) {
				throw new ErrorMessage("Default Send Email Not Found");
			}
			DefaultSendEmail sendEmailDB = sendEmailDao.findByID(sendEmail.getDefaultSendEmailId());
			if (!sendEmail.getDefaultSendEmailCode().equals(sendEmailDB.getDefaultSendEmailCode())) {
				throw new ErrorMessage("The Default Email Code Cannot Be Changed");
			}
			if (sendEmail.getDefaultSendEmailDate() <= 0) {
				throw new ErrorMessage("date cannot be changed below 1");
			}
			sendEmailDao.save(sendEmail);
			System.out.println("Default Send Email Successfully Updated");
		}

		@Transactional
		public void updateDefaultSendEmailByPatch(String defaultSendEmailID, DefaultSendEmail sendEmail) throws ErrorMessage {
			DefaultSendEmail send = sendEmailDao.findByID(defaultSendEmailID);
			send.setDefaultSendEmailTime(sendEmail.getDefaultSendEmailTime());
			send.setDefaultSendEmailDate(sendEmail.getDefaultSendEmailDate());
			sendEmailDao.save(send);
		}

		@Transactional
		public void deleteDefaultSendEmail(String defaultSendEmaiID) throws ErrorMessage {
			if (!sendEmailDao.isIDExsist(defaultSendEmaiID)) {
				throw new ErrorMessage("Default Send Email ID Not Found");
			}
			sendEmailDao.delete(defaultSendEmaiID);
			System.out.println("Default Send Email Successfully Deleted");
		}

		// ================FIND BY======================
		@Transactional
		public DefaultSendEmail findSendEmailByID(String defaultSendEmailID) {
			return sendEmailDao.findByID(defaultSendEmailID);
		}

		@Transactional
		public DefaultSendEmail findSendEmailByCode(String defaultSendEmailCode) {
			return sendEmailDao.findByBK(defaultSendEmailCode);
		}

		@Transactional
		public List<DefaultSendEmail> findAllDefaultSendEmail() {
			return sendEmailDao.getAll();
		}
		
		
}

