package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation;
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
     * @param operation the entity to save.
     * @return the persisted entity.
     */
    Operation save(Operation operation);

    /**
     * Updates a operation.
     *
     * @param operation the entity to update.
     * @return the persisted entity.
     */
    Operation update(Operation operation);

    /**
     * Partially updates a operation.
     *
     * @param operation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Operation> partialUpdate(Operation operation);

    /**
     * Get all the operations.
     *
     * @return the list of entities.
     */
    List<Operation> findAll();

    /**
     * Get the "id" operation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Operation> findOne(UUID id);

    /**
     * Delete the "id" operation.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
