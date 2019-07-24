package com.project.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.ErrorHandling.ErrorMessage;
import com.project.dao.TransactionDao;
import com.project.model.Company;
import com.project.model.DefaultSchedule;
import com.project.model.PayrollMapping;
import com.project.service.CompanyService;
import com.project.service.DefaultScheduleService;
import com.project.service.PayrollMappingService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@Autowired
	TransactionDao transactionDao;

	@Autowired
	DefaultScheduleService dfltScheduleService;
	
	@Autowired
	PayrollMappingService pmService;

	@PostMapping("/add/")
	public ResponseEntity<?> addCompany(@RequestParam String company, @RequestParam(name = "file", required= false) MultipartFile file)
			throws Exception, JsonMappingException, IOException, ErrorMessage {
		try {
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
			Company companyObject = mapper.readValue(company, Company.class);
			companyObject.setCompanyLogo(file.getBytes());
			companyService.insertCompany(companyObject);
			Company comp = companyService.findCompanyByCode(companyObject.getCompanyCode());
			DefaultSchedule dflt = new DefaultSchedule();
			dflt.setDefaultScheduleId("uuid");
			dflt.setCompany(comp);
			dfltScheduleService.insertDefaultSchedule(dflt);
			PayrollMapping pm = new PayrollMapping();
			pm.setPayrollMappingId("uuid");
			pm.setCompany(comp);
			pmService.insertPayrollMapping(pm);

		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Company Successfully Added");
	}

	@PostMapping("/")
	public ResponseEntity<?> insertCompany(@RequestBody Company company) throws ErrorMessage {
		try {
			companyService.insertCompany(company);
			transactionDao.flush();
			Company comp = companyService.findCompanyByCode(company.getCompanyCode());
			DefaultSchedule dflt = new DefaultSchedule();
			dflt.setDefaultScheduleId("uuid");
			dflt.setCompany(comp);
			dfltScheduleService.insertDefaultSchedule(dflt);
		} catch (Exception e) {
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Company Successfully Added");
	}

	@PatchMapping("/updatelogo/{companyCode}")
	public ResponseEntity<?> updateLogoByPatch(@RequestParam("file") MultipartFile[] file,
			@PathVariable String companyCode) {
		try {
			Company company = companyService.findCompanyByCode(companyCode);
			byte[] logo = null;
			for (MultipartFile comp : file) {
				logo = comp.getBytes();
			}
			company.setCompanyLogo(logo);
			companyService.updateCompany(company);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Company Logo Successfully Added");

	}

	@PutMapping("/")
	public ResponseEntity<?> updateCompany(@RequestParam String company, @RequestParam(name = "file", required= false) MultipartFile file) throws ErrorMessage {
		try {
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
			Company companyObject = mapper.readValue(company, Company.class);
			if (file == null) {
				Company comp = companyService.findCompanyByID(companyObject.getCompanyId());
				comp.setCompanyId(companyObject.getCompanyId());
				comp.setCompanyCode(companyObject.getCompanyCode());
				comp.setCompanyName(companyObject.getCompanyName());
				companyService.updateCompany(comp);
			}
			if(file != null) {
				System.out.println("TIDAK SAMA DENGAN NULL");
				companyObject.setCompanyLogo(file.getBytes());
				companyService.updateCompany(companyObject);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Company Successfully Updated");
	}

	@DeleteMapping("/{companyId}")
	public ResponseEntity<?> deleteCompany(@PathVariable String companyId) throws ErrorMessage {
		try {
			DefaultSchedule schedule = dfltScheduleService.findScheduleByCode(companyId);
			PayrollMapping pm = pmService.findPayrollMappingByCode(companyId);
			System.out.println(schedule.getDefaultScheduleId());
			System.out.println(pm.getPayrollMappingId());
			pmService.deletePayrollMapping(pm.getPayrollMappingId());
			dfltScheduleService.deleteDefaultSchedule(schedule.getDefaultScheduleId());
			companyService.deleteCompany(companyId);		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Company Successfully Deleted");
	}

	@GetMapping("/id/{companyId}")
	public ResponseEntity<?> getCompanyByID(@PathVariable String companyId) {
		return ResponseEntity.ok(companyService.findCompanyByID(companyId));
	}

	@GetMapping("/code/{companyCode}")
	public ResponseEntity<?> getCompanyByCode(@PathVariable String companyCode) {
		return ResponseEntity.ok(companyService.findCompanyByCode(companyCode));
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllCompany() {
		return ResponseEntity.ok(companyService.findAllCompany());
	}
}
