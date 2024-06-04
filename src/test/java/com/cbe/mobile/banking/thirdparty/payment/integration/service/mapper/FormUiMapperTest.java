package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUiAsserts.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUiTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormUiMapperTest {

    private FormUiMapper formUiMapper;

    @BeforeEach
    void setUp() {
        formUiMapper = new FormUiMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFormUiSample1();
        var actual = formUiMapper.toEntity(formUiMapper.toDto(expected));
        assertFormUiAllPropertiesEquals(expected, actual);
    }
}
