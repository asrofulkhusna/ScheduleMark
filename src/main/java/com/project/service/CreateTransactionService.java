package com.project.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.DefaultScheduleDao;
import com.project.dao.ScheduleDao;
import com.project.dao.TransactionDao;
import com.project.model.DefaultSchedule;
import com.project.model.Task;
import com.project.model.TransactionDetail;
import com.project.model.TransactionHeader;
import com.project.model.enumerated.StatusTrxDtl;
import com.project.model.enumerated.StatusTrxHdr;

@Service("CreateTransaksiService")
public class CreateTransactionService {

	@Autowired
	DefaultScheduleDao defaultScheduleDao;

	@Autowired
	TransactionDao transactionDao;
	
	@Autowired
	TransactionService transactionService;
	@Autowired
	ScheduleDao scheduleDao;

	@Scheduled(cron="0 0 5 1 * ?")
	public String CreateTransaction() throws ErrorMessage {
		Calendar cal = Calendar.getInstance();
		String namaBulan[] = { "JANUARI", "FEBRUARI", "MARET", "APRIL", "MEI", "JUNI", "JULI", "AGUSTUS", "SEPTEMBER",
				"OKTOBER", "NOVEMBER", "DESEMBER" };
		int bulan = cal.get(Calendar.MONTH);
		int count = 0;
		String yearMonth = cal.get(Calendar.YEAR) + "-" + namaBulan[bulan];

		for (DefaultSchedule sch : defaultScheduleDao.getAllDefaultSchedule()) {
			count++;
			TransactionHeader header = new TransactionHeader();
			header.setHdrTrxId("uuid");
			header.setTrxCode("TRX-" + count + "");
			header.setYearMonth(yearMonth);
			header.setStatus(StatusTrxHdr.ON_PROGRESS);
//			transactionDao.saveHeader(header);
			transactionService.insertHeader(header);
			System.out.println("Save Header" + count);
			transactionDao.flush();

			TransactionHeader trxHeader = transactionDao.findHeaderByBK(header.getTrxCode(), header.getYearMonth());

			List<Task> listDetail = scheduleDao.getTask(sch.getSchedule().getScheduleId());
			for (Task task : listDetail) {
					TransactionDetail detail = new TransactionDetail();
					detail.setDtlTrxId("uuid");
					detail.setTransactionHeader(trxHeader);
					detail.setTask(task);
					detail.setStatus(StatusTrxDtl.PENDING);
					transactionService.insertDetail(detail);
//					transactionDao.saveDetail(detail);
				System.out.println("Save Detail");
			}
		}

		System.out.println("Transaction Successfully Added");
		return "Transaction Successfully Added";
	}
}
