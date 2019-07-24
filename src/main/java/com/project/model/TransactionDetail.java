package com.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.project.model.enumerated.StatusTrxDtl;

@Entity
@Table(name="tbl_transaction_detail",uniqueConstraints=@UniqueConstraint(columnNames=
{"hdr_trx_id","task_id"}))
public class TransactionDetail {

	@Id
	@Column(name = "dtl_trx_id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String dtlTrxId;
	
	@ManyToOne
	@JoinColumn(name = "hdr_trx_id", referencedColumnName = "hdr_trx_id")
	private TransactionHeader transactionHeader;
	
	@OneToOne
	@JoinColumn(name="task_id", referencedColumnName="task_id")
	private Task task;
	
	@Column(name="date_realization")
	private Date dateRealization;
	
	@Column(name="notes")
	private String notes;
	
	@Enumerated(EnumType.STRING)
	private StatusTrxDtl status;

	public String getDtlTrxId() {
		return dtlTrxId;
	}

	public void setDtlTrxId(String dtlTrxId) {
		this.dtlTrxId = dtlTrxId;
	}

	public TransactionHeader getTransactionHeader() {
		return transactionHeader;
	}

	public void setTransactionHeader(TransactionHeader transactionHeader) {
		this.transactionHeader = transactionHeader;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Date getDateRealization() {
		return dateRealization;
	}

	public void setDateRealization(Date dateRealization) {
		this.dateRealization = dateRealization;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public StatusTrxDtl getStatus() {
		return status;
	}

	public void setStatus(StatusTrxDtl status) {
		this.status = status;
	}
	
	
}
