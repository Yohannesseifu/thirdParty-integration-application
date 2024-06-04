package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperationAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.OperationType;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.IntegrationOperationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.IntegrationOperationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.IntegrationOperationMapper;
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
 * Integration tests for the {@link IntegrationOperationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntegrationOperationResourceIT {

    private static final OperationType DEFAULT_OPERATION_TYPE = OperationType.FETCH;
    private static final OperationType UPDATED_OPERATION_TYPE = OperationType.NOTIFY;

    private static final String ENTITY_API_URL = "/api/integration-operations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IntegrationOperationRepository integrationOperationRepository;

    @Autowired
    private IntegrationOperationMapper integrationOperationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntegrationOperationMockMvc;

    private IntegrationOperation integrationOperation;

    private IntegrationOperation insertedIntegrationOperation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntegrationOperation createEntity(EntityManager em) {
        IntegrationOperation integrationOperation = new IntegrationOperation().operationType(DEFAULT_OPERATION_TYPE);
        return integrationOperation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntegrationOperation createUpdatedEntity(EntityManager em) {
        IntegrationOperation integrationOperation = new IntegrationOperation().operationType(UPDATED_OPERATION_TYPE);
        return integrationOperation;
    }

    @BeforeEach
    public void initTest() {
        integrationOperation = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedIntegrationOperation != null) {
            integrationOperationRepository.delete(insertedIntegrationOperation);
            insertedIntegrationOperation = null;
        }
    }

    @Test
    @Transactional
    void createIntegrationOperation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the IntegrationOperation
        IntegrationOperationDTO integrationOperationDTO = integrationOperationMapper.toDto(integrationOperation);
        var returnedIntegrationOperationDTO = om.readValue(
            restIntegrationOperationMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(integrationOperationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IntegrationOperationDTO.class
        );

        // Validate the IntegrationOperation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedIntegrationOperation = integrationOperationMapper.toEntity(returnedIntegrationOperationDTO);
        assertIntegrationOperationUpdatableFieldsEquals(
            returnedIntegrationOperation,
            getPersistedIntegrationOperation(returnedIntegrationOperation)
        );

        insertedIntegrationOperation = returnedIntegrationOperation;
    }

    @Test
    @Transactional
    void createIntegrationOperationWithExistingId() throws Exception {
        // Create the IntegrationOperation with an existing ID
        integrationOperation.setId(1L);
        IntegrationOperationDTO integrationOperationDTO = integrationOperationMapper.toDto(integrationOperation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntegrationOperationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(integrationOperationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntegrationOperation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIntegrationOperations() throws Exception {
        // Initialize the database
        insertedIntegrationOperation = integrationOperationRepository.saveAndFlush(integrationOperation);

        // Get all the integrationOperationList
        restIntegrationOperationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrationOperation.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationType").value(hasItem(DEFAULT_OPERATION_TYPE.toString())));
    }

    @Test
    @Transactional
    void getIntegrationOperation() throws Exception {
        // Initialize the database
        insertedIntegrationOperation = integrationOperationRepository.saveAndFlush(integrationOperation);

        // Get the integrationOperation
        restIntegrationOperationMockMvc
            .perform(get(ENTITY_API_URL_ID, integrationOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(integrationOperation.getId().intValue()))
            .andExpect(jsonPath("$.operationType").value(DEFAULT_OPERATION_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIntegrationOperation() throws Exception {
        // Get the integrationOperation
        restIntegrationOperationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntegrationOperation() throws Exception {
        // Initialize the database
        insertedIntegrationOperation = integrationOperationRepository.saveAndFlush(integrationOperation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the integrationOperation
        IntegrationOperation updatedIntegrationOperation = integrationOperationRepository
            .findById(integrationOperation.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedIntegrationOperation are not directly saved in db
        em.detach(updatedIntegrationOperation);
        updatedIntegrationOperation.operationType(UPDATED_OPERATION_TYPE);
        IntegrationOperationDTO integrationOperationDTO = integrationOperationMapper.toDto(updatedIntegrationOperation);

        restIntegrationOperationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, integrationOperationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(integrationOperationDTO))
            )
            .andExpect(status().isOk());

        // Validate the IntegrationOperation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIntegrationOperationToMatchAllProperties(updatedIntegrationOperation);
    }

    @Test
    @Transactional
    void putNonExistingIntegrationOperation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationOperation.setId(longCount.incrementAndGet());

        // Create the IntegrationOperation
        IntegrationOperationDTO integrationOperationDTO = integrationOperationMapper.toDto(integrationOperation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegrationOperationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, integrationOperationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(integrationOperationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationOperation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntegrationOperation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationOperation.setId(longCount.incrementAndGet());

        // Create the IntegrationOperation
        IntegrationOperationDTO integrationOperationDTO = integrationOperationMapper.toDto(integrationOperation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationOperationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(integrationOperationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationOperation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntegrationOperation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationOperation.setId(longCount.incrementAndGet());

        // Create the IntegrationOperation
        IntegrationOperationDTO integrationOperationDTO = integrationOperationMapper.toDto(integrationOperation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationOperationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(integrationOperationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntegrationOperation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntegrationOperationWithPatch() throws Exception {
        // Initialize the database
        insertedIntegrationOperation = integrationOperationRepository.saveAndFlush(integrationOperation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the integrationOperation using partial update
        IntegrationOperation partialUpdatedIntegrationOperation = new IntegrationOperation();
        partialUpdatedIntegrationOperation.setId(integrationOperation.getId());

        partialUpdatedIntegrationOperation.operationType(UPDATED_OPERATION_TYPE);

        restIntegrationOperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntegrationOperation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntegrationOperation))
            )
            .andExpect(status().isOk());

        // Validate the IntegrationOperation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntegrationOperationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIntegrationOperation, integrationOperation),
            getPersistedIntegrationOperation(integrationOperation)
        );
    }

    @Test
    @Transactional
    void fullUpdateIntegrationOperationWithPatch() throws Exception {
        // Initialize the database
        insertedIntegrationOperation = integrationOperationRepository.saveAndFlush(integrationOperation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the integrationOperation using partial update
        IntegrationOperation partialUpdatedIntegrationOperation = new IntegrationOperation();
        partialUpdatedIntegrationOperation.setId(integrationOperation.getId());

        partialUpdatedIntegrationOperation.operationType(UPDATED_OPERATION_TYPE);

        restIntegrationOperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntegrationOperation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntegrationOperation))
            )
            .andExpect(status().isOk());

        // Validate the IntegrationOperation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntegrationOperationUpdatableFieldsEquals(
            partialUpdatedIntegrationOperation,
            getPersistedIntegrationOperation(partialUpdatedIntegrationOperation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingIntegrationOperation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationOperation.setId(longCount.incrementAndGet());

        // Create the IntegrationOperation
        IntegrationOperationDTO integrationOperationDTO = integrationOperationMapper.toDto(integrationOperation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegrationOperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, integrationOperationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(integrationOperationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationOperation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntegrationOperation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationOperation.setId(longCount.incrementAndGet());

        // Create the IntegrationOperation
        IntegrationOperationDTO integrationOperationDTO = integrationOperationMapper.toDto(integrationOperation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationOperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(integrationOperationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationOperation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntegrationOperation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationOperation.setId(longCount.incrementAndGet());

        // Create the IntegrationOperation
        IntegrationOperationDTO integrationOperationDTO = integrationOperationMapper.toDto(integrationOperation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationOperationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(integrationOperationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntegrationOperation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntegrationOperation() throws Exception {
        // Initialize the database
        insertedIntegrationOperation = integrationOperationRepository.saveAndFlush(integrationOperation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the integrationOperation
        restIntegrationOperationMockMvc
            .perform(delete(ENTITY_API_URL_ID, integrationOperation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return integrationOperationRepository.count();
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

    protected IntegrationOperation getPersistedIntegrationOperation(IntegrationOperation integrationOperation) {
        return integrationOperationRepository.findById(integrationOperation.getId()).orElseThrow();
    }

    protected void assertPersistedIntegrationOperationToMatchAllProperties(IntegrationOperation expectedIntegrationOperation) {
        assertIntegrationOperationAllPropertiesEquals(
            expectedIntegrationOperation,
            getPersistedIntegrationOperation(expectedIntegrationOperation)
        );
    }

    protected void assertPersistedIntegrationOperationToMatchUpdatableProperties(IntegrationOperation expectedIntegrationOperation) {
        assertIntegrationOperationAllUpdatablePropertiesEquals(
            expectedIntegrationOperation,
            getPersistedIntegrationOperation(expectedIntegrationOperation)
        );
    }
}
