package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Menu;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MenuRepositoryWithBagRelationships {
    Optional<Menu> fetchBagRelationships(Optional<Menu> menu);

    List<Menu> fetchBagRelationships(List<Menu> menus);

    Page<Menu> fetchBagRelationships(Page<Menu> menus);
}
