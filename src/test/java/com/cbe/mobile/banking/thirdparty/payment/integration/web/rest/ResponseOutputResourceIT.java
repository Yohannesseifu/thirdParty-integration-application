package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutputAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.CoreTransferParams;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.LogicalOperator;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.Scope;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ResponseOutputRepository;
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

    private static final CoreTransferParams DEFAULT_TRANSFER_CORE_MAPPING = CoreTransferParams.AMOUNT;
    private static final CoreTransferParams UPDATED_TRANSFER_CORE_MAPPING = CoreTransferParams.CREDIT_ACCOUNT;

    private static final Boolean DEFAULT_IS_LOGIC_FIELD = false;
    private static final Boolean UPDATED_IS_LOGIC_FIELD = true;

    private static final String DEFAULT_CONSTANT_VALUE_TO_COMPARE = "AAAAAAAAAA";
    private static final String UPDATED_CONSTANT_VALUE_TO_COMPARE = "BBBBBBBBBB";

    private static final LogicalOperator DEFAULT_OPERATOR_TO_COMPARE_VALUE = LogicalOperator.EQUAL_TO;
    private static final LogicalOperator UPDATED_OPERATOR_TO_COMPARE_VALUE = LogicalOperator.NOT_EQUAL_TO;

    private static final Boolean DEFAULT_IS_REQUIRED = false;
    private static final Boolean UPDATED_IS_REQUIRED = true;

    private static final String ENTITY_API_URL = "/api/response-outputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ResponseOutputRepository responseOutputRepository;

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
            .transferCoreMapping(DEFAULT_TRANSFER_CORE_MAPPING)
            .isLogicField(DEFAULT_IS_LOGIC_FIELD)
            .constantValueToCompare(DEFAULT_CONSTANT_VALUE_TO_COMPARE)
            .operatorToCompareValue(DEFAULT_OPERATOR_TO_COMPARE_VALUE)
            .isRequired(DEFAULT_IS_REQUIRED);
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
            .transferCoreMapping(UPDATED_TRANSFER_CORE_MAPPING)
            .isLogicField(UPDATED_IS_LOGIC_FIELD)
            .constantValueToCompare(UPDATED_CONSTANT_VALUE_TO_COMPARE)
            .operatorToCompareValue(UPDATED_OPERATOR_TO_COMPARE_VALUE)
            .isRequired(UPDATED_IS_REQUIRED);
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
        var returnedResponseOutput = om.readValue(
            restResponseOutputMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutput)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ResponseOutput.class
        );

        // Validate the ResponseOutput in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertResponseOutputUpdatableFieldsEquals(returnedResponseOutput, getPersistedResponseOutput(returnedResponseOutput));

        insertedResponseOutput = returnedResponseOutput;
    }

    @Test
    @Transactional
    void createResponseOutputWithExistingId() throws Exception {
        // Create the ResponseOutput with an existing ID
        insertedResponseOutput = responseOutputRepository.saveAndFlush(responseOutput);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponseOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutput)))
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

        restResponseOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutput)))
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

        restResponseOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutput)))
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

        restResponseOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutput)))
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

        restResponseOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutput)))
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
            .andExpect(jsonPath("$.[*].id").value(hasItem(responseOutput.getId().toString())))
            .andExpect(jsonPath("$.[*].outputName").value(hasItem(DEFAULT_OUTPUT_NAME)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].responseValuePath").value(hasItem(DEFAULT_RESPONSE_VALUE_PATH)))
            .andExpect(jsonPath("$.[*].responseScope").value(hasItem(DEFAULT_RESPONSE_SCOPE.toString())))
            .andExpect(jsonPath("$.[*].transferCoreMapping").value(hasItem(DEFAULT_TRANSFER_CORE_MAPPING.toString())))
            .andExpect(jsonPath("$.[*].isLogicField").value(hasItem(DEFAULT_IS_LOGIC_FIELD.booleanValue())))
            .andExpect(jsonPath("$.[*].constantValueToCompare").value(hasItem(DEFAULT_CONSTANT_VALUE_TO_COMPARE)))
            .andExpect(jsonPath("$.[*].operatorToCompareValue").value(hasItem(DEFAULT_OPERATOR_TO_COMPARE_VALUE.toString())))
            .andExpect(jsonPath("$.[*].isRequired").value(hasItem(DEFAULT_IS_REQUIRED.booleanValue())));
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
            .andExpect(jsonPath("$.id").value(responseOutput.getId().toString()))
            .andExpect(jsonPath("$.outputName").value(DEFAULT_OUTPUT_NAME))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.responseValuePath").value(DEFAULT_RESPONSE_VALUE_PATH))
            .andExpect(jsonPath("$.responseScope").value(DEFAULT_RESPONSE_SCOPE.toString()))
            .andExpect(jsonPath("$.transferCoreMapping").value(DEFAULT_TRANSFER_CORE_MAPPING.toString()))
            .andExpect(jsonPath("$.isLogicField").value(DEFAULT_IS_LOGIC_FIELD.booleanValue()))
            .andExpect(jsonPath("$.constantValueToCompare").value(DEFAULT_CONSTANT_VALUE_TO_COMPARE))
            .andExpect(jsonPath("$.operatorToCompareValue").value(DEFAULT_OPERATOR_TO_COMPARE_VALUE.toString()))
            .andExpect(jsonPath("$.isRequired").value(DEFAULT_IS_REQUIRED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingResponseOutput() throws Exception {
        // Get the responseOutput
        restResponseOutputMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
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
            .transferCoreMapping(UPDATED_TRANSFER_CORE_MAPPING)
            .isLogicField(UPDATED_IS_LOGIC_FIELD)
            .constantValueToCompare(UPDATED_CONSTANT_VALUE_TO_COMPARE)
            .operatorToCompareValue(UPDATED_OPERATOR_TO_COMPARE_VALUE)
            .isRequired(UPDATED_IS_REQUIRED);

        restResponseOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResponseOutput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedResponseOutput))
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
        responseOutput.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responseOutput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(responseOutput))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponseOutput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responseOutput.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(responseOutput))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponseOutput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responseOutput.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(responseOutput)))
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
            .transferCoreMapping(UPDATED_TRANSFER_CORE_MAPPING)
            .constantValueToCompare(UPDATED_CONSTANT_VALUE_TO_COMPARE)
            .operatorToCompareValue(UPDATED_OPERATOR_TO_COMPARE_VALUE)
            .isRequired(UPDATED_IS_REQUIRED);

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
            .transferCoreMapping(UPDATED_TRANSFER_CORE_MAPPING)
            .isLogicField(UPDATED_IS_LOGIC_FIELD)
            .constantValueToCompare(UPDATED_CONSTANT_VALUE_TO_COMPARE)
            .operatorToCompareValue(UPDATED_OPERATOR_TO_COMPARE_VALUE)
            .isRequired(UPDATED_IS_REQUIRED);

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
        responseOutput.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responseOutput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(responseOutput))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponseOutput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responseOutput.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(responseOutput))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseOutput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponseOutput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        responseOutput.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseOutputMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(responseOutput)))
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
            .perform(delete(ENTITY_API_URL_ID, responseOutput.getId().toString()).accept(MediaType.APPLICATION_JSON))
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
