package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.HeaderAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.HeaderTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeaderMapperTest {

    private HeaderMapper headerMapper;

    @BeforeEach
    void setUp() {
        headerMapper = new HeaderMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHeaderSample1();
        var actual = headerMapper.toEntity(headerMapper.toDto(expected));
        assertHeaderAllPropertiesEquals(expected, actual);
    }
}
