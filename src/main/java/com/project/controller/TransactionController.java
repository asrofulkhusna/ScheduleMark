package com.project.controller;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ErrorHandling.ErrorMessage;
import com.project.model.Realization;
import com.project.model.TransactionDetail;
import com.project.model.TransactionHeader;
import com.project.model.enumerated.StatusTrxDtl;
import com.project.service.CreateTransactionService;
import com.project.service.TransactionService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@Autowired
	CreateTransactionService createTransactionService;

	@PostMapping("/")
	public ResponseEntity<?> insert(@RequestBody TransactionHeader trxHeader) throws ErrorMessage {
		try {
			transactionService.insertHeader(trxHeader);
			TransactionHeader trxHdr = transactionService.findHeaderByBK(trxHeader.getTrxCode(),
					trxHeader.getYearMonth());
			for (TransactionDetail detail : trxHeader.getTransactionDetails()) {
				detail.setTransactionHeader(trxHdr);
				transactionService.insertDetail(detail);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Transaction Successfully Added");

	}

	@DeleteMapping("/{hdrTrxId}")
	public ResponseEntity<?> deleteHeaderByID(@PathVariable String hdrTrxId) throws ErrorMessage {
		return ResponseEntity.ok(transactionService.deleteHeaderDetail(hdrTrxId));
	}

	@DeleteMapping("/header/{hdrTrxId}")
	public ResponseEntity<?> deleteHeader(@PathVariable String hdrTrxId) throws ErrorMessage {
		return ResponseEntity.ok(transactionService.deleteHeader(hdrTrxId));
	}

	///////////////////// HEADER////////////
	@GetMapping("/header/id/{trxHdrId}")
	public ResponseEntity<?> getHeaderByID(@PathVariable String trxHdrId) {
		return ResponseEntity.ok(transactionService.findHeaderByID(trxHdrId));
	}

	@GetMapping("/header/code/{trxCode}/{yearMonth}")
	public ResponseEntity<?> getHeaderByCode(@PathVariable String trxCode, @PathVariable String yearMonth) {
		return ResponseEntity.ok(transactionService.findHeaderByBK(trxCode, yearMonth));
	}

	@GetMapping("/header/")
	public ResponseEntity<?> getAllHeader() {
		return ResponseEntity.ok(transactionService.findAllHeader());
	}

	///////////////////////////// DETAIL///////////////
	@GetMapping("/detail/id/{dtlTrxId}")
	public ResponseEntity<?> getDetailByID(@PathVariable String dtlTrxId) {
		return ResponseEntity.ok(transactionService.findDetailByID(dtlTrxId));
	}

	@GetMapping("/detail/code/{trxHdrId}/{taskId}")
	public ResponseEntity<?> getDetailByCode(@PathVariable String trxHdrId, @PathVariable String taskId) {
		return ResponseEntity.ok(transactionService.findDetailByBK(trxHdrId, taskId));
	}

	@GetMapping("/detail/")
	public ResponseEntity<?> getAllDetail() {
		return ResponseEntity.ok(transactionService.findAllDetail());
	}

	@GetMapping("/")
	public ResponseEntity<?> CreaeTransaction() throws ErrorMessage {
		try {
			return ResponseEntity.ok(createTransactionService.CreateTransaction());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	///////////////////////////////////////////////////////////
	@GetMapping("/header/onprogress/")
	public ResponseEntity<?> findHeaderOnProgress() {
		return ResponseEntity.ok(transactionService.findHeaderOnProgress());
	}

	@GetMapping("/detail/pending/{hdrTrxId}")
	public ResponseEntity<?> findDetailPending(@PathVariable String hdrTrxId) {
		return ResponseEntity.ok(transactionService.findDetailPending(hdrTrxId));
	}

	@GetMapping("/updatestatusdetail/")
	public ResponseEntity<?> updateStatusDetail() {
		return ResponseEntity.ok(transactionService.updateStatusDetail());
	}

	@GetMapping("/resolve/{dtlTrxId}")
	public ResponseEntity<?> resolve(@PathVariable String dtlTrxId) {
		return ResponseEntity.ok(transactionService.Resolve(dtlTrxId));
	}

	@PatchMapping("/realized/{dtlTrxId}")
	public ResponseEntity<?> realized(@PathVariable String dtlTrxId, @RequestBody Realization realized)
			throws ErrorMessage {
		try {
			TransactionDetail detail = transactionService.findDetailByID(dtlTrxId);
			LocalDate date = LocalDate.parse(realized.getDateRealized());
			Date tanggal = java.sql.Date.valueOf(date);
			detail.setDateRealization(tanggal);
			detail.setNotes(realized.getNotes());
			transactionService.updateDetail(detail);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Transaction Successfully Realization");

	}
	
	@PatchMapping("/realization/")
	public ResponseEntity<?> realization(@RequestBody Realization realized)
			throws ErrorMessage {
		try {
			TransactionDetail detail = transactionService.findDetailByID(realized.getDtlTRxId());
			LocalDate date = LocalDate.parse(realized.getDateRealized());
			Date tanggal = java.sql.Date.valueOf(date);
			detail.setDateRealization(tanggal);
			detail.setNotes(realized.getNotes());
			detail.setStatus(StatusTrxDtl.REALIZED);
			transactionService.updateDetail(detail);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Transaction Successfully Realization");

	}
}
