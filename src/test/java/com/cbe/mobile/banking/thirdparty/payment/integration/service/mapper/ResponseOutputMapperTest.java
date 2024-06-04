package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutputAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutputTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResponseOutputMapperTest {

    private ResponseOutputMapper responseOutputMapper;

    @BeforeEach
    void setUp() {
        responseOutputMapper = new ResponseOutputMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getResponseOutputSample1();
        var actual = responseOutputMapper.toEntity(responseOutputMapper.toDto(expected));
        assertResponseOutputAllPropertiesEquals(expected, actual);
    }
}
