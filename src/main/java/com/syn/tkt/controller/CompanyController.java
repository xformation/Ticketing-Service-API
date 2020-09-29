package com.syn.tkt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.syn.tkt.config.Constants;
import com.syn.tkt.domain.Company;
import com.syn.tkt.domain.Contact;
import com.syn.tkt.repository.CompanyRepository;
import com.syn.tkt.repository.ContactRepository;
import com.syn.tkt.service.dto.CompanyDTO;
import com.syn.tkt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.syn.tkt.domain.Company}.
 */
@RestController
@RequestMapping("/api")
public class CompanyController {

    private final Logger log = LoggerFactory.getLogger(CompanyController.class);

    private static final String ENTITY_NAME = "company";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

//    private final CompanyService companyService;
//
//    public CompanyController(CompanyService companyService) {
//        this.companyService = companyService;
//    }

    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private ContactRepository contactRepository;
    /**
     * {@code POST  /company} : Create a new company.
     *
     * @param companyDTO the companyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyDTO, or with status {@code 400 (Bad Request)} if the company has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
	@PostMapping("/company")
    public ResponseEntity<Company> createCompany(@RequestParam(required = false) MultipartFile logo, @RequestParam String companyName,
			@RequestParam String description, @RequestParam String notes, @RequestParam String domain,
			@RequestParam String healthScore, @RequestParam String accountTier, @RequestParam(required = false) LocalDate renewalDate,
			@RequestParam String industry) throws URISyntaxException {
        log.debug("REST request to save Company : {}", companyName);
        try {
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
		
		Company company=new Company();
        company.setCompanyName(companyName);
        company.setDescription(description);
        company.setNotes(notes);
        company.setDomain(domain);
        company.setHealthScore(healthScore);
        company.setAccountTier(accountTier);
        company.setRenewalDate(renewalDate);
        company.setIndustry(industry);
        company.setCompanyLogoFileName(logo.getOriginalFilename());
        company.setCompanyLogoFileLocation(Constants.COMPANY_LOGO_FILE_PATH);
        Company result = companyRepository.save(company);
       
        return ResponseEntity.created(new URI("/api/company/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
        }catch (Exception e) {
			// TODO: handle exception
        	return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
		}
    }

    /**
     * {@code PUT  /company} : Updates an existing company.
     *
     * @param companyDTO the companyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyDTO,
     * or with status {@code 400 (Bad Request)} if the companyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company companyDTO) throws URISyntaxException {
        log.debug("REST request to update Company : {}", companyDTO);
        if (companyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Company result = companyRepository.save(companyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company} : get all the company.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of company in body.
     */
    @GetMapping("/company")
    public List<Company> getAllcompany() {
        log.debug("REST request to get all company");
        return companyRepository.findAll();
    }

    @GetMapping("/companyConatctList")
    public Map<String, Integer> getCompanyListWithContactCount() {
        log.debug("REST request to get all company");
        List<Company> list =companyRepository.findAll();
        Map<String, Integer> map=new HashMap<String, Integer>();
        for(Company company : list) {
        	Contact contact=new Contact();
        	contact.setCompany(company);
        	List<Contact> contatcList=contactRepository.findAll(Example.of(contact));
        	int numberOfRecord=contatcList.size();
        	map.put(company.getCompanyName(), numberOfRecord);
        }
		return map;
        
    }
    @GetMapping("/companyConatctList2")
    public List<Map<String, Object>> getCompanyListWithContactCount2() throws JSONException {
        log.debug("REST request to get all company");
        List<Company> list =companyRepository.findAll();
        List<Map<String, Object>> companyConatctList =new ArrayList<Map<String,Object>>();
        for(Company company : list) {
        	Contact contact=new Contact();
        	contact.setCompany(company);
        	List<Contact> contatcList=contactRepository.findAll(Example.of(contact));
        	Map<String, Object> map=new HashMap<String, Object>();
        	map.put("company", company.getCompanyName());
        	map.put("contacts",contatcList.size());
        	companyConatctList.add(map);
        }
		return companyConatctList;
        
    }
    
    /**
     * {@code GET  /company/:id} : get the "id" company.
     *
     * @param id the id of the companyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyDTO, or with status {@code 404 (Not Found)}.
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
        companyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
