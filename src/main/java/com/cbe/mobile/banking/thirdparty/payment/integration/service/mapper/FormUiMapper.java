package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FormUiDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ThirdPartyIntegrationDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormUi} and its DTO {@link FormUiDTO}.
 */
@Mapper(componentModel = "spring")
public interface FormUiMapper extends EntityMapper<FormUiDTO, FormUi> {
    @Mapping(target = "thirdPartyIntegration", source = "thirdPartyIntegration", qualifiedByName = "thirdPartyIntegrationId")
    FormUiDTO toDto(FormUi s);

    @Named("thirdPartyIntegrationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ThirdPartyIntegrationDTO toDtoThirdPartyIntegrationId(ThirdPartyIntegration thirdPartyIntegration);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
