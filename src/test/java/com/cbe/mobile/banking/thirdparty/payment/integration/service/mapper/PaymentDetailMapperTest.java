package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetailAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetailTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentDetailMapperTest {

    private PaymentDetailMapper paymentDetailMapper;

    @BeforeEach
    void setUp() {
        paymentDetailMapper = new PaymentDetailMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPaymentDetailSample1();
        var actual = paymentDetailMapper.toEntity(paymentDetailMapper.toDto(expected));
        assertPaymentDetailAllPropertiesEquals(expected, actual);
    }
}
