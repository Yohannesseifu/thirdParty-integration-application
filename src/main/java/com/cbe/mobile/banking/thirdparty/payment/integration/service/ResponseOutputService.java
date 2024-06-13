package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput}.
 */
public interface ResponseOutputService {
    /**
     * Save a responseOutput.
     *
     * @param responseOutput the entity to save.
     * @return the persisted entity.
     */
    ResponseOutput save(ResponseOutput responseOutput);

    /**
     * Updates a responseOutput.
     *
     * @param responseOutput the entity to update.
     * @return the persisted entity.
     */
    ResponseOutput update(ResponseOutput responseOutput);

    /**
     * Partially updates a responseOutput.
     *
     * @param responseOutput the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResponseOutput> partialUpdate(ResponseOutput responseOutput);

    /**
     * Get all the responseOutputs.
     *
     * @return the list of entities.
     */
    List<ResponseOutput> findAll();

    /**
     * Get the "id" responseOutput.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponseOutput> findOne(UUID id);

    /**
     * Delete the "id" responseOutput.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
