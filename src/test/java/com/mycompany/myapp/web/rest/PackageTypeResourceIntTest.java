package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;
import com.mycompany.myapp.domain.PackageType;
import com.mycompany.myapp.repository.PackageTypeRepository;
import com.mycompany.myapp.service.PackageTypeService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PackageTypeResource REST controller.
 *
 * @see PackageTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyappApp.class)
@WebAppConfiguration
@IntegrationTest
public class PackageTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "A";
    private static final String UPDATED_TYPE = "B";
    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";
    private static final String DEFAULT_DESCRIPTION = "A";
    private static final String UPDATED_DESCRIPTION = "B";
    private static final String DEFAULT_RULE = "A";
    private static final String UPDATED_RULE = "B";

    @Inject
    private PackageTypeRepository packageTypeRepository;

    @Inject
    private PackageTypeService packageTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPackageTypeMockMvc;

    private PackageType packageType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PackageTypeResource packageTypeResource = new PackageTypeResource();
        ReflectionTestUtils.setField(packageTypeResource, "packageTypeService", packageTypeService);
        this.restPackageTypeMockMvc = MockMvcBuilders.standaloneSetup(packageTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        packageType = new PackageType().builder()
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .rule(DEFAULT_RULE)
            .build();
    }

    @Test
    @Transactional
    public void createPackageType() throws Exception {
        int databaseSizeBeforeCreate = packageTypeRepository.findAll().size();

        // Create the PackageType

        restPackageTypeMockMvc.perform(post("/api/package-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packageType)))
                .andExpect(status().isCreated());

        // Validate the PackageType in the database
        List<PackageType> packageTypes = packageTypeRepository.findAll();
        assertThat(packageTypes).hasSize(databaseSizeBeforeCreate + 1);
        PackageType testPackageType = packageTypes.get(packageTypes.size() - 1);
        assertThat(testPackageType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPackageType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPackageType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPackageType.getRule()).isEqualTo(DEFAULT_RULE);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageTypeRepository.findAll().size();
        // set the field null
        packageType.setType(null);

        // Create the PackageType, which fails.

        restPackageTypeMockMvc.perform(post("/api/package-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packageType)))
                .andExpect(status().isBadRequest());

        List<PackageType> packageTypes = packageTypeRepository.findAll();
        assertThat(packageTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageTypeRepository.findAll().size();
        // set the field null
        packageType.setName(null);

        // Create the PackageType, which fails.

        restPackageTypeMockMvc.perform(post("/api/package-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packageType)))
                .andExpect(status().isBadRequest());

        List<PackageType> packageTypes = packageTypeRepository.findAll();
        assertThat(packageTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuleIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageTypeRepository.findAll().size();
        // set the field null
        packageType.setRule(null);

        // Create the PackageType, which fails.

        restPackageTypeMockMvc.perform(post("/api/package-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packageType)))
                .andExpect(status().isBadRequest());

        List<PackageType> packageTypes = packageTypeRepository.findAll();
        assertThat(packageTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPackageTypes() throws Exception {
        // Initialize the database
        packageTypeRepository.saveAndFlush(packageType);

        // Get all the packageTypes
        restPackageTypeMockMvc.perform(get("/api/package-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(packageType.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].rule").value(hasItem(DEFAULT_RULE.toString())));
    }

    @Test
    @Transactional
    public void getPackageType() throws Exception {
        // Initialize the database
        packageTypeRepository.saveAndFlush(packageType);

        // Get the packageType
        restPackageTypeMockMvc.perform(get("/api/package-types/{id}", packageType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(packageType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.rule").value(DEFAULT_RULE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPackageType() throws Exception {
        // Get the packageType
        restPackageTypeMockMvc.perform(get("/api/package-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackageType() throws Exception {
        // Initialize the database
        packageTypeService.save(packageType);

        int databaseSizeBeforeUpdate = packageTypeRepository.findAll().size();

        // Update the packageType
        PackageType updatedPackageType = new PackageType();
        updatedPackageType.setId(packageType.getId());
        updatedPackageType.setType(UPDATED_TYPE);
        updatedPackageType.setName(UPDATED_NAME);
        updatedPackageType.setDescription(UPDATED_DESCRIPTION);
        updatedPackageType.setRule(UPDATED_RULE);

        restPackageTypeMockMvc.perform(put("/api/package-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPackageType)))
                .andExpect(status().isOk());

        // Validate the PackageType in the database
        List<PackageType> packageTypes = packageTypeRepository.findAll();
        assertThat(packageTypes).hasSize(databaseSizeBeforeUpdate);
        PackageType testPackageType = packageTypes.get(packageTypes.size() - 1);
        assertThat(testPackageType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPackageType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPackageType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPackageType.getRule()).isEqualTo(UPDATED_RULE);
    }

    @Test
    @Transactional
    public void deletePackageType() throws Exception {
        // Initialize the database
        packageTypeService.save(packageType);

        int databaseSizeBeforeDelete = packageTypeRepository.findAll().size();

        // Get the packageType
        restPackageTypeMockMvc.perform(delete("/api/package-types/{id}", packageType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PackageType> packageTypes = packageTypeRepository.findAll();
        assertThat(packageTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
