package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.DefaultSendEmail;

@Repository("DefaultSendEmailDao")
public class DefaultSendEmailDao extends ParentDao{

	@Transactional
	public void save(DefaultSendEmail defaultEmail) {
		super.entityManager.merge(defaultEmail);
	}

	@Transactional
	public void delete(String defaultSendEmailId) {
		DefaultSendEmail sendEmail = findByID(defaultSendEmailId);
		super.entityManager.remove(sendEmail);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public DefaultSendEmail findByID(String defaultSendEmailId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM DefaultSendEmail ");
		sb.append(" WHERE defaultSendEmailId = :defaultSendEmailId");

		List<DefaultSendEmail> sendEmail = super.entityManager.createQuery(sb.toString())
				.setParameter("defaultScheduleId", defaultSendEmailId)
				.getResultList();
		if (sendEmail.size() > 0) {
			return (DefaultSendEmail) sendEmail.get(0);
		} else {
			return new DefaultSendEmail();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public DefaultSendEmail findByBK(String defaultSendEmailCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM DefaultSendEmail ");
		sb.append(" WHERE defaultSendEmailCode = :defaultSendEmailCode");

		List<DefaultSendEmail> sendEmail = super.entityManager.createQuery(sb.toString())
				.setParameter("defaultSendEmailCode", defaultSendEmailCode)
				.getResultList();
		if (sendEmail.size() > 0) {
			return (DefaultSendEmail) sendEmail.get(0);
		} else {
			return new DefaultSendEmail();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<DefaultSendEmail> getAll() {

		List<DefaultSendEmail> sendEmail = super.entityManager
				.createQuery("FROM DefaultSendEmail")
				.getResultList();

		if (sendEmail.size() > 0) {
			return sendEmail;
		} else {
			return new ArrayList<DefaultSendEmail>();
		}
	}

	@Transactional
	public boolean isIDExsist(String defaultSendEmailId) {
		DefaultSendEmail sendEmail = findByID(defaultSendEmailId);
		if (sendEmail.getDefaultSendEmailId()==null) {
			return false;
		} else {
			return true;
		}

	}

	@Transactional
	public boolean isBKExsist(String defaultSendEmailCode) {
		DefaultSendEmail sendEmail = findByBK(defaultSendEmailCode);
		if (sendEmail.getDefaultSendEmailCode()==null) {
			return false;
		} else {
			return true;
		}
	}
	
}
