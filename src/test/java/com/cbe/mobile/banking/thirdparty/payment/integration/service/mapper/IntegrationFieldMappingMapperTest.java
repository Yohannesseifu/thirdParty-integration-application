package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMappingAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMappingTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegrationFieldMappingMapperTest {

    private IntegrationFieldMappingMapper integrationFieldMappingMapper;

    @BeforeEach
    void setUp() {
        integrationFieldMappingMapper = new IntegrationFieldMappingMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getIntegrationFieldMappingSample1();
        var actual = integrationFieldMappingMapper.toEntity(integrationFieldMappingMapper.toDto(expected));
        assertIntegrationFieldMappingAllPropertiesEquals(expected, actual);
    }
}
