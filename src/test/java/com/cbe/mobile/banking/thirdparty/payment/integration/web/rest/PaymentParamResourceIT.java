package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParamAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.PaymentParamType;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.PaymentParamRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.PaymentParamDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.PaymentParamMapper;
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
 * Integration tests for the {@link PaymentParamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentParamResourceIT {

    private static final PaymentParamType DEFAULT_TYPE = PaymentParamType.USER_INPUT;
    private static final PaymentParamType UPDATED_TYPE = PaymentParamType.FETCH_RESPONSE;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final DataType DEFAULT_DATA_TYPE = DataType.COLLECTION;
    private static final DataType UPDATED_DATA_TYPE = DataType.STRING;

    private static final String ENTITY_API_URL = "/api/payment-params";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PaymentParamRepository paymentParamRepository;

    @Autowired
    private PaymentParamMapper paymentParamMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentParamMockMvc;

    private PaymentParam paymentParam;

    private PaymentParam insertedPaymentParam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentParam createEntity(EntityManager em) {
        PaymentParam paymentParam = new PaymentParam()
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .dataType(DEFAULT_DATA_TYPE);
        return paymentParam;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentParam createUpdatedEntity(EntityManager em) {
        PaymentParam paymentParam = new PaymentParam()
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .dataType(UPDATED_DATA_TYPE);
        return paymentParam;
    }

    @BeforeEach
    public void initTest() {
        paymentParam = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPaymentParam != null) {
            paymentParamRepository.delete(insertedPaymentParam);
            insertedPaymentParam = null;
        }
    }

    @Test
    @Transactional
    void createPaymentParam() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PaymentParam
        PaymentParamDTO paymentParamDTO = paymentParamMapper.toDto(paymentParam);
        var returnedPaymentParamDTO = om.readValue(
            restPaymentParamMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentParamDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PaymentParamDTO.class
        );

        // Validate the PaymentParam in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPaymentParam = paymentParamMapper.toEntity(returnedPaymentParamDTO);
        assertPaymentParamUpdatableFieldsEquals(returnedPaymentParam, getPersistedPaymentParam(returnedPaymentParam));

        insertedPaymentParam = returnedPaymentParam;
    }

    @Test
    @Transactional
    void createPaymentParamWithExistingId() throws Exception {
        // Create the PaymentParam with an existing ID
        insertedPaymentParam = paymentParamRepository.saveAndFlush(paymentParam);
        PaymentParamDTO paymentParamDTO = paymentParamMapper.toDto(paymentParam);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentParamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentParam in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paymentParam.setDataType(null);

        // Create the PaymentParam, which fails.
        PaymentParamDTO paymentParamDTO = paymentParamMapper.toDto(paymentParam);

        restPaymentParamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentParamDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentParams() throws Exception {
        // Initialize the database
        insertedPaymentParam = paymentParamRepository.saveAndFlush(paymentParam);

        // Get all the paymentParamList
        restPaymentParamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentParam.getId().toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())));
    }

    @Test
    @Transactional
    void getPaymentParam() throws Exception {
        // Initialize the database
        insertedPaymentParam = paymentParamRepository.saveAndFlush(paymentParam);

        // Get the paymentParam
        restPaymentParamMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentParam.getId().toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentParam() throws Exception {
        // Get the paymentParam
        restPaymentParamMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentParam() throws Exception {
        // Initialize the database
        insertedPaymentParam = paymentParamRepository.saveAndFlush(paymentParam);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentParam
        PaymentParam updatedPaymentParam = paymentParamRepository.findById(paymentParam.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaymentParam are not directly saved in db
        em.detach(updatedPaymentParam);
        updatedPaymentParam.type(UPDATED_TYPE).name(UPDATED_NAME).value(UPDATED_VALUE).dataType(UPDATED_DATA_TYPE);
        PaymentParamDTO paymentParamDTO = paymentParamMapper.toDto(updatedPaymentParam);

        restPaymentParamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentParamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentParamDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentParam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPaymentParamToMatchAllProperties(updatedPaymentParam);
    }

    @Test
    @Transactional
    void putNonExistingPaymentParam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentParam.setId(UUID.randomUUID());

        // Create the PaymentParam
        PaymentParamDTO paymentParamDTO = paymentParamMapper.toDto(paymentParam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentParamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentParamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentParamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentParam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentParam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentParam.setId(UUID.randomUUID());

        // Create the PaymentParam
        PaymentParamDTO paymentParamDTO = paymentParamMapper.toDto(paymentParam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentParamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentParamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentParam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentParam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentParam.setId(UUID.randomUUID());

        // Create the PaymentParam
        PaymentParamDTO paymentParamDTO = paymentParamMapper.toDto(paymentParam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentParamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentParamDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentParam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentParamWithPatch() throws Exception {
        // Initialize the database
        insertedPaymentParam = paymentParamRepository.saveAndFlush(paymentParam);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentParam using partial update
        PaymentParam partialUpdatedPaymentParam = new PaymentParam();
        partialUpdatedPaymentParam.setId(paymentParam.getId());

        restPaymentParamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentParam.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentParam))
            )
            .andExpect(status().isOk());

        // Validate the PaymentParam in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentParamUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPaymentParam, paymentParam),
            getPersistedPaymentParam(paymentParam)
        );
    }

    @Test
    @Transactional
    void fullUpdatePaymentParamWithPatch() throws Exception {
        // Initialize the database
        insertedPaymentParam = paymentParamRepository.saveAndFlush(paymentParam);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentParam using partial update
        PaymentParam partialUpdatedPaymentParam = new PaymentParam();
        partialUpdatedPaymentParam.setId(paymentParam.getId());

        partialUpdatedPaymentParam.type(UPDATED_TYPE).name(UPDATED_NAME).value(UPDATED_VALUE).dataType(UPDATED_DATA_TYPE);

        restPaymentParamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentParam.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentParam))
            )
            .andExpect(status().isOk());

        // Validate the PaymentParam in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentParamUpdatableFieldsEquals(partialUpdatedPaymentParam, getPersistedPaymentParam(partialUpdatedPaymentParam));
    }

    @Test
    @Transactional
    void patchNonExistingPaymentParam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentParam.setId(UUID.randomUUID());

        // Create the PaymentParam
        PaymentParamDTO paymentParamDTO = paymentParamMapper.toDto(paymentParam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentParamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentParamDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentParamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentParam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentParam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentParam.setId(UUID.randomUUID());

        // Create the PaymentParam
        PaymentParamDTO paymentParamDTO = paymentParamMapper.toDto(paymentParam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentParamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentParamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentParam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentParam() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentParam.setId(UUID.randomUUID());

        // Create the PaymentParam
        PaymentParamDTO paymentParamDTO = paymentParamMapper.toDto(paymentParam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentParamMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(paymentParamDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentParam in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentParam() throws Exception {
        // Initialize the database
        insertedPaymentParam = paymentParamRepository.saveAndFlush(paymentParam);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the paymentParam
        restPaymentParamMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentParam.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return paymentParamRepository.count();
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

    protected PaymentParam getPersistedPaymentParam(PaymentParam paymentParam) {
        return paymentParamRepository.findById(paymentParam.getId()).orElseThrow();
    }

    protected void assertPersistedPaymentParamToMatchAllProperties(PaymentParam expectedPaymentParam) {
        assertPaymentParamAllPropertiesEquals(expectedPaymentParam, getPersistedPaymentParam(expectedPaymentParam));
    }

    protected void assertPersistedPaymentParamToMatchUpdatableProperties(PaymentParam expectedPaymentParam) {
        assertPaymentParamAllUpdatablePropertiesEquals(expectedPaymentParam, getPersistedPaymentParam(expectedPaymentParam));
    }
}
