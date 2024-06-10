package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiIntegrationDTO;
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
     * @param apiIntegrationDTO the entity to save.
     * @return the persisted entity.
     */
    ApiIntegrationDTO save(ApiIntegrationDTO apiIntegrationDTO);

    /**
     * Updates a apiIntegration.
     *
     * @param apiIntegrationDTO the entity to update.
     * @return the persisted entity.
     */
    ApiIntegrationDTO update(ApiIntegrationDTO apiIntegrationDTO);

    /**
     * Partially updates a apiIntegration.
     *
     * @param apiIntegrationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApiIntegrationDTO> partialUpdate(ApiIntegrationDTO apiIntegrationDTO);

    /**
     * Get all the apiIntegrations.
     *
     * @return the list of entities.
     */
    List<ApiIntegrationDTO> findAll();

    /**
     * Get the "id" apiIntegration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiIntegrationDTO> findOne(UUID id);

    /**
     * Delete the "id" apiIntegration.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
