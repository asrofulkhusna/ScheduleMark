package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_default_schedule", uniqueConstraints = @UniqueConstraint(columnNames = 
{"company_id", "schedule_id"}))
public class DefaultSchedule {

	@Id
	@Column(name = "default_schedule_id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String defaultScheduleId;
	
	@OneToOne
	@JoinColumn(name = "company_id", referencedColumnName = "company_id")
	private Company company;
	
	@OneToOne
	@JoinColumn(name = "schedule_id", referencedColumnName= "schedule_id")
	private Schedule schedule;

	public String getDefaultScheduleId() {
		return defaultScheduleId;
	}

	public void setDefaultScheduleId(String defaultScheduleId) {
		this.defaultScheduleId = defaultScheduleId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}


	
}
