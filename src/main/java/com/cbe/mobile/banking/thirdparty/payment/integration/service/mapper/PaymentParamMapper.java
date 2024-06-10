package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.PaymentDetailDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.PaymentParamDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentParam} and its DTO {@link PaymentParamDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentParamMapper extends EntityMapper<PaymentParamDTO, PaymentParam> {
    @Mapping(target = "paymentDetail", source = "paymentDetail", qualifiedByName = "paymentDetailId")
    PaymentParamDTO toDto(PaymentParam s);

    @Named("paymentDetailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentDetailDTO toDtoPaymentDetailId(PaymentDetail paymentDetail);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
