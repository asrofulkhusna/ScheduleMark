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
@Table(name="tbl_payroll_mapping", uniqueConstraints=@UniqueConstraint(columnNames="company_id"))
public class PayrollMapping {

	@Id
	@Column(name = "mapping_id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String payrollMappingId;
		
	@OneToOne
	@JoinColumn(name="company_id", referencedColumnName="company_id")
	private Company company;

	@OneToOne
	@JoinColumn(name="user_id", referencedColumnName="user_id")
	private UserAccount user;

	
	public String getPayrollMappingId() {
		return payrollMappingId;
	}

	public void setPayrollMappingId(String payrollMappingId) {
		this.payrollMappingId = payrollMappingId;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
}
