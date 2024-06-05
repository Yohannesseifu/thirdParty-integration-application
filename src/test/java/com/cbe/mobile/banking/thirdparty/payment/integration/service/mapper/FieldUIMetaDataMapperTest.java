package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaDataAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaDataTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldUIMetaDataMapperTest {

    private FieldUIMetaDataMapper fieldUIMetaDataMapper;

    @BeforeEach
    void setUp() {
        fieldUIMetaDataMapper = new FieldUIMetaDataMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFieldUIMetaDataSample1();
        var actual = fieldUIMetaDataMapper.toEntity(fieldUIMetaDataMapper.toDto(expected));
        assertFieldUIMetaDataAllPropertiesEquals(expected, actual);
    }
}
