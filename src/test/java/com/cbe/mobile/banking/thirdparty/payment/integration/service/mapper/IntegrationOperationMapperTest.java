package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperationAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegrationOperationMapperTest {

    private IntegrationOperationMapper integrationOperationMapper;

    @BeforeEach
    void setUp() {
        integrationOperationMapper = new IntegrationOperationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getIntegrationOperationSample1();
        var actual = integrationOperationMapper.toEntity(integrationOperationMapper.toDto(expected));
        assertIntegrationOperationAllPropertiesEquals(expected, actual);
    }
}
