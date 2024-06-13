package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi}.
 */
public interface FormUiService {
    /**
     * Save a formUi.
     *
     * @param formUi the entity to save.
     * @return the persisted entity.
     */
    FormUi save(FormUi formUi);

    /**
     * Updates a formUi.
     *
     * @param formUi the entity to update.
     * @return the persisted entity.
     */
    FormUi update(FormUi formUi);

    /**
     * Partially updates a formUi.
     *
     * @param formUi the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormUi> partialUpdate(FormUi formUi);

    /**
     * Get all the formUis.
     *
     * @return the list of entities.
     */
    List<FormUi> findAll();

    /**
     * Get the "id" formUi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormUi> findOne(UUID id);

    /**
     * Delete the "id" formUi.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
