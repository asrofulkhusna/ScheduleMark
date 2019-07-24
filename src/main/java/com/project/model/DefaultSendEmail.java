package com.project.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tbl_default_send_email", uniqueConstraints= @UniqueConstraint(columnNames="default_send_email_code"))
public class DefaultSendEmail {

	@Id
	@Column(name = "default_send_email_id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String defaultSendEmailId;
	
	@Column(name="default_send_email_code")
	private String defaultSendEmailCode;
	
	@Column(name ="default_send_email_date")
	private int defaultSendEmailDate;
	
	@Column(name="default_send_email_time")
	private Time defaultSendEmailTime;

	public String getDefaultSendEmailId() {
		return defaultSendEmailId;
	}

	public void setDefaultSendEmailId(String defaultSendEmailId) {
		this.defaultSendEmailId = defaultSendEmailId;
	}

	public String getDefaultSendEmailCode() {
		return defaultSendEmailCode;
	}

	public void setDefaultSendEmailCode(String defaultSendEmailCode) {
		this.defaultSendEmailCode = defaultSendEmailCode;
	}

	public int getDefaultSendEmailDate() {
		return defaultSendEmailDate;
	}

	public void setDefaultSendEmailDate(int defaultSendEmailDate) {
		this.defaultSendEmailDate = defaultSendEmailDate;
	}

	public Time getDefaultSendEmailTime() {
		return defaultSendEmailTime;
	}

	public void setDefaultSendEmailTime(Time defaultSendEmailTime) {
		this.defaultSendEmailTime = defaultSendEmailTime;
	}
	
}
