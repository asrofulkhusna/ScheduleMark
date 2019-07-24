package com.project.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.model.enumerated.StatusSchedule;
import com.project.model.enumerated.StatusTrxDtl;
import com.project.model.enumerated.StatusTrxHdr;
import com.project.model.enumerated.UserRole;

@Repository("EnumDao")
public class EnumDao extends ParentDao{

	@Transactional
	public UserRole[] getEnumUserRole() {
		UserRole[] userRole = UserRole.values();
		return userRole;
	}
	
	@Transactional
	public StatusSchedule[] getEnumStatusSchedule() {
		StatusSchedule[] statusSchedule = StatusSchedule.values();
		return statusSchedule;
	}
	
	@Transactional
	public StatusTrxHdr [] getEnumStatusTrxHdr() {
		StatusTrxHdr[] statusTrxHdr = StatusTrxHdr.values();
		return statusTrxHdr;
	}
	
	@Transactional
	public StatusTrxDtl[] getEnumStatusTrxDtl() {
		StatusTrxDtl[] statusTrxDtl = StatusTrxDtl.values();
		return statusTrxDtl;
	}
 }
