package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.IntegrationOperationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation}.
 */
public interface IntegrationOperationService {
    /**
     * Save a integrationOperation.
     *
     * @param integrationOperationDTO the entity to save.
     * @return the persisted entity.
     */
    IntegrationOperationDTO save(IntegrationOperationDTO integrationOperationDTO);

    /**
     * Updates a integrationOperation.
     *
     * @param integrationOperationDTO the entity to update.
     * @return the persisted entity.
     */
    IntegrationOperationDTO update(IntegrationOperationDTO integrationOperationDTO);

    /**
     * Partially updates a integrationOperation.
     *
     * @param integrationOperationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntegrationOperationDTO> partialUpdate(IntegrationOperationDTO integrationOperationDTO);

    /**
     * Get all the integrationOperations.
     *
     * @return the list of entities.
     */
    List<IntegrationOperationDTO> findAll();

    /**
     * Get the "id" integrationOperation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntegrationOperationDTO> findOne(Long id);

    /**
     * Delete the "id" integrationOperation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
