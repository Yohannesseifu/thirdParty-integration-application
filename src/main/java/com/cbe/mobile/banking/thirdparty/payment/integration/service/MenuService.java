package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Menu;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Menu}.
 */
public interface MenuService {
    /**
     * Save a menu.
     *
     * @param menu the entity to save.
     * @return the persisted entity.
     */
    Menu save(Menu menu);

    /**
     * Updates a menu.
     *
     * @param menu the entity to update.
     * @return the persisted entity.
     */
    Menu update(Menu menu);

    /**
     * Partially updates a menu.
     *
     * @param menu the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Menu> partialUpdate(Menu menu);

    /**
     * Get all the menus.
     *
     * @return the list of entities.
     */
    List<Menu> findAll();

    /**
     * Get all the menus with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Menu> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" menu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Menu> findOne(UUID id);

    /**
     * Delete the "id" menu.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
