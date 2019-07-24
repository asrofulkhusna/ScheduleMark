package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.CompanyDao;
import com.project.model.Company;

@Service("CompanyService")
public class CompanyService {

	@Autowired
	CompanyDao companyDao;
	
	@Transactional
	public void insertCompany(Company company) throws ErrorMessage {
		if (companyDao.isIDExsist(company.getCompanyId())) {
			throw new ErrorMessage("Company ID  Already Exists");
		}
		if (companyDao.isBKExsist(company.getCompanyCode())) {
			throw new ErrorMessage("Company Code Already Exists");
		}
		if (company.getCompanyName().isEmpty()) {
			throw new ErrorMessage("Company Name Can't Be Empty");
		}
		companyDao.save(company);

		System.out.println("Company Successfully Added");
	}

	@Transactional
	public void updateCompany(Company company) throws ErrorMessage {
		if (!companyDao.isIDExsist(company.getCompanyId())) {
			throw new ErrorMessage("Company ID Not Found");
		}
		if (!companyDao.isBKExsist(company.getCompanyCode())) {
			throw new ErrorMessage("Company Code Not Found");
		}
		Company companyDB = companyDao.findByID(company.getCompanyId());
		if (!company.getCompanyCode().equals(companyDB.getCompanyCode())) {
			throw new ErrorMessage("The Company Code Cannot Be Changed");
		}
		if (company.getCompanyName().isEmpty()) {
			throw new ErrorMessage("Company Name Can't Be Empty");
		}
		companyDao.save(company);
		System.out.println("Company Successfully Updated");
	}

	@Transactional
	public void deleteCompany(String companyId) throws ErrorMessage {
		if (!companyDao.isIDExsist(companyId)) {
			throw new ErrorMessage("Company ID Not Found");
		}
		companyDao.delete(companyId);
		System.out.println("Company Successfully Deleted");
	}

	// ================FIND BY======================
	@Transactional
	public Company findCompanyByID(String companyId) {
		return companyDao.findByID(companyId);
	}

	@Transactional
	public Company findCompanyByCode(String companyCode) {
		return companyDao.findByBK(companyCode);
	}

	@Transactional
	public List<Company> findAllCompany() {
		return companyDao.getAll();
	}
}
