package com.synectiks.tkt.web.rest;

import com.synectiks.tkt.ServicedeskApp;
import com.synectiks.tkt.domain.EmailTicketAssociation;
import com.synectiks.tkt.repository.EmailTicketAssociationRepository;
import com.synectiks.tkt.service.EmailTicketAssociationService;
import com.synectiks.tkt.service.dto.EmailTicketAssociationDTO;
import com.synectiks.tkt.service.mapper.EmailTicketAssociationMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmailTicketAssociationResource} REST controller.
 */
@SpringBootTest(classes = ServicedeskApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmailTicketAssociationResourceIT {

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmailTicketAssociationRepository emailTicketAssociationRepository;

    @Autowired
    private EmailTicketAssociationMapper emailTicketAssociationMapper;

    @Autowired
    private EmailTicketAssociationService emailTicketAssociationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailTicketAssociationMockMvc;

    private EmailTicketAssociation emailTicketAssociation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTicketAssociation createEntity(EntityManager em) {
        EmailTicketAssociation emailTicketAssociation = new EmailTicketAssociation()
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return emailTicketAssociation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTicketAssociation createUpdatedEntity(EntityManager em) {
        EmailTicketAssociation emailTicketAssociation = new EmailTicketAssociation()
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return emailTicketAssociation;
    }

    @BeforeEach
    public void initTest() {
        emailTicketAssociation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailTicketAssociation() throws Exception {
        int databaseSizeBeforeCreate = emailTicketAssociationRepository.findAll().size();
        // Create the EmailTicketAssociation
        EmailTicketAssociationDTO emailTicketAssociationDTO = emailTicketAssociationMapper.toDto(emailTicketAssociation);
        restEmailTicketAssociationMockMvc.perform(post("/api/email-ticket-associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTicketAssociationDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailTicketAssociation in the database
        List<EmailTicketAssociation> emailTicketAssociationList = emailTicketAssociationRepository.findAll();
        assertThat(emailTicketAssociationList).hasSize(databaseSizeBeforeCreate + 1);
        EmailTicketAssociation testEmailTicketAssociation = emailTicketAssociationList.get(emailTicketAssociationList.size() - 1);
        assertThat(testEmailTicketAssociation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testEmailTicketAssociation.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createEmailTicketAssociationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailTicketAssociationRepository.findAll().size();

        // Create the EmailTicketAssociation with an existing ID
        emailTicketAssociation.setId(1L);
        EmailTicketAssociationDTO emailTicketAssociationDTO = emailTicketAssociationMapper.toDto(emailTicketAssociation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailTicketAssociationMockMvc.perform(post("/api/email-ticket-associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTicketAssociationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailTicketAssociation in the database
        List<EmailTicketAssociation> emailTicketAssociationList = emailTicketAssociationRepository.findAll();
        assertThat(emailTicketAssociationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmailTicketAssociations() throws Exception {
        // Initialize the database
        emailTicketAssociationRepository.saveAndFlush(emailTicketAssociation);

        // Get all the emailTicketAssociationList
        restEmailTicketAssociationMockMvc.perform(get("/api/email-ticket-associations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTicketAssociation.getId().intValue())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getEmailTicketAssociation() throws Exception {
        // Initialize the database
        emailTicketAssociationRepository.saveAndFlush(emailTicketAssociation);

        // Get the emailTicketAssociation
        restEmailTicketAssociationMockMvc.perform(get("/api/email-ticket-associations/{id}", emailTicketAssociation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailTicketAssociation.getId().intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmailTicketAssociation() throws Exception {
        // Get the emailTicketAssociation
        restEmailTicketAssociationMockMvc.perform(get("/api/email-ticket-associations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailTicketAssociation() throws Exception {
        // Initialize the database
        emailTicketAssociationRepository.saveAndFlush(emailTicketAssociation);

        int databaseSizeBeforeUpdate = emailTicketAssociationRepository.findAll().size();

        // Update the emailTicketAssociation
        EmailTicketAssociation updatedEmailTicketAssociation = emailTicketAssociationRepository.findById(emailTicketAssociation.getId()).get();
        // Disconnect from session so that the updates on updatedEmailTicketAssociation are not directly saved in db
        em.detach(updatedEmailTicketAssociation);
        updatedEmailTicketAssociation
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        EmailTicketAssociationDTO emailTicketAssociationDTO = emailTicketAssociationMapper.toDto(updatedEmailTicketAssociation);

        restEmailTicketAssociationMockMvc.perform(put("/api/email-ticket-associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTicketAssociationDTO)))
            .andExpect(status().isOk());

        // Validate the EmailTicketAssociation in the database
        List<EmailTicketAssociation> emailTicketAssociationList = emailTicketAssociationRepository.findAll();
        assertThat(emailTicketAssociationList).hasSize(databaseSizeBeforeUpdate);
        EmailTicketAssociation testEmailTicketAssociation = emailTicketAssociationList.get(emailTicketAssociationList.size() - 1);
        assertThat(testEmailTicketAssociation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testEmailTicketAssociation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailTicketAssociation() throws Exception {
        int databaseSizeBeforeUpdate = emailTicketAssociationRepository.findAll().size();

        // Create the EmailTicketAssociation
        EmailTicketAssociationDTO emailTicketAssociationDTO = emailTicketAssociationMapper.toDto(emailTicketAssociation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailTicketAssociationMockMvc.perform(put("/api/email-ticket-associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTicketAssociationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailTicketAssociation in the database
        List<EmailTicketAssociation> emailTicketAssociationList = emailTicketAssociationRepository.findAll();
        assertThat(emailTicketAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailTicketAssociation() throws Exception {
        // Initialize the database
        emailTicketAssociationRepository.saveAndFlush(emailTicketAssociation);

        int databaseSizeBeforeDelete = emailTicketAssociationRepository.findAll().size();

        // Delete the emailTicketAssociation
        restEmailTicketAssociationMockMvc.perform(delete("/api/email-ticket-associations/{id}", emailTicketAssociation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailTicketAssociation> emailTicketAssociationList = emailTicketAssociationRepository.findAll();
        assertThat(emailTicketAssociationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
