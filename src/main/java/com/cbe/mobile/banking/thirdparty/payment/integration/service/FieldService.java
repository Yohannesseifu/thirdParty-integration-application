package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FieldDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Field}.
 */
public interface FieldService {
    /**
     * Save a field.
     *
     * @param fieldDTO the entity to save.
     * @return the persisted entity.
     */
    FieldDTO save(FieldDTO fieldDTO);

    /**
     * Updates a field.
     *
     * @param fieldDTO the entity to update.
     * @return the persisted entity.
     */
    FieldDTO update(FieldDTO fieldDTO);

    /**
     * Partially updates a field.
     *
     * @param fieldDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldDTO> partialUpdate(FieldDTO fieldDTO);

    /**
     * Get all the fields.
     *
     * @return the list of entities.
     */
    List<FieldDTO> findAll();

    /**
     * Get the "id" field.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldDTO> findOne(UUID id);

    /**
     * Delete the "id" field.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
