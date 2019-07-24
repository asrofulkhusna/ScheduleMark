package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.PagginationDao;
import com.project.model.paggination.PagginationCompany;
import com.project.model.paggination.PagginationSchedule;
import com.project.model.paggination.PagginationUserAccount;
import com.project.model.paggination.pagginationHome;

@Service
public class PagginationService {

	@Autowired
	PagginationDao pagginationDao;

	
	@Transactional
	public List<PagginationSchedule> findAllScheduleByPaggination(int page, int size) {
		return pagginationDao.getAllScheduleByPaggination(page, size);

	}
	
	@Transactional
	public List<PagginationCompany> findAllCompanyByPaggination(int page, int size) {
		return pagginationDao.getAllCompanyByPaggination(page, size);

	}
	
	@Transactional
	public List<PagginationUserAccount> findAllUserByPaggination(int page, int size) {
		return pagginationDao.getAllUserByPaggination(page, size);

	}
	
	@Transactional
	public List<pagginationHome> findAllHomeByPaggination(int page, int size) {
		return pagginationDao.getAllHomeByPaggination(page, size);

	}
}
