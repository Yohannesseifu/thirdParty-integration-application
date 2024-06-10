package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParamAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParamTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentParamMapperTest {

    private PaymentParamMapper paymentParamMapper;

    @BeforeEach
    void setUp() {
        paymentParamMapper = new PaymentParamMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPaymentParamSample1();
        var actual = paymentParamMapper.toEntity(paymentParamMapper.toDto(expected));
        assertPaymentParamAllPropertiesEquals(expected, actual);
    }
}
