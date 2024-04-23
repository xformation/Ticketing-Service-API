package com.synectiks.tkt.controller;

import com.synectiks.tkt.config.Constants;
import com.synectiks.tkt.domain.Agent;
import com.synectiks.tkt.domain.Company;
import com.synectiks.tkt.domain.CompanyContactVo;
import com.synectiks.tkt.domain.Contact;
import com.synectiks.tkt.repository.AgentRepository;
import com.synectiks.tkt.repository.CompanyRepository;
import com.synectiks.tkt.repository.ContactRepository;
import com.synectiks.tkt.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

/**
 * REST controller for managing {@link Company}.
 */
@RestController
@RequestMapping("/api")
public class CompanyController {

	private final Logger log = LoggerFactory.getLogger(CompanyController.class);

	private static final String ENTITY_NAME = "company";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private AgentRepository agentRepository;

	/**
	 * {@code POST  /company} : Create a new company.
	 *
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new companyDTO, or with status {@code 400 (Bad Request)} if
	 *         the company has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/addCompany")
	public ResponseEntity<Company> addCompany(@RequestParam(required = false) MultipartFile logo, @RequestParam String companyName,
			@RequestParam String description, @RequestParam String notes, @RequestParam String domain,
			@RequestParam String healthScore, @RequestParam String accountTier, @RequestParam(required = false) LocalDate renewalDate,
			@RequestParam String industry) throws URISyntaxException {
        	log.debug("REST request to save Company : {}", companyName);

        	Company company=new Company();
        	try {
	        	if(logo != null) {
	        		File file=new File(Constants.COMPANY_LOGO_FILE_PATH);
	    			if(!file.exists()) {
	    				file.mkdirs();
	    			}
	    			Path path=Paths.get(Constants.COMPANY_LOGO_FILE_PATH,logo.getOriginalFilename());
	    			try {
	    				Files.write(path, logo.getBytes());
	    			} catch (IOException e) {
	    				log.error("IOException while creating company logo file. ",e);
	    			}
	    			company.setCompanyLogoFileName(logo.getOriginalFilename());
	        	}

				company.setCompanyName(companyName);
		        company.setDescription(description);
		        company.setNotes(notes);
		        company.setDomain(domain);
		        company.setHealthScore(healthScore);
		        company.setAccountTier(accountTier);
		        company.setRenewalDate(renewalDate);
		        company.setIndustry(industry);


		        company.setCompanyLogoFileLocation(Constants.COMPANY_LOGO_FILE_PATH);
		        Company result = companyRepository.save(company);

		        return ResponseEntity.created(new URI("/api/company/" + result.getId()))
		            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
		            .body(result);
        }catch (Exception e) {
			log.error("Exception : ",e);
        	return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
		}
    }

	/**
	 * {@code PUT  /company} : Updates an existing company.
	 *
	 * @param companyDTO the companyDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated companyDTO, or with status {@code 400 (Bad Request)} if
	 *         the companyDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the companyDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/updateCompany")
	public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company companyDTO) throws URISyntaxException {
		log.debug("REST request to update Company : {}", companyDTO);
		if (companyDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Company result = companyRepository.save(companyDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyDTO.getId().toString()))
				.body(result);
	}

	@GetMapping("/listAllcompanies")
	public List<Company> listAllcompanies() {
		log.debug("REST request to get all companies");
		return companyRepository.findAll();
	}

	@GetMapping(value = "/companyLogo/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public Map<String, Object> getImage(@PathVariable Long id) throws IOException {
		Company company=companyRepository.findById(id).get();
		byte[] img = null;
		Path path = Paths.get(Constants.COMPANY_LOGO_FILE_PATH, company.getCompanyLogoFileName());
		try {
			img = Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("img", img);
		return map;
	}

	@GetMapping(value = "/listAllcompanies2", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody List<Map<String, Object>> listAllcompanies2() {
		log.debug("REST request to get all companies");
		List<Company> companyList = companyRepository.findAll();
		List<Map<String, Object>> companiesList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < companyList.size(); i++) {
			Company company = companyList.get(i);
			byte[] img = null;
			Path path = Paths.get(Constants.COMPANY_LOGO_FILE_PATH, company.getCompanyLogoFileName());
			try {
				img = Files.readAllBytes(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, Object> companyDataMap = new HashMap<String, Object>();
			companyDataMap.put("companyLogo", img);
			companyDataMap.put("id", company.getId());
			companyDataMap.put("companyName", company.getCompanyName());
			companyDataMap.put("description", company.getDescription());
			companyDataMap.put("domain", company.getDomain());
			companyDataMap.put("healthScore", company.getHealthScore());
			companyDataMap.put("industry", company.getIndustry());
			companyDataMap.put("notes", company.getNotes());
			companyDataMap.put("accountTier", company.getAccountTier());
			companiesList.add(companyDataMap);

		}
		return companiesList;
	}

	@GetMapping("/companyConatctList")
	public List<CompanyContactVo> getCompanyListWithContactCount() {
		log.debug("Request to get all companies and total contacts belongs to it");
		List<CompanyContactVo> jsonList = new ArrayList<>();
		List<Company> companyList = companyRepository.findAll();
		for (Company company : companyList) {
			CompanyContactVo obj = new CompanyContactVo();
			Contact contact = new Contact();
			contact.setCompany(company);
			List<Contact> contatcList = contactRepository.findAll(Example.of(contact));
			obj.setCompany(company.getCompanyName());
			obj.setContacts(contatcList.size());
			obj.setCheckStatus(false);
			jsonList.add(obj);
		}
		return jsonList;
	}

	@GetMapping("/companyConatctList2")
	public List<Map<String, Object>> getCompanyListWithContactCount2()  {
		log.debug("REST request to get all company");
		List<Company> list = companyRepository.findAll();
		List<Map<String, Object>> companyConatctList = new ArrayList<Map<String, Object>>();
		for (Company company : list) {
			Contact contact = new Contact();
			contact.setCompany(company);
			List<Contact> contatcList = contactRepository.findAll(Example.of(contact));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("company", company.getCompanyName());
			map.put("contacts", contatcList.size());
			companyConatctList.add(map);
		}
		return companyConatctList;

	}

	/**
	 * {@code GET  /company/:id} : get the "id" company.
	 *
	 * @param id the id of the companyDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the companyDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/company/{id}")
	public ResponseEntity<Company> getCompany(@PathVariable Long id) {
		log.debug("REST request to get Company : {}", id);
		Optional<Company> companyDTO = companyRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(companyDTO);
	}

	/**
	 * {@code DELETE  /company/:id} : delete the "id" company.
	 *
	 * @param id the id of the companyDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/company/{id}")
	public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
		log.debug("REST request to delete Company : {}", id);
		Company company=companyRepository.findById(id).get();
		Agent agent=new Agent();
		agent.setCompany(company);
		List<Agent> lst=agentRepository.findAll(Example.of(agent));
		agentRepository.deleteInBatch(lst);
		Contact contact=new Contact();
		contact.setCompany(company);
 		List<Contact> contactList=contactRepository.findAll(Example.of(contact));
		contactRepository.deleteInBatch(contactList);
		companyRepository.deleteById(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
