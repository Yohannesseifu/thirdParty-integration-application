package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMappingAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.IntegrationFieldMappingRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.IntegrationFieldMappingDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.IntegrationFieldMappingMapper;
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
 * Integration tests for the {@link IntegrationFieldMappingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntegrationFieldMappingResourceIT {

    private static final String ENTITY_API_URL = "/api/integration-field-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IntegrationFieldMappingRepository integrationFieldMappingRepository;

    @Autowired
    private IntegrationFieldMappingMapper integrationFieldMappingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntegrationFieldMappingMockMvc;

    private IntegrationFieldMapping integrationFieldMapping;

    private IntegrationFieldMapping insertedIntegrationFieldMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntegrationFieldMapping createEntity(EntityManager em) {
        IntegrationFieldMapping integrationFieldMapping = new IntegrationFieldMapping();
        return integrationFieldMapping;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntegrationFieldMapping createUpdatedEntity(EntityManager em) {
        IntegrationFieldMapping integrationFieldMapping = new IntegrationFieldMapping();
        return integrationFieldMapping;
    }

    @BeforeEach
    public void initTest() {
        integrationFieldMapping = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedIntegrationFieldMapping != null) {
            integrationFieldMappingRepository.delete(insertedIntegrationFieldMapping);
            insertedIntegrationFieldMapping = null;
        }
    }

    @Test
    @Transactional
    void createIntegrationFieldMapping() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the IntegrationFieldMapping
        IntegrationFieldMappingDTO integrationFieldMappingDTO = integrationFieldMappingMapper.toDto(integrationFieldMapping);
        var returnedIntegrationFieldMappingDTO = om.readValue(
            restIntegrationFieldMappingMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(integrationFieldMappingDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IntegrationFieldMappingDTO.class
        );

        // Validate the IntegrationFieldMapping in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedIntegrationFieldMapping = integrationFieldMappingMapper.toEntity(returnedIntegrationFieldMappingDTO);
        assertIntegrationFieldMappingUpdatableFieldsEquals(
            returnedIntegrationFieldMapping,
            getPersistedIntegrationFieldMapping(returnedIntegrationFieldMapping)
        );

        insertedIntegrationFieldMapping = returnedIntegrationFieldMapping;
    }

    @Test
    @Transactional
    void createIntegrationFieldMappingWithExistingId() throws Exception {
        // Create the IntegrationFieldMapping with an existing ID
        insertedIntegrationFieldMapping = integrationFieldMappingRepository.saveAndFlush(integrationFieldMapping);
        IntegrationFieldMappingDTO integrationFieldMappingDTO = integrationFieldMappingMapper.toDto(integrationFieldMapping);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntegrationFieldMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(integrationFieldMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntegrationFieldMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIntegrationFieldMappings() throws Exception {
        // Initialize the database
        insertedIntegrationFieldMapping = integrationFieldMappingRepository.saveAndFlush(integrationFieldMapping);

        // Get all the integrationFieldMappingList
        restIntegrationFieldMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrationFieldMapping.getId().toString())));
    }

    @Test
    @Transactional
    void getIntegrationFieldMapping() throws Exception {
        // Initialize the database
        insertedIntegrationFieldMapping = integrationFieldMappingRepository.saveAndFlush(integrationFieldMapping);

        // Get the integrationFieldMapping
        restIntegrationFieldMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, integrationFieldMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(integrationFieldMapping.getId().toString()));
    }

    @Test
    @Transactional
    void getNonExistingIntegrationFieldMapping() throws Exception {
        // Get the integrationFieldMapping
        restIntegrationFieldMappingMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntegrationFieldMapping() throws Exception {
        // Initialize the database
        insertedIntegrationFieldMapping = integrationFieldMappingRepository.saveAndFlush(integrationFieldMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the integrationFieldMapping
        IntegrationFieldMapping updatedIntegrationFieldMapping = integrationFieldMappingRepository
            .findById(integrationFieldMapping.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedIntegrationFieldMapping are not directly saved in db
        em.detach(updatedIntegrationFieldMapping);
        IntegrationFieldMappingDTO integrationFieldMappingDTO = integrationFieldMappingMapper.toDto(updatedIntegrationFieldMapping);

        restIntegrationFieldMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, integrationFieldMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(integrationFieldMappingDTO))
            )
            .andExpect(status().isOk());

        // Validate the IntegrationFieldMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIntegrationFieldMappingToMatchAllProperties(updatedIntegrationFieldMapping);
    }

    @Test
    @Transactional
    void putNonExistingIntegrationFieldMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationFieldMapping.setId(UUID.randomUUID());

        // Create the IntegrationFieldMapping
        IntegrationFieldMappingDTO integrationFieldMappingDTO = integrationFieldMappingMapper.toDto(integrationFieldMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegrationFieldMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, integrationFieldMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(integrationFieldMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationFieldMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntegrationFieldMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationFieldMapping.setId(UUID.randomUUID());

        // Create the IntegrationFieldMapping
        IntegrationFieldMappingDTO integrationFieldMappingDTO = integrationFieldMappingMapper.toDto(integrationFieldMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationFieldMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(integrationFieldMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationFieldMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntegrationFieldMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationFieldMapping.setId(UUID.randomUUID());

        // Create the IntegrationFieldMapping
        IntegrationFieldMappingDTO integrationFieldMappingDTO = integrationFieldMappingMapper.toDto(integrationFieldMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationFieldMappingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(integrationFieldMappingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntegrationFieldMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntegrationFieldMappingWithPatch() throws Exception {
        // Initialize the database
        insertedIntegrationFieldMapping = integrationFieldMappingRepository.saveAndFlush(integrationFieldMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the integrationFieldMapping using partial update
        IntegrationFieldMapping partialUpdatedIntegrationFieldMapping = new IntegrationFieldMapping();
        partialUpdatedIntegrationFieldMapping.setId(integrationFieldMapping.getId());

        restIntegrationFieldMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntegrationFieldMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntegrationFieldMapping))
            )
            .andExpect(status().isOk());

        // Validate the IntegrationFieldMapping in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntegrationFieldMappingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIntegrationFieldMapping, integrationFieldMapping),
            getPersistedIntegrationFieldMapping(integrationFieldMapping)
        );
    }

    @Test
    @Transactional
    void fullUpdateIntegrationFieldMappingWithPatch() throws Exception {
        // Initialize the database
        insertedIntegrationFieldMapping = integrationFieldMappingRepository.saveAndFlush(integrationFieldMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the integrationFieldMapping using partial update
        IntegrationFieldMapping partialUpdatedIntegrationFieldMapping = new IntegrationFieldMapping();
        partialUpdatedIntegrationFieldMapping.setId(integrationFieldMapping.getId());

        restIntegrationFieldMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntegrationFieldMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntegrationFieldMapping))
            )
            .andExpect(status().isOk());

        // Validate the IntegrationFieldMapping in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntegrationFieldMappingUpdatableFieldsEquals(
            partialUpdatedIntegrationFieldMapping,
            getPersistedIntegrationFieldMapping(partialUpdatedIntegrationFieldMapping)
        );
    }

    @Test
    @Transactional
    void patchNonExistingIntegrationFieldMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationFieldMapping.setId(UUID.randomUUID());

        // Create the IntegrationFieldMapping
        IntegrationFieldMappingDTO integrationFieldMappingDTO = integrationFieldMappingMapper.toDto(integrationFieldMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegrationFieldMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, integrationFieldMappingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(integrationFieldMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationFieldMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntegrationFieldMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationFieldMapping.setId(UUID.randomUUID());

        // Create the IntegrationFieldMapping
        IntegrationFieldMappingDTO integrationFieldMappingDTO = integrationFieldMappingMapper.toDto(integrationFieldMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationFieldMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(integrationFieldMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationFieldMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntegrationFieldMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrationFieldMapping.setId(UUID.randomUUID());

        // Create the IntegrationFieldMapping
        IntegrationFieldMappingDTO integrationFieldMappingDTO = integrationFieldMappingMapper.toDto(integrationFieldMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationFieldMappingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(integrationFieldMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntegrationFieldMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntegrationFieldMapping() throws Exception {
        // Initialize the database
        insertedIntegrationFieldMapping = integrationFieldMappingRepository.saveAndFlush(integrationFieldMapping);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the integrationFieldMapping
        restIntegrationFieldMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, integrationFieldMapping.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return integrationFieldMappingRepository.count();
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

    protected IntegrationFieldMapping getPersistedIntegrationFieldMapping(IntegrationFieldMapping integrationFieldMapping) {
        return integrationFieldMappingRepository.findById(integrationFieldMapping.getId()).orElseThrow();
    }

    protected void assertPersistedIntegrationFieldMappingToMatchAllProperties(IntegrationFieldMapping expectedIntegrationFieldMapping) {
        assertIntegrationFieldMappingAllPropertiesEquals(
            expectedIntegrationFieldMapping,
            getPersistedIntegrationFieldMapping(expectedIntegrationFieldMapping)
        );
    }

    protected void assertPersistedIntegrationFieldMappingToMatchUpdatableProperties(
        IntegrationFieldMapping expectedIntegrationFieldMapping
    ) {
        assertIntegrationFieldMappingAllUpdatablePropertiesEquals(
            expectedIntegrationFieldMapping,
            getPersistedIntegrationFieldMapping(expectedIntegrationFieldMapping)
        );
    }
}
