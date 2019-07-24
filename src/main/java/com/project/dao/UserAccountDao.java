package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.UserAccount;
import com.project.model.enumerated.UserRole;

@Repository("UserAccountDao")
public class UserAccountDao extends ParentDao {

	@Transactional
	public void save(UserAccount user) {
		super.entityManager.merge(user);
	}

	@Transactional
	public void delete(String userID) {
		UserAccount user = findByID(userID);
		super.entityManager.remove(user);

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserAccount findByID(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM UserAccount ");
		sb.append(" WHERE userId = :userId");

		List<UserAccount> user = super.entityManager.createQuery(sb.toString()).setParameter("userId", userId)
				.getResultList();
		if (user.size() > 0) {
			return (UserAccount) user.get(0);
		} else {
			UserAccount account = new UserAccount();
			account.setUserRole(UserRole.ADMIN);
			return account;
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserAccount findByBK(String userCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM UserAccount ");
		sb.append(" WHERE userCode = :userCode");

		List<UserAccount> user = super.entityManager.createQuery(sb.toString()).setParameter("userCode", userCode)
				.getResultList();
		if (user.size() > 0) {
			return (UserAccount) user.get(0);
		} else {
			UserAccount account = new UserAccount();
			account.setUserRole(UserRole.ADMIN);
			return account;
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserAccount findByEmail(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM UserAccount ");
		sb.append(" WHERE email = :email");

		List<UserAccount> user = super.entityManager.createQuery(sb.toString()).setParameter("email", email)
				.getResultList();
		if (user.size() > 0) {
			return (UserAccount) user.get(0);
		} else {
			UserAccount account = new UserAccount();
			account.setUserRole(UserRole.ADMIN);
			return account;
		}

	}
	

	@SuppressWarnings("unchecked")
	@Transactional
	public UserAccount Login(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM UserAccount ");
		sb.append(" WHERE email = :email");

		List<UserAccount> user = super.entityManager.createQuery(sb.toString()).setParameter("email", email)
				.getResultList();
		if (user.size() > 0) {
			return (UserAccount) user.get(0);
		} else {
			UserAccount account = new UserAccount();
			return account;
		}

	}
	

	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserAccount> getAll() {

		List<UserAccount> user = super.entityManager.createQuery("FROM UserAccount").getResultList();

		if (user.size() > 0) {
			return user;
		} else {
			return new ArrayList<UserAccount>();
		}
	}

	@Transactional
	public boolean isIDExsist(String userId) {
		if(findByID(userId).getUserId() == null) {
			return false;
		}
		else {
			return true;
		}

	}

	@Transactional
	public boolean isBKExsist(String userCode) {
		if(findByBK(userCode).getUserCode() == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	@Transactional
	public boolean isEmailExist(String email) {
		if(findByEmail(email).getEmail() == null) {
			return false;
		}
		else {
			return true;
		}
	}
}
