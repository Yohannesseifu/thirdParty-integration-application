package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration}.
 */
public interface ApiIntegrationService {
    /**
     * Save a apiIntegration.
     *
     * @param apiIntegration the entity to save.
     * @return the persisted entity.
     */
    ApiIntegration save(ApiIntegration apiIntegration);

    /**
     * Updates a apiIntegration.
     *
     * @param apiIntegration the entity to update.
     * @return the persisted entity.
     */
    ApiIntegration update(ApiIntegration apiIntegration);

    /**
     * Partially updates a apiIntegration.
     *
     * @param apiIntegration the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApiIntegration> partialUpdate(ApiIntegration apiIntegration);

    /**
     * Get all the apiIntegrations.
     *
     * @return the list of entities.
     */
    List<ApiIntegration> findAll();

    /**
     * Get the "id" apiIntegration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiIntegration> findOne(UUID id);

    /**
     * Delete the "id" apiIntegration.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
