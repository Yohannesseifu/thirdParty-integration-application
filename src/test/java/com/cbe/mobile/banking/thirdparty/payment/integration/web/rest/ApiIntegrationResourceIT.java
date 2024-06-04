package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegrationAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.AuthType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.ContentType;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ApiIntegrationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiIntegrationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.ApiIntegrationMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ApiIntegrationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApiIntegrationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final ContentType DEFAULT_TYPE = ContentType.JSON;
    private static final ContentType UPDATED_TYPE = ContentType.SOAP;

    private static final AuthType DEFAULT_AUTH = AuthType.NONE;
    private static final AuthType UPDATED_AUTH = AuthType.BASIC;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMEOUT = 1;
    private static final Integer UPDATED_TIMEOUT = 2;

    private static final Integer DEFAULT_RETRY_RETRIES = 1;
    private static final Integer UPDATED_RETRY_RETRIES = 2;

    private static final Integer DEFAULT_RETRY_DELAY = 1;
    private static final Integer UPDATED_RETRY_DELAY = 2;

    private static final String ENTITY_API_URL = "/api/api-integrations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApiIntegrationRepository apiIntegrationRepository;

    @Autowired
    private ApiIntegrationMapper apiIntegrationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiIntegrationMockMvc;

    private ApiIntegration apiIntegration;

    private ApiIntegration insertedApiIntegration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiIntegration createEntity(EntityManager em) {
        ApiIntegration apiIntegration = new ApiIntegration()
            .name(DEFAULT_NAME)
            .url(DEFAULT_URL)
            .type(DEFAULT_TYPE)
            .auth(DEFAULT_AUTH)
            .description(DEFAULT_DESCRIPTION)
            .version(DEFAULT_VERSION)
            .timeout(DEFAULT_TIMEOUT)
            .retryRetries(DEFAULT_RETRY_RETRIES)
            .retryDelay(DEFAULT_RETRY_DELAY);
        return apiIntegration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiIntegration createUpdatedEntity(EntityManager em) {
        ApiIntegration apiIntegration = new ApiIntegration()
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .type(UPDATED_TYPE)
            .auth(UPDATED_AUTH)
            .description(UPDATED_DESCRIPTION)
            .version(UPDATED_VERSION)
            .timeout(UPDATED_TIMEOUT)
            .retryRetries(UPDATED_RETRY_RETRIES)
            .retryDelay(UPDATED_RETRY_DELAY);
        return apiIntegration;
    }

    @BeforeEach
    public void initTest() {
        apiIntegration = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedApiIntegration != null) {
            apiIntegrationRepository.delete(insertedApiIntegration);
            insertedApiIntegration = null;
        }
    }

    @Test
    @Transactional
    void createApiIntegration() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ApiIntegration
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);
        var returnedApiIntegrationDTO = om.readValue(
            restApiIntegrationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiIntegrationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ApiIntegrationDTO.class
        );

        // Validate the ApiIntegration in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedApiIntegration = apiIntegrationMapper.toEntity(returnedApiIntegrationDTO);
        assertApiIntegrationUpdatableFieldsEquals(returnedApiIntegration, getPersistedApiIntegration(returnedApiIntegration));

        insertedApiIntegration = returnedApiIntegration;
    }

    @Test
    @Transactional
    void createApiIntegrationWithExistingId() throws Exception {
        // Create the ApiIntegration with an existing ID
        apiIntegration.setId(1L);
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiIntegrationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiIntegrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        apiIntegration.setName(null);

        // Create the ApiIntegration, which fails.
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        restApiIntegrationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiIntegrationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUrlIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        apiIntegration.setUrl(null);

        // Create the ApiIntegration, which fails.
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        restApiIntegrationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiIntegrationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        apiIntegration.setType(null);

        // Create the ApiIntegration, which fails.
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        restApiIntegrationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiIntegrationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        apiIntegration.setAuth(null);

        // Create the ApiIntegration, which fails.
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        restApiIntegrationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiIntegrationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApiIntegrations() throws Exception {
        // Initialize the database
        insertedApiIntegration = apiIntegrationRepository.saveAndFlush(apiIntegration);

        // Get all the apiIntegrationList
        restApiIntegrationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiIntegration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].auth").value(hasItem(DEFAULT_AUTH.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].timeout").value(hasItem(DEFAULT_TIMEOUT)))
            .andExpect(jsonPath("$.[*].retryRetries").value(hasItem(DEFAULT_RETRY_RETRIES)))
            .andExpect(jsonPath("$.[*].retryDelay").value(hasItem(DEFAULT_RETRY_DELAY)));
    }

    @Test
    @Transactional
    void getApiIntegration() throws Exception {
        // Initialize the database
        insertedApiIntegration = apiIntegrationRepository.saveAndFlush(apiIntegration);

        // Get the apiIntegration
        restApiIntegrationMockMvc
            .perform(get(ENTITY_API_URL_ID, apiIntegration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiIntegration.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.auth").value(DEFAULT_AUTH.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.timeout").value(DEFAULT_TIMEOUT))
            .andExpect(jsonPath("$.retryRetries").value(DEFAULT_RETRY_RETRIES))
            .andExpect(jsonPath("$.retryDelay").value(DEFAULT_RETRY_DELAY));
    }

    @Test
    @Transactional
    void getNonExistingApiIntegration() throws Exception {
        // Get the apiIntegration
        restApiIntegrationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApiIntegration() throws Exception {
        // Initialize the database
        insertedApiIntegration = apiIntegrationRepository.saveAndFlush(apiIntegration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiIntegration
        ApiIntegration updatedApiIntegration = apiIntegrationRepository.findById(apiIntegration.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApiIntegration are not directly saved in db
        em.detach(updatedApiIntegration);
        updatedApiIntegration
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .type(UPDATED_TYPE)
            .auth(UPDATED_AUTH)
            .description(UPDATED_DESCRIPTION)
            .version(UPDATED_VERSION)
            .timeout(UPDATED_TIMEOUT)
            .retryRetries(UPDATED_RETRY_RETRIES)
            .retryDelay(UPDATED_RETRY_DELAY);
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(updatedApiIntegration);

        restApiIntegrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiIntegrationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiIntegrationDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApiIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedApiIntegrationToMatchAllProperties(updatedApiIntegration);
    }

    @Test
    @Transactional
    void putNonExistingApiIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiIntegration.setId(longCount.incrementAndGet());

        // Create the ApiIntegration
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiIntegrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiIntegrationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiIntegrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApiIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiIntegration.setId(longCount.incrementAndGet());

        // Create the ApiIntegration
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiIntegrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiIntegrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApiIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiIntegration.setId(longCount.incrementAndGet());

        // Create the ApiIntegration
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiIntegrationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiIntegrationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApiIntegrationWithPatch() throws Exception {
        // Initialize the database
        insertedApiIntegration = apiIntegrationRepository.saveAndFlush(apiIntegration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiIntegration using partial update
        ApiIntegration partialUpdatedApiIntegration = new ApiIntegration();
        partialUpdatedApiIntegration.setId(apiIntegration.getId());

        partialUpdatedApiIntegration.url(UPDATED_URL).auth(UPDATED_AUTH).description(UPDATED_DESCRIPTION).retryDelay(UPDATED_RETRY_DELAY);

        restApiIntegrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiIntegration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApiIntegration))
            )
            .andExpect(status().isOk());

        // Validate the ApiIntegration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApiIntegrationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedApiIntegration, apiIntegration),
            getPersistedApiIntegration(apiIntegration)
        );
    }

    @Test
    @Transactional
    void fullUpdateApiIntegrationWithPatch() throws Exception {
        // Initialize the database
        insertedApiIntegration = apiIntegrationRepository.saveAndFlush(apiIntegration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiIntegration using partial update
        ApiIntegration partialUpdatedApiIntegration = new ApiIntegration();
        partialUpdatedApiIntegration.setId(apiIntegration.getId());

        partialUpdatedApiIntegration
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .type(UPDATED_TYPE)
            .auth(UPDATED_AUTH)
            .description(UPDATED_DESCRIPTION)
            .version(UPDATED_VERSION)
            .timeout(UPDATED_TIMEOUT)
            .retryRetries(UPDATED_RETRY_RETRIES)
            .retryDelay(UPDATED_RETRY_DELAY);

        restApiIntegrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiIntegration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApiIntegration))
            )
            .andExpect(status().isOk());

        // Validate the ApiIntegration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApiIntegrationUpdatableFieldsEquals(partialUpdatedApiIntegration, getPersistedApiIntegration(partialUpdatedApiIntegration));
    }

    @Test
    @Transactional
    void patchNonExistingApiIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiIntegration.setId(longCount.incrementAndGet());

        // Create the ApiIntegration
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiIntegrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apiIntegrationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiIntegrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApiIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiIntegration.setId(longCount.incrementAndGet());

        // Create the ApiIntegration
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiIntegrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiIntegrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApiIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiIntegration.setId(longCount.incrementAndGet());

        // Create the ApiIntegration
        ApiIntegrationDTO apiIntegrationDTO = apiIntegrationMapper.toDto(apiIntegration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiIntegrationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(apiIntegrationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApiIntegration() throws Exception {
        // Initialize the database
        insertedApiIntegration = apiIntegrationRepository.saveAndFlush(apiIntegration);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the apiIntegration
        restApiIntegrationMockMvc
            .perform(delete(ENTITY_API_URL_ID, apiIntegration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return apiIntegrationRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected ApiIntegration getPersistedApiIntegration(ApiIntegration apiIntegration) {
        return apiIntegrationRepository.findById(apiIntegration.getId()).orElseThrow();
    }

    protected void assertPersistedApiIntegrationToMatchAllProperties(ApiIntegration expectedApiIntegration) {
        assertApiIntegrationAllPropertiesEquals(expectedApiIntegration, getPersistedApiIntegration(expectedApiIntegration));
    }

    protected void assertPersistedApiIntegrationToMatchUpdatableProperties(ApiIntegration expectedApiIntegration) {
        assertApiIntegrationAllUpdatablePropertiesEquals(expectedApiIntegration, getPersistedApiIntegration(expectedApiIntegration));
    }
}
