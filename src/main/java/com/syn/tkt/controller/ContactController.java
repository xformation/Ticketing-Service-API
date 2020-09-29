package com.syn.tkt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.aspectj.apache.bcel.classfile.Constant;
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

import com.syn.tkt.config.Constants;
import com.syn.tkt.domain.Company;
import com.syn.tkt.domain.Contact;
import com.syn.tkt.repository.CompanyRepository;
import com.syn.tkt.repository.ContactRepository;
import com.syn.tkt.service.ContactService;
import com.syn.tkt.service.dto.ContactDTO;
import com.syn.tkt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.syn.tkt.domain.Contact}.
 */
@RestController
@RequestMapping("/api")
public class ContactController {

    private final Logger log = LoggerFactory.getLogger(ContactController.class);

    private static final String ENTITY_NAME = "contact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
//    private final ContactService contactService;
//
//    public ContactController(ContactService contactService) {
//        this.contactService = contactService;
//    }
    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * {@code POST  /contact} : Create a new contact.
     *
     * @param contactDTO the contactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactDTO, or with status {@code 400 (Bad Request)} if the contact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact")
    public ResponseEntity<Contact> createContact(@RequestParam MultipartFile photo, @RequestParam String fullName,
			@RequestParam String title, @RequestParam String primaryEmail, @RequestParam String alternateEmail,
			@RequestParam String workPhone, @RequestParam String mobilePhone, @RequestParam String twitterHandle,
			@RequestParam String uniqueExternalId,@RequestParam(required = false) Long companyId) throws URISyntaxException {
        log.debug("REST request to save Contact : {}", title);
        File file=new File(Constants.CONTACT_PHOTO_FILE_PATH);
		if(!file.exists()) {
			file.mkdirs();
		}
		Path path=Paths.get(Constants.CONTACT_PHOTO_FILE_PATH,photo.getOriginalFilename());
		try {
			Files.write(path, photo.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Company  company =companyRepository.findById(companyId).get();
		
		Contact contact=new Contact();
		contact.setUserName(fullName);
		contact.setTitle(title);
		contact.setPrimaryEmail(primaryEmail);
		contact.setAlternateEmail(alternateEmail);
		contact.setWorkPhone(workPhone);
		contact.setMobilePhone(mobilePhone);
		contact.setTwitterHandle(twitterHandle);
		contact.setUniqueExternalId(uniqueExternalId);
		contact.setCompany(company);
		contact.setImageFileName(photo.getOriginalFilename());
		contact.setImageLocation(Constants.CONTACT_PHOTO_FILE_PATH);
        if (contact.getId() != null) {
            throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contact result = contactRepository.save(contact);
        return ResponseEntity.created(new URI("/api/contact/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact} : Updates an existing contact.
     *
     * @param contactDTO the contactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactDTO,
     * or with status {@code 400 (Bad Request)} if the contactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact")
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) throws URISyntaxException {
        log.debug("REST request to update Contact : {}", contact);
        if (contact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contact result = contactRepository.save(contact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contact.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contact} : get all the contact.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contact in body.
     */
    @GetMapping("/contact")
    public List<Contact> getAllContacts() {
        log.debug("REST request to get all Contacts");
        return contactRepository.findAll();
    }

    /**
     * {@code GET  /contact/:id} : get the "id" contact.
     *
     * @param id the id of the contactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactDTO, or with status {@code 404 (Not Found)}.
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
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
