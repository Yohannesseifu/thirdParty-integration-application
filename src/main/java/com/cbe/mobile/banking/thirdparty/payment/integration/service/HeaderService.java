package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header;
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
     * @param header the entity to save.
     * @return the persisted entity.
     */
    Header save(Header header);

    /**
     * Updates a header.
     *
     * @param header the entity to update.
     * @return the persisted entity.
     */
    Header update(Header header);

    /**
     * Partially updates a header.
     *
     * @param header the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Header> partialUpdate(Header header);

    /**
     * Get all the headers.
     *
     * @return the list of entities.
     */
    List<Header> findAll();

    /**
     * Get the "id" header.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Header> findOne(UUID id);

    /**
     * Delete the "id" header.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
