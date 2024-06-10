package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldMapperTest {

    private FieldMapper fieldMapper;

    @BeforeEach
    void setUp() {
        fieldMapper = new FieldMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFieldSample1();
        var actual = fieldMapper.toEntity(fieldMapper.toDto(expected));
        assertFieldAllPropertiesEquals(expected, actual);
    }
}
