package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Menu;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.MenuDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ThirdPartyIntegrationDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThirdPartyIntegration} and its DTO {@link ThirdPartyIntegrationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ThirdPartyIntegrationMapper extends EntityMapper<ThirdPartyIntegrationDTO, ThirdPartyIntegration> {
    @Mapping(target = "categoryMenus", source = "categoryMenus", qualifiedByName = "menuIdSet")
    ThirdPartyIntegrationDTO toDto(ThirdPartyIntegration s);

    @Mapping(target = "categoryMenus", ignore = true)
    @Mapping(target = "removeCategoryMenus", ignore = true)
    ThirdPartyIntegration toEntity(ThirdPartyIntegrationDTO thirdPartyIntegrationDTO);

    @Named("menuId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MenuDTO toDtoMenuId(Menu menu);

    @Named("menuIdSet")
    default Set<MenuDTO> toDtoMenuIdSet(Set<Menu> menu) {
        return menu.stream().map(this::toDtoMenuId).collect(Collectors.toSet());
    }
}
