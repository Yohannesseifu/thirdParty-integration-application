package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiIntegrationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.OperationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Operation} and its DTO {@link OperationDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperationMapper extends EntityMapper<OperationDTO, Operation> {
    @Mapping(target = "apiIntegration", source = "apiIntegration", qualifiedByName = "apiIntegrationId")
    OperationDTO toDto(Operation s);

    @Named("apiIntegrationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApiIntegrationDTO toDtoApiIntegrationId(ApiIntegration apiIntegration);
}
