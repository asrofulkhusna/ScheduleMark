package com.project.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.project.model.enumerated.StatusTrxHdr;

@Entity
@Table(name="tbl_transaction_header", uniqueConstraints=@UniqueConstraint(columnNames= {"trx_code","year_month"}))
public class TransactionHeader {

	@Id
	@Column(name = "hdr_trx_id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String hdrTrxId;
	
	@Column(name="trx_code")
	private String trxCode;
	
	@Column(name="year_month")
	private String yearMonth;
		
	@Enumerated(EnumType.STRING)
	private StatusTrxHdr status;
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER,
			mappedBy = "transactionHeader")
	private List<TransactionDetail> transactionDetails;

	public String getHdrTrxId() {
		return hdrTrxId;
	}

	public void setHdrTrxId(String hdrTrxId) {
		this.hdrTrxId = hdrTrxId;
	}

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public StatusTrxHdr getStatus() {
		return status;
	}

	public void setStatus(StatusTrxHdr status) {
		this.status = status;
	}

	public List<TransactionDetail> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	
}
