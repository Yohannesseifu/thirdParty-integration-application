package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Menu;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.MenuRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.MenuService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Menu}.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu save(Menu menu) {
        log.debug("Request to save Menu : {}", menu);
        return menuRepository.save(menu);
    }

    @Override
    public Menu update(Menu menu) {
        log.debug("Request to update Menu : {}", menu);
        return menuRepository.save(menu);
    }

    @Override
    public Optional<Menu> partialUpdate(Menu menu) {
        log.debug("Request to partially update Menu : {}", menu);

        return menuRepository
            .findById(menu.getId())
            .map(existingMenu -> {
                if (menu.getMenuName() != null) {
                    existingMenu.setMenuName(menu.getMenuName());
                }
                if (menu.getMenuDescription() != null) {
                    existingMenu.setMenuDescription(menu.getMenuDescription());
                }
                if (menu.getIconPath() != null) {
                    existingMenu.setIconPath(menu.getIconPath());
                }
                if (menu.getEnabled() != null) {
                    existingMenu.setEnabled(menu.getEnabled());
                }

                return existingMenu;
            })
            .map(menuRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        log.debug("Request to get all Menus");
        return menuRepository.findAll();
    }

    public Page<Menu> findAllWithEagerRelationships(Pageable pageable) {
        return menuRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Menu> findOne(UUID id) {
        log.debug("Request to get Menu : {}", id);
        return menuRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Menu : {}", id);
        menuRepository.deleteById(id);
    }
}
