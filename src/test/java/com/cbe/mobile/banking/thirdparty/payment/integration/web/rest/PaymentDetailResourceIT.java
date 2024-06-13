package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetailAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.PaymentDetailRepository;
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
 * Integration tests for the {@link PaymentDetailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentDetailResourceIT {

    private static final String DEFAULT_COMPUTED_PAYMENT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_COMPUTED_PAYMENT_DETAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentDetailMockMvc;

    private PaymentDetail paymentDetail;

    private PaymentDetail insertedPaymentDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentDetail createEntity(EntityManager em) {
        PaymentDetail paymentDetail = new PaymentDetail().computedPaymentDetail(DEFAULT_COMPUTED_PAYMENT_DETAIL);
        return paymentDetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentDetail createUpdatedEntity(EntityManager em) {
        PaymentDetail paymentDetail = new PaymentDetail().computedPaymentDetail(UPDATED_COMPUTED_PAYMENT_DETAIL);
        return paymentDetail;
    }

    @BeforeEach
    public void initTest() {
        paymentDetail = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPaymentDetail != null) {
            paymentDetailRepository.delete(insertedPaymentDetail);
            insertedPaymentDetail = null;
        }
    }

    @Test
    @Transactional
    void createPaymentDetail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PaymentDetail
        var returnedPaymentDetail = om.readValue(
            restPaymentDetailMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentDetail)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PaymentDetail.class
        );

        // Validate the PaymentDetail in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPaymentDetailUpdatableFieldsEquals(returnedPaymentDetail, getPersistedPaymentDetail(returnedPaymentDetail));

        insertedPaymentDetail = returnedPaymentDetail;
    }

    @Test
    @Transactional
    void createPaymentDetailWithExistingId() throws Exception {
        // Create the PaymentDetail with an existing ID
        insertedPaymentDetail = paymentDetailRepository.saveAndFlush(paymentDetail);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentDetail)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentDetails() throws Exception {
        // Initialize the database
        insertedPaymentDetail = paymentDetailRepository.saveAndFlush(paymentDetail);

        // Get all the paymentDetailList
        restPaymentDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentDetail.getId().toString())))
            .andExpect(jsonPath("$.[*].computedPaymentDetail").value(hasItem(DEFAULT_COMPUTED_PAYMENT_DETAIL)));
    }

    @Test
    @Transactional
    void getPaymentDetail() throws Exception {
        // Initialize the database
        insertedPaymentDetail = paymentDetailRepository.saveAndFlush(paymentDetail);

        // Get the paymentDetail
        restPaymentDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentDetail.getId().toString()))
            .andExpect(jsonPath("$.computedPaymentDetail").value(DEFAULT_COMPUTED_PAYMENT_DETAIL));
    }

    @Test
    @Transactional
    void getNonExistingPaymentDetail() throws Exception {
        // Get the paymentDetail
        restPaymentDetailMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentDetail() throws Exception {
        // Initialize the database
        insertedPaymentDetail = paymentDetailRepository.saveAndFlush(paymentDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentDetail
        PaymentDetail updatedPaymentDetail = paymentDetailRepository.findById(paymentDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaymentDetail are not directly saved in db
        em.detach(updatedPaymentDetail);
        updatedPaymentDetail.computedPaymentDetail(UPDATED_COMPUTED_PAYMENT_DETAIL);

        restPaymentDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentDetail.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPaymentDetail))
            )
            .andExpect(status().isOk());

        // Validate the PaymentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPaymentDetailToMatchAllProperties(updatedPaymentDetail);
    }

    @Test
    @Transactional
    void putNonExistingPaymentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentDetail.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentDetail.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentDetail))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentDetail.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentDetail))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentDetail.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentDetailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentDetail)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentDetailWithPatch() throws Exception {
        // Initialize the database
        insertedPaymentDetail = paymentDetailRepository.saveAndFlush(paymentDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentDetail using partial update
        PaymentDetail partialUpdatedPaymentDetail = new PaymentDetail();
        partialUpdatedPaymentDetail.setId(paymentDetail.getId());

        restPaymentDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentDetail))
            )
            .andExpect(status().isOk());

        // Validate the PaymentDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentDetailUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPaymentDetail, paymentDetail),
            getPersistedPaymentDetail(paymentDetail)
        );
    }

    @Test
    @Transactional
    void fullUpdatePaymentDetailWithPatch() throws Exception {
        // Initialize the database
        insertedPaymentDetail = paymentDetailRepository.saveAndFlush(paymentDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentDetail using partial update
        PaymentDetail partialUpdatedPaymentDetail = new PaymentDetail();
        partialUpdatedPaymentDetail.setId(paymentDetail.getId());

        partialUpdatedPaymentDetail.computedPaymentDetail(UPDATED_COMPUTED_PAYMENT_DETAIL);

        restPaymentDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentDetail))
            )
            .andExpect(status().isOk());

        // Validate the PaymentDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentDetailUpdatableFieldsEquals(partialUpdatedPaymentDetail, getPersistedPaymentDetail(partialUpdatedPaymentDetail));
    }

    @Test
    @Transactional
    void patchNonExistingPaymentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentDetail.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentDetail))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentDetail.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentDetail))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentDetail.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentDetailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(paymentDetail)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentDetail() throws Exception {
        // Initialize the database
        insertedPaymentDetail = paymentDetailRepository.saveAndFlush(paymentDetail);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the paymentDetail
        restPaymentDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentDetail.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return paymentDetailRepository.count();
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

    protected PaymentDetail getPersistedPaymentDetail(PaymentDetail paymentDetail) {
        return paymentDetailRepository.findById(paymentDetail.getId()).orElseThrow();
    }

    protected void assertPersistedPaymentDetailToMatchAllProperties(PaymentDetail expectedPaymentDetail) {
        assertPaymentDetailAllPropertiesEquals(expectedPaymentDetail, getPersistedPaymentDetail(expectedPaymentDetail));
    }

    protected void assertPersistedPaymentDetailToMatchUpdatableProperties(PaymentDetail expectedPaymentDetail) {
        assertPaymentDetailAllUpdatablePropertiesEquals(expectedPaymentDetail, getPersistedPaymentDetail(expectedPaymentDetail));
    }
}
