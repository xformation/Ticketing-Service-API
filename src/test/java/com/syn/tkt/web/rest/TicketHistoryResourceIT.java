package com.syn.tkt.web.rest;

import com.syn.tkt.ServicedeskApp;
import com.syn.tkt.domain.TicketHistory;
import com.syn.tkt.domain.Ticket;
import com.syn.tkt.repository.TicketHistoryRepository;
import com.syn.tkt.service.TicketHistoryService;
import com.syn.tkt.service.dto.TicketHistoryDTO;
import com.syn.tkt.service.mapper.TicketHistoryMapper;

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
 * Integration tests for the {@link TicketHistoryResource} REST controller.
 */
@SpringBootTest(classes = ServicedeskApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TicketHistoryResourceIT {

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

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPECTED_DATE_OF_COMPLETION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_DATE_OF_COMPLETION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUAL_DATE_OF_COMPLETION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUAL_DATE_OF_COMPLETION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGNED_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_USER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSOCIATED_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ASSOCIATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ASSOCIATED_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_ASSOCIATED_ENTITY_ID = "BBBBBBBBBB";

    @Autowired
    private TicketHistoryRepository ticketHistoryRepository;

    @Autowired
    private TicketHistoryMapper ticketHistoryMapper;

    @Autowired
    private TicketHistoryService ticketHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketHistoryMockMvc;

    private TicketHistory ticketHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketHistory createEntity(EntityManager em) {
        TicketHistory ticketHistory = new TicketHistory()
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
            .expectedDateOfCompletion(DEFAULT_EXPECTED_DATE_OF_COMPLETION)
            .actualDateOfCompletion(DEFAULT_ACTUAL_DATE_OF_COMPLETION)
            .tag(DEFAULT_TAG)
            .assignedUserType(DEFAULT_ASSIGNED_USER_TYPE)
            .associatedEntityName(DEFAULT_ASSOCIATED_ENTITY_NAME)
            .associatedEntityId(DEFAULT_ASSOCIATED_ENTITY_ID);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        ticketHistory.setTicket(ticket);
        return ticketHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketHistory createUpdatedEntity(EntityManager em) {
        TicketHistory ticketHistory = new TicketHistory()
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
            .expectedDateOfCompletion(UPDATED_EXPECTED_DATE_OF_COMPLETION)
            .actualDateOfCompletion(UPDATED_ACTUAL_DATE_OF_COMPLETION)
            .tag(UPDATED_TAG)
            .assignedUserType(UPDATED_ASSIGNED_USER_TYPE)
            .associatedEntityName(UPDATED_ASSOCIATED_ENTITY_NAME)
            .associatedEntityId(UPDATED_ASSOCIATED_ENTITY_ID);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createUpdatedEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        ticketHistory.setTicket(ticket);
        return ticketHistory;
    }

    @BeforeEach
    public void initTest() {
        ticketHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicketHistory() throws Exception {
        int databaseSizeBeforeCreate = ticketHistoryRepository.findAll().size();
        // Create the TicketHistory
        TicketHistoryDTO ticketHistoryDTO = ticketHistoryMapper.toDto(ticketHistory);
        restTicketHistoryMockMvc.perform(post("/api/ticket-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketHistory in the database
        List<TicketHistory> ticketHistoryList = ticketHistoryRepository.findAll();
        assertThat(ticketHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TicketHistory testTicketHistory = ticketHistoryList.get(ticketHistoryList.size() - 1);
        assertThat(testTicketHistory.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testTicketHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTicketHistory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTicketHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTicketHistory.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testTicketHistory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTicketHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTicketHistory.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTicketHistory.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testTicketHistory.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testTicketHistory.getExpectedDateOfCompletion()).isEqualTo(DEFAULT_EXPECTED_DATE_OF_COMPLETION);
        assertThat(testTicketHistory.getActualDateOfCompletion()).isEqualTo(DEFAULT_ACTUAL_DATE_OF_COMPLETION);
        assertThat(testTicketHistory.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testTicketHistory.getAssignedUserType()).isEqualTo(DEFAULT_ASSIGNED_USER_TYPE);
        assertThat(testTicketHistory.getAssociatedEntityName()).isEqualTo(DEFAULT_ASSOCIATED_ENTITY_NAME);
        assertThat(testTicketHistory.getAssociatedEntityId()).isEqualTo(DEFAULT_ASSOCIATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void createTicketHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketHistoryRepository.findAll().size();

        // Create the TicketHistory with an existing ID
        ticketHistory.setId(1L);
        TicketHistoryDTO ticketHistoryDTO = ticketHistoryMapper.toDto(ticketHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketHistoryMockMvc.perform(post("/api/ticket-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketHistory in the database
        List<TicketHistory> ticketHistoryList = ticketHistoryRepository.findAll();
        assertThat(ticketHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTicketHistories() throws Exception {
        // Initialize the database
        ticketHistoryRepository.saveAndFlush(ticketHistory);

        // Get all the ticketHistoryList
        restTicketHistoryMockMvc.perform(get("/api/ticket-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketHistory.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].expectedDateOfCompletion").value(hasItem(DEFAULT_EXPECTED_DATE_OF_COMPLETION.toString())))
            .andExpect(jsonPath("$.[*].actualDateOfCompletion").value(hasItem(DEFAULT_ACTUAL_DATE_OF_COMPLETION.toString())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].assignedUserType").value(hasItem(DEFAULT_ASSIGNED_USER_TYPE)))
            .andExpect(jsonPath("$.[*].associatedEntityName").value(hasItem(DEFAULT_ASSOCIATED_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].associatedEntityId").value(hasItem(DEFAULT_ASSOCIATED_ENTITY_ID)));
    }
    
    @Test
    @Transactional
    public void getTicketHistory() throws Exception {
        // Initialize the database
        ticketHistoryRepository.saveAndFlush(ticketHistory);

        // Get the ticketHistory
        restTicketHistoryMockMvc.perform(get("/api/ticket-histories/{id}", ticketHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketHistory.getId().intValue()))
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
            .andExpect(jsonPath("$.expectedDateOfCompletion").value(DEFAULT_EXPECTED_DATE_OF_COMPLETION.toString()))
            .andExpect(jsonPath("$.actualDateOfCompletion").value(DEFAULT_ACTUAL_DATE_OF_COMPLETION.toString()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.assignedUserType").value(DEFAULT_ASSIGNED_USER_TYPE))
            .andExpect(jsonPath("$.associatedEntityName").value(DEFAULT_ASSOCIATED_ENTITY_NAME))
            .andExpect(jsonPath("$.associatedEntityId").value(DEFAULT_ASSOCIATED_ENTITY_ID));
    }
    @Test
    @Transactional
    public void getNonExistingTicketHistory() throws Exception {
        // Get the ticketHistory
        restTicketHistoryMockMvc.perform(get("/api/ticket-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketHistory() throws Exception {
        // Initialize the database
        ticketHistoryRepository.saveAndFlush(ticketHistory);

        int databaseSizeBeforeUpdate = ticketHistoryRepository.findAll().size();

        // Update the ticketHistory
        TicketHistory updatedTicketHistory = ticketHistoryRepository.findById(ticketHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTicketHistory are not directly saved in db
        em.detach(updatedTicketHistory);
        updatedTicketHistory
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
            .expectedDateOfCompletion(UPDATED_EXPECTED_DATE_OF_COMPLETION)
            .actualDateOfCompletion(UPDATED_ACTUAL_DATE_OF_COMPLETION)
            .tag(UPDATED_TAG)
            .assignedUserType(UPDATED_ASSIGNED_USER_TYPE)
            .associatedEntityName(UPDATED_ASSOCIATED_ENTITY_NAME)
            .associatedEntityId(UPDATED_ASSOCIATED_ENTITY_ID);
        TicketHistoryDTO ticketHistoryDTO = ticketHistoryMapper.toDto(updatedTicketHistory);

        restTicketHistoryMockMvc.perform(put("/api/ticket-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the TicketHistory in the database
        List<TicketHistory> ticketHistoryList = ticketHistoryRepository.findAll();
        assertThat(ticketHistoryList).hasSize(databaseSizeBeforeUpdate);
        TicketHistory testTicketHistory = ticketHistoryList.get(ticketHistoryList.size() - 1);
        assertThat(testTicketHistory.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testTicketHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTicketHistory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTicketHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTicketHistory.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testTicketHistory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTicketHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTicketHistory.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTicketHistory.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testTicketHistory.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testTicketHistory.getExpectedDateOfCompletion()).isEqualTo(UPDATED_EXPECTED_DATE_OF_COMPLETION);
        assertThat(testTicketHistory.getActualDateOfCompletion()).isEqualTo(UPDATED_ACTUAL_DATE_OF_COMPLETION);
        assertThat(testTicketHistory.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testTicketHistory.getAssignedUserType()).isEqualTo(UPDATED_ASSIGNED_USER_TYPE);
        assertThat(testTicketHistory.getAssociatedEntityName()).isEqualTo(UPDATED_ASSOCIATED_ENTITY_NAME);
        assertThat(testTicketHistory.getAssociatedEntityId()).isEqualTo(UPDATED_ASSOCIATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTicketHistory() throws Exception {
        int databaseSizeBeforeUpdate = ticketHistoryRepository.findAll().size();

        // Create the TicketHistory
        TicketHistoryDTO ticketHistoryDTO = ticketHistoryMapper.toDto(ticketHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketHistoryMockMvc.perform(put("/api/ticket-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketHistory in the database
        List<TicketHistory> ticketHistoryList = ticketHistoryRepository.findAll();
        assertThat(ticketHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTicketHistory() throws Exception {
        // Initialize the database
        ticketHistoryRepository.saveAndFlush(ticketHistory);

        int databaseSizeBeforeDelete = ticketHistoryRepository.findAll().size();

        // Delete the ticketHistory
        restTicketHistoryMockMvc.perform(delete("/api/ticket-histories/{id}", ticketHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TicketHistory> ticketHistoryList = ticketHistoryRepository.findAll();
        assertThat(ticketHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
