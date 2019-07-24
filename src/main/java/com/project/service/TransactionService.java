package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.ReportDao;
import com.project.dao.TransactionDao;
import com.project.model.Home;
import com.project.model.HomeDetail;
import com.project.model.TransactionDetail;
import com.project.model.TransactionHeader;

@Service("TransactionService")
public class TransactionService {

	@Autowired
	TransactionDao transactionDao;

	@Autowired
	ReportDao reportDao;

	///////////////////////// Header Transaction/////////////////////////

	public void insertHeader(TransactionHeader trxHeader) throws ErrorMessage {
		if (transactionDao.isHeaderIdExists(trxHeader.getHdrTrxId())) {
			throw new ErrorMessage("Transaction Header ID Already Exists");
		}

		if (transactionDao.isHeaderBkExists(trxHeader.getTrxCode(), trxHeader.getYearMonth())) {
			throw new ErrorMessage("Transaction Header Code Already Exists");
		}

		if (trxHeader.getYearMonth().isEmpty()) {
			throw new ErrorMessage("Year And Month Cannot Be Empty");
		}

		transactionDao.saveHeader(trxHeader);
	}

	public String deleteHeader(String hdrTrxId) throws ErrorMessage {
//		if (!transactionDao.isHeaderIdExists(hdrTrxId)) {
//			throw new ErrorMessage("Transaction Header ID Not Found");
//		}
		transactionDao.deleteHeader(hdrTrxId);
		return "Transaction Succesfull Deleted";
	}

	public void updateHeader(TransactionHeader hdrTrx) throws ErrorMessage {
		if (!transactionDao.isHeaderIdExists(hdrTrx.getHdrTrxId())) {
			throw new ErrorMessage("Transaction Header ID Not Found");
		}

		if (!transactionDao.isHeaderBkExists(hdrTrx.getTrxCode(), hdrTrx.getYearMonth())) {
			throw new ErrorMessage("Transaction Header Code Not Found");
		}

		TransactionHeader hdrTrxDB = transactionDao.findHeaderById(hdrTrx.getHdrTrxId());
		if (!hdrTrxDB.getTrxCode().equals(hdrTrx.getTrxCode())) {
			throw new ErrorMessage("Transaction Header Code Cannot be Changed");
		}

		if (hdrTrx.getYearMonth().isEmpty()) {
			throw new ErrorMessage("Year And Month Cannot Be Empty");
		}
		transactionDao.saveHeader(hdrTrx);
	}

	public TransactionHeader findHeaderByID(String trxHdrId) {
		return transactionDao.findHeaderById(trxHdrId);
	}

	public TransactionHeader findHeaderByBK(String trxCode, String yearMonth) {
		return transactionDao.findHeaderByBK(trxCode, yearMonth);
	}

//	public TransactionHeader findHeaderBySchedule(String scheduleId) {
//		return transactionDao.findHeaderBySchedule(scheduleId);
//	}
//	
//	public TransactionHeader findHeaderByCode(String trxCode) {
//		return transactionDao.findHeaderByCode(trxCode);
//	}

	public List<TransactionHeader> findAllHeader() {
		return transactionDao.findAllHeader();
	}

	//////////////////////////////////////// DETAIL TRANSAKSI
	//////////////////////////////////////// //////////////////////////////////////////////////////////

	public void deleteDetail(String dtlTrxId) throws ErrorMessage {
		if (!transactionDao.isDetailIdExists(dtlTrxId)) {
			transactionDao.deleteDetail(dtlTrxId);
		} else {
			throw new ErrorMessage("Transaction Detail ID Not Found");
		}
	}

	public void insertDetail(TransactionDetail trxDetail) throws ErrorMessage {
		if (transactionDao.isDetailIdExists(trxDetail.getDtlTrxId())) {
			throw new ErrorMessage("Transaction Detail ID Already Exists");
		}

		if (transactionDao.isDetailBkExists(trxDetail.getTransactionHeader().getHdrTrxId(),
				trxDetail.getTask().getTaskId())) {
			throw new ErrorMessage("Transaction Detail Code Already Exists");
		}
		transactionDao.saveDetail(trxDetail);
	}

	public void updateDetail(TransactionDetail trxDetail) throws ErrorMessage {
		if (!transactionDao.isDetailIdExists(trxDetail.getDtlTrxId())) {
			throw new ErrorMessage("Transaction Detail ID Not Found");
		}
		String idHeader = trxDetail.getTransactionHeader().getHdrTrxId();
		String idTask = trxDetail.getTask().getTaskId();
		System.out.println(idHeader);
		if (!transactionDao.isDetailBkExists(idHeader, idTask)) {
			throw new ErrorMessage("Transaction Detail Code Not Found");
		}

		TransactionDetail trxDetailDB = transactionDao.findDetailById(trxDetail.getDtlTrxId());
		if (!trxDetailDB.getTransactionHeader().getHdrTrxId().equals(trxDetail.getTransactionHeader().getHdrTrxId())
				&& !trxDetailDB.getTask().getTaskId().equals(trxDetail.getTask().getTaskId())) {
			throw new ErrorMessage("Transaction Detail Code Cannot be Changed");
		}

		transactionDao.saveDetail(trxDetail);
	}

	public TransactionDetail findDetailByID(String dtlTrxId) {
		return transactionDao.findDetailById(dtlTrxId);
	}

	public TransactionDetail findDetailByBK(String trxHdrId, String taskId) {
		return transactionDao.findDetailByBK(trxHdrId, taskId);
	}

	public List<TransactionDetail> findAllDetail() {
		return transactionDao.findAllDetail();
	}

	///////////////////////////// BUSINESS LOGIC //////////////////////

	public List<HomeDetail> findDetailPending(String hdrTrxId) {
		return transactionDao.findDetailPending(hdrTrxId);
	}

	public List<Home> findHeaderOnProgress() {
		return transactionDao.findHeaderOnProggres();
	}

	public String updateStatusDetail() {
		transactionDao.updateStatusDetail();
		return "Status Succesfuly Update";
	}

	public String deleteHeaderDetail(String hdrTrxId) {
		List<TransactionDetail> details = transactionDao.findDetailByHeaderId(hdrTrxId);
		for (TransactionDetail td : details) {
			transactionDao.deleteDetail(td.getDtlTrxId());
		}
		transactionDao.deleteHeader(hdrTrxId);
		return "Header Succesfuly Deleted";

	}

	public String Resolve(String dtlTrxId) {
		transactionDao.resolved(dtlTrxId);
		return "Task Succesfully Resolved";
	}
}
