package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUiAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.FormType;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FormUiRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FormUiDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.FormUiMapper;
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
 * Integration tests for the {@link FormUiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormUiResourceIT {

    private static final String DEFAULT_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FORM_DESCRIPTION = "BBBBBBBBBB";

    private static final FormType DEFAULT_FORM_TYPE = FormType.INIT;
    private static final FormType UPDATED_FORM_TYPE = FormType.REMARK;

    private static final Integer DEFAULT_STEP_ORDER = 1;
    private static final Integer UPDATED_STEP_ORDER = 2;

    private static final String ENTITY_API_URL = "/api/form-uis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormUiRepository formUiRepository;

    @Autowired
    private FormUiMapper formUiMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormUiMockMvc;

    private FormUi formUi;

    private FormUi insertedFormUi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormUi createEntity(EntityManager em) {
        FormUi formUi = new FormUi()
            .formName(DEFAULT_FORM_NAME)
            .formDescription(DEFAULT_FORM_DESCRIPTION)
            .formType(DEFAULT_FORM_TYPE)
            .stepOrder(DEFAULT_STEP_ORDER);
        return formUi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormUi createUpdatedEntity(EntityManager em) {
        FormUi formUi = new FormUi()
            .formName(UPDATED_FORM_NAME)
            .formDescription(UPDATED_FORM_DESCRIPTION)
            .formType(UPDATED_FORM_TYPE)
            .stepOrder(UPDATED_STEP_ORDER);
        return formUi;
    }

    @BeforeEach
    public void initTest() {
        formUi = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFormUi != null) {
            formUiRepository.delete(insertedFormUi);
            insertedFormUi = null;
        }
    }

    @Test
    @Transactional
    void createFormUi() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FormUi
        FormUiDTO formUiDTO = formUiMapper.toDto(formUi);
        var returnedFormUiDTO = om.readValue(
            restFormUiMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formUiDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FormUiDTO.class
        );

        // Validate the FormUi in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFormUi = formUiMapper.toEntity(returnedFormUiDTO);
        assertFormUiUpdatableFieldsEquals(returnedFormUi, getPersistedFormUi(returnedFormUi));

        insertedFormUi = returnedFormUi;
    }

    @Test
    @Transactional
    void createFormUiWithExistingId() throws Exception {
        // Create the FormUi with an existing ID
        insertedFormUi = formUiRepository.saveAndFlush(formUi);
        FormUiDTO formUiDTO = formUiMapper.toDto(formUi);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormUiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formUiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormUi in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormUis() throws Exception {
        // Initialize the database
        insertedFormUi = formUiRepository.saveAndFlush(formUi);

        // Get all the formUiList
        restFormUiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formUi.getId().toString())))
            .andExpect(jsonPath("$.[*].formName").value(hasItem(DEFAULT_FORM_NAME)))
            .andExpect(jsonPath("$.[*].formDescription").value(hasItem(DEFAULT_FORM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].formType").value(hasItem(DEFAULT_FORM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].stepOrder").value(hasItem(DEFAULT_STEP_ORDER)));
    }

    @Test
    @Transactional
    void getFormUi() throws Exception {
        // Initialize the database
        insertedFormUi = formUiRepository.saveAndFlush(formUi);

        // Get the formUi
        restFormUiMockMvc
            .perform(get(ENTITY_API_URL_ID, formUi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formUi.getId().toString()))
            .andExpect(jsonPath("$.formName").value(DEFAULT_FORM_NAME))
            .andExpect(jsonPath("$.formDescription").value(DEFAULT_FORM_DESCRIPTION))
            .andExpect(jsonPath("$.formType").value(DEFAULT_FORM_TYPE.toString()))
            .andExpect(jsonPath("$.stepOrder").value(DEFAULT_STEP_ORDER));
    }

    @Test
    @Transactional
    void getNonExistingFormUi() throws Exception {
        // Get the formUi
        restFormUiMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormUi() throws Exception {
        // Initialize the database
        insertedFormUi = formUiRepository.saveAndFlush(formUi);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formUi
        FormUi updatedFormUi = formUiRepository.findById(formUi.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFormUi are not directly saved in db
        em.detach(updatedFormUi);
        updatedFormUi
            .formName(UPDATED_FORM_NAME)
            .formDescription(UPDATED_FORM_DESCRIPTION)
            .formType(UPDATED_FORM_TYPE)
            .stepOrder(UPDATED_STEP_ORDER);
        FormUiDTO formUiDTO = formUiMapper.toDto(updatedFormUi);

        restFormUiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formUiDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formUiDTO))
            )
            .andExpect(status().isOk());

        // Validate the FormUi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormUiToMatchAllProperties(updatedFormUi);
    }

    @Test
    @Transactional
    void putNonExistingFormUi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formUi.setId(UUID.randomUUID());

        // Create the FormUi
        FormUiDTO formUiDTO = formUiMapper.toDto(formUi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormUiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formUiDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formUiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormUi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormUi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formUi.setId(UUID.randomUUID());

        // Create the FormUi
        FormUiDTO formUiDTO = formUiMapper.toDto(formUi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormUiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formUiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormUi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormUi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formUi.setId(UUID.randomUUID());

        // Create the FormUi
        FormUiDTO formUiDTO = formUiMapper.toDto(formUi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormUiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formUiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormUi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormUiWithPatch() throws Exception {
        // Initialize the database
        insertedFormUi = formUiRepository.saveAndFlush(formUi);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formUi using partial update
        FormUi partialUpdatedFormUi = new FormUi();
        partialUpdatedFormUi.setId(formUi.getId());

        partialUpdatedFormUi.formDescription(UPDATED_FORM_DESCRIPTION).formType(UPDATED_FORM_TYPE);

        restFormUiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormUi.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormUi))
            )
            .andExpect(status().isOk());

        // Validate the FormUi in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormUiUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFormUi, formUi), getPersistedFormUi(formUi));
    }

    @Test
    @Transactional
    void fullUpdateFormUiWithPatch() throws Exception {
        // Initialize the database
        insertedFormUi = formUiRepository.saveAndFlush(formUi);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formUi using partial update
        FormUi partialUpdatedFormUi = new FormUi();
        partialUpdatedFormUi.setId(formUi.getId());

        partialUpdatedFormUi
            .formName(UPDATED_FORM_NAME)
            .formDescription(UPDATED_FORM_DESCRIPTION)
            .formType(UPDATED_FORM_TYPE)
            .stepOrder(UPDATED_STEP_ORDER);

        restFormUiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormUi.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormUi))
            )
            .andExpect(status().isOk());

        // Validate the FormUi in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormUiUpdatableFieldsEquals(partialUpdatedFormUi, getPersistedFormUi(partialUpdatedFormUi));
    }

    @Test
    @Transactional
    void patchNonExistingFormUi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formUi.setId(UUID.randomUUID());

        // Create the FormUi
        FormUiDTO formUiDTO = formUiMapper.toDto(formUi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormUiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formUiDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formUiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormUi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormUi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formUi.setId(UUID.randomUUID());

        // Create the FormUi
        FormUiDTO formUiDTO = formUiMapper.toDto(formUi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormUiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formUiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormUi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormUi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formUi.setId(UUID.randomUUID());

        // Create the FormUi
        FormUiDTO formUiDTO = formUiMapper.toDto(formUi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormUiMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(formUiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormUi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormUi() throws Exception {
        // Initialize the database
        insertedFormUi = formUiRepository.saveAndFlush(formUi);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formUi
        restFormUiMockMvc
            .perform(delete(ENTITY_API_URL_ID, formUi.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formUiRepository.count();
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

    protected FormUi getPersistedFormUi(FormUi formUi) {
        return formUiRepository.findById(formUi.getId()).orElseThrow();
    }

    protected void assertPersistedFormUiToMatchAllProperties(FormUi expectedFormUi) {
        assertFormUiAllPropertiesEquals(expectedFormUi, getPersistedFormUi(expectedFormUi));
    }

    protected void assertPersistedFormUiToMatchUpdatableProperties(FormUi expectedFormUi) {
        assertFormUiAllUpdatablePropertiesEquals(expectedFormUi, getPersistedFormUi(expectedFormUi));
    }
}
