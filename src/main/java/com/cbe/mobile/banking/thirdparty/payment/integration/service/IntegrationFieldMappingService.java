package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping}.
 */
public interface IntegrationFieldMappingService {
    /**
     * Save a integrationFieldMapping.
     *
     * @param integrationFieldMapping the entity to save.
     * @return the persisted entity.
     */
    IntegrationFieldMapping save(IntegrationFieldMapping integrationFieldMapping);

    /**
     * Updates a integrationFieldMapping.
     *
     * @param integrationFieldMapping the entity to update.
     * @return the persisted entity.
     */
    IntegrationFieldMapping update(IntegrationFieldMapping integrationFieldMapping);

    /**
     * Partially updates a integrationFieldMapping.
     *
     * @param integrationFieldMapping the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntegrationFieldMapping> partialUpdate(IntegrationFieldMapping integrationFieldMapping);

    /**
     * Get all the integrationFieldMappings.
     *
     * @return the list of entities.
     */
    List<IntegrationFieldMapping> findAll();

    /**
     * Get the "id" integrationFieldMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntegrationFieldMapping> findOne(UUID id);

    /**
     * Delete the "id" integrationFieldMapping.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
