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
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.HeaderDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.HeaderMapper;
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

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/headers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HeaderRepository headerRepository;

    @Autowired
    private HeaderMapper headerMapper;

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
        Header header = new Header().key(DEFAULT_KEY).value(DEFAULT_VALUE);
        return header;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Header createUpdatedEntity(EntityManager em) {
        Header header = new Header().key(UPDATED_KEY).value(UPDATED_VALUE);
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
        HeaderDTO headerDTO = headerMapper.toDto(header);
        var returnedHeaderDTO = om.readValue(
            restHeaderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(headerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HeaderDTO.class
        );

        // Validate the Header in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHeader = headerMapper.toEntity(returnedHeaderDTO);
        assertHeaderUpdatableFieldsEquals(returnedHeader, getPersistedHeader(returnedHeader));

        insertedHeader = returnedHeader;
    }

    @Test
    @Transactional
    void createHeaderWithExistingId() throws Exception {
        // Create the Header with an existing ID
        insertedHeader = headerRepository.saveAndFlush(header);
        HeaderDTO headerDTO = headerMapper.toDto(header);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeaderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(headerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Header in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKeyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        header.setKey(null);

        // Create the Header, which fails.
        HeaderDTO headerDTO = headerMapper.toDto(header);

        restHeaderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(headerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        header.setValue(null);

        // Create the Header, which fails.
        HeaderDTO headerDTO = headerMapper.toDto(header);

        restHeaderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(headerDTO)))
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
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
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
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
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
        updatedHeader.key(UPDATED_KEY).value(UPDATED_VALUE);
        HeaderDTO headerDTO = headerMapper.toDto(updatedHeader);

        restHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, headerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(headerDTO))
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

        // Create the Header
        HeaderDTO headerDTO = headerMapper.toDto(header);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, headerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(headerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Header in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        header.setId(UUID.randomUUID());

        // Create the Header
        HeaderDTO headerDTO = headerMapper.toDto(header);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(headerDTO))
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

        // Create the Header
        HeaderDTO headerDTO = headerMapper.toDto(header);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(headerDTO)))
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

        partialUpdatedHeader.value(UPDATED_VALUE);

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

        partialUpdatedHeader.key(UPDATED_KEY).value(UPDATED_VALUE);

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

        // Create the Header
        HeaderDTO headerDTO = headerMapper.toDto(header);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, headerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(headerDTO))
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

        // Create the Header
        HeaderDTO headerDTO = headerMapper.toDto(header);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(headerDTO))
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

        // Create the Header
        HeaderDTO headerDTO = headerMapper.toDto(header);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeaderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(headerDTO)))
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
