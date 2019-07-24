package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.PayrollMapping;

@Repository("PayrollMappingDao")
public class PayrollMappingDao extends ParentDao{

	@Transactional
	public void save(PayrollMapping payroll) {
		super.entityManager.merge(payroll);
	}

	@Transactional
	public void delete(String payrollMappingId) {
		PayrollMapping payroll = findByID(payrollMappingId);
		super.entityManager.remove(payroll);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public PayrollMapping findByID(String payrollMappingId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM PayrollMapping ");
		sb.append(" WHERE payrollMappingId = :payrollMappingId");

		List<PayrollMapping> payroll = super.entityManager.createQuery(sb.toString())
				.setParameter("payrollMappingId", payrollMappingId)
				.getResultList();
		if (payroll.size() > 0) {
			return (PayrollMapping) payroll.get(0);
		} else {
			return new PayrollMapping();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public PayrollMapping findByBK(String companyId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM PayrollMapping ");
		sb.append(" WHERE company.companyId = :companyId");

		List<PayrollMapping> payroll = super.entityManager.createQuery(sb.toString())
				.setParameter("companyId", companyId)
				.getResultList();
		if (payroll.size() > 0) {
			return (PayrollMapping) payroll.get(0);
		} else {
			return new PayrollMapping();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<PayrollMapping> getAll() {

		List<PayrollMapping> payroll = super.entityManager
				.createQuery("FROM PayrollMapping")
				.getResultList();

		if (payroll.size() > 0) {
			return payroll;
		} else {
			return new ArrayList<PayrollMapping>();
		}
	}

	@Transactional
	public boolean isIDExsist(String payrollMappingId) {
		PayrollMapping payroll = findByID(payrollMappingId);
		if (payroll.getPayrollMappingId()==null) {
			return false;
		} else {
			return true;
		}

	}

	@Transactional
	public boolean isBKExsist(String companyId) {
		PayrollMapping payroll = findByBK(companyId);
		if (payroll.getCompany()==null) {
			return false;
		} else {
			return true;
		}
	}
	
	
}
