package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutputAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.CoreMapping;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.Scope;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ResponseOutputRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ResponseOutputDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.ResponseOutputMapper;
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
 * Integration tests for the {@link ResponseOutputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResponseOutputResourceIT {

    private static final String DEFAULT_OUTPUT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OUTPUT_NAME = "BBBBBBBBBB";

    private static final DataType DEFAULT_DATA_TYPE = DataType.COLLECTION;
    private static final DataType UPDATED_DATA_TYPE = DataType.STRING;

    private static final String DEFAULT_RESPONSE_VALUE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_VALUE_PATH = "BBBBBBBBBB";

    private static final Scope DEFAULT_RESPONSE_SCOPE = Scope.RESPONSE;
    private static final Scope UPDATED_RESPONSE_SCOPE = Scope.HEADER;

    private static final CoreMapping DEFAULT_CORE_MAPPING = CoreMapping.AMOUNT;
    private static final CoreMapping UPDATED_CORE_MAPPING = CoreMapping.CREDIT_ACCOUNT;

    private static final String DEFAULT_EXPECTED_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_EXPECTED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/response-outputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ResponseOutputRepository responseOutputRepository;

    @Autowired
    private ResponseOutputMapper responseOutputMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponseOutputMockMvc;

    private ResponseOutput responseOutput;

    private ResponseOutput insertedResponseOutput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponseOutput createEntity(EntityManager em) {
        ResponseOutput responseOutput = new ResponseOutput()
            .outputName(DEFAULT_OUTPUT_NAME)
            .dataType(DEFAULT_DATA_TYPE)
            .responseValuePath(DEFAULT_RESPONSE_VALUE_PATH)
            .responseScope(DEFAULT_RESPONSE_SCOPE)
            .coreMapping(DEFAULT_CORE_MAPPING)
            .expectedValue(DEFAULT_EXPECTED_VALUE);
        return responseOutput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponseOutput createUpdatedEntity(EntityManager em) {
        ResponseOutput responseOutput = new ResponseOutput()
            .outputName(UPDATED_OUTPUT_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .responseValuePath(UPDATED_RESPONSE_VALUE_PATH)
            .responseScope(UPDATED_RESPONSE_SCOPE)
            .coreMapping(UPDATED_CORE_MAPPING)
            .expectedValue(UPDATED_EXPECTED_VALUE);
        return responseOutput;
    }

    @BeforeEach
    public void initTest() {
        responseOutput = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedResponseOutput != null) {
            responseOutputRepository.delete(insertedResponseOutput);
            insertedResponseOutput = null;
        }
    }

    @Test
    @Transactional
    void createResponseOutput() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ResponseOutput
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);
        var returnedResponseOutputDTO = om.readValue(
            restResponseOutputMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutputDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ResponseOutputDTO.class
        );

        // Validate the ResponseOutput in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedResponseOutput = responseOutputMapper.toEntity(returnedResponseOutputDTO);
        assertResponseOutputUpdatableFieldsEquals(returnedResponseOutput, getPersistedResponseOutput(returnedResponseOutput));

        insertedResponseOutput = returnedResponseOutput;
    }

    @Test
    @Transactional
    void createResponseOutputWithExistingId() throws Exception {
        // Create the ResponseOutput with an existing ID
        responseOutput.setId(1L);
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponseOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOutputNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        responseOutput.setOutputName(null);

        // Create the ResponseOutput, which fails.
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        restResponseOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutputDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        responseOutput.setDataType(null);

        // Create the ResponseOutput, which fails.
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        restResponseOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutputDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResponseValuePathIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        responseOutput.setResponseValuePath(null);

        // Create the ResponseOutput, which fails.
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        restResponseOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutputDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResponseScopeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        responseOutput.setResponseScope(null);

        // Create the ResponseOutput, which fails.
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        restResponseOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutputDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResponseOutputs() throws Exception {
        // Initialize the database
        insertedResponseOutput = responseOutputRepository.saveAndFlush(responseOutput);

        // Get all the responseOutputList
        restResponseOutputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responseOutput.getId().intValue())))
            .andExpect(jsonPath("$.[*].outputName").value(hasItem(DEFAULT_OUTPUT_NAME)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].responseValuePath").value(hasItem(DEFAULT_RESPONSE_VALUE_PATH)))
            .andExpect(jsonPath("$.[*].responseScope").value(hasItem(DEFAULT_RESPONSE_SCOPE.toString())))
            .andExpect(jsonPath("$.[*].coreMapping").value(hasItem(DEFAULT_CORE_MAPPING.toString())))
            .andExpect(jsonPath("$.[*].expectedValue").value(hasItem(DEFAULT_EXPECTED_VALUE)));
    }

    @Test
    @Transactional
    void getResponseOutput() throws Exception {
        // Initialize the database
        insertedResponseOutput = responseOutputRepository.saveAndFlush(responseOutput);

        // Get the responseOutput
        restResponseOutputMockMvc
            .perform(get(ENTITY_API_URL_ID, responseOutput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responseOutput.getId().intValue()))
            .andExpect(jsonPath("$.outputName").value(DEFAULT_OUTPUT_NAME))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.responseValuePath").value(DEFAULT_RESPONSE_VALUE_PATH))
            .andExpect(jsonPath("$.responseScope").value(DEFAULT_RESPONSE_SCOPE.toString()))
            .andExpect(jsonPath("$.coreMapping").value(DEFAULT_CORE_MAPPING.toString()))
            .andExpect(jsonPath("$.expectedValue").value(DEFAULT_EXPECTED_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingResponseOutput() throws Exception {
        // Get the responseOutput
        restResponseOutputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResponseOutput() throws Exception {
        // Initialize the database
        insertedResponseOutput = responseOutputRepository.saveAndFlush(responseOutput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the responseOutput
        ResponseOutput updatedResponseOutput = responseOutputRepository.findById(responseOutput.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedResponseOutput are not directly saved in db
        em.detach(updatedResponseOutput);
        updatedResponseOutput
            .outputName(UPDATED_OUTPUT_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .responseValuePath(UPDATED_RESPONSE_VALUE_PATH)
            .responseScope(UPDATED_RESPONSE_SCOPE)
            .coreMapping(UPDATED_CORE_MAPPING)
            .expectedValue(UPDATED_EXPECTED_VALUE);
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(updatedResponseOutput);

        restResponseOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responseOutputDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(responseOutputDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedResponseOutputToMatchAllProperties(updatedResponseOutput);
    }

    @Test
    @Transactional
    void putNonExistingResponseOutput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responseOutput.setId(longCount.incrementAndGet());

        // Create the ResponseOutput
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responseOutputDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(responseOutputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponseOutput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responseOutput.setId(longCount.incrementAndGet());

        // Create the ResponseOutput
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(responseOutputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponseOutput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responseOutput.setId(longCount.incrementAndGet());

        // Create the ResponseOutput
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutputDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponseOutputWithPatch() throws Exception {
        // Initialize the database
        insertedResponseOutput = responseOutputRepository.saveAndFlush(responseOutput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the responseOutput using partial update
        ResponseOutput partialUpdatedResponseOutput = new ResponseOutput();
        partialUpdatedResponseOutput.setId(responseOutput.getId());

        partialUpdatedResponseOutput
            .outputName(UPDATED_OUTPUT_NAME)
            .responseScope(UPDATED_RESPONSE_SCOPE)
            .coreMapping(UPDATED_CORE_MAPPING);

        restResponseOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponseOutput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedResponseOutput))
            )
            .andExpect(status().isOk());

        // Validate the ResponseOutput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertResponseOutputUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedResponseOutput, responseOutput),
            getPersistedResponseOutput(responseOutput)
        );
    }

    @Test
    @Transactional
    void fullUpdateResponseOutputWithPatch() throws Exception {
        // Initialize the database
        insertedResponseOutput = responseOutputRepository.saveAndFlush(responseOutput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the responseOutput using partial update
        ResponseOutput partialUpdatedResponseOutput = new ResponseOutput();
        partialUpdatedResponseOutput.setId(responseOutput.getId());

        partialUpdatedResponseOutput
            .outputName(UPDATED_OUTPUT_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .responseValuePath(UPDATED_RESPONSE_VALUE_PATH)
            .responseScope(UPDATED_RESPONSE_SCOPE)
            .coreMapping(UPDATED_CORE_MAPPING)
            .expectedValue(UPDATED_EXPECTED_VALUE);

        restResponseOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponseOutput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedResponseOutput))
            )
            .andExpect(status().isOk());

        // Validate the ResponseOutput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertResponseOutputUpdatableFieldsEquals(partialUpdatedResponseOutput, getPersistedResponseOutput(partialUpdatedResponseOutput));
    }

    @Test
    @Transactional
    void patchNonExistingResponseOutput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responseOutput.setId(longCount.incrementAndGet());

        // Create the ResponseOutput
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responseOutputDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(responseOutputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponseOutput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responseOutput.setId(longCount.incrementAndGet());

        // Create the ResponseOutput
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(responseOutputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponseOutput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responseOutput.setId(longCount.incrementAndGet());

        // Create the ResponseOutput
        ResponseOutputDTO responseOutputDTO = responseOutputMapper.toDto(responseOutput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(responseOutputDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponseOutput() throws Exception {
        // Initialize the database
        insertedResponseOutput = responseOutputRepository.saveAndFlush(responseOutput);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the responseOutput
        restResponseOutputMockMvc
            .perform(delete(ENTITY_API_URL_ID, responseOutput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return responseOutputRepository.count();
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

    protected ResponseOutput getPersistedResponseOutput(ResponseOutput responseOutput) {
        return responseOutputRepository.findById(responseOutput.getId()).orElseThrow();
    }

    protected void assertPersistedResponseOutputToMatchAllProperties(ResponseOutput expectedResponseOutput) {
        assertResponseOutputAllPropertiesEquals(expectedResponseOutput, getPersistedResponseOutput(expectedResponseOutput));
    }

    protected void assertPersistedResponseOutputToMatchUpdatableProperties(ResponseOutput expectedResponseOutput) {
        assertResponseOutputAllUpdatablePropertiesEquals(expectedResponseOutput, getPersistedResponseOutput(expectedResponseOutput));
    }
}
