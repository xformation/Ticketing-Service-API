package com.syn.tkt.web.rest;

import com.syn.tkt.ServicedeskApp;
import com.syn.tkt.domain.TicketAuditHistory;
import com.syn.tkt.domain.Ticket;
import com.syn.tkt.repository.TicketAuditHistoryRepository;
import com.syn.tkt.service.TicketAuditHistoryService;
import com.syn.tkt.service.dto.TicketAuditHistoryDTO;
import com.syn.tkt.service.mapper.TicketAuditHistoryMapper;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TicketAuditHistoryResource} REST controller.
 */
@SpringBootTest(classes = ServicedeskApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TicketAuditHistoryResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGNED_TO = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_TO = "BBBBBBBBBB";

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACTION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OPERATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_TYPE = "BBBBBBBBBB";

    @Autowired
    private TicketAuditHistoryRepository ticketAuditHistoryRepository;

    @Autowired
    private TicketAuditHistoryMapper ticketAuditHistoryMapper;

    @Autowired
    private TicketAuditHistoryService ticketAuditHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketAuditHistoryMockMvc;

    private TicketAuditHistory ticketAuditHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketAuditHistory createEntity(EntityManager em) {
        TicketAuditHistory ticketAuditHistory = new TicketAuditHistory()
            .subject(DEFAULT_SUBJECT)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .priority(DEFAULT_PRIORITY)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .comments(DEFAULT_COMMENTS)
            .assignedTo(DEFAULT_ASSIGNED_TO)
            .tag(DEFAULT_TAG)
            .actionTime(DEFAULT_ACTION_TIME)
            .operationType(DEFAULT_OPERATION_TYPE);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        ticketAuditHistory.setTicket(ticket);
        return ticketAuditHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketAuditHistory createUpdatedEntity(EntityManager em) {
        TicketAuditHistory ticketAuditHistory = new TicketAuditHistory()
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .comments(UPDATED_COMMENTS)
            .assignedTo(UPDATED_ASSIGNED_TO)
            .tag(UPDATED_TAG)
            .actionTime(UPDATED_ACTION_TIME)
            .operationType(UPDATED_OPERATION_TYPE);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createUpdatedEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        ticketAuditHistory.setTicket(ticket);
        return ticketAuditHistory;
    }

    @BeforeEach
    public void initTest() {
        ticketAuditHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicketAuditHistory() throws Exception {
        int databaseSizeBeforeCreate = ticketAuditHistoryRepository.findAll().size();
        // Create the TicketAuditHistory
        TicketAuditHistoryDTO ticketAuditHistoryDTO = ticketAuditHistoryMapper.toDto(ticketAuditHistory);
        restTicketAuditHistoryMockMvc.perform(post("/api/ticket-audit-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketAuditHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketAuditHistory in the database
        List<TicketAuditHistory> ticketAuditHistoryList = ticketAuditHistoryRepository.findAll();
        assertThat(ticketAuditHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TicketAuditHistory testTicketAuditHistory = ticketAuditHistoryList.get(ticketAuditHistoryList.size() - 1);
        assertThat(testTicketAuditHistory.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testTicketAuditHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTicketAuditHistory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTicketAuditHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTicketAuditHistory.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testTicketAuditHistory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTicketAuditHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTicketAuditHistory.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTicketAuditHistory.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testTicketAuditHistory.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testTicketAuditHistory.getAssignedTo()).isEqualTo(DEFAULT_ASSIGNED_TO);
        assertThat(testTicketAuditHistory.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testTicketAuditHistory.getActionTime()).isEqualTo(DEFAULT_ACTION_TIME);
        assertThat(testTicketAuditHistory.getOperationType()).isEqualTo(DEFAULT_OPERATION_TYPE);
    }

    @Test
    @Transactional
    public void createTicketAuditHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketAuditHistoryRepository.findAll().size();

        // Create the TicketAuditHistory with an existing ID
        ticketAuditHistory.setId(1L);
        TicketAuditHistoryDTO ticketAuditHistoryDTO = ticketAuditHistoryMapper.toDto(ticketAuditHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketAuditHistoryMockMvc.perform(post("/api/ticket-audit-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketAuditHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketAuditHistory in the database
        List<TicketAuditHistory> ticketAuditHistoryList = ticketAuditHistoryRepository.findAll();
        assertThat(ticketAuditHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTicketAuditHistories() throws Exception {
        // Initialize the database
        ticketAuditHistoryRepository.saveAndFlush(ticketAuditHistory);

        // Get all the ticketAuditHistoryList
        restTicketAuditHistoryMockMvc.perform(get("/api/ticket-audit-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketAuditHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].assignedTo").value(hasItem(DEFAULT_ASSIGNED_TO)))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].actionTime").value(hasItem(DEFAULT_ACTION_TIME.toString())))
            .andExpect(jsonPath("$.[*].operationType").value(hasItem(DEFAULT_OPERATION_TYPE)));
    }
    
    @Test
    @Transactional
    public void getTicketAuditHistory() throws Exception {
        // Initialize the database
        ticketAuditHistoryRepository.saveAndFlush(ticketAuditHistory);

        // Get the ticketAuditHistory
        restTicketAuditHistoryMockMvc.perform(get("/api/ticket-audit-histories/{id}", ticketAuditHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketAuditHistory.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.assignedTo").value(DEFAULT_ASSIGNED_TO))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.actionTime").value(DEFAULT_ACTION_TIME.toString()))
            .andExpect(jsonPath("$.operationType").value(DEFAULT_OPERATION_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingTicketAuditHistory() throws Exception {
        // Get the ticketAuditHistory
        restTicketAuditHistoryMockMvc.perform(get("/api/ticket-audit-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketAuditHistory() throws Exception {
        // Initialize the database
        ticketAuditHistoryRepository.saveAndFlush(ticketAuditHistory);

        int databaseSizeBeforeUpdate = ticketAuditHistoryRepository.findAll().size();

        // Update the ticketAuditHistory
        TicketAuditHistory updatedTicketAuditHistory = ticketAuditHistoryRepository.findById(ticketAuditHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTicketAuditHistory are not directly saved in db
        em.detach(updatedTicketAuditHistory);
        updatedTicketAuditHistory
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .comments(UPDATED_COMMENTS)
            .assignedTo(UPDATED_ASSIGNED_TO)
            .tag(UPDATED_TAG)
            .actionTime(UPDATED_ACTION_TIME)
            .operationType(UPDATED_OPERATION_TYPE);
        TicketAuditHistoryDTO ticketAuditHistoryDTO = ticketAuditHistoryMapper.toDto(updatedTicketAuditHistory);

        restTicketAuditHistoryMockMvc.perform(put("/api/ticket-audit-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketAuditHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the TicketAuditHistory in the database
        List<TicketAuditHistory> ticketAuditHistoryList = ticketAuditHistoryRepository.findAll();
        assertThat(ticketAuditHistoryList).hasSize(databaseSizeBeforeUpdate);
        TicketAuditHistory testTicketAuditHistory = ticketAuditHistoryList.get(ticketAuditHistoryList.size() - 1);
        assertThat(testTicketAuditHistory.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testTicketAuditHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTicketAuditHistory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTicketAuditHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTicketAuditHistory.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testTicketAuditHistory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTicketAuditHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTicketAuditHistory.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTicketAuditHistory.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testTicketAuditHistory.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testTicketAuditHistory.getAssignedTo()).isEqualTo(UPDATED_ASSIGNED_TO);
        assertThat(testTicketAuditHistory.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testTicketAuditHistory.getActionTime()).isEqualTo(UPDATED_ACTION_TIME);
        assertThat(testTicketAuditHistory.getOperationType()).isEqualTo(UPDATED_OPERATION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTicketAuditHistory() throws Exception {
        int databaseSizeBeforeUpdate = ticketAuditHistoryRepository.findAll().size();

        // Create the TicketAuditHistory
        TicketAuditHistoryDTO ticketAuditHistoryDTO = ticketAuditHistoryMapper.toDto(ticketAuditHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketAuditHistoryMockMvc.perform(put("/api/ticket-audit-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketAuditHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketAuditHistory in the database
        List<TicketAuditHistory> ticketAuditHistoryList = ticketAuditHistoryRepository.findAll();
        assertThat(ticketAuditHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTicketAuditHistory() throws Exception {
        // Initialize the database
        ticketAuditHistoryRepository.saveAndFlush(ticketAuditHistory);

        int databaseSizeBeforeDelete = ticketAuditHistoryRepository.findAll().size();

        // Delete the ticketAuditHistory
        restTicketAuditHistoryMockMvc.perform(delete("/api/ticket-audit-histories/{id}", ticketAuditHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TicketAuditHistory> ticketAuditHistoryList = ticketAuditHistoryRepository.findAll();
        assertThat(ticketAuditHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
