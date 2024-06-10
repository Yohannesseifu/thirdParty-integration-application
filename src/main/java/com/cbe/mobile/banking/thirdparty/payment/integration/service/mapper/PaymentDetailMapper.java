package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiRequestDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.OperationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.PaymentDetailDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentDetail} and its DTO {@link PaymentDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentDetailMapper extends EntityMapper<PaymentDetailDTO, PaymentDetail> {
    @Mapping(target = "apiRequest", source = "apiRequest", qualifiedByName = "apiRequestId")
    @Mapping(target = "operation", source = "operation", qualifiedByName = "operationId")
    PaymentDetailDTO toDto(PaymentDetail s);

    @Named("apiRequestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApiRequestDTO toDtoApiRequestId(ApiRequest apiRequest);

    @Named("operationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OperationDTO toDtoOperationId(Operation operation);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
