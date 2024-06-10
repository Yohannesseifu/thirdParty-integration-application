package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Field;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FieldRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FieldDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.FieldMapper;
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
 * Integration tests for the {@link FieldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final DataType DEFAULT_DATA_TYPE = DataType.COLLECTION;
    private static final DataType UPDATED_DATA_TYPE = DataType.STRING;

    private static final Boolean DEFAULT_IS_UNIQUE = false;
    private static final Boolean UPDATED_IS_UNIQUE = true;

    private static final Integer DEFAULT_MAX_LENGTH = 1;
    private static final Integer UPDATED_MAX_LENGTH = 2;

    private static final Integer DEFAULT_MIN_LENGTH = 1;
    private static final Integer UPDATED_MIN_LENGTH = 2;

    private static final String DEFAULT_MIN_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_MIN_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_MAX_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_MAX_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_REQUIRED = false;
    private static final Boolean UPDATED_IS_REQUIRED = true;

    private static final Long DEFAULT_SORT_ORDER = 1L;
    private static final Long UPDATED_SORT_ORDER = 2L;

    private static final String ENTITY_API_URL = "/api/fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldMockMvc;

    private Field field;

    private Field insertedField;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Field createEntity(EntityManager em) {
        Field field = new Field()
            .name(DEFAULT_NAME)
            .dataType(DEFAULT_DATA_TYPE)
            .isUnique(DEFAULT_IS_UNIQUE)
            .maxLength(DEFAULT_MAX_LENGTH)
            .minLength(DEFAULT_MIN_LENGTH)
            .minValue(DEFAULT_MIN_VALUE)
            .maxValue(DEFAULT_MAX_VALUE)
            .isRequired(DEFAULT_IS_REQUIRED)
            .sortOrder(DEFAULT_SORT_ORDER);
        return field;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Field createUpdatedEntity(EntityManager em) {
        Field field = new Field()
            .name(UPDATED_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .isUnique(UPDATED_IS_UNIQUE)
            .maxLength(UPDATED_MAX_LENGTH)
            .minLength(UPDATED_MIN_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .isRequired(UPDATED_IS_REQUIRED)
            .sortOrder(UPDATED_SORT_ORDER);
        return field;
    }

    @BeforeEach
    public void initTest() {
        field = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedField != null) {
            fieldRepository.delete(insertedField);
            insertedField = null;
        }
    }

    @Test
    @Transactional
    void createField() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Field
        FieldDTO fieldDTO = fieldMapper.toDto(field);
        var returnedFieldDTO = om.readValue(
            restFieldMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FieldDTO.class
        );

        // Validate the Field in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedField = fieldMapper.toEntity(returnedFieldDTO);
        assertFieldUpdatableFieldsEquals(returnedField, getPersistedField(returnedField));

        insertedField = returnedField;
    }

    @Test
    @Transactional
    void createFieldWithExistingId() throws Exception {
        // Create the Field with an existing ID
        insertedField = fieldRepository.saveAndFlush(field);
        FieldDTO fieldDTO = fieldMapper.toDto(field);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Field in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFields() throws Exception {
        // Initialize the database
        insertedField = fieldRepository.saveAndFlush(field);

        // Get all the fieldList
        restFieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(field.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isUnique").value(hasItem(DEFAULT_IS_UNIQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE)))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE)))
            .andExpect(jsonPath("$.[*].isRequired").value(hasItem(DEFAULT_IS_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER.intValue())));
    }

    @Test
    @Transactional
    void getField() throws Exception {
        // Initialize the database
        insertedField = fieldRepository.saveAndFlush(field);

        // Get the field
        restFieldMockMvc
            .perform(get(ENTITY_API_URL_ID, field.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(field.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.isUnique").value(DEFAULT_IS_UNIQUE.booleanValue()))
            .andExpect(jsonPath("$.maxLength").value(DEFAULT_MAX_LENGTH))
            .andExpect(jsonPath("$.minLength").value(DEFAULT_MIN_LENGTH))
            .andExpect(jsonPath("$.minValue").value(DEFAULT_MIN_VALUE))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE))
            .andExpect(jsonPath("$.isRequired").value(DEFAULT_IS_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingField() throws Exception {
        // Get the field
        restFieldMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingField() throws Exception {
        // Initialize the database
        insertedField = fieldRepository.saveAndFlush(field);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the field
        Field updatedField = fieldRepository.findById(field.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedField are not directly saved in db
        em.detach(updatedField);
        updatedField
            .name(UPDATED_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .isUnique(UPDATED_IS_UNIQUE)
            .maxLength(UPDATED_MAX_LENGTH)
            .minLength(UPDATED_MIN_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .isRequired(UPDATED_IS_REQUIRED)
            .sortOrder(UPDATED_SORT_ORDER);
        FieldDTO fieldDTO = fieldMapper.toDto(updatedField);

        restFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldDTO))
            )
            .andExpect(status().isOk());

        // Validate the Field in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFieldToMatchAllProperties(updatedField);
    }

    @Test
    @Transactional
    void putNonExistingField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        field.setId(UUID.randomUUID());

        // Create the Field
        FieldDTO fieldDTO = fieldMapper.toDto(field);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Field in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        field.setId(UUID.randomUUID());

        // Create the Field
        FieldDTO fieldDTO = fieldMapper.toDto(field);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Field in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        field.setId(UUID.randomUUID());

        // Create the Field
        FieldDTO fieldDTO = fieldMapper.toDto(field);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Field in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldWithPatch() throws Exception {
        // Initialize the database
        insertedField = fieldRepository.saveAndFlush(field);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the field using partial update
        Field partialUpdatedField = new Field();
        partialUpdatedField.setId(field.getId());

        partialUpdatedField
            .maxLength(UPDATED_MAX_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .isRequired(UPDATED_IS_REQUIRED)
            .sortOrder(UPDATED_SORT_ORDER);

        restFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedField.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedField))
            )
            .andExpect(status().isOk());

        // Validate the Field in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFieldUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedField, field), getPersistedField(field));
    }

    @Test
    @Transactional
    void fullUpdateFieldWithPatch() throws Exception {
        // Initialize the database
        insertedField = fieldRepository.saveAndFlush(field);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the field using partial update
        Field partialUpdatedField = new Field();
        partialUpdatedField.setId(field.getId());

        partialUpdatedField
            .name(UPDATED_NAME)
            .dataType(UPDATED_DATA_TYPE)
            .isUnique(UPDATED_IS_UNIQUE)
            .maxLength(UPDATED_MAX_LENGTH)
            .minLength(UPDATED_MIN_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .isRequired(UPDATED_IS_REQUIRED)
            .sortOrder(UPDATED_SORT_ORDER);

        restFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedField.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedField))
            )
            .andExpect(status().isOk());

        // Validate the Field in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFieldUpdatableFieldsEquals(partialUpdatedField, getPersistedField(partialUpdatedField));
    }

    @Test
    @Transactional
    void patchNonExistingField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        field.setId(UUID.randomUUID());

        // Create the Field
        FieldDTO fieldDTO = fieldMapper.toDto(field);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Field in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        field.setId(UUID.randomUUID());

        // Create the Field
        FieldDTO fieldDTO = fieldMapper.toDto(field);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Field in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        field.setId(UUID.randomUUID());

        // Create the Field
        FieldDTO fieldDTO = fieldMapper.toDto(field);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fieldDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Field in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteField() throws Exception {
        // Initialize the database
        insertedField = fieldRepository.saveAndFlush(field);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the field
        restFieldMockMvc
            .perform(delete(ENTITY_API_URL_ID, field.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fieldRepository.count();
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

    protected Field getPersistedField(Field field) {
        return fieldRepository.findById(field.getId()).orElseThrow();
    }

    protected void assertPersistedFieldToMatchAllProperties(Field expectedField) {
        assertFieldAllPropertiesEquals(expectedField, getPersistedField(expectedField));
    }

    protected void assertPersistedFieldToMatchUpdatableProperties(Field expectedField) {
        assertFieldAllUpdatablePropertiesEquals(expectedField, getPersistedField(expectedField));
    }
}
