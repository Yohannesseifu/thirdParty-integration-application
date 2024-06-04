package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FormUiDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi}.
 */
public interface FormUiService {
    /**
     * Save a formUi.
     *
     * @param formUiDTO the entity to save.
     * @return the persisted entity.
     */
    FormUiDTO save(FormUiDTO formUiDTO);

    /**
     * Updates a formUi.
     *
     * @param formUiDTO the entity to update.
     * @return the persisted entity.
     */
    FormUiDTO update(FormUiDTO formUiDTO);

    /**
     * Partially updates a formUi.
     *
     * @param formUiDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormUiDTO> partialUpdate(FormUiDTO formUiDTO);

    /**
     * Get all the formUis.
     *
     * @return the list of entities.
     */
    List<FormUiDTO> findAll();

    /**
     * Get the "id" formUi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormUiDTO> findOne(Long id);

    /**
     * Delete the "id" formUi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
