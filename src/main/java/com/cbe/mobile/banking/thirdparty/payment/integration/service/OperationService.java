package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.OperationDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation}.
 */
public interface OperationService {
    /**
     * Save a operation.
     *
     * @param operationDTO the entity to save.
     * @return the persisted entity.
     */
    OperationDTO save(OperationDTO operationDTO);

    /**
     * Updates a operation.
     *
     * @param operationDTO the entity to update.
     * @return the persisted entity.
     */
    OperationDTO update(OperationDTO operationDTO);

    /**
     * Partially updates a operation.
     *
     * @param operationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OperationDTO> partialUpdate(OperationDTO operationDTO);

    /**
     * Get all the operations.
     *
     * @return the list of entities.
     */
    List<OperationDTO> findAll();

    /**
     * Get the "id" operation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperationDTO> findOne(UUID id);

    /**
     * Delete the "id" operation.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
