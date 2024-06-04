package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegrationAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegrationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApiIntegrationMapperTest {

    private ApiIntegrationMapper apiIntegrationMapper;

    @BeforeEach
    void setUp() {
        apiIntegrationMapper = new ApiIntegrationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getApiIntegrationSample1();
        var actual = apiIntegrationMapper.toEntity(apiIntegrationMapper.toDto(expected));
        assertApiIntegrationAllPropertiesEquals(expected, actual);
    }
}
