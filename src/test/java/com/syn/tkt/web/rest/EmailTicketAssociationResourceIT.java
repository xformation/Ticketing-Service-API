package com.syn.tkt.web.rest;

import com.syn.tkt.ServicedeskApp;
import com.syn.tkt.domain.EmailTicketAssociation;
import com.syn.tkt.repository.EmailTicketAssociationRepository;
import com.syn.tkt.service.EmailTicketAssociationService;
import com.syn.tkt.service.dto.EmailTicketAssociationDTO;
import com.syn.tkt.service.mapper.EmailTicketAssociationMapper;

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
 * Integration tests for the {@link EmailTicketAssociationResource} REST controller.
 */
@SpringBootTest(classes = ServicedeskApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmailTicketAssociationResourceIT {

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
        EmailTicketAssociation emailTicketAssociation = new EmailTicketAssociation();
        return emailTicketAssociation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTicketAssociation createUpdatedEntity(EntityManager em) {
        EmailTicketAssociation emailTicketAssociation = new EmailTicketAssociation();
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
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTicketAssociation.getId().intValue())));
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
            .andExpect(jsonPath("$.id").value(emailTicketAssociation.getId().intValue()));
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
        EmailTicketAssociationDTO emailTicketAssociationDTO = emailTicketAssociationMapper.toDto(updatedEmailTicketAssociation);

        restEmailTicketAssociationMockMvc.perform(put("/api/email-ticket-associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTicketAssociationDTO)))
            .andExpect(status().isOk());

        // Validate the EmailTicketAssociation in the database
        List<EmailTicketAssociation> emailTicketAssociationList = emailTicketAssociationRepository.findAll();
        assertThat(emailTicketAssociationList).hasSize(databaseSizeBeforeUpdate);
        EmailTicketAssociation testEmailTicketAssociation = emailTicketAssociationList.get(emailTicketAssociationList.size() - 1);
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
