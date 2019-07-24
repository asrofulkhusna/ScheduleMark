package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.Company;

@Repository("CompanyDao")
public class CompanyDao extends ParentDao {

	/**
	 * method untuk menyimpan company
	 * @param company entity
	 */
	@Transactional
	public void save(Company company) {
		super.entityManager.merge(company);
	}

	@Transactional
	public void delete(String companyId) {
		Company company = findByID(companyId);
		super.entityManager.remove(company);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Company findByID(String companyId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Company ");
		sb.append(" WHERE companyId = :companyId");

		List<Company> company = super.entityManager.createQuery(sb.toString())
				.setParameter("companyId", companyId)
				.getResultList();
		if (company.size() > 0) {
			return (Company) company.get(0);
		} else {
			return new Company();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Company findByBK(String companyCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Company ");
		sb.append(" WHERE companyCode = :companyCode");

		List<Company> company = super.entityManager
				.createQuery(sb.toString())
				.setParameter("companyCode", companyCode)
				.getResultList();
		if (company.size() > 0) {
			return (Company) company.get(0);
		} else {
			return new Company();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Company> getAll() {

		List<Company> company = super.entityManager
				.createQuery("FROM Company")
				.getResultList();

		if (company.size() > 0) {
			return company;
		} else {
			return new ArrayList<Company>();
		}
	}

	@Transactional
	public boolean isIDExsist(String companyId) {
		Company company = findByID(companyId);
		if (company.getCompanyId()==null) {
			return false;
		} else {
			return true;
		}

	}

	@Transactional
	public boolean isBKExsist(String companyCode) {
		Company company = findByBK(companyCode);
		if (company.getCompanyCode() == null) {
			return false;
		} else {
			return true;
		}
	}
}
