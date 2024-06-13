package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation}.
 */
public interface IntegrationOperationService {
    /**
     * Save a integrationOperation.
     *
     * @param integrationOperation the entity to save.
     * @return the persisted entity.
     */
    IntegrationOperation save(IntegrationOperation integrationOperation);

    /**
     * Updates a integrationOperation.
     *
     * @param integrationOperation the entity to update.
     * @return the persisted entity.
     */
    IntegrationOperation update(IntegrationOperation integrationOperation);

    /**
     * Partially updates a integrationOperation.
     *
     * @param integrationOperation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntegrationOperation> partialUpdate(IntegrationOperation integrationOperation);

    /**
     * Get all the integrationOperations.
     *
     * @return the list of entities.
     */
    List<IntegrationOperation> findAll();

    /**
     * Get the "id" integrationOperation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntegrationOperation> findOne(UUID id);

    /**
     * Delete the "id" integrationOperation.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
