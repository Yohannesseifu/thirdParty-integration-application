package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration}.
 */
public interface ThirdPartyIntegrationService {
    /**
     * Save a thirdPartyIntegration.
     *
     * @param thirdPartyIntegration the entity to save.
     * @return the persisted entity.
     */
    ThirdPartyIntegration save(ThirdPartyIntegration thirdPartyIntegration);

    /**
     * Updates a thirdPartyIntegration.
     *
     * @param thirdPartyIntegration the entity to update.
     * @return the persisted entity.
     */
    ThirdPartyIntegration update(ThirdPartyIntegration thirdPartyIntegration);

    /**
     * Partially updates a thirdPartyIntegration.
     *
     * @param thirdPartyIntegration the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThirdPartyIntegration> partialUpdate(ThirdPartyIntegration thirdPartyIntegration);

    /**
     * Get all the thirdPartyIntegrations.
     *
     * @return the list of entities.
     */
    List<ThirdPartyIntegration> findAll();

    /**
     * Get the "id" thirdPartyIntegration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThirdPartyIntegration> findOne(UUID id);

    /**
     * Delete the "id" thirdPartyIntegration.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
