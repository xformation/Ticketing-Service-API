package com.synectiks.tkt.web.rest;

import com.synectiks.tkt.ServicedeskApp;
import com.synectiks.tkt.domain.Ticket;
import com.synectiks.tkt.repository.TicketRepository;
import com.synectiks.tkt.service.TicketService;
import com.synectiks.tkt.service.dto.TicketDTO;
import com.synectiks.tkt.service.mapper.TicketMapper;

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
 * Integration tests for the {@link TicketResource} REST controller.
 */
@SpringBootTest(classes = ServicedeskApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TicketResourceIT {

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

    private static final String DEFAULT_ASSIGNED_TO_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_TO_USER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTER_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTER_USER_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_ASSIGNED_TO_ID = 1L;
    private static final Long UPDATED_ASSIGNED_TO_ID = 2L;

    private static final Long DEFAULT_REQUESTER_ID = 1L;
    private static final Long UPDATED_REQUESTER_ID = 2L;

    private static final String DEFAULT_ASSOCIATED_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ASSOCIATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ASSOCIATED_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_ASSOCIATED_ENTITY_ID = "BBBBBBBBBB";

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketMockMvc;

    private Ticket ticket;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ticket createEntity(EntityManager em) {
        Ticket ticket = new Ticket()
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
            .assignedToUserType(DEFAULT_ASSIGNED_TO_USER_TYPE)
            .requesterUserType(DEFAULT_REQUESTER_USER_TYPE)
            .assignedToId(DEFAULT_ASSIGNED_TO_ID)
            .requesterId(DEFAULT_REQUESTER_ID)
            .associatedEntityName(DEFAULT_ASSOCIATED_ENTITY_NAME)
            .associatedEntityId(DEFAULT_ASSOCIATED_ENTITY_ID);
        return ticket;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ticket createUpdatedEntity(EntityManager em) {
        Ticket ticket = new Ticket()
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
            .assignedToUserType(UPDATED_ASSIGNED_TO_USER_TYPE)
            .requesterUserType(UPDATED_REQUESTER_USER_TYPE)
            .assignedToId(UPDATED_ASSIGNED_TO_ID)
            .requesterId(UPDATED_REQUESTER_ID)
            .associatedEntityName(UPDATED_ASSOCIATED_ENTITY_NAME)
            .associatedEntityId(UPDATED_ASSOCIATED_ENTITY_ID);
        return ticket;
    }

    @BeforeEach
    public void initTest() {
        ticket = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicket() throws Exception {
        int databaseSizeBeforeCreate = ticketRepository.findAll().size();
        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);
        restTicketMockMvc.perform(post("/api/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isCreated());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeCreate + 1);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testTicket.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTicket.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTicket.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTicket.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testTicket.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTicket.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTicket.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTicket.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testTicket.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testTicket.getExpectedDateOfCompletion()).isEqualTo(DEFAULT_EXPECTED_DATE_OF_COMPLETION);
        assertThat(testTicket.getActualDateOfCompletion()).isEqualTo(DEFAULT_ACTUAL_DATE_OF_COMPLETION);
        assertThat(testTicket.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testTicket.getAssignedToUserType()).isEqualTo(DEFAULT_ASSIGNED_TO_USER_TYPE);
        assertThat(testTicket.getRequesterUserType()).isEqualTo(DEFAULT_REQUESTER_USER_TYPE);
        assertThat(testTicket.getAssignedToId()).isEqualTo(DEFAULT_ASSIGNED_TO_ID);
        assertThat(testTicket.getRequesterId()).isEqualTo(DEFAULT_REQUESTER_ID);
        assertThat(testTicket.getAssociatedEntityName()).isEqualTo(DEFAULT_ASSOCIATED_ENTITY_NAME);
        assertThat(testTicket.getAssociatedEntityId()).isEqualTo(DEFAULT_ASSOCIATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void createTicketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketRepository.findAll().size();

        // Create the Ticket with an existing ID
        ticket.setId(1L);
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketMockMvc.perform(post("/api/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTickets() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList
        restTicketMockMvc.perform(get("/api/tickets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticket.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].assignedToUserType").value(hasItem(DEFAULT_ASSIGNED_TO_USER_TYPE)))
            .andExpect(jsonPath("$.[*].requesterUserType").value(hasItem(DEFAULT_REQUESTER_USER_TYPE)))
            .andExpect(jsonPath("$.[*].assignedToId").value(hasItem(DEFAULT_ASSIGNED_TO_ID.intValue())))
            .andExpect(jsonPath("$.[*].requesterId").value(hasItem(DEFAULT_REQUESTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].associatedEntityName").value(hasItem(DEFAULT_ASSOCIATED_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].associatedEntityId").value(hasItem(DEFAULT_ASSOCIATED_ENTITY_ID)));
    }

    @Test
    @Transactional
    public void getTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        // Get the ticket
        restTicketMockMvc.perform(get("/api/tickets/{id}", ticket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticket.getId().intValue()))
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
            .andExpect(jsonPath("$.assignedToUserType").value(DEFAULT_ASSIGNED_TO_USER_TYPE))
            .andExpect(jsonPath("$.requesterUserType").value(DEFAULT_REQUESTER_USER_TYPE))
            .andExpect(jsonPath("$.assignedToId").value(DEFAULT_ASSIGNED_TO_ID.intValue()))
            .andExpect(jsonPath("$.requesterId").value(DEFAULT_REQUESTER_ID.intValue()))
            .andExpect(jsonPath("$.associatedEntityName").value(DEFAULT_ASSOCIATED_ENTITY_NAME))
            .andExpect(jsonPath("$.associatedEntityId").value(DEFAULT_ASSOCIATED_ENTITY_ID));
    }
    @Test
    @Transactional
    public void getNonExistingTicket() throws Exception {
        // Get the ticket
        restTicketMockMvc.perform(get("/api/tickets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();

        // Update the ticket
        Ticket updatedTicket = ticketRepository.findById(ticket.getId()).get();
        // Disconnect from session so that the updates on updatedTicket are not directly saved in db
        em.detach(updatedTicket);
        updatedTicket
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
            .assignedToUserType(UPDATED_ASSIGNED_TO_USER_TYPE)
            .requesterUserType(UPDATED_REQUESTER_USER_TYPE)
            .assignedToId(UPDATED_ASSIGNED_TO_ID)
            .requesterId(UPDATED_REQUESTER_ID)
            .associatedEntityName(UPDATED_ASSOCIATED_ENTITY_NAME)
            .associatedEntityId(UPDATED_ASSOCIATED_ENTITY_ID);
        TicketDTO ticketDTO = ticketMapper.toDto(updatedTicket);

        restTicketMockMvc.perform(put("/api/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isOk());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testTicket.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTicket.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTicket.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTicket.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testTicket.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTicket.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTicket.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTicket.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testTicket.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testTicket.getExpectedDateOfCompletion()).isEqualTo(UPDATED_EXPECTED_DATE_OF_COMPLETION);
        assertThat(testTicket.getActualDateOfCompletion()).isEqualTo(UPDATED_ACTUAL_DATE_OF_COMPLETION);
        assertThat(testTicket.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testTicket.getAssignedToUserType()).isEqualTo(UPDATED_ASSIGNED_TO_USER_TYPE);
        assertThat(testTicket.getRequesterUserType()).isEqualTo(UPDATED_REQUESTER_USER_TYPE);
        assertThat(testTicket.getAssignedToId()).isEqualTo(UPDATED_ASSIGNED_TO_ID);
        assertThat(testTicket.getRequesterId()).isEqualTo(UPDATED_REQUESTER_ID);
        assertThat(testTicket.getAssociatedEntityName()).isEqualTo(UPDATED_ASSOCIATED_ENTITY_NAME);
        assertThat(testTicket.getAssociatedEntityId()).isEqualTo(UPDATED_ASSOCIATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketMockMvc.perform(put("/api/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        int databaseSizeBeforeDelete = ticketRepository.findAll().size();

        // Delete the ticket
        restTicketMockMvc.perform(delete("/api/tickets/{id}", ticket.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
