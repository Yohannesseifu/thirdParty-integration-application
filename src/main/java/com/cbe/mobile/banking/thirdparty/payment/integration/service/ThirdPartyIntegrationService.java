package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ThirdPartyIntegrationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration}.
 */
public interface ThirdPartyIntegrationService {
    /**
     * Save a thirdPartyIntegration.
     *
     * @param thirdPartyIntegrationDTO the entity to save.
     * @return the persisted entity.
     */
    ThirdPartyIntegrationDTO save(ThirdPartyIntegrationDTO thirdPartyIntegrationDTO);

    /**
     * Updates a thirdPartyIntegration.
     *
     * @param thirdPartyIntegrationDTO the entity to update.
     * @return the persisted entity.
     */
    ThirdPartyIntegrationDTO update(ThirdPartyIntegrationDTO thirdPartyIntegrationDTO);

    /**
     * Partially updates a thirdPartyIntegration.
     *
     * @param thirdPartyIntegrationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThirdPartyIntegrationDTO> partialUpdate(ThirdPartyIntegrationDTO thirdPartyIntegrationDTO);

    /**
     * Get all the thirdPartyIntegrations.
     *
     * @return the list of entities.
     */
    List<ThirdPartyIntegrationDTO> findAll();

    /**
     * Get the "id" thirdPartyIntegration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThirdPartyIntegrationDTO> findOne(Long id);

    /**
     * Delete the "id" thirdPartyIntegration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
