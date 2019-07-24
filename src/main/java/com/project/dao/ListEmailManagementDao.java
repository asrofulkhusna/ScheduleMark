package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.ListEmailManagement;

@Repository("ListEmailManagementDao")
public class ListEmailManagementDao extends ParentDao {

	@Transactional
	public void save(ListEmailManagement emailManagement) {
		super.entityManager.merge(emailManagement);
	}

	@Transactional
	public void delete(String emailId) {
		ListEmailManagement emailManagement = findByID(emailId);
		super.entityManager.remove(emailManagement);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public ListEmailManagement findByID(String emailId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM ListEmailManagement ");
		sb.append(" WHERE emailId = :emailId");

		List<ListEmailManagement> mail = super.entityManager.createQuery(sb.toString())
				.setParameter("emailId", emailId)
				.getResultList();
		if (mail.size() > 0) {
			return (ListEmailManagement) mail.get(0);
		} else {
			return new ListEmailManagement();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public ListEmailManagement findByBK(String emailCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM ListEmailManagement ");
		sb.append(" WHERE emailCode = :emailCode");

		List<ListEmailManagement> mail = super.entityManager.createQuery(sb.toString())
				.setParameter("emailCode", emailCode)
				.getResultList();
		if (mail.size() > 0) {
			return (ListEmailManagement) mail.get(0);
		} else {
			return new ListEmailManagement();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ListEmailManagement> getAll() {

		List<ListEmailManagement> mail = super.entityManager
				.createQuery("FROM ListEmailManagement")
				.getResultList();

		if (mail.size() > 0) {
			return mail;
		} else {
			return new ArrayList<ListEmailManagement>();
		}
	}

	@Transactional
	public boolean isIDExsist(String emailId) {
		ListEmailManagement sendEmail = findByID(emailId);
		if (sendEmail.getEmailId()==null) {
			return false;
		} else {
			return true;
		}

	}

	@Transactional
	public boolean isBKExsist(String emailCode) {
		ListEmailManagement sendEmail = findByBK(emailCode);
		if (sendEmail.getEmailCode()==null) {
			return false;
		} else {
			return true;
		}
	}
}
