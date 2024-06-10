package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.IntegrationOperationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.OperationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ThirdPartyIntegrationDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntegrationOperation} and its DTO {@link IntegrationOperationDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntegrationOperationMapper extends EntityMapper<IntegrationOperationDTO, IntegrationOperation> {
    @Mapping(target = "thirdPartyIntegration", source = "thirdPartyIntegration", qualifiedByName = "thirdPartyIntegrationId")
    @Mapping(target = "operation", source = "operation", qualifiedByName = "operationId")
    IntegrationOperationDTO toDto(IntegrationOperation s);

    @Named("thirdPartyIntegrationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ThirdPartyIntegrationDTO toDtoThirdPartyIntegrationId(ThirdPartyIntegration thirdPartyIntegration);

    @Named("operationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OperationDTO toDtoOperationId(Operation operation);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
