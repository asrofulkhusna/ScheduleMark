package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.PayrollMappingDao;
import com.project.model.PayrollMapping;

@Service("PayrolMappingService")
public class PayrollMappingService {

	@Autowired
	PayrollMappingDao payrollDao;
	

	@Transactional
	public void insertPayrollMapping(PayrollMapping payroll) throws ErrorMessage {
		if (payrollDao.isIDExsist(payroll.getPayrollMappingId())) {
			throw new ErrorMessage("Payroll Mapping ID  Already Exists");
		}
		if (payrollDao.isBKExsist(payroll.getCompany().getCompanyId())) {
			throw new ErrorMessage("Company ID Already Exists");
		}
		if (payroll.getCompany().getCompanyId().isEmpty()) {
			throw new ErrorMessage("Company ID Cannot Be Empaty");
		}

		payrollDao.save(payroll);

		System.out.println(" Payroll Mapping Successfully Added");
	}

	@Transactional
	public void updatePayrollMapping(PayrollMapping payroll) throws ErrorMessage {
		if (!payrollDao.isIDExsist(payroll.getPayrollMappingId())) {
			throw new ErrorMessage("Payroll Mapping ID Not Found");
		}
		if (!payrollDao.isBKExsist(payroll.getCompany().getCompanyId())) {
			throw new ErrorMessage("Company Not Found");
		}
		PayrollMapping payrollDB = payrollDao.findByID(payroll.getPayrollMappingId());
		if (!payroll.getCompany().getCompanyId().equals(payrollDB.getCompany().getCompanyId())) {
			throw new ErrorMessage("The Company Cannot Be Changed");
		}

		payrollDao.save(payroll);
		System.out.println("Payroll Mapping Successfully Updated");
	}

	@Transactional
	public void deletePayrollMapping(String payrollMappingId) throws ErrorMessage {
		if (!payrollDao.isIDExsist(payrollMappingId)) {
			throw new ErrorMessage("Payroll Mapping ID Not Found");
		}
		payrollDao.delete(payrollMappingId);
		System.out.println("Payroll Mapping Successfully Deleted");
	}

	// ================FIND BY======================
	@Transactional
	public PayrollMapping findPayrollMappingByID(String payrollMappigId) {
		return payrollDao.findByID(payrollMappigId);
	}

	@Transactional
	public PayrollMapping findPayrollMappingByCode(String companyId) {
		return payrollDao.findByBK(companyId);
	}

	@Transactional
	public List<PayrollMapping> findAllPayrollMapping() {
		return payrollDao.getAll();
	}
}
