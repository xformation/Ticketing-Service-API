package com.syn.tkt.web.rest;

import com.syn.tkt.ServicedeskApp;
import com.syn.tkt.domain.TicketAuditReport;
import com.syn.tkt.domain.Ticket;
import com.syn.tkt.repository.TicketAuditReportRepository;
import com.syn.tkt.service.TicketAuditReportService;
import com.syn.tkt.service.dto.TicketAuditReportDTO;
import com.syn.tkt.service.mapper.TicketAuditReportMapper;

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
 * Integration tests for the {@link TicketAuditReportResource} REST controller.
 */
@SpringBootTest(classes = ServicedeskApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TicketAuditReportResourceIT {

    private static final String DEFAULT_PROPERTY = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY = "BBBBBBBBBB";

    private static final String DEFAULT_OLD_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_OLD_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_NEW_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACTION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private TicketAuditReportRepository ticketAuditReportRepository;

    @Autowired
    private TicketAuditReportMapper ticketAuditReportMapper;

    @Autowired
    private TicketAuditReportService ticketAuditReportService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketAuditReportMockMvc;

    private TicketAuditReport ticketAuditReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketAuditReport createEntity(EntityManager em) {
        TicketAuditReport ticketAuditReport = new TicketAuditReport()
            .property(DEFAULT_PROPERTY)
            .oldValue(DEFAULT_OLD_VALUE)
            .newValue(DEFAULT_NEW_VALUE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .actionTime(DEFAULT_ACTION_TIME)
            .comments(DEFAULT_COMMENTS);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        ticketAuditReport.setTicket(ticket);
        return ticketAuditReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketAuditReport createUpdatedEntity(EntityManager em) {
        TicketAuditReport ticketAuditReport = new TicketAuditReport()
            .property(UPDATED_PROPERTY)
            .oldValue(UPDATED_OLD_VALUE)
            .newValue(UPDATED_NEW_VALUE)
            .updatedBy(UPDATED_UPDATED_BY)
            .actionTime(UPDATED_ACTION_TIME)
            .comments(UPDATED_COMMENTS);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createUpdatedEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        ticketAuditReport.setTicket(ticket);
        return ticketAuditReport;
    }

    @BeforeEach
    public void initTest() {
        ticketAuditReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicketAuditReport() throws Exception {
        int databaseSizeBeforeCreate = ticketAuditReportRepository.findAll().size();
        // Create the TicketAuditReport
        TicketAuditReportDTO ticketAuditReportDTO = ticketAuditReportMapper.toDto(ticketAuditReport);
        restTicketAuditReportMockMvc.perform(post("/api/ticket-audit-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketAuditReportDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketAuditReport in the database
        List<TicketAuditReport> ticketAuditReportList = ticketAuditReportRepository.findAll();
        assertThat(ticketAuditReportList).hasSize(databaseSizeBeforeCreate + 1);
        TicketAuditReport testTicketAuditReport = ticketAuditReportList.get(ticketAuditReportList.size() - 1);
        assertThat(testTicketAuditReport.getProperty()).isEqualTo(DEFAULT_PROPERTY);
        assertThat(testTicketAuditReport.getOldValue()).isEqualTo(DEFAULT_OLD_VALUE);
        assertThat(testTicketAuditReport.getNewValue()).isEqualTo(DEFAULT_NEW_VALUE);
        assertThat(testTicketAuditReport.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTicketAuditReport.getActionTime()).isEqualTo(DEFAULT_ACTION_TIME);
        assertThat(testTicketAuditReport.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createTicketAuditReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketAuditReportRepository.findAll().size();

        // Create the TicketAuditReport with an existing ID
        ticketAuditReport.setId(1L);
        TicketAuditReportDTO ticketAuditReportDTO = ticketAuditReportMapper.toDto(ticketAuditReport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketAuditReportMockMvc.perform(post("/api/ticket-audit-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketAuditReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketAuditReport in the database
        List<TicketAuditReport> ticketAuditReportList = ticketAuditReportRepository.findAll();
        assertThat(ticketAuditReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTicketAuditReports() throws Exception {
        // Initialize the database
        ticketAuditReportRepository.saveAndFlush(ticketAuditReport);

        // Get all the ticketAuditReportList
        restTicketAuditReportMockMvc.perform(get("/api/ticket-audit-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketAuditReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].property").value(hasItem(DEFAULT_PROPERTY)))
            .andExpect(jsonPath("$.[*].oldValue").value(hasItem(DEFAULT_OLD_VALUE)))
            .andExpect(jsonPath("$.[*].newValue").value(hasItem(DEFAULT_NEW_VALUE)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].actionTime").value(hasItem(DEFAULT_ACTION_TIME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getTicketAuditReport() throws Exception {
        // Initialize the database
        ticketAuditReportRepository.saveAndFlush(ticketAuditReport);

        // Get the ticketAuditReport
        restTicketAuditReportMockMvc.perform(get("/api/ticket-audit-reports/{id}", ticketAuditReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketAuditReport.getId().intValue()))
            .andExpect(jsonPath("$.property").value(DEFAULT_PROPERTY))
            .andExpect(jsonPath("$.oldValue").value(DEFAULT_OLD_VALUE))
            .andExpect(jsonPath("$.newValue").value(DEFAULT_NEW_VALUE))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.actionTime").value(DEFAULT_ACTION_TIME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }
    @Test
    @Transactional
    public void getNonExistingTicketAuditReport() throws Exception {
        // Get the ticketAuditReport
        restTicketAuditReportMockMvc.perform(get("/api/ticket-audit-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketAuditReport() throws Exception {
        // Initialize the database
        ticketAuditReportRepository.saveAndFlush(ticketAuditReport);

        int databaseSizeBeforeUpdate = ticketAuditReportRepository.findAll().size();

        // Update the ticketAuditReport
        TicketAuditReport updatedTicketAuditReport = ticketAuditReportRepository.findById(ticketAuditReport.getId()).get();
        // Disconnect from session so that the updates on updatedTicketAuditReport are not directly saved in db
        em.detach(updatedTicketAuditReport);
        updatedTicketAuditReport
            .property(UPDATED_PROPERTY)
            .oldValue(UPDATED_OLD_VALUE)
            .newValue(UPDATED_NEW_VALUE)
            .updatedBy(UPDATED_UPDATED_BY)
            .actionTime(UPDATED_ACTION_TIME)
            .comments(UPDATED_COMMENTS);
        TicketAuditReportDTO ticketAuditReportDTO = ticketAuditReportMapper.toDto(updatedTicketAuditReport);

        restTicketAuditReportMockMvc.perform(put("/api/ticket-audit-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketAuditReportDTO)))
            .andExpect(status().isOk());

        // Validate the TicketAuditReport in the database
        List<TicketAuditReport> ticketAuditReportList = ticketAuditReportRepository.findAll();
        assertThat(ticketAuditReportList).hasSize(databaseSizeBeforeUpdate);
        TicketAuditReport testTicketAuditReport = ticketAuditReportList.get(ticketAuditReportList.size() - 1);
        assertThat(testTicketAuditReport.getProperty()).isEqualTo(UPDATED_PROPERTY);
        assertThat(testTicketAuditReport.getOldValue()).isEqualTo(UPDATED_OLD_VALUE);
        assertThat(testTicketAuditReport.getNewValue()).isEqualTo(UPDATED_NEW_VALUE);
        assertThat(testTicketAuditReport.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTicketAuditReport.getActionTime()).isEqualTo(UPDATED_ACTION_TIME);
        assertThat(testTicketAuditReport.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingTicketAuditReport() throws Exception {
        int databaseSizeBeforeUpdate = ticketAuditReportRepository.findAll().size();

        // Create the TicketAuditReport
        TicketAuditReportDTO ticketAuditReportDTO = ticketAuditReportMapper.toDto(ticketAuditReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketAuditReportMockMvc.perform(put("/api/ticket-audit-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketAuditReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketAuditReport in the database
        List<TicketAuditReport> ticketAuditReportList = ticketAuditReportRepository.findAll();
        assertThat(ticketAuditReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTicketAuditReport() throws Exception {
        // Initialize the database
        ticketAuditReportRepository.saveAndFlush(ticketAuditReport);

        int databaseSizeBeforeDelete = ticketAuditReportRepository.findAll().size();

        // Delete the ticketAuditReport
        restTicketAuditReportMockMvc.perform(delete("/api/ticket-audit-reports/{id}", ticketAuditReport.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TicketAuditReport> ticketAuditReportList = ticketAuditReportRepository.findAll();
        assertThat(ticketAuditReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
