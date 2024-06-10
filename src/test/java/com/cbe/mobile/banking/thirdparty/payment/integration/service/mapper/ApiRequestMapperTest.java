package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequestAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequestTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApiRequestMapperTest {

    private ApiRequestMapper apiRequestMapper;

    @BeforeEach
    void setUp() {
        apiRequestMapper = new ApiRequestMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getApiRequestSample1();
        var actual = apiRequestMapper.toEntity(apiRequestMapper.toDto(expected));
        assertApiRequestAllPropertiesEquals(expected, actual);
    }
}
