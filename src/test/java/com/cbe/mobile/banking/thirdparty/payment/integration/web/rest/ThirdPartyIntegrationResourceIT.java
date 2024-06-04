package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegrationAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbe.mobile.banking.thirdparty.payment.integration.IntegrationTest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.IntegrationType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.Visiblity;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ThirdPartyIntegrationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ThirdPartyIntegrationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.ThirdPartyIntegrationMapper;
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
 * Integration tests for the {@link ThirdPartyIntegrationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThirdPartyIntegrationResourceIT {

    private static final Boolean DEFAULT_IS_DRAFT = false;
    private static final Boolean UPDATED_IS_DRAFT = true;

    private static final String DEFAULT_INTEGRATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INTEGRATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ICON_PATH = "AAAAAAAAAA";
    private static final String UPDATED_ICON_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_MINIMUM_AMOUNT = 1D;
    private static final Double UPDATED_MINIMUM_AMOUNT = 2D;

    private static final Double DEFAULT_MAXIMUM_AMOUNT = 1D;
    private static final Double UPDATED_MAXIMUM_AMOUNT = 2D;

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_CONFIRMATION_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_CONFIRMATION_TEMPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_DETAIL_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_DETAIL_TEMPLATE = "BBBBBBBBBB";

    private static final IntegrationType DEFAULT_INTEGRATION_CATEGORY = IntegrationType.PAYMENT;
    private static final IntegrationType UPDATED_INTEGRATION_CATEGORY = IntegrationType.TRANSFER;

    private static final Visiblity DEFAULT_VISIBLITY = Visiblity.EVERYONE;
    private static final Visiblity UPDATED_VISIBLITY = Visiblity.FOR_STUFF;

    private static final Boolean DEFAULT_CONFIRM_RECIPIENT_IDENTITY = false;
    private static final Boolean UPDATED_CONFIRM_RECIPIENT_IDENTITY = true;

    private static final String ENTITY_API_URL = "/api/third-party-integrations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ThirdPartyIntegrationRepository thirdPartyIntegrationRepository;

    @Autowired
    private ThirdPartyIntegrationMapper thirdPartyIntegrationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThirdPartyIntegrationMockMvc;

    private ThirdPartyIntegration thirdPartyIntegration;

    private ThirdPartyIntegration insertedThirdPartyIntegration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThirdPartyIntegration createEntity(EntityManager em) {
        ThirdPartyIntegration thirdPartyIntegration = new ThirdPartyIntegration()
            .isDraft(DEFAULT_IS_DRAFT)
            .integrationName(DEFAULT_INTEGRATION_NAME)
            .companyName(DEFAULT_COMPANY_NAME)
            .description(DEFAULT_DESCRIPTION)
            .iconPath(DEFAULT_ICON_PATH)
            .enabled(DEFAULT_ENABLED)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .minimumAmount(DEFAULT_MINIMUM_AMOUNT)
            .maximumAmount(DEFAULT_MAXIMUM_AMOUNT)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .paymentConfirmationTemplate(DEFAULT_PAYMENT_CONFIRMATION_TEMPLATE)
            .paymentDetailTemplate(DEFAULT_PAYMENT_DETAIL_TEMPLATE)
            .integrationCategory(DEFAULT_INTEGRATION_CATEGORY)
            .visiblity(DEFAULT_VISIBLITY)
            .confirmRecipientIdentity(DEFAULT_CONFIRM_RECIPIENT_IDENTITY);
        return thirdPartyIntegration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThirdPartyIntegration createUpdatedEntity(EntityManager em) {
        ThirdPartyIntegration thirdPartyIntegration = new ThirdPartyIntegration()
            .isDraft(UPDATED_IS_DRAFT)
            .integrationName(UPDATED_INTEGRATION_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .description(UPDATED_DESCRIPTION)
            .iconPath(UPDATED_ICON_PATH)
            .enabled(UPDATED_ENABLED)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .minimumAmount(UPDATED_MINIMUM_AMOUNT)
            .maximumAmount(UPDATED_MAXIMUM_AMOUNT)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .paymentConfirmationTemplate(UPDATED_PAYMENT_CONFIRMATION_TEMPLATE)
            .paymentDetailTemplate(UPDATED_PAYMENT_DETAIL_TEMPLATE)
            .integrationCategory(UPDATED_INTEGRATION_CATEGORY)
            .visiblity(UPDATED_VISIBLITY)
            .confirmRecipientIdentity(UPDATED_CONFIRM_RECIPIENT_IDENTITY);
        return thirdPartyIntegration;
    }

    @BeforeEach
    public void initTest() {
        thirdPartyIntegration = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedThirdPartyIntegration != null) {
            thirdPartyIntegrationRepository.delete(insertedThirdPartyIntegration);
            insertedThirdPartyIntegration = null;
        }
    }

    @Test
    @Transactional
    void createThirdPartyIntegration() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ThirdPartyIntegration
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO = thirdPartyIntegrationMapper.toDto(thirdPartyIntegration);
        var returnedThirdPartyIntegrationDTO = om.readValue(
            restThirdPartyIntegrationMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thirdPartyIntegrationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ThirdPartyIntegrationDTO.class
        );

        // Validate the ThirdPartyIntegration in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedThirdPartyIntegration = thirdPartyIntegrationMapper.toEntity(returnedThirdPartyIntegrationDTO);
        assertThirdPartyIntegrationUpdatableFieldsEquals(
            returnedThirdPartyIntegration,
            getPersistedThirdPartyIntegration(returnedThirdPartyIntegration)
        );

        insertedThirdPartyIntegration = returnedThirdPartyIntegration;
    }

    @Test
    @Transactional
    void createThirdPartyIntegrationWithExistingId() throws Exception {
        // Create the ThirdPartyIntegration with an existing ID
        thirdPartyIntegration.setId(1L);
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO = thirdPartyIntegrationMapper.toDto(thirdPartyIntegration);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThirdPartyIntegrationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thirdPartyIntegrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThirdPartyIntegrations() throws Exception {
        // Initialize the database
        insertedThirdPartyIntegration = thirdPartyIntegrationRepository.saveAndFlush(thirdPartyIntegration);

        // Get all the thirdPartyIntegrationList
        restThirdPartyIntegrationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdPartyIntegration.getId().intValue())))
            .andExpect(jsonPath("$.[*].isDraft").value(hasItem(DEFAULT_IS_DRAFT.booleanValue())))
            .andExpect(jsonPath("$.[*].integrationName").value(hasItem(DEFAULT_INTEGRATION_NAME)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].iconPath").value(hasItem(DEFAULT_ICON_PATH)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].minimumAmount").value(hasItem(DEFAULT_MINIMUM_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maximumAmount").value(hasItem(DEFAULT_MAXIMUM_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].paymentConfirmationTemplate").value(hasItem(DEFAULT_PAYMENT_CONFIRMATION_TEMPLATE)))
            .andExpect(jsonPath("$.[*].paymentDetailTemplate").value(hasItem(DEFAULT_PAYMENT_DETAIL_TEMPLATE)))
            .andExpect(jsonPath("$.[*].integrationCategory").value(hasItem(DEFAULT_INTEGRATION_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].visiblity").value(hasItem(DEFAULT_VISIBLITY.toString())))
            .andExpect(jsonPath("$.[*].confirmRecipientIdentity").value(hasItem(DEFAULT_CONFIRM_RECIPIENT_IDENTITY.booleanValue())));
    }

    @Test
    @Transactional
    void getThirdPartyIntegration() throws Exception {
        // Initialize the database
        insertedThirdPartyIntegration = thirdPartyIntegrationRepository.saveAndFlush(thirdPartyIntegration);

        // Get the thirdPartyIntegration
        restThirdPartyIntegrationMockMvc
            .perform(get(ENTITY_API_URL_ID, thirdPartyIntegration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thirdPartyIntegration.getId().intValue()))
            .andExpect(jsonPath("$.isDraft").value(DEFAULT_IS_DRAFT.booleanValue()))
            .andExpect(jsonPath("$.integrationName").value(DEFAULT_INTEGRATION_NAME))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.iconPath").value(DEFAULT_ICON_PATH))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.minimumAmount").value(DEFAULT_MINIMUM_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.maximumAmount").value(DEFAULT_MAXIMUM_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE))
            .andExpect(jsonPath("$.paymentConfirmationTemplate").value(DEFAULT_PAYMENT_CONFIRMATION_TEMPLATE))
            .andExpect(jsonPath("$.paymentDetailTemplate").value(DEFAULT_PAYMENT_DETAIL_TEMPLATE))
            .andExpect(jsonPath("$.integrationCategory").value(DEFAULT_INTEGRATION_CATEGORY.toString()))
            .andExpect(jsonPath("$.visiblity").value(DEFAULT_VISIBLITY.toString()))
            .andExpect(jsonPath("$.confirmRecipientIdentity").value(DEFAULT_CONFIRM_RECIPIENT_IDENTITY.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingThirdPartyIntegration() throws Exception {
        // Get the thirdPartyIntegration
        restThirdPartyIntegrationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingThirdPartyIntegration() throws Exception {
        // Initialize the database
        insertedThirdPartyIntegration = thirdPartyIntegrationRepository.saveAndFlush(thirdPartyIntegration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thirdPartyIntegration
        ThirdPartyIntegration updatedThirdPartyIntegration = thirdPartyIntegrationRepository
            .findById(thirdPartyIntegration.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedThirdPartyIntegration are not directly saved in db
        em.detach(updatedThirdPartyIntegration);
        updatedThirdPartyIntegration
            .isDraft(UPDATED_IS_DRAFT)
            .integrationName(UPDATED_INTEGRATION_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .description(UPDATED_DESCRIPTION)
            .iconPath(UPDATED_ICON_PATH)
            .enabled(UPDATED_ENABLED)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .minimumAmount(UPDATED_MINIMUM_AMOUNT)
            .maximumAmount(UPDATED_MAXIMUM_AMOUNT)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .paymentConfirmationTemplate(UPDATED_PAYMENT_CONFIRMATION_TEMPLATE)
            .paymentDetailTemplate(UPDATED_PAYMENT_DETAIL_TEMPLATE)
            .integrationCategory(UPDATED_INTEGRATION_CATEGORY)
            .visiblity(UPDATED_VISIBLITY)
            .confirmRecipientIdentity(UPDATED_CONFIRM_RECIPIENT_IDENTITY);
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO = thirdPartyIntegrationMapper.toDto(updatedThirdPartyIntegration);

        restThirdPartyIntegrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thirdPartyIntegrationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thirdPartyIntegrationDTO))
            )
            .andExpect(status().isOk());

        // Validate the ThirdPartyIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedThirdPartyIntegrationToMatchAllProperties(updatedThirdPartyIntegration);
    }

    @Test
    @Transactional
    void putNonExistingThirdPartyIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyIntegration.setId(longCount.incrementAndGet());

        // Create the ThirdPartyIntegration
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO = thirdPartyIntegrationMapper.toDto(thirdPartyIntegration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThirdPartyIntegrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thirdPartyIntegrationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thirdPartyIntegrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThirdPartyIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyIntegration.setId(longCount.incrementAndGet());

        // Create the ThirdPartyIntegration
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO = thirdPartyIntegrationMapper.toDto(thirdPartyIntegration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyIntegrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thirdPartyIntegrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThirdPartyIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyIntegration.setId(longCount.incrementAndGet());

        // Create the ThirdPartyIntegration
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO = thirdPartyIntegrationMapper.toDto(thirdPartyIntegration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyIntegrationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thirdPartyIntegrationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThirdPartyIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThirdPartyIntegrationWithPatch() throws Exception {
        // Initialize the database
        insertedThirdPartyIntegration = thirdPartyIntegrationRepository.saveAndFlush(thirdPartyIntegration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thirdPartyIntegration using partial update
        ThirdPartyIntegration partialUpdatedThirdPartyIntegration = new ThirdPartyIntegration();
        partialUpdatedThirdPartyIntegration.setId(thirdPartyIntegration.getId());

        partialUpdatedThirdPartyIntegration
            .isDraft(UPDATED_IS_DRAFT)
            .integrationName(UPDATED_INTEGRATION_NAME)
            .description(UPDATED_DESCRIPTION)
            .enabled(UPDATED_ENABLED)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .integrationCategory(UPDATED_INTEGRATION_CATEGORY)
            .confirmRecipientIdentity(UPDATED_CONFIRM_RECIPIENT_IDENTITY);

        restThirdPartyIntegrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThirdPartyIntegration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThirdPartyIntegration))
            )
            .andExpect(status().isOk());

        // Validate the ThirdPartyIntegration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThirdPartyIntegrationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedThirdPartyIntegration, thirdPartyIntegration),
            getPersistedThirdPartyIntegration(thirdPartyIntegration)
        );
    }

    @Test
    @Transactional
    void fullUpdateThirdPartyIntegrationWithPatch() throws Exception {
        // Initialize the database
        insertedThirdPartyIntegration = thirdPartyIntegrationRepository.saveAndFlush(thirdPartyIntegration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thirdPartyIntegration using partial update
        ThirdPartyIntegration partialUpdatedThirdPartyIntegration = new ThirdPartyIntegration();
        partialUpdatedThirdPartyIntegration.setId(thirdPartyIntegration.getId());

        partialUpdatedThirdPartyIntegration
            .isDraft(UPDATED_IS_DRAFT)
            .integrationName(UPDATED_INTEGRATION_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .description(UPDATED_DESCRIPTION)
            .iconPath(UPDATED_ICON_PATH)
            .enabled(UPDATED_ENABLED)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .minimumAmount(UPDATED_MINIMUM_AMOUNT)
            .maximumAmount(UPDATED_MAXIMUM_AMOUNT)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .paymentConfirmationTemplate(UPDATED_PAYMENT_CONFIRMATION_TEMPLATE)
            .paymentDetailTemplate(UPDATED_PAYMENT_DETAIL_TEMPLATE)
            .integrationCategory(UPDATED_INTEGRATION_CATEGORY)
            .visiblity(UPDATED_VISIBLITY)
            .confirmRecipientIdentity(UPDATED_CONFIRM_RECIPIENT_IDENTITY);

        restThirdPartyIntegrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThirdPartyIntegration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThirdPartyIntegration))
            )
            .andExpect(status().isOk());

        // Validate the ThirdPartyIntegration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThirdPartyIntegrationUpdatableFieldsEquals(
            partialUpdatedThirdPartyIntegration,
            getPersistedThirdPartyIntegration(partialUpdatedThirdPartyIntegration)
        );
    }

    @Test
    @Transactional
    void patchNonExistingThirdPartyIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyIntegration.setId(longCount.incrementAndGet());

        // Create the ThirdPartyIntegration
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO = thirdPartyIntegrationMapper.toDto(thirdPartyIntegration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThirdPartyIntegrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thirdPartyIntegrationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thirdPartyIntegrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThirdPartyIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyIntegration.setId(longCount.incrementAndGet());

        // Create the ThirdPartyIntegration
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO = thirdPartyIntegrationMapper.toDto(thirdPartyIntegration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyIntegrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thirdPartyIntegrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThirdPartyIntegration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyIntegration.setId(longCount.incrementAndGet());

        // Create the ThirdPartyIntegration
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO = thirdPartyIntegrationMapper.toDto(thirdPartyIntegration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyIntegrationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(thirdPartyIntegrationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThirdPartyIntegration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThirdPartyIntegration() throws Exception {
        // Initialize the database
        insertedThirdPartyIntegration = thirdPartyIntegrationRepository.saveAndFlush(thirdPartyIntegration);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the thirdPartyIntegration
        restThirdPartyIntegrationMockMvc
            .perform(delete(ENTITY_API_URL_ID, thirdPartyIntegration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return thirdPartyIntegrationRepository.count();
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

    protected ThirdPartyIntegration getPersistedThirdPartyIntegration(ThirdPartyIntegration thirdPartyIntegration) {
        return thirdPartyIntegrationRepository.findById(thirdPartyIntegration.getId()).orElseThrow();
    }

    protected void assertPersistedThirdPartyIntegrationToMatchAllProperties(ThirdPartyIntegration expectedThirdPartyIntegration) {
        assertThirdPartyIntegrationAllPropertiesEquals(
            expectedThirdPartyIntegration,
            getPersistedThirdPartyIntegration(expectedThirdPartyIntegration)
        );
    }

    protected void assertPersistedThirdPartyIntegrationToMatchUpdatableProperties(ThirdPartyIntegration expectedThirdPartyIntegration) {
        assertThirdPartyIntegrationAllUpdatablePropertiesEquals(
            expectedThirdPartyIntegration,
            getPersistedThirdPartyIntegration(expectedThirdPartyIntegration)
        );
    }
}
