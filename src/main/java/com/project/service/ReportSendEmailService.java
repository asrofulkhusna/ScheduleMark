package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.DefaultSendEmailDao;

@Service("ReporSendEmailService")
public class ReportSendEmailService {

	@Autowired
	DefaultSendEmailDao sendEmailDao;
	
	
//	DefaultSendEmail sendEmail = sendEmailDao.findByBK("DEFAULT-01");
//	int tanggal = sendEmail.getDefaultSendEmailDate();
//	Time waktu = sendEmail.getDefaultSendEmailTime();
	
//	@Scheduled(cron="${cron.scheduledtime}")
	public void SetSchedule() {
		System.out.println("OK SCHEDULER");
	}
//	DefaultSendEmail sendEmail = sendEmailDao.findByBK("DEFAULT-01");
//	int tanggal = sendEmail.getDefaultSendEmailDate();
//	String waktu = sendEmail.getDefaultSendEmailTime().toString();
//	String[] w = waktu.split(":");
//
//	int hour=Integer.parseInt(w[0]);
//	int minute=Integer.parseInt(w[1]);
//	int second=Integer.parseInt(w[2]);
	
////	@Scheduled(cron=""+second+" "+minute+" "+hour+" "+tanggal+" * ?")
//	public void SendEmail() {
//		
//	}
}
