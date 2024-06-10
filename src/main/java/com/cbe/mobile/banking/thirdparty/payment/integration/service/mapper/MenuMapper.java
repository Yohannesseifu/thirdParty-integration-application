package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Menu;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.MenuDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ThirdPartyIntegrationDTO;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Menu} and its DTO {@link MenuDTO}.
 */
@Mapper(componentModel = "spring")
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {
    @Mapping(target = "dynamicPaymentMenus", source = "dynamicPaymentMenus", qualifiedByName = "thirdPartyIntegrationIdSet")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "menuId")
    MenuDTO toDto(Menu s);

    @Mapping(target = "removeDynamicPaymentMenus", ignore = true)
    Menu toEntity(MenuDTO menuDTO);

    @Named("menuId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MenuDTO toDtoMenuId(Menu menu);

    @Named("thirdPartyIntegrationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ThirdPartyIntegrationDTO toDtoThirdPartyIntegrationId(ThirdPartyIntegration thirdPartyIntegration);

    @Named("thirdPartyIntegrationIdSet")
    default Set<ThirdPartyIntegrationDTO> toDtoThirdPartyIntegrationIdSet(Set<ThirdPartyIntegration> thirdPartyIntegration) {
        return thirdPartyIntegration.stream().map(this::toDtoThirdPartyIntegrationId).collect(Collectors.toSet());
    }

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
