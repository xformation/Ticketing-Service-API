package com.synectiks.tkt.web.rest;

import com.synectiks.tkt.ServicedeskApp;
import com.synectiks.tkt.domain.Agent;
import com.synectiks.tkt.repository.AgentRepository;
import com.synectiks.tkt.service.AgentService;
import com.synectiks.tkt.service.dto.AgentDTO;
import com.synectiks.tkt.service.mapper.AgentMapper;

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
 * Integration tests for the {@link AgentResource} REST controller.
 */
@SpringBootTest(classes = ServicedeskApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AgentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private AgentService agentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgentMockMvc;

    private Agent agent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agent createEntity(EntityManager em) {
        Agent agent = new Agent()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .title(DEFAULT_TITLE)
            .primaryEmail(DEFAULT_PRIMARY_EMAIL)
            .alternateEmail(DEFAULT_ALTERNATE_EMAIL)
            .workPhone(DEFAULT_WORK_PHONE)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .twitterHandle(DEFAULT_TWITTER_HANDLE)
            .uniqueExternalId(DEFAULT_UNIQUE_EXTERNAL_ID)
            .imageLocation(DEFAULT_IMAGE_LOCATION)
            .imageFileName(DEFAULT_IMAGE_FILE_NAME)
            .address(DEFAULT_ADDRESS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return agent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agent createUpdatedEntity(EntityManager em) {
        Agent agent = new Agent()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .title(UPDATED_TITLE)
            .primaryEmail(UPDATED_PRIMARY_EMAIL)
            .alternateEmail(UPDATED_ALTERNATE_EMAIL)
            .workPhone(UPDATED_WORK_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .twitterHandle(UPDATED_TWITTER_HANDLE)
            .uniqueExternalId(UPDATED_UNIQUE_EXTERNAL_ID)
            .imageLocation(UPDATED_IMAGE_LOCATION)
            .imageFileName(UPDATED_IMAGE_FILE_NAME)
            .address(UPDATED_ADDRESS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return agent;
    }

    @BeforeEach
    public void initTest() {
        agent = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgent() throws Exception {
        int databaseSizeBeforeCreate = agentRepository.findAll().size();
        // Create the Agent
        AgentDTO agentDTO = agentMapper.toDto(agent);
        restAgentMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agentDTO)))
            .andExpect(status().isCreated());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate + 1);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAgent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAgent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAgent.getPrimaryEmail()).isEqualTo(DEFAULT_PRIMARY_EMAIL);
        assertThat(testAgent.getAlternateEmail()).isEqualTo(DEFAULT_ALTERNATE_EMAIL);
        assertThat(testAgent.getWorkPhone()).isEqualTo(DEFAULT_WORK_PHONE);
        assertThat(testAgent.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testAgent.getTwitterHandle()).isEqualTo(DEFAULT_TWITTER_HANDLE);
        assertThat(testAgent.getUniqueExternalId()).isEqualTo(DEFAULT_UNIQUE_EXTERNAL_ID);
        assertThat(testAgent.getImageLocation()).isEqualTo(DEFAULT_IMAGE_LOCATION);
        assertThat(testAgent.getImageFileName()).isEqualTo(DEFAULT_IMAGE_FILE_NAME);
        assertThat(testAgent.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAgent.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAgent.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAgent.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAgent.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createAgentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agentRepository.findAll().size();

        // Create the Agent with an existing ID
        agent.setId(1L);
        AgentDTO agentDTO = agentMapper.toDto(agent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentMockMvc.perform(post("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgents() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get all the agentList
        restAgentMockMvc.perform(get("/api/agents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].primaryEmail").value(hasItem(DEFAULT_PRIMARY_EMAIL)))
            .andExpect(jsonPath("$.[*].alternateEmail").value(hasItem(DEFAULT_ALTERNATE_EMAIL)))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE)))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].twitterHandle").value(hasItem(DEFAULT_TWITTER_HANDLE)))
            .andExpect(jsonPath("$.[*].uniqueExternalId").value(hasItem(DEFAULT_UNIQUE_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].imageLocation").value(hasItem(DEFAULT_IMAGE_LOCATION)))
            .andExpect(jsonPath("$.[*].imageFileName").value(hasItem(DEFAULT_IMAGE_FILE_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get the agent
        restAgentMockMvc.perform(get("/api/agents/{id}", agent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.primaryEmail").value(DEFAULT_PRIMARY_EMAIL))
            .andExpect(jsonPath("$.alternateEmail").value(DEFAULT_ALTERNATE_EMAIL))
            .andExpect(jsonPath("$.workPhone").value(DEFAULT_WORK_PHONE))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE))
            .andExpect(jsonPath("$.twitterHandle").value(DEFAULT_TWITTER_HANDLE))
            .andExpect(jsonPath("$.uniqueExternalId").value(DEFAULT_UNIQUE_EXTERNAL_ID))
            .andExpect(jsonPath("$.imageLocation").value(DEFAULT_IMAGE_LOCATION))
            .andExpect(jsonPath("$.imageFileName").value(DEFAULT_IMAGE_FILE_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAgent() throws Exception {
        // Get the agent
        restAgentMockMvc.perform(get("/api/agents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent
        Agent updatedAgent = agentRepository.findById(agent.getId()).get();
        // Disconnect from session so that the updates on updatedAgent are not directly saved in db
        em.detach(updatedAgent);
        updatedAgent
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .title(UPDATED_TITLE)
            .primaryEmail(UPDATED_PRIMARY_EMAIL)
            .alternateEmail(UPDATED_ALTERNATE_EMAIL)
            .workPhone(UPDATED_WORK_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .twitterHandle(UPDATED_TWITTER_HANDLE)
            .uniqueExternalId(UPDATED_UNIQUE_EXTERNAL_ID)
            .imageLocation(UPDATED_IMAGE_LOCATION)
            .imageFileName(UPDATED_IMAGE_FILE_NAME)
            .address(UPDATED_ADDRESS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        AgentDTO agentDTO = agentMapper.toDto(updatedAgent);

        restAgentMockMvc.perform(put("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agentDTO)))
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAgent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAgent.getPrimaryEmail()).isEqualTo(UPDATED_PRIMARY_EMAIL);
        assertThat(testAgent.getAlternateEmail()).isEqualTo(UPDATED_ALTERNATE_EMAIL);
        assertThat(testAgent.getWorkPhone()).isEqualTo(UPDATED_WORK_PHONE);
        assertThat(testAgent.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testAgent.getTwitterHandle()).isEqualTo(UPDATED_TWITTER_HANDLE);
        assertThat(testAgent.getUniqueExternalId()).isEqualTo(UPDATED_UNIQUE_EXTERNAL_ID);
        assertThat(testAgent.getImageLocation()).isEqualTo(UPDATED_IMAGE_LOCATION);
        assertThat(testAgent.getImageFileName()).isEqualTo(UPDATED_IMAGE_FILE_NAME);
        assertThat(testAgent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAgent.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAgent.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAgent.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAgent.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Create the Agent
        AgentDTO agentDTO = agentMapper.toDto(agent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentMockMvc.perform(put("/api/agents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeDelete = agentRepository.findAll().size();

        // Delete the agent
        restAgentMockMvc.perform(delete("/api/agents/{id}", agent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
