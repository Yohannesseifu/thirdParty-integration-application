package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ResponseOutputDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput}.
 */
public interface ResponseOutputService {
    /**
     * Save a responseOutput.
     *
     * @param responseOutputDTO the entity to save.
     * @return the persisted entity.
     */
    ResponseOutputDTO save(ResponseOutputDTO responseOutputDTO);

    /**
     * Updates a responseOutput.
     *
     * @param responseOutputDTO the entity to update.
     * @return the persisted entity.
     */
    ResponseOutputDTO update(ResponseOutputDTO responseOutputDTO);

    /**
     * Partially updates a responseOutput.
     *
     * @param responseOutputDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResponseOutputDTO> partialUpdate(ResponseOutputDTO responseOutputDTO);

    /**
     * Get all the responseOutputs.
     *
     * @return the list of entities.
     */
    List<ResponseOutputDTO> findAll();

    /**
     * Get the "id" responseOutput.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponseOutputDTO> findOne(Long id);

    /**
     * Delete the "id" responseOutput.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
