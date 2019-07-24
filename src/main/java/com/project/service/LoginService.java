package com.project.service;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.UserAccountDao;
import com.project.model.UserAccount;

@Service("LoginService")
public class LoginService {

	@Autowired
	UserAccountDao userDao;

	public UserAccount Login(String email, String pass) throws ErrorMessage {
		UserAccount user = userDao.findByEmail(email);
		System.out.println(email + "  "+ pass);
			if (userDao.isEmailExist(email)) {
				boolean check = checkingEmail(email, pass);
				if (check == true) {
					return user;
				} else {
					throw new ErrorMessage("Email dan Password salah !!!");
				}
			} else {
				throw new ErrorMessage("Email Tidak Di Temukan");
			}
		}

	public boolean checkingEmail(String email, String pass) {
		try {
			// create properties field
			Properties properties = new Properties();
			// GMAIL
			String username = email;
			String password = pass;
			String host = "imap.gmail.com";

			int port = 993;
			properties.put("mail.imap.ssl.enable", "true"); // required for Gmail
			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);
			properties.put("mail.imap.starttls.enable", "true");
//			properties.put("mail.imap.auth.login.enable", "true");


			Session emailSession = Session.getDefaultInstance(properties);

			emailSession.setDebug(true);
			Store store = emailSession.getStore("imap");
			System.out.println("connecting .....");
			store.connect(host, port, username, password);
			if (store.isConnected() == true) {
				System.out.println("connect");
				return true;
			} else {
				System.out.println("gagal connect");
				return false;
			}
//			store.close();
//			System.out.println("Connection Close");
			
		} catch (Exception e) {
			System.out.println("GAGAL");
			System.out.println(e);
			return false;
		}
		
	}

}
