package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.IntegrationFieldMappingDTO;
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
     * @param integrationFieldMappingDTO the entity to save.
     * @return the persisted entity.
     */
    IntegrationFieldMappingDTO save(IntegrationFieldMappingDTO integrationFieldMappingDTO);

    /**
     * Updates a integrationFieldMapping.
     *
     * @param integrationFieldMappingDTO the entity to update.
     * @return the persisted entity.
     */
    IntegrationFieldMappingDTO update(IntegrationFieldMappingDTO integrationFieldMappingDTO);

    /**
     * Partially updates a integrationFieldMapping.
     *
     * @param integrationFieldMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntegrationFieldMappingDTO> partialUpdate(IntegrationFieldMappingDTO integrationFieldMappingDTO);

    /**
     * Get all the integrationFieldMappings.
     *
     * @return the list of entities.
     */
    List<IntegrationFieldMappingDTO> findAll();

    /**
     * Get the "id" integrationFieldMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntegrationFieldMappingDTO> findOne(UUID id);

    /**
     * Delete the "id" integrationFieldMapping.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
