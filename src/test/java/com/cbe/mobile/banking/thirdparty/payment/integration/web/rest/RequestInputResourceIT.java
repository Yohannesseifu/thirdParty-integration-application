package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInputAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.RequestInputType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.RequestValueSource;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.RequestInputRepository;
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
 * Integration tests for the {@link RequestInputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestInputResourceIT {

    private static final String DEFAULT_INPUT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INPUT_NAME = "BBBBBBBBBB";

    private static final RequestInputType DEFAULT_INPUT_TYPE = RequestInputType.HEADER;
    private static final RequestInputType UPDATED_INPUT_TYPE = RequestInputType.BODY;

    private static final DataType DEFAULT_DATA_TYPE = DataType.COLLECTION;
    private static final DataType UPDATED_DATA_TYPE = DataType.STRING;

    private static final String DEFAULT_TEST_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TEST_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final RequestValueSource DEFAULT_VALUE_SOURCE = RequestValueSource.USER_INPUT;
    private static final RequestValueSource UPDATED_VALUE_SOURCE = RequestValueSource.PAYMENT_DETAIL;

    private static final Boolean DEFAULT_IS_ENCODED = false;
    private static final Boolean UPDATED_IS_ENCODED = true;

    private static final Integer DEFAULT_MAX_LENGTH = 1;
    private static final Integer UPDATED_MAX_LENGTH = 2;

    private static final Integer DEFAULT_MIN_LENGTH = 1;
    private static final Integer UPDATED_MIN_LENGTH = 2;

    private static final String DEFAULT_MIN_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_MIN_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_MAX_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_MAX_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_PATTERN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_REQUIRED = false;
    private static final Boolean UPDATED_IS_REQUIRED = true;

    private static final String ENTITY_API_URL = "/api/request-inputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RequestInputRepository requestInputRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestInputMockMvc;

    private RequestInput requestInput;

    private RequestInput insertedRequestInput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestInput createEntity(EntityManager em) {
        RequestInput requestInput = new RequestInput()
            .inputName(DEFAULT_INPUT_NAME)
            .inputType(DEFAULT_INPUT_TYPE)
            .dataType(DEFAULT_DATA_TYPE)
            .testValue(DEFAULT_TEST_VALUE)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .valueSource(DEFAULT_VALUE_SOURCE)
            .isEncoded(DEFAULT_IS_ENCODED)
            .maxLength(DEFAULT_MAX_LENGTH)
            .minLength(DEFAULT_MIN_LENGTH)
            .minValue(DEFAULT_MIN_VALUE)
            .maxValue(DEFAULT_MAX_VALUE)
            .validationPattern(DEFAULT_VALIDATION_PATTERN)
            .isRequired(DEFAULT_IS_REQUIRED);
        return requestInput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestInput createUpdatedEntity(EntityManager em) {
        RequestInput requestInput = new RequestInput()
            .inputName(UPDATED_INPUT_NAME)
            .inputType(UPDATED_INPUT_TYPE)
            .dataType(UPDATED_DATA_TYPE)
            .testValue(UPDATED_TEST_VALUE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .valueSource(UPDATED_VALUE_SOURCE)
            .isEncoded(UPDATED_IS_ENCODED)
            .maxLength(UPDATED_MAX_LENGTH)
            .minLength(UPDATED_MIN_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .validationPattern(UPDATED_VALIDATION_PATTERN)
            .isRequired(UPDATED_IS_REQUIRED);
        return requestInput;
    }

    @BeforeEach
    public void initTest() {
        requestInput = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRequestInput != null) {
            requestInputRepository.delete(insertedRequestInput);
            insertedRequestInput = null;
        }
    }

    @Test
    @Transactional
    void createRequestInput() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RequestInput
        var returnedRequestInput = om.readValue(
            restRequestInputMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInput)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RequestInput.class
        );

        // Validate the RequestInput in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRequestInputUpdatableFieldsEquals(returnedRequestInput, getPersistedRequestInput(returnedRequestInput));

        insertedRequestInput = returnedRequestInput;
    }

    @Test
    @Transactional
    void createRequestInputWithExistingId() throws Exception {
        // Create the RequestInput with an existing ID
        insertedRequestInput = requestInputRepository.saveAndFlush(requestInput);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestInputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInput)))
            .andExpect(status().isBadRequest());

        // Validate the RequestInput in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInputNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestInput.setInputName(null);

        // Create the RequestInput, which fails.

        restRequestInputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInput)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInputTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestInput.setInputType(null);

        // Create the RequestInput, which fails.

        restRequestInputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInput)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        requestInput.setDataType(null);

        // Create the RequestInput, which fails.

        restRequestInputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInput)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequestInputs() throws Exception {
        // Initialize the database
        insertedRequestInput = requestInputRepository.saveAndFlush(requestInput);

        // Get all the requestInputList
        restRequestInputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestInput.getId().toString())))
            .andExpect(jsonPath("$.[*].inputName").value(hasItem(DEFAULT_INPUT_NAME)))
            .andExpect(jsonPath("$.[*].inputType").value(hasItem(DEFAULT_INPUT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].testValue").value(hasItem(DEFAULT_TEST_VALUE)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueSource").value(hasItem(DEFAULT_VALUE_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].isEncoded").value(hasItem(DEFAULT_IS_ENCODED.booleanValue())))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE)))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE)))
            .andExpect(jsonPath("$.[*].validationPattern").value(hasItem(DEFAULT_VALIDATION_PATTERN)))
            .andExpect(jsonPath("$.[*].isRequired").value(hasItem(DEFAULT_IS_REQUIRED.booleanValue())));
    }

    @Test
    @Transactional
    void getRequestInput() throws Exception {
        // Initialize the database
        insertedRequestInput = requestInputRepository.saveAndFlush(requestInput);

        // Get the requestInput
        restRequestInputMockMvc
            .perform(get(ENTITY_API_URL_ID, requestInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestInput.getId().toString()))
            .andExpect(jsonPath("$.inputName").value(DEFAULT_INPUT_NAME))
            .andExpect(jsonPath("$.inputType").value(DEFAULT_INPUT_TYPE.toString()))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.testValue").value(DEFAULT_TEST_VALUE))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueSource").value(DEFAULT_VALUE_SOURCE.toString()))
            .andExpect(jsonPath("$.isEncoded").value(DEFAULT_IS_ENCODED.booleanValue()))
            .andExpect(jsonPath("$.maxLength").value(DEFAULT_MAX_LENGTH))
            .andExpect(jsonPath("$.minLength").value(DEFAULT_MIN_LENGTH))
            .andExpect(jsonPath("$.minValue").value(DEFAULT_MIN_VALUE))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE))
            .andExpect(jsonPath("$.validationPattern").value(DEFAULT_VALIDATION_PATTERN))
            .andExpect(jsonPath("$.isRequired").value(DEFAULT_IS_REQUIRED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRequestInput() throws Exception {
        // Get the requestInput
        restRequestInputMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestInput() throws Exception {
        // Initialize the database
        insertedRequestInput = requestInputRepository.saveAndFlush(requestInput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestInput
        RequestInput updatedRequestInput = requestInputRepository.findById(requestInput.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRequestInput are not directly saved in db
        em.detach(updatedRequestInput);
        updatedRequestInput
            .inputName(UPDATED_INPUT_NAME)
            .inputType(UPDATED_INPUT_TYPE)
            .dataType(UPDATED_DATA_TYPE)
            .testValue(UPDATED_TEST_VALUE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .valueSource(UPDATED_VALUE_SOURCE)
            .isEncoded(UPDATED_IS_ENCODED)
            .maxLength(UPDATED_MAX_LENGTH)
            .minLength(UPDATED_MIN_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .validationPattern(UPDATED_VALIDATION_PATTERN)
            .isRequired(UPDATED_IS_REQUIRED);

        restRequestInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequestInput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRequestInput))
            )
            .andExpect(status().isOk());

        // Validate the RequestInput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRequestInputToMatchAllProperties(updatedRequestInput);
    }

    @Test
    @Transactional
    void putNonExistingRequestInput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInput.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestInput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestInput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestInput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInput.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(requestInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestInput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestInput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInput.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestInputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(requestInput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestInput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestInputWithPatch() throws Exception {
        // Initialize the database
        insertedRequestInput = requestInputRepository.saveAndFlush(requestInput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestInput using partial update
        RequestInput partialUpdatedRequestInput = new RequestInput();
        partialUpdatedRequestInput.setId(requestInput.getId());

        partialUpdatedRequestInput
            .inputType(UPDATED_INPUT_TYPE)
            .isEncoded(UPDATED_IS_ENCODED)
            .minLength(UPDATED_MIN_LENGTH)
            .maxValue(UPDATED_MAX_VALUE);

        restRequestInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestInput))
            )
            .andExpect(status().isOk());

        // Validate the RequestInput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestInputUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRequestInput, requestInput),
            getPersistedRequestInput(requestInput)
        );
    }

    @Test
    @Transactional
    void fullUpdateRequestInputWithPatch() throws Exception {
        // Initialize the database
        insertedRequestInput = requestInputRepository.saveAndFlush(requestInput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the requestInput using partial update
        RequestInput partialUpdatedRequestInput = new RequestInput();
        partialUpdatedRequestInput.setId(requestInput.getId());

        partialUpdatedRequestInput
            .inputName(UPDATED_INPUT_NAME)
            .inputType(UPDATED_INPUT_TYPE)
            .dataType(UPDATED_DATA_TYPE)
            .testValue(UPDATED_TEST_VALUE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .valueSource(UPDATED_VALUE_SOURCE)
            .isEncoded(UPDATED_IS_ENCODED)
            .maxLength(UPDATED_MAX_LENGTH)
            .minLength(UPDATED_MIN_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .validationPattern(UPDATED_VALIDATION_PATTERN)
            .isRequired(UPDATED_IS_REQUIRED);

        restRequestInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRequestInput))
            )
            .andExpect(status().isOk());

        // Validate the RequestInput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRequestInputUpdatableFieldsEquals(partialUpdatedRequestInput, getPersistedRequestInput(partialUpdatedRequestInput));
    }

    @Test
    @Transactional
    void patchNonExistingRequestInput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInput.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestInput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestInput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInput.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(requestInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestInput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestInput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        requestInput.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestInputMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(requestInput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestInput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestInput() throws Exception {
        // Initialize the database
        insertedRequestInput = requestInputRepository.saveAndFlush(requestInput);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the requestInput
        restRequestInputMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestInput.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return requestInputRepository.count();
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

    protected RequestInput getPersistedRequestInput(RequestInput requestInput) {
        return requestInputRepository.findById(requestInput.getId()).orElseThrow();
    }

    protected void assertPersistedRequestInputToMatchAllProperties(RequestInput expectedRequestInput) {
        assertRequestInputAllPropertiesEquals(expectedRequestInput, getPersistedRequestInput(expectedRequestInput));
    }

    protected void assertPersistedRequestInputToMatchUpdatableProperties(RequestInput expectedRequestInput) {
        assertRequestInputAllUpdatablePropertiesEquals(expectedRequestInput, getPersistedRequestInput(expectedRequestInput));
    }
}
