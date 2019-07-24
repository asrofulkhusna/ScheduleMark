package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.NotifikasiDao;
import com.project.model.Notifikasi;

@Service("NotifikasiService")
public class NotifikasiService {

	@Autowired
	NotifikasiDao notifDao;
	
	@Transactional
	public List<Notifikasi> getNotif(String userCode){
		return notifDao.getNotifikasi(userCode);
		
	}
}
