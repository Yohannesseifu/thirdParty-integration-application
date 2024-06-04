package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaDataAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FieldUIMetaDataRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FieldUIMetaDataDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.FieldUIMetaDataMapper;
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
 * Integration tests for the {@link FieldUIMetaDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldUIMetaDataResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_INPUT_INTERFACE = "AAAAAAAAAA";
    private static final String UPDATED_INPUT_INTERFACE = "BBBBBBBBBB";

    private static final String DEFAULT_WIDTH = "AAAAAAAAAA";
    private static final String UPDATED_WIDTH = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_PATTERN = "BBBBBBBBBB";

    private static final String DEFAULT_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_OPTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_OPTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITIONS = "AAAAAAAAAA";
    private static final String UPDATED_CONDITIONS = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSLATIONS = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/field-ui-meta-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FieldUIMetaDataRepository fieldUIMetaDataRepository;

    @Autowired
    private FieldUIMetaDataMapper fieldUIMetaDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldUIMetaDataMockMvc;

    private FieldUIMetaData fieldUIMetaData;

    private FieldUIMetaData insertedFieldUIMetaData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldUIMetaData createEntity(EntityManager em) {
        FieldUIMetaData fieldUIMetaData = new FieldUIMetaData()
            .label(DEFAULT_LABEL)
            .inputInterface(DEFAULT_INPUT_INTERFACE)
            .width(DEFAULT_WIDTH)
            .note(DEFAULT_NOTE)
            .validationPattern(DEFAULT_VALIDATION_PATTERN)
            .options(DEFAULT_OPTIONS)
            .displayOptions(DEFAULT_DISPLAY_OPTIONS)
            .conditions(DEFAULT_CONDITIONS)
            .translations(DEFAULT_TRANSLATIONS);
        return fieldUIMetaData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldUIMetaData createUpdatedEntity(EntityManager em) {
        FieldUIMetaData fieldUIMetaData = new FieldUIMetaData()
            .label(UPDATED_LABEL)
            .inputInterface(UPDATED_INPUT_INTERFACE)
            .width(UPDATED_WIDTH)
            .note(UPDATED_NOTE)
            .validationPattern(UPDATED_VALIDATION_PATTERN)
            .options(UPDATED_OPTIONS)
            .displayOptions(UPDATED_DISPLAY_OPTIONS)
            .conditions(UPDATED_CONDITIONS)
            .translations(UPDATED_TRANSLATIONS);
        return fieldUIMetaData;
    }

    @BeforeEach
    public void initTest() {
        fieldUIMetaData = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFieldUIMetaData != null) {
            fieldUIMetaDataRepository.delete(insertedFieldUIMetaData);
            insertedFieldUIMetaData = null;
        }
    }

    @Test
    @Transactional
    void createFieldUIMetaData() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FieldUIMetaData
        FieldUIMetaDataDTO fieldUIMetaDataDTO = fieldUIMetaDataMapper.toDto(fieldUIMetaData);
        var returnedFieldUIMetaDataDTO = om.readValue(
            restFieldUIMetaDataMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldUIMetaDataDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FieldUIMetaDataDTO.class
        );

        // Validate the FieldUIMetaData in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFieldUIMetaData = fieldUIMetaDataMapper.toEntity(returnedFieldUIMetaDataDTO);
        assertFieldUIMetaDataUpdatableFieldsEquals(returnedFieldUIMetaData, getPersistedFieldUIMetaData(returnedFieldUIMetaData));

        insertedFieldUIMetaData = returnedFieldUIMetaData;
    }

    @Test
    @Transactional
    void createFieldUIMetaDataWithExistingId() throws Exception {
        // Create the FieldUIMetaData with an existing ID
        fieldUIMetaData.setId(1L);
        FieldUIMetaDataDTO fieldUIMetaDataDTO = fieldUIMetaDataMapper.toDto(fieldUIMetaData);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldUIMetaDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldUIMetaDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FieldUIMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFieldUIMetaData() throws Exception {
        // Initialize the database
        insertedFieldUIMetaData = fieldUIMetaDataRepository.saveAndFlush(fieldUIMetaData);

        // Get all the fieldUIMetaDataList
        restFieldUIMetaDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldUIMetaData.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].inputInterface").value(hasItem(DEFAULT_INPUT_INTERFACE)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].validationPattern").value(hasItem(DEFAULT_VALIDATION_PATTERN)))
            .andExpect(jsonPath("$.[*].options").value(hasItem(DEFAULT_OPTIONS)))
            .andExpect(jsonPath("$.[*].displayOptions").value(hasItem(DEFAULT_DISPLAY_OPTIONS)))
            .andExpect(jsonPath("$.[*].conditions").value(hasItem(DEFAULT_CONDITIONS)))
            .andExpect(jsonPath("$.[*].translations").value(hasItem(DEFAULT_TRANSLATIONS)));
    }

    @Test
    @Transactional
    void getFieldUIMetaData() throws Exception {
        // Initialize the database
        insertedFieldUIMetaData = fieldUIMetaDataRepository.saveAndFlush(fieldUIMetaData);

        // Get the fieldUIMetaData
        restFieldUIMetaDataMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldUIMetaData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldUIMetaData.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.inputInterface").value(DEFAULT_INPUT_INTERFACE))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.validationPattern").value(DEFAULT_VALIDATION_PATTERN))
            .andExpect(jsonPath("$.options").value(DEFAULT_OPTIONS))
            .andExpect(jsonPath("$.displayOptions").value(DEFAULT_DISPLAY_OPTIONS))
            .andExpect(jsonPath("$.conditions").value(DEFAULT_CONDITIONS))
            .andExpect(jsonPath("$.translations").value(DEFAULT_TRANSLATIONS));
    }

    @Test
    @Transactional
    void getNonExistingFieldUIMetaData() throws Exception {
        // Get the fieldUIMetaData
        restFieldUIMetaDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldUIMetaData() throws Exception {
        // Initialize the database
        insertedFieldUIMetaData = fieldUIMetaDataRepository.saveAndFlush(fieldUIMetaData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldUIMetaData
        FieldUIMetaData updatedFieldUIMetaData = fieldUIMetaDataRepository.findById(fieldUIMetaData.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFieldUIMetaData are not directly saved in db
        em.detach(updatedFieldUIMetaData);
        updatedFieldUIMetaData
            .label(UPDATED_LABEL)
            .inputInterface(UPDATED_INPUT_INTERFACE)
            .width(UPDATED_WIDTH)
            .note(UPDATED_NOTE)
            .validationPattern(UPDATED_VALIDATION_PATTERN)
            .options(UPDATED_OPTIONS)
            .displayOptions(UPDATED_DISPLAY_OPTIONS)
            .conditions(UPDATED_CONDITIONS)
            .translations(UPDATED_TRANSLATIONS);
        FieldUIMetaDataDTO fieldUIMetaDataDTO = fieldUIMetaDataMapper.toDto(updatedFieldUIMetaData);

        restFieldUIMetaDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldUIMetaDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldUIMetaDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the FieldUIMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFieldUIMetaDataToMatchAllProperties(updatedFieldUIMetaData);
    }

    @Test
    @Transactional
    void putNonExistingFieldUIMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldUIMetaData.setId(longCount.incrementAndGet());

        // Create the FieldUIMetaData
        FieldUIMetaDataDTO fieldUIMetaDataDTO = fieldUIMetaDataMapper.toDto(fieldUIMetaData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldUIMetaDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldUIMetaDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldUIMetaDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldUIMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldUIMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldUIMetaData.setId(longCount.incrementAndGet());

        // Create the FieldUIMetaData
        FieldUIMetaDataDTO fieldUIMetaDataDTO = fieldUIMetaDataMapper.toDto(fieldUIMetaData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldUIMetaDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldUIMetaDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldUIMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldUIMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldUIMetaData.setId(longCount.incrementAndGet());

        // Create the FieldUIMetaData
        FieldUIMetaDataDTO fieldUIMetaDataDTO = fieldUIMetaDataMapper.toDto(fieldUIMetaData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldUIMetaDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldUIMetaDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldUIMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldUIMetaDataWithPatch() throws Exception {
        // Initialize the database
        insertedFieldUIMetaData = fieldUIMetaDataRepository.saveAndFlush(fieldUIMetaData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldUIMetaData using partial update
        FieldUIMetaData partialUpdatedFieldUIMetaData = new FieldUIMetaData();
        partialUpdatedFieldUIMetaData.setId(fieldUIMetaData.getId());

        partialUpdatedFieldUIMetaData
            .label(UPDATED_LABEL)
            .validationPattern(UPDATED_VALIDATION_PATTERN)
            .options(UPDATED_OPTIONS)
            .displayOptions(UPDATED_DISPLAY_OPTIONS);

        restFieldUIMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldUIMetaData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFieldUIMetaData))
            )
            .andExpect(status().isOk());

        // Validate the FieldUIMetaData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFieldUIMetaDataUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFieldUIMetaData, fieldUIMetaData),
            getPersistedFieldUIMetaData(fieldUIMetaData)
        );
    }

    @Test
    @Transactional
    void fullUpdateFieldUIMetaDataWithPatch() throws Exception {
        // Initialize the database
        insertedFieldUIMetaData = fieldUIMetaDataRepository.saveAndFlush(fieldUIMetaData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldUIMetaData using partial update
        FieldUIMetaData partialUpdatedFieldUIMetaData = new FieldUIMetaData();
        partialUpdatedFieldUIMetaData.setId(fieldUIMetaData.getId());

        partialUpdatedFieldUIMetaData
            .label(UPDATED_LABEL)
            .inputInterface(UPDATED_INPUT_INTERFACE)
            .width(UPDATED_WIDTH)
            .note(UPDATED_NOTE)
            .validationPattern(UPDATED_VALIDATION_PATTERN)
            .options(UPDATED_OPTIONS)
            .displayOptions(UPDATED_DISPLAY_OPTIONS)
            .conditions(UPDATED_CONDITIONS)
            .translations(UPDATED_TRANSLATIONS);

        restFieldUIMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldUIMetaData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFieldUIMetaData))
            )
            .andExpect(status().isOk());

        // Validate the FieldUIMetaData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFieldUIMetaDataUpdatableFieldsEquals(
            partialUpdatedFieldUIMetaData,
            getPersistedFieldUIMetaData(partialUpdatedFieldUIMetaData)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFieldUIMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldUIMetaData.setId(longCount.incrementAndGet());

        // Create the FieldUIMetaData
        FieldUIMetaDataDTO fieldUIMetaDataDTO = fieldUIMetaDataMapper.toDto(fieldUIMetaData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldUIMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldUIMetaDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldUIMetaDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldUIMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldUIMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldUIMetaData.setId(longCount.incrementAndGet());

        // Create the FieldUIMetaData
        FieldUIMetaDataDTO fieldUIMetaDataDTO = fieldUIMetaDataMapper.toDto(fieldUIMetaData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldUIMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldUIMetaDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldUIMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldUIMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldUIMetaData.setId(longCount.incrementAndGet());

        // Create the FieldUIMetaData
        FieldUIMetaDataDTO fieldUIMetaDataDTO = fieldUIMetaDataMapper.toDto(fieldUIMetaData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldUIMetaDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fieldUIMetaDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldUIMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldUIMetaData() throws Exception {
        // Initialize the database
        insertedFieldUIMetaData = fieldUIMetaDataRepository.saveAndFlush(fieldUIMetaData);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fieldUIMetaData
        restFieldUIMetaDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldUIMetaData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fieldUIMetaDataRepository.count();
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

    protected FieldUIMetaData getPersistedFieldUIMetaData(FieldUIMetaData fieldUIMetaData) {
        return fieldUIMetaDataRepository.findById(fieldUIMetaData.getId()).orElseThrow();
    }

    protected void assertPersistedFieldUIMetaDataToMatchAllProperties(FieldUIMetaData expectedFieldUIMetaData) {
        assertFieldUIMetaDataAllPropertiesEquals(expectedFieldUIMetaData, getPersistedFieldUIMetaData(expectedFieldUIMetaData));
    }

    protected void assertPersistedFieldUIMetaDataToMatchUpdatableProperties(FieldUIMetaData expectedFieldUIMetaData) {
        assertFieldUIMetaDataAllUpdatablePropertiesEquals(expectedFieldUIMetaData, getPersistedFieldUIMetaData(expectedFieldUIMetaData));
    }
}
