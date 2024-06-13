package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Field;
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
     * @param field the entity to save.
     * @return the persisted entity.
     */
    Field save(Field field);

    /**
     * Updates a field.
     *
     * @param field the entity to update.
     * @return the persisted entity.
     */
    Field update(Field field);

    /**
     * Partially updates a field.
     *
     * @param field the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Field> partialUpdate(Field field);

    /**
     * Get all the fields.
     *
     * @return the list of entities.
     */
    List<Field> findAll();

    /**
     * Get the "id" field.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Field> findOne(UUID id);

    /**
     * Delete the "id" field.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
