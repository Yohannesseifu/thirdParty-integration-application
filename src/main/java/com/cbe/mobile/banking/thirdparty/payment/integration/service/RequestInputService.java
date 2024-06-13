package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput}.
 */
public interface RequestInputService {
    /**
     * Save a requestInput.
     *
     * @param requestInput the entity to save.
     * @return the persisted entity.
     */
    RequestInput save(RequestInput requestInput);

    /**
     * Updates a requestInput.
     *
     * @param requestInput the entity to update.
     * @return the persisted entity.
     */
    RequestInput update(RequestInput requestInput);

    /**
     * Partially updates a requestInput.
     *
     * @param requestInput the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestInput> partialUpdate(RequestInput requestInput);

    /**
     * Get all the requestInputs.
     *
     * @return the list of entities.
     */
    List<RequestInput> findAll();

    /**
     * Get the "id" requestInput.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestInput> findOne(UUID id);

    /**
     * Delete the "id" requestInput.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
