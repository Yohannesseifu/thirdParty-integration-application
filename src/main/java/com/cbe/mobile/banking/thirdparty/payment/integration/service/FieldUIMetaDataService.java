package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData}.
 */
public interface FieldUIMetaDataService {
    /**
     * Save a fieldUIMetaData.
     *
     * @param fieldUIMetaData the entity to save.
     * @return the persisted entity.
     */
    FieldUIMetaData save(FieldUIMetaData fieldUIMetaData);

    /**
     * Updates a fieldUIMetaData.
     *
     * @param fieldUIMetaData the entity to update.
     * @return the persisted entity.
     */
    FieldUIMetaData update(FieldUIMetaData fieldUIMetaData);

    /**
     * Partially updates a fieldUIMetaData.
     *
     * @param fieldUIMetaData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldUIMetaData> partialUpdate(FieldUIMetaData fieldUIMetaData);

    /**
     * Get all the fieldUIMetaData.
     *
     * @return the list of entities.
     */
    List<FieldUIMetaData> findAll();

    /**
     * Get the "id" fieldUIMetaData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldUIMetaData> findOne(UUID id);

    /**
     * Delete the "id" fieldUIMetaData.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
