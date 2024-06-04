package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInputAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInputTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestInputMapperTest {

    private RequestInputMapper requestInputMapper;

    @BeforeEach
    void setUp() {
        requestInputMapper = new RequestInputMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRequestInputSample1();
        var actual = requestInputMapper.toEntity(requestInputMapper.toDto(expected));
        assertRequestInputAllPropertiesEquals(expected, actual);
    }
}
