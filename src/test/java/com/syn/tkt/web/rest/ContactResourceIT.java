package com.syn.tkt.web.rest;

import com.syn.tkt.ServicedeskApp;
import com.syn.tkt.domain.Contact;
import com.syn.tkt.repository.ContactRepository;
import com.syn.tkt.service.ContactService;
import com.syn.tkt.service.dto.ContactDTO;
import com.syn.tkt.service.mapper.ContactMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContactResource} REST controller.
 */
@SpringBootTest(classes = ServicedeskApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ALTERNATE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_ALTERNATE_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER_HANDLE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIQUE_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_EXTERNAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_FILE_NAME = "BBBBBBBBBB";

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ContactService contactService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMockMvc;

    private Contact contact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact()
            .userName(DEFAULT_USER_NAME)
            .title(DEFAULT_TITLE)
            .primaryEmail(DEFAULT_PRIMARY_EMAIL)
            .alternateEmail(DEFAULT_ALTERNATE_EMAIL)
            .workPhone(DEFAULT_WORK_PHONE)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .twitterHandle(DEFAULT_TWITTER_HANDLE)
            .uniqueExternalId(DEFAULT_UNIQUE_EXTERNAL_ID)
            .imageLocation(DEFAULT_IMAGE_LOCATION)
            .imageFileName(DEFAULT_IMAGE_FILE_NAME);
        return contact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createUpdatedEntity(EntityManager em) {
        Contact contact = new Contact()
            .userName(UPDATED_USER_NAME)
            .title(UPDATED_TITLE)
            .primaryEmail(UPDATED_PRIMARY_EMAIL)
            .alternateEmail(UPDATED_ALTERNATE_EMAIL)
            .workPhone(UPDATED_WORK_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .twitterHandle(UPDATED_TWITTER_HANDLE)
            .uniqueExternalId(UPDATED_UNIQUE_EXTERNAL_ID)
            .imageLocation(UPDATED_IMAGE_LOCATION)
            .imageFileName(UPDATED_IMAGE_FILE_NAME);
        return contact;
    }

    @BeforeEach
    public void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
    public void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();
        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);
        restContactMockMvc.perform(post("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isCreated());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testContact.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testContact.getPrimaryEmail()).isEqualTo(DEFAULT_PRIMARY_EMAIL);
        assertThat(testContact.getAlternateEmail()).isEqualTo(DEFAULT_ALTERNATE_EMAIL);
        assertThat(testContact.getWorkPhone()).isEqualTo(DEFAULT_WORK_PHONE);
        assertThat(testContact.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testContact.getTwitterHandle()).isEqualTo(DEFAULT_TWITTER_HANDLE);
        assertThat(testContact.getUniqueExternalId()).isEqualTo(DEFAULT_UNIQUE_EXTERNAL_ID);
        assertThat(testContact.getImageLocation()).isEqualTo(DEFAULT_IMAGE_LOCATION);
        assertThat(testContact.getImageFileName()).isEqualTo(DEFAULT_IMAGE_FILE_NAME);
    }

    @Test
    @Transactional
    public void createContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // Create the Contact with an existing ID
        contact.setId(1L);
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMockMvc.perform(post("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContacts() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList
        restContactMockMvc.perform(get("/api/contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].primaryEmail").value(hasItem(DEFAULT_PRIMARY_EMAIL)))
            .andExpect(jsonPath("$.[*].alternateEmail").value(hasItem(DEFAULT_ALTERNATE_EMAIL)))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE)))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].twitterHandle").value(hasItem(DEFAULT_TWITTER_HANDLE)))
            .andExpect(jsonPath("$.[*].uniqueExternalId").value(hasItem(DEFAULT_UNIQUE_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].imageLocation").value(hasItem(DEFAULT_IMAGE_LOCATION)))
            .andExpect(jsonPath("$.[*].imageFileName").value(hasItem(DEFAULT_IMAGE_FILE_NAME)));
    }
    
    @Test
    @Transactional
    public void getContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", contact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.primaryEmail").value(DEFAULT_PRIMARY_EMAIL))
            .andExpect(jsonPath("$.alternateEmail").value(DEFAULT_ALTERNATE_EMAIL))
            .andExpect(jsonPath("$.workPhone").value(DEFAULT_WORK_PHONE))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE))
            .andExpect(jsonPath("$.twitterHandle").value(DEFAULT_TWITTER_HANDLE))
            .andExpect(jsonPath("$.uniqueExternalId").value(DEFAULT_UNIQUE_EXTERNAL_ID))
            .andExpect(jsonPath("$.imageLocation").value(DEFAULT_IMAGE_LOCATION))
            .andExpect(jsonPath("$.imageFileName").value(DEFAULT_IMAGE_FILE_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingContact() throws Exception {
        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findById(contact.getId()).get();
        // Disconnect from session so that the updates on updatedContact are not directly saved in db
        em.detach(updatedContact);
        updatedContact
            .userName(UPDATED_USER_NAME)
            .title(UPDATED_TITLE)
            .primaryEmail(UPDATED_PRIMARY_EMAIL)
            .alternateEmail(UPDATED_ALTERNATE_EMAIL)
            .workPhone(UPDATED_WORK_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .twitterHandle(UPDATED_TWITTER_HANDLE)
            .uniqueExternalId(UPDATED_UNIQUE_EXTERNAL_ID)
            .imageLocation(UPDATED_IMAGE_LOCATION)
            .imageFileName(UPDATED_IMAGE_FILE_NAME);
        ContactDTO contactDTO = contactMapper.toDto(updatedContact);

        restContactMockMvc.perform(put("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testContact.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testContact.getPrimaryEmail()).isEqualTo(UPDATED_PRIMARY_EMAIL);
        assertThat(testContact.getAlternateEmail()).isEqualTo(UPDATED_ALTERNATE_EMAIL);
        assertThat(testContact.getWorkPhone()).isEqualTo(UPDATED_WORK_PHONE);
        assertThat(testContact.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testContact.getTwitterHandle()).isEqualTo(UPDATED_TWITTER_HANDLE);
        assertThat(testContact.getUniqueExternalId()).isEqualTo(UPDATED_UNIQUE_EXTERNAL_ID);
        assertThat(testContact.getImageLocation()).isEqualTo(UPDATED_IMAGE_LOCATION);
        assertThat(testContact.getImageFileName()).isEqualTo(UPDATED_IMAGE_FILE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMockMvc.perform(put("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Delete the contact
        restContactMockMvc.perform(delete("/api/contacts/{id}", contact.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
