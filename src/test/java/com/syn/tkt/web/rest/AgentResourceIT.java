package com.syn.tkt.web.rest;

import com.syn.tkt.ServicedeskApp;
import com.syn.tkt.domain.Agent;
import com.syn.tkt.repository.AgentRepository;
import com.syn.tkt.service.AgentService;
import com.syn.tkt.service.dto.AgentDTO;
import com.syn.tkt.service.mapper.AgentMapper;

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
            .address(DEFAULT_ADDRESS);
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
            .address(UPDATED_ADDRESS);
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
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
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
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
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
            .address(UPDATED_ADDRESS);
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
