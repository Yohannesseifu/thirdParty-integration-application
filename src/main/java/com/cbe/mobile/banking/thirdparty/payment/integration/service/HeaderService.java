package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.HeaderDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header}.
 */
public interface HeaderService {
    /**
     * Save a header.
     *
     * @param headerDTO the entity to save.
     * @return the persisted entity.
     */
    HeaderDTO save(HeaderDTO headerDTO);

    /**
     * Updates a header.
     *
     * @param headerDTO the entity to update.
     * @return the persisted entity.
     */
    HeaderDTO update(HeaderDTO headerDTO);

    /**
     * Partially updates a header.
     *
     * @param headerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HeaderDTO> partialUpdate(HeaderDTO headerDTO);

    /**
     * Get all the headers.
     *
     * @return the list of entities.
     */
    List<HeaderDTO> findAll();

    /**
     * Get the "id" header.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HeaderDTO> findOne(UUID id);

    /**
     * Delete the "id" header.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
