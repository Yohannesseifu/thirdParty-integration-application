package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FieldUIMetaDataDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData}.
 */
public interface FieldUIMetaDataService {
    /**
     * Save a fieldUIMetaData.
     *
     * @param fieldUIMetaDataDTO the entity to save.
     * @return the persisted entity.
     */
    FieldUIMetaDataDTO save(FieldUIMetaDataDTO fieldUIMetaDataDTO);

    /**
     * Updates a fieldUIMetaData.
     *
     * @param fieldUIMetaDataDTO the entity to update.
     * @return the persisted entity.
     */
    FieldUIMetaDataDTO update(FieldUIMetaDataDTO fieldUIMetaDataDTO);

    /**
     * Partially updates a fieldUIMetaData.
     *
     * @param fieldUIMetaDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldUIMetaDataDTO> partialUpdate(FieldUIMetaDataDTO fieldUIMetaDataDTO);

    /**
     * Get all the fieldUIMetaData.
     *
     * @return the list of entities.
     */
    List<FieldUIMetaDataDTO> findAll();

    /**
     * Get the "id" fieldUIMetaData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldUIMetaDataDTO> findOne(Long id);

    /**
     * Delete the "id" fieldUIMetaData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
