package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.HeaderAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.HeaderRepository;
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
 * Integration tests for the {@link HeaderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeaderResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_STR = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_STR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/headers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HeaderRepository headerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeaderMockMvc;

    private Header header;

    private Header insertedHeader;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Header createEntity(EntityManager em) {
        Header header = new Header().name(DEFAULT_NAME).valueStr(DEFAULT_VALUE_STR);
        return header;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Header createUpdatedEntity(EntityManager em) {
        Header header = new Header().name(UPDATED_NAME).valueStr(UPDATED_VALUE_STR);
        return header;
    }

    @BeforeEach
    public void initTest() {
        header = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedHeader != null) {
            headerRepository.delete(insertedHeader);
            insertedHeader = null;
        }
    }

    @Test
    @Transactional
    void createHeader() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Header
        var returnedHeader = om.readValue(
            restHeaderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(header)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Header.class
        );

        // Validate the Header in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHeaderUpdatableFieldsEquals(returnedHeader, getPersistedHeader(returnedHeader));

        insertedHeader = returnedHeader;
    }

    @Test
    @Transactional
    void createHeaderWithExistingId() throws Exception {
        // Create the Header with an existing ID
        insertedHeader = headerRepository.saveAndFlush(header);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeaderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(header)))
            .andExpect(status().isBadRequest());

        // Validate the Header in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        header.setName(null);

        // Create the Header, which fails.

        restHeaderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(header)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueStrIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        header.setValueStr(null);

        // Create the Header, which fails.

        restHeaderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(header)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHeaders() throws Exception {
        // Initialize the database
        insertedHeader = headerRepository.saveAndFlush(header);

        // Get all the headerList
        restHeaderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(header.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].valueStr").value(hasItem(DEFAULT_VALUE_STR)));
    }

    @Test
    @Transactional
    void getHeader() throws Exception {
        // Initialize the database
        insertedHeader = headerRepository.saveAndFlush(header);

        // Get the header
        restHeaderMockMvc
            .perform(get(ENTITY_API_URL_ID, header.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(header.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.valueStr").value(DEFAULT_VALUE_STR));
    }

    @Test
    @Transactional
    void getNonExistingHeader() throws Exception {
        // Get the header
        restHeaderMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHeader() throws Exception {
        // Initialize the database
        insertedHeader = headerRepository.saveAndFlush(header);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the header
        Header updatedHeader = headerRepository.findById(header.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHeader are not directly saved in db
        em.detach(updatedHeader);
        updatedHeader.name(UPDATED_NAME).valueStr(UPDATED_VALUE_STR);

        restHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHeader.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHeader))
            )
            .andExpect(status().isOk());

        // Validate the Header in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHeaderToMatchAllProperties(updatedHeader);
    }

    @Test
    @Transactional
    void putNonExistingHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        header.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(put(ENTITY_API_URL_ID, header.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(header)))
            .andExpect(status().isBadRequest());

        // Validate the Header in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        header.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(header))
            )
            .andExpect(status().isBadRequest());

        // Validate the Header in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        header.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(header)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Header in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHeaderWithPatch() throws Exception {
        // Initialize the database
        insertedHeader = headerRepository.saveAndFlush(header);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the header using partial update
        Header partialUpdatedHeader = new Header();
        partialUpdatedHeader.setId(header.getId());

        partialUpdatedHeader.valueStr(UPDATED_VALUE_STR);

        restHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeader.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHeader))
            )
            .andExpect(status().isOk());

        // Validate the Header in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHeaderUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHeader, header), getPersistedHeader(header));
    }

    @Test
    @Transactional
    void fullUpdateHeaderWithPatch() throws Exception {
        // Initialize the database
        insertedHeader = headerRepository.saveAndFlush(header);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the header using partial update
        Header partialUpdatedHeader = new Header();
        partialUpdatedHeader.setId(header.getId());

        partialUpdatedHeader.name(UPDATED_NAME).valueStr(UPDATED_VALUE_STR);

        restHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeader.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHeader))
            )
            .andExpect(status().isOk());

        // Validate the Header in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHeaderUpdatableFieldsEquals(partialUpdatedHeader, getPersistedHeader(partialUpdatedHeader));
    }

    @Test
    @Transactional
    void patchNonExistingHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        header.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, header.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(header))
            )
            .andExpect(status().isBadRequest());

        // Validate the Header in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        header.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(header))
            )
            .andExpect(status().isBadRequest());

        // Validate the Header in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        header.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(header)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Header in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHeader() throws Exception {
        // Initialize the database
        insertedHeader = headerRepository.saveAndFlush(header);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the header
        restHeaderMockMvc
            .perform(delete(ENTITY_API_URL_ID, header.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return headerRepository.count();
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

    protected Header getPersistedHeader(Header header) {
        return headerRepository.findById(header.getId()).orElseThrow();
    }

    protected void assertPersistedHeaderToMatchAllProperties(Header expectedHeader) {
        assertHeaderAllPropertiesEquals(expectedHeader, getPersistedHeader(expectedHeader));
    }

    protected void assertPersistedHeaderToMatchUpdatableProperties(Header expectedHeader) {
        assertHeaderAllUpdatablePropertiesEquals(expectedHeader, getPersistedHeader(expectedHeader));
    }
}
