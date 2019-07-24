package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.EnumDao;
import com.project.model.enumerated.StatusSchedule;
import com.project.model.enumerated.StatusTrxDtl;
import com.project.model.enumerated.StatusTrxHdr;
import com.project.model.enumerated.UserRole;

@Service("EnumService")
public class EnumService {

	@Autowired
	EnumDao enumDao;
	
	public UserRole[] getEnumserRole() {
		return enumDao.getEnumUserRole();
	}
	
	public StatusSchedule[] getEnumStatusSchedule() {
		return enumDao.getEnumStatusSchedule();
	}
	
	public StatusTrxHdr[] getEnumStatusTrxHdr() {
		return enumDao.getEnumStatusTrxHdr();
	}
	
	public StatusTrxDtl[] getEnumStatusTrxDtl() {
		return enumDao.getEnumStatusTrxDtl();
	}
}
