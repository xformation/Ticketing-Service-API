package com.synectiks.tkt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.synectiks.tkt.domain.Company;
import com.synectiks.tkt.domain.ContactCompanyVo;
import com.synectiks.tkt.repository.CompanyRepository;
import com.synectiks.tkt.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.synectiks.tkt.config.Constants;
import com.synectiks.tkt.domain.Contact;
import com.synectiks.tkt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Contact}.
 */
@RestController
@RequestMapping("/api")
public class ContactController {

	private final Logger log = LoggerFactory.getLogger(ContactController.class);

	private static final String ENTITY_NAME = "contact";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private CompanyRepository companyRepository;

	/**
	 * {@code POST  /contact} : Create a new contact.
	 *
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new contactDTO, or with status {@code 400 (Bad Request)} if
	 *         the contact has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/addContact")
	public ResponseEntity<Contact> createContact(@RequestParam(required = false) MultipartFile contactPhoto,
			@RequestParam String fullName, @RequestParam String title,
			@RequestParam(required = false) String primaryEmail, @RequestParam(required = false) String alternateEmail,
			@RequestParam(required = false) String workPhone, @RequestParam(required = false) String mobilePhone,
			@RequestParam(required = false) String twitterHandle,
			@RequestParam(required = false) String uniqueExternalId, @RequestParam(required = false) Long companyId,
			@RequestParam(required = false) MultipartFile logo, @RequestParam(required = false) String companyName,
			@RequestParam(required = false) String description, @RequestParam(required = false) String notes,
			@RequestParam(required = false) String domain, @RequestParam(required = false) String healthScore,
			@RequestParam(required = false) String accountTier, @RequestParam(required = false) LocalDate renewalDate,
			@RequestParam(required = false) String industry) throws URISyntaxException {
		log.debug("REST request to save Contact : {}", title);
		Contact result = null;
		if (companyId == -1) {
			log.debug("REST request to save Company : {}", companyName);
			File file = new File(Constants.COMPANY_LOGO_FILE_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			Path path = Paths.get(Constants.COMPANY_LOGO_FILE_PATH, logo.getOriginalFilename());
			try {
				Files.write(path, logo.getBytes());
			} catch (IOException e) {
				log.error("IOException while creating company logo file. ", e);
			}

			Company company = new Company();
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
			company = companyRepository.save(company);
			Contact contact = new Contact();
			contact.setUserName(fullName);
			contact.setTitle(title);
			contact.setPrimaryEmail(primaryEmail);
			contact.setAlternateEmail(alternateEmail);
			contact.setWorkPhone(workPhone);
			contact.setMobilePhone(mobilePhone);
			contact.setTwitterHandle(twitterHandle);
			contact.setUniqueExternalId(uniqueExternalId);
			contact.setCompany(company);
			contact.setImageFileName(contactPhoto.getOriginalFilename());
			contact.setImageLocation(Constants.CONTACT_PHOTO_FILE_PATH);
			if (contact.getId() != null) {
				throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
			}
			result = contactRepository.save(contact);
		} else {
			File file = new File(Constants.CONTACT_PHOTO_FILE_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			Path path = Paths.get(Constants.CONTACT_PHOTO_FILE_PATH, contactPhoto.getOriginalFilename());
			try {
				Files.write(path, contactPhoto.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Company company = null;
			try {
				company = companyRepository.findById(companyId).get();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			Contact contact = new Contact();
			contact.setUserName(fullName);
			contact.setTitle(title);
			contact.setPrimaryEmail(primaryEmail);
			contact.setAlternateEmail(alternateEmail);
			contact.setWorkPhone(workPhone);
			contact.setMobilePhone(mobilePhone);
			contact.setTwitterHandle(twitterHandle);
			contact.setUniqueExternalId(uniqueExternalId);
			contact.setCompany(company);
			contact.setImageFileName(contactPhoto.getOriginalFilename());
			contact.setImageLocation(Constants.CONTACT_PHOTO_FILE_PATH);
			if (contact.getId() != null) {
				throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
			}
			result = contactRepository.save(contact);
		}
		return ResponseEntity
				.created(new URI("/api/contact/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@GetMapping("/contactWithCompanyName")
	public List<ContactCompanyVo> contactWithCompanyName() {

		List<Contact> listContacts = contactRepository.findAll();
		List<ContactCompanyVo> jsonList = new ArrayList<ContactCompanyVo>();
		for (Contact contact : listContacts) {
			ContactCompanyVo obj = new ContactCompanyVo();
			obj.setTitle(contact.getTitle());
			obj.setCompany(contact.getCompany().getCompanyName());
			obj.setPrimaryEmail(contact.getPrimaryEmail());
			obj.setUniqueExternalId(contact.getUniqueExternalId());
			obj.setWorkPhone(contact.getWorkPhone());
			obj.setTwitterHandle(contact.getTwitterHandle());
			obj.setName(contact.getUserName());
			jsonList.add(obj);
		}
		return jsonList;

	}

	/**
	 * {@code PUT  /contact} : Updates an existing contact.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated contactDTO, or with status {@code 400 (Bad Request)} if
	 *         the contactDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the contactDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/contact")
	public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) throws URISyntaxException {
		log.debug("REST request to update Contact : {}", contact);
		if (contact.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Contact result = contactRepository.save(contact);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contact.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /contact} : get all the contact.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of contact in body.
	 */
	@GetMapping("/listAllContacts")
	public List<Contact> getAllContacts() {
		log.debug("REST request to get all Contacts");
		return contactRepository.findAll();
	}

	/**
	 * {@code GET  /contact/:id} : get the "id" contact.
	 *
	 * @param id the id of the contactDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the contactDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/contact/{id}")
	public ResponseEntity<Contact> getContact(@PathVariable Long id) {
		log.debug("REST request to get Contact : {}", id);
		Optional<Contact> contact = contactRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(contact);
	}

	/**
	 * {@code DELETE  /contact/:id} : delete the "id" contact.
	 *
	 * @param id the id of the contactDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/contact/{id}")
	public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
		log.debug("REST request to delete Contact : {}", id);
		contactRepository.deleteById(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
