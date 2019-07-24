package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.project.model.enumerated.StatusSchedule;

@Entity
@Table(name="tbl_schedule", uniqueConstraints=@UniqueConstraint(columnNames= {"schedule_code","company_id"}))
public class Schedule {

	@Id
	@Column(name = "schedule_id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String scheduleId;
	
	@Column(name="schedule_code")
	private String scheduleCode;
	
	@OneToOne
	@JoinColumn(name="company_id", referencedColumnName="company_id")
	private Company company;
	
	@Enumerated(EnumType.STRING)
	private StatusSchedule statusSchedule;
	
	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getScheduleCode() {
		return scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public StatusSchedule getStatusSchedule() {
		return statusSchedule;
	}

	public void setStatusSchedule(StatusSchedule statusSchedule) {
		this.statusSchedule = statusSchedule;
	}

}
