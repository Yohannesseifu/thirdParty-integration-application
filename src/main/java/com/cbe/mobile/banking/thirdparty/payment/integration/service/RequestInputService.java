package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.RequestInputDTO;
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
     * @param requestInputDTO the entity to save.
     * @return the persisted entity.
     */
    RequestInputDTO save(RequestInputDTO requestInputDTO);

    /**
     * Updates a requestInput.
     *
     * @param requestInputDTO the entity to update.
     * @return the persisted entity.
     */
    RequestInputDTO update(RequestInputDTO requestInputDTO);

    /**
     * Partially updates a requestInput.
     *
     * @param requestInputDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestInputDTO> partialUpdate(RequestInputDTO requestInputDTO);

    /**
     * Get all the requestInputs.
     *
     * @return the list of entities.
     */
    List<RequestInputDTO> findAll();

    /**
     * Get the "id" requestInput.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestInputDTO> findOne(UUID id);

    /**
     * Delete the "id" requestInput.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
