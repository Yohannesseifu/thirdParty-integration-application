package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegrationAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegrationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThirdPartyIntegrationMapperTest {

    private ThirdPartyIntegrationMapper thirdPartyIntegrationMapper;

    @BeforeEach
    void setUp() {
        thirdPartyIntegrationMapper = new ThirdPartyIntegrationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getThirdPartyIntegrationSample1();
        var actual = thirdPartyIntegrationMapper.toEntity(thirdPartyIntegrationMapper.toDto(expected));
        assertThirdPartyIntegrationAllPropertiesEquals(expected, actual);
    }
}
