package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.OperationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.RequestInputDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestInput} and its DTO {@link RequestInputDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestInputMapper extends EntityMapper<RequestInputDTO, RequestInput> {
    @Mapping(target = "operation", source = "operation", qualifiedByName = "operationId")
    RequestInputDTO toDto(RequestInput s);

    @Named("operationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OperationDTO toDtoOperationId(Operation operation);
}
