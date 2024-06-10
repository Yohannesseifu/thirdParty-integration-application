package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.OperationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ResponseOutputDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResponseOutput} and its DTO {@link ResponseOutputDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResponseOutputMapper extends EntityMapper<ResponseOutputDTO, ResponseOutput> {
    @Mapping(target = "operation", source = "operation", qualifiedByName = "operationId")
    ResponseOutputDTO toDto(ResponseOutput s);

    @Named("operationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OperationDTO toDtoOperationId(Operation operation);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
