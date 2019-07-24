package com.project.service;

import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.dao.ListEmailManagementDao;
import com.project.dao.ListForSendEmailDao;
import com.project.dao.UserAccountDao;
import com.project.model.ListEmailManagement;
import com.project.model.SendEmailPayroll;
import com.project.model.enumerated.UserRole;

@Service("SendEmailService")
public class SendEmailService {

	@Autowired
	ListEmailManagementDao emailManagementService;

	@Autowired
	UserAccountDao userDao;

	@Autowired
	ListForSendEmailDao sendEmailDao;

	@Autowired
	JReportService jReportService;

	@Autowired
	public JavaMailSender emailSender;

	@Scheduled(cron= "${cron.scheduledtime}")
	public void SendEmailManagement() {

		try {
			List<ListEmailManagement> emailManagement = emailManagementService.getAll();
			for (ListEmailManagement sendEmail : emailManagement) {
				MimeMessage message = emailSender.createMimeMessage();
				System.out.println(sendEmail.getEmailName());
				try {
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
					helper.setTo(sendEmail.getEmailName());
					helper.setSubject("monthly Report");
					helper.setText("Report For Management \n\n Dalam email ini terlampil 2 file laporan dari pada bulan sebelumnya."
							+ "\n Terdiri dari laporan Per Payroll Spesialist dan laporan Per Client \n\nTerima Kasih");
					jReportService.getReportByPayroll();
					jReportService.getReportByCompany();
//					List;
//					List<SendEmailPayroll> listPS = sendEmailDao.getListPayroll();

					FileSystemResource file = new FileSystemResource(
							"/home/asroful/eclipse-workspace/LinovScheduleMark/report_company.pdf");
					FileSystemResource file1 = new FileSystemResource(
							"/home/asroful/eclipse-workspace/LinovScheduleMark/report_payroll.pdf");
					helper.addAttachment(file.getFilename(), file);
					helper.addAttachment(file1.getFilename(), file1);

				} catch (MessagingException e) {
					throw new MailParseException(e);
				}
				emailSender.send(message);
				System.out.println("Email Succesfully Send");

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Scheduled(cron = " 0 0 7 * * ?")
	public void SendEmailPayroll() {
		Calendar cal = Calendar.getInstance();
		int tanggal = cal.get(Calendar.DATE);

		try {
			List<SendEmailPayroll> listPS = sendEmailDao.getListPayroll();
			for (SendEmailPayroll Ps : listPS) {
				if (tanggal == Ps.getStartDate() || tanggal == Ps.getFinishDate() || tanggal == Ps.getFinishDate() - 1
						|| tanggal == Ps.getFinishDate() - 2) {
					MimeMessage message = emailSender.createMimeMessage();
					try {
						MimeMessageHelper helper = new MimeMessageHelper(message, true);
						helper.setTo(Ps.getEmail());
						helper.setSubject("Payroll Notification");
						helper.setText("Notifikasi For Payroll Spesialist ");
						jReportService.getNotifPayroll(Ps.getUserCode());

						FileSystemResource file = new FileSystemResource(
								"/home/asroful/eclipse-workspace/LinovScheduleMark/notif_payroll.html");
						helper.addAttachment(file.getFilename(), file);

					} catch (MessagingException e) {
						throw new MailParseException(e);
					}
					emailSender.send(message);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SendNotifNewAccount(String userName, String email, UserRole userRole) {
		MimeMessage message = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(email);
			helper.setSubject("Notification New User");
			helper.setText("Dear " + userName + ",\n\nEmail Anda (" + email + ") telah terdaftar sebagai " + userRole
					+ " dalam aplikasi Linov Schedule Mark.\n"
					+ " Sebelum login ke aplikasi pastikan pengaturan 'Less secure app access' pada email anda telah dalam keadaan hidup\n\nTerima Kasih");

		} catch (MessagingException e) {
			throw new MailParseException(e);
		}

		emailSender.send(message);
	}
}
