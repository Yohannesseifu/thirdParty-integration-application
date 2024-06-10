package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequestAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.HttpMethod;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ApiRequestRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiRequestDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.ApiRequestMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
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
 * Integration tests for the {@link ApiRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApiRequestResourceIT {

    private static final String DEFAULT_URI = "AAAAAAAAAA";
    private static final String UPDATED_URI = "BBBBBBBBBB";

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    private static final HttpMethod DEFAULT_METHOD = HttpMethod.POST;
    private static final HttpMethod UPDATED_METHOD = HttpMethod.GET;

    private static final String ENTITY_API_URL = "/api/api-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApiRequestRepository apiRequestRepository;

    @Autowired
    private ApiRequestMapper apiRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiRequestMockMvc;

    private ApiRequest apiRequest;

    private ApiRequest insertedApiRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiRequest createEntity(EntityManager em) {
        ApiRequest apiRequest = new ApiRequest().uri(DEFAULT_URI).body(DEFAULT_BODY).method(DEFAULT_METHOD);
        return apiRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiRequest createUpdatedEntity(EntityManager em) {
        ApiRequest apiRequest = new ApiRequest().uri(UPDATED_URI).body(UPDATED_BODY).method(UPDATED_METHOD);
        return apiRequest;
    }

    @BeforeEach
    public void initTest() {
        apiRequest = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedApiRequest != null) {
            apiRequestRepository.delete(insertedApiRequest);
            insertedApiRequest = null;
        }
    }

    @Test
    @Transactional
    void createApiRequest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);
        var returnedApiRequestDTO = om.readValue(
            restApiRequestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ApiRequestDTO.class
        );

        // Validate the ApiRequest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedApiRequest = apiRequestMapper.toEntity(returnedApiRequestDTO);
        assertApiRequestUpdatableFieldsEquals(returnedApiRequest, getPersistedApiRequest(returnedApiRequest));

        insertedApiRequest = returnedApiRequest;
    }

    @Test
    @Transactional
    void createApiRequestWithExistingId() throws Exception {
        // Create the ApiRequest with an existing ID
        insertedApiRequest = apiRequestRepository.saveAndFlush(apiRequest);
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUriIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        apiRequest.setUri(null);

        // Create the ApiRequest, which fails.
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        restApiRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMethodIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        apiRequest.setMethod(null);

        // Create the ApiRequest, which fails.
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        restApiRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApiRequests() throws Exception {
        // Initialize the database
        insertedApiRequest = apiRequestRepository.saveAndFlush(apiRequest);

        // Get all the apiRequestList
        restApiRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiRequest.getId().toString())))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY)))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())));
    }

    @Test
    @Transactional
    void getApiRequest() throws Exception {
        // Initialize the database
        insertedApiRequest = apiRequestRepository.saveAndFlush(apiRequest);

        // Get the apiRequest
        restApiRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, apiRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiRequest.getId().toString()))
            .andExpect(jsonPath("$.uri").value(DEFAULT_URI))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApiRequest() throws Exception {
        // Get the apiRequest
        restApiRequestMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApiRequest() throws Exception {
        // Initialize the database
        insertedApiRequest = apiRequestRepository.saveAndFlush(apiRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiRequest
        ApiRequest updatedApiRequest = apiRequestRepository.findById(apiRequest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApiRequest are not directly saved in db
        em.detach(updatedApiRequest);
        updatedApiRequest.uri(UPDATED_URI).body(UPDATED_BODY).method(UPDATED_METHOD);
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(updatedApiRequest);

        restApiRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApiRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedApiRequestToMatchAllProperties(updatedApiRequest);
    }

    @Test
    @Transactional
    void putNonExistingApiRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiRequest.setId(UUID.randomUUID());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApiRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiRequest.setId(UUID.randomUUID());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApiRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiRequest.setId(UUID.randomUUID());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apiRequestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApiRequestWithPatch() throws Exception {
        // Initialize the database
        insertedApiRequest = apiRequestRepository.saveAndFlush(apiRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiRequest using partial update
        ApiRequest partialUpdatedApiRequest = new ApiRequest();
        partialUpdatedApiRequest.setId(apiRequest.getId());

        partialUpdatedApiRequest.body(UPDATED_BODY).method(UPDATED_METHOD);

        restApiRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApiRequest))
            )
            .andExpect(status().isOk());

        // Validate the ApiRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApiRequestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedApiRequest, apiRequest),
            getPersistedApiRequest(apiRequest)
        );
    }

    @Test
    @Transactional
    void fullUpdateApiRequestWithPatch() throws Exception {
        // Initialize the database
        insertedApiRequest = apiRequestRepository.saveAndFlush(apiRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiRequest using partial update
        ApiRequest partialUpdatedApiRequest = new ApiRequest();
        partialUpdatedApiRequest.setId(apiRequest.getId());

        partialUpdatedApiRequest.uri(UPDATED_URI).body(UPDATED_BODY).method(UPDATED_METHOD);

        restApiRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApiRequest))
            )
            .andExpect(status().isOk());

        // Validate the ApiRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApiRequestUpdatableFieldsEquals(partialUpdatedApiRequest, getPersistedApiRequest(partialUpdatedApiRequest));
    }

    @Test
    @Transactional
    void patchNonExistingApiRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiRequest.setId(UUID.randomUUID());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apiRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApiRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiRequest.setId(UUID.randomUUID());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApiRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiRequest.setId(UUID.randomUUID());

        // Create the ApiRequest
        ApiRequestDTO apiRequestDTO = apiRequestMapper.toDto(apiRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiRequestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(apiRequestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApiRequest() throws Exception {
        // Initialize the database
        insertedApiRequest = apiRequestRepository.saveAndFlush(apiRequest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the apiRequest
        restApiRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, apiRequest.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return apiRequestRepository.count();
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

    protected ApiRequest getPersistedApiRequest(ApiRequest apiRequest) {
        return apiRequestRepository.findById(apiRequest.getId()).orElseThrow();
    }

    protected void assertPersistedApiRequestToMatchAllProperties(ApiRequest expectedApiRequest) {
        assertApiRequestAllPropertiesEquals(expectedApiRequest, getPersistedApiRequest(expectedApiRequest));
    }

    protected void assertPersistedApiRequestToMatchUpdatableProperties(ApiRequest expectedApiRequest) {
        assertApiRequestAllUpdatablePropertiesEquals(expectedApiRequest, getPersistedApiRequest(expectedApiRequest));
    }
}
