package com.syn.tkt.web.rest;

import com.syn.tkt.ServicedeskApp;
import com.syn.tkt.domain.Company;
import com.syn.tkt.repository.CompanyRepository;
import com.syn.tkt.service.CompanyService;
import com.syn.tkt.service.dto.CompanyDTO;
import com.syn.tkt.service.mapper.CompanyMapper;

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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@SpringBootTest(classes = ServicedeskApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyResourceIT {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_HEALTH_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_HEALTH_SCORE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TIER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TIER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RENEWAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RENEWAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INDUSTRY = "AAAAAAAAAA";
    private static final String UPDATED_INDUSTRY = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_LOGO_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_LOGO_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_LOGO_FILE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_LOGO_FILE_LOCATION = "BBBBBBBBBB";

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .companyName(DEFAULT_COMPANY_NAME)
            .description(DEFAULT_DESCRIPTION)
            .notes(DEFAULT_NOTES)
            .healthScore(DEFAULT_HEALTH_SCORE)
            .accountTier(DEFAULT_ACCOUNT_TIER)
            .renewalDate(DEFAULT_RENEWAL_DATE)
            .industry(DEFAULT_INDUSTRY)
            .companyLogoFileName(DEFAULT_COMPANY_LOGO_FILE_NAME)
            .companyLogoFileLocation(DEFAULT_COMPANY_LOGO_FILE_LOCATION);
        return company;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .companyName(UPDATED_COMPANY_NAME)
            .description(UPDATED_DESCRIPTION)
            .notes(UPDATED_NOTES)
            .healthScore(UPDATED_HEALTH_SCORE)
            .accountTier(UPDATED_ACCOUNT_TIER)
            .renewalDate(UPDATED_RENEWAL_DATE)
            .industry(UPDATED_INDUSTRY)
            .companyLogoFileName(UPDATED_COMPANY_LOGO_FILE_NAME)
            .companyLogoFileLocation(UPDATED_COMPANY_LOGO_FILE_LOCATION);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();
        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompany.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCompany.getHealthScore()).isEqualTo(DEFAULT_HEALTH_SCORE);
        assertThat(testCompany.getAccountTier()).isEqualTo(DEFAULT_ACCOUNT_TIER);
        assertThat(testCompany.getRenewalDate()).isEqualTo(DEFAULT_RENEWAL_DATE);
        assertThat(testCompany.getIndustry()).isEqualTo(DEFAULT_INDUSTRY);
        assertThat(testCompany.getCompanyLogoFileName()).isEqualTo(DEFAULT_COMPANY_LOGO_FILE_NAME);
        assertThat(testCompany.getCompanyLogoFileLocation()).isEqualTo(DEFAULT_COMPANY_LOGO_FILE_LOCATION);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].healthScore").value(hasItem(DEFAULT_HEALTH_SCORE)))
            .andExpect(jsonPath("$.[*].accountTier").value(hasItem(DEFAULT_ACCOUNT_TIER)))
            .andExpect(jsonPath("$.[*].renewalDate").value(hasItem(DEFAULT_RENEWAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].industry").value(hasItem(DEFAULT_INDUSTRY)))
            .andExpect(jsonPath("$.[*].companyLogoFileName").value(hasItem(DEFAULT_COMPANY_LOGO_FILE_NAME)))
            .andExpect(jsonPath("$.[*].companyLogoFileLocation").value(hasItem(DEFAULT_COMPANY_LOGO_FILE_LOCATION)));
    }
    
    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.healthScore").value(DEFAULT_HEALTH_SCORE))
            .andExpect(jsonPath("$.accountTier").value(DEFAULT_ACCOUNT_TIER))
            .andExpect(jsonPath("$.renewalDate").value(DEFAULT_RENEWAL_DATE.toString()))
            .andExpect(jsonPath("$.industry").value(DEFAULT_INDUSTRY))
            .andExpect(jsonPath("$.companyLogoFileName").value(DEFAULT_COMPANY_LOGO_FILE_NAME))
            .andExpect(jsonPath("$.companyLogoFileLocation").value(DEFAULT_COMPANY_LOGO_FILE_LOCATION));
    }
    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .companyName(UPDATED_COMPANY_NAME)
            .description(UPDATED_DESCRIPTION)
            .notes(UPDATED_NOTES)
            .healthScore(UPDATED_HEALTH_SCORE)
            .accountTier(UPDATED_ACCOUNT_TIER)
            .renewalDate(UPDATED_RENEWAL_DATE)
            .industry(UPDATED_INDUSTRY)
            .companyLogoFileName(UPDATED_COMPANY_LOGO_FILE_NAME)
            .companyLogoFileLocation(UPDATED_COMPANY_LOGO_FILE_LOCATION);
        CompanyDTO companyDTO = companyMapper.toDto(updatedCompany);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompany.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCompany.getHealthScore()).isEqualTo(UPDATED_HEALTH_SCORE);
        assertThat(testCompany.getAccountTier()).isEqualTo(UPDATED_ACCOUNT_TIER);
        assertThat(testCompany.getRenewalDate()).isEqualTo(UPDATED_RENEWAL_DATE);
        assertThat(testCompany.getIndustry()).isEqualTo(UPDATED_INDUSTRY);
        assertThat(testCompany.getCompanyLogoFileName()).isEqualTo(UPDATED_COMPANY_LOGO_FILE_NAME);
        assertThat(testCompany.getCompanyLogoFileLocation()).isEqualTo(UPDATED_COMPANY_LOGO_FILE_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
