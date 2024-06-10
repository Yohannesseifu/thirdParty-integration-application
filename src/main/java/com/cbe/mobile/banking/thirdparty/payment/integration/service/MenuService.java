package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.MenuDTO;
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
     * @param menuDTO the entity to save.
     * @return the persisted entity.
     */
    MenuDTO save(MenuDTO menuDTO);

    /**
     * Updates a menu.
     *
     * @param menuDTO the entity to update.
     * @return the persisted entity.
     */
    MenuDTO update(MenuDTO menuDTO);

    /**
     * Partially updates a menu.
     *
     * @param menuDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MenuDTO> partialUpdate(MenuDTO menuDTO);

    /**
     * Get all the menus.
     *
     * @return the list of entities.
     */
    List<MenuDTO> findAll();

    /**
     * Get all the menus with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MenuDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" menu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MenuDTO> findOne(UUID id);

    /**
     * Delete the "id" menu.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
