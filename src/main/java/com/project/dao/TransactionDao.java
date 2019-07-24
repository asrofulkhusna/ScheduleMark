package com.project.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.model.Home;
import com.project.model.HomeDetail;
import com.project.model.TransactionDetail;
import com.project.model.TransactionHeader;
import com.project.model.enumerated.StatusTrxDtl;

@Repository("TransactionDao")
public class TransactionDao extends ParentDao {

	@Transactional
	public void flush() {
		super.entityManager.flush();
	}

	// ===============HEADER===============

	@Transactional
	public void saveHeader(TransactionHeader trxHeader) {
		super.entityManager.merge(trxHeader);
	}

	@Transactional
	public void deleteHeader(String hdrTrxId) {
		TransactionHeader trxHeader = findHeaderByIdForDelete(hdrTrxId);
		super.entityManager.remove(trxHeader);
	}
	
	@Transactional
	public TransactionHeader findHeaderByIdForDelete(String hdrTrxId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM TransactionHeader");
		sb.append(" WHERE hdrTrxId = :hdrTrxId");
		try {
			TransactionHeader header = (TransactionHeader) super.entityManager.createQuery(sb.toString())
					.setParameter("hdrTrxId", hdrTrxId).getSingleResult();
			return (TransactionHeader) header;
		} catch (Exception e) {
			return new TransactionHeader();
		}
	}

	@Transactional
	public TransactionHeader findHeaderById(String hdrTrxId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM TransactionHeader");
		sb.append(" WHERE hdrTrxId = :hdrTrxId");
		try {
			TransactionHeader header = (TransactionHeader) super.entityManager.createQuery(sb.toString())
					.setParameter("hdrTrxId", hdrTrxId).getSingleResult();
			for (TransactionDetail td : header.getTransactionDetails()) {
				td.setTransactionHeader(new TransactionHeader());
			}
			super.entityManager.clear();
			return (TransactionHeader) header;
		} catch (Exception e) {
			return new TransactionHeader();
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public TransactionHeader findHeaderByBK(String trxCode, String yearMonth) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM TransactionHeader");
		sb.append(" WHERE trxCode = :trxCode");
		sb.append(" AND yearMonth = :yearMonth");

		List<TransactionHeader> header = super.entityManager.createQuery(sb.toString()).setParameter("trxCode", trxCode)
				.setParameter("yearMonth", yearMonth).getResultList();
		if (header.size() > 0) {
			return (TransactionHeader) header.get(0);
		} else {
			return new TransactionHeader();
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<TransactionHeader> findAllHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM TransactionHeader");

		List<TransactionHeader> headers = super.entityManager.createQuery(sb.toString()).getResultList();
		for (TransactionHeader header : headers) {
			for (TransactionDetail detail : header.getTransactionDetails()) {
				detail.setTransactionHeader(new TransactionHeader());
				detail.getTask().getSchedule().getCompany().setCompanyLogo(null);
			}
		}
		super.entityManager.clear();
		if (headers.size() > 0) {
			return headers;
		} else {
			return new ArrayList<TransactionHeader>();
		}
	}

	//////////////////////////////////////////// DETAIL ////////////////////

	@Transactional
	public void saveDetail(TransactionDetail trxDetail) {
		super.entityManager.merge(trxDetail);
	}

	@Transactional
	public void deleteDetail(String dtlTrxId) {
		TransactionDetail trxDetail = findDetailById(dtlTrxId);
		super.entityManager.remove(trxDetail);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public TransactionDetail findDetailById(String dtlTrxId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM TransactionDetail");
		sb.append(" WHERE dtlTrxId = :dtlTrxId");

		List<TransactionDetail> detail;
		detail = super.entityManager.createQuery(sb.toString())
				.setParameter("dtlTrxId", dtlTrxId)
				.getResultList();
		for (TransactionDetail td : detail) {
			td.getTransactionHeader().setTransactionDetails(new ArrayList<TransactionDetail>());
			td.getTask().getSchedule().getCompany().setCompanyLogo(null);
		}
		super.entityManager.clear();
		if (detail.size() > 0) {
			return (TransactionDetail) detail.get(0);
		} else {
			TransactionDetail td = new TransactionDetail();
//			td.setStatus(StatusTrxDtl.PENDING);
			return td;
		}
	}

	@Transactional
	public TransactionDetail findDetailByBK(String hdrTrxId, String taskId) {

		StringBuilder sb = new StringBuilder();
		sb.append(" FROM TransactionDetail ");
		sb.append(" WHERE transactionHeader.hdrTrxId = :hdrTrxId");
		sb.append(" AND task.taskId = :taskId");
		TransactionDetail detail;
		try {
			detail = (TransactionDetail) super.entityManager
					.createQuery(sb.toString()).setParameter("hdrTrxId", hdrTrxId).setParameter("taskId", taskId)
					.getSingleResult();
			detail.getTransactionHeader()
			.setTransactionDetails(new ArrayList<TransactionDetail>());;
			super.entityManager.clear();
		} catch (Exception e) {
			detail = new TransactionDetail();
			detail.setStatus(StatusTrxDtl.PENDING);
		}
		return detail;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<TransactionDetail> findAllDetail() {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM TransactionDetail");

		List<TransactionDetail> detail;
		try {
			detail = super.entityManager
//					.createNativeQuery(sb.toString(), TransactionDetail.class)
					.createQuery(sb.toString()).getResultList();
			for (TransactionDetail td : detail) {
				td.setTransactionHeader(new TransactionHeader());
				td.getTask().getSchedule().getCompany().setCompanyLogo(null);
			}
			super.entityManager.clear();
		} catch (Exception e) {
			detail = new ArrayList<TransactionDetail>();
		}
		return detail;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<TransactionDetail> findDetailByHeaderId(String hdrTrxId){
		StringBuilder sb = new StringBuilder();
		sb.append("From TransactionDetail ");
		sb.append(" WHERE transactionHeader.hdrTrxId = :hdrTrxId");
		List<TransactionDetail> detail;
		try {
			detail = super.entityManager
					.createQuery(sb.toString())
					.setParameter("hdrtrxId", hdrTrxId)
					.getResultList();
			for (TransactionDetail td : detail) {
				td.setTransactionHeader(new TransactionHeader());
				td.getTask().getSchedule().getCompany().setCompanyLogo(null);
			}
			super.entityManager.clear();
		} catch (Exception e) {
			detail = new ArrayList<TransactionDetail>();
		}
		return detail;
		
	}

	/////////////////////////////////////// IS EXISTS
	/////////////////////////////////////// //////////////////////////////////////////

	@Transactional
	public boolean isHeaderIdExists(String hdrTrxId) {
		TransactionHeader header = findHeaderById(hdrTrxId);
		if (header.getHdrTrxId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@Transactional
	public boolean isHeaderBkExists(String trxCode, String yearMonth) {
		TransactionHeader header = findHeaderByBK(trxCode, yearMonth);
		if (header.getTrxCode() == null) {
			return false;
		} else {
			return true;
		}
	}

	@Transactional
	public boolean isDetailIdExists(String dtlTrxId) {
		TransactionDetail detail = findDetailById(dtlTrxId);
		if (detail.getDtlTrxId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@Transactional
	public boolean isDetailBkExists(String trxHdrId, String taskId) {
		TransactionDetail detail = findDetailByBK(trxHdrId, taskId);
		if (detail.getTransactionHeader().getHdrTrxId() == null && detail.getTask().getTaskId() == null) {
			return false;
		} else {
			return true;
		}
	}

	//////////////////////////// BUSSINES LOGIC///////////////
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Home> findHeaderOnProggres() {
		Calendar cal = Calendar.getInstance();
		String namaBulan[] = { "JANUARI", "FEBRUARI", "MARET", "APRIL", "MEI", "JUNI", "JULI", "AGUSTUS", "SEPTEMBER",
				"OKTOBER", "NOVEMBER", "DESEMBER" };
		int bulan = cal.get(Calendar.MONTH);
		String yearMonth = cal.get(Calendar.YEAR) + "-" + namaBulan[bulan];

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT th.hdr_trx_id , th.trx_code, th.year_month,c.company_name,s.schedule_code, th.status");
		sb.append(" FROM tbl_transaction_header as th");
		sb.append(" INNER JOIN tbl_transaction_detail AS td ");
		sb.append(" ON th.hdr_trx_id = td.hdr_trx_id ");
		sb.append(" INNER JOIN tbl_task AS t ");
		sb.append(" ON td.task_id = t.task_id");
		sb.append(" INNER JOIN tbl_schedule AS s ");
		sb.append(" ON t.schedule_id = s.schedule_id");
		sb.append(" INNER JOIN tbl_company AS c ");
		sb.append(" ON s.company_id = c.company_id");
		sb.append(" WHERE th.status = 'ON_PROGRESS'");
		sb.append(" AND th.year_month = :yearMonth");
		sb.append(" GROUP BY th.hdr_trx_id, th.trx_code, th.year_month, c.company_name, s.schedule_code, th.status ");
		sb.append(" ORDER BY th.trx_code ASC");
		List<Object[]> onProgress = super.entityManager.createNativeQuery(sb.toString())
				.setParameter("yearMonth", yearMonth).getResultList();

		ArrayList<Home> home = new ArrayList<Home>();
		for (Object[] obj : onProgress) {
			Home hdr = new Home();
			hdr.setHdrTrxId(obj[0].toString());
			hdr.setTrxCode(obj[1].toString());
			hdr.setYearMonth(obj[2].toString());
			hdr.setCompanyName(obj[3].toString());
			hdr.setScheduleCode(obj[4].toString());
			hdr.setStatus(obj[5].toString());
			home.add(hdr);
		}
		if (home.size() > 0) {
			return home;
		} else {
			return new ArrayList<Home>();
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<HomeDetail> findDetailPending(String hdrTrxId) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT td.dtl_trx_id,th.trx_code, s.schedule_code, t.task_code, t.task_description, t.start_date, t.finish_date, td.date_realization, td.notes, td.status");
		sb.append(" FROM tbl_transaction_detail as td");
		sb.append(" INNER JOIN tbl_transaction_header as th");
		sb.append(" ON td.hdr_trx_id = th.hdr_trx_id");
		sb.append(" INNER JOIN tbl_task as t");
		sb.append(" ON td.task_id = t.task_id");
		sb.append(" INNER JOIN tbl_schedule AS s ");
		sb.append(" ON t.schedule_id = s.schedule_id");
		sb.append(" WHERE td.status = 'PENDING'");
		sb.append(" AND td.hdr_trx_id = :hdrTrxId");
		sb.append(
				" GROUP BY td.dtl_trx_id,th.trx_code, s.schedule_code, t.task_code, t.task_description, t.start_date, t.finish_date, td.date_realization, td.notes, td.status");
		sb.append(" ORDER BY td.dtl_trx_id,th.trx_code, s.schedule_code, t.task_code ASC");
		List<Object[]> detailPending = super.entityManager.createNativeQuery(sb.toString())
				.setParameter("hdrTrxId", hdrTrxId).getResultList();

		ArrayList<HomeDetail> details = new ArrayList<HomeDetail>();
		for (Object[] obj : detailPending) {
			HomeDetail dtlHome = new HomeDetail();
			dtlHome.setDtlTRxId(obj[0].toString());
			dtlHome.setTrxCode(obj[1].toString());
			dtlHome.setScheduleCode(obj[2].toString());
			dtlHome.setTaskCode(obj[3].toString());
			dtlHome.setTaskDescription(obj[4].toString());
			dtlHome.setStartDate(Integer.parseInt(obj[5].toString()));
			dtlHome.setFinishDate(Integer.parseInt(obj[6].toString()));
			dtlHome.setDateRealized((Date) obj[7]);
			if (obj[8] != null) {
				dtlHome.setNotes(obj[8].toString());
			}
			dtlHome.setStatus(obj[9].toString());
			details.add(dtlHome);
		}
		if (details.size() > 0) {
			return details;
		} else {
			return new ArrayList<HomeDetail>();
		}

	}
	
	@SuppressWarnings("unused")
	@Transactional
	public void updateStatusDetail() {
		StringBuilder sb = new StringBuilder();
		sb.append("	UPDATE tbl_transaction_detail");
		sb.append("	SET status ='REALIZED'");
		sb.append(" WHERE date_realization IS NOT NULL");
		sb.append(" AND status = 'PENDING'");
		
		int updateDetailStatus = super.entityManager
				.createNativeQuery(sb.toString())
				.executeUpdate();
	}
	
	@SuppressWarnings("unused")
	@Transactional
	public void resolved(String dtlTrxId) {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();

		sb.append("	UPDATE tbl_transaction_detail");
		sb.append("	SET status ='RESOLVED'");
		sb.append(" ,date_realization = :tanggal");
		sb.append(" ,notes = 'RESOLVED BY PAYROLL'");
		sb.append(" WHERE date_realization IS NULL");
		sb.append(" AND dtl_trx_id = :dtlTrxId");
		sb.append(" AND status = 'PENDING'");
		
		int updateDetailStatus = super.entityManager
				.createNativeQuery(sb.toString())
				.setParameter("tanggal", cal)
				.setParameter("dtlTrxId", dtlTrxId)
				.executeUpdate();
	}
	
}
