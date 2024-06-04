package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaDataTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUiTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMappingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FieldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Field.class);
        Field field1 = getFieldSample1();
        Field field2 = new Field();
        assertThat(field1).isNotEqualTo(field2);

        field2.setId(field1.getId());
        assertThat(field1).isEqualTo(field2);

        field2 = getFieldSample2();
        assertThat(field1).isNotEqualTo(field2);
    }

    @Test
    void formUiTest() {
        Field field = getFieldRandomSampleGenerator();
        FormUi formUiBack = getFormUiRandomSampleGenerator();

        field.setFormUi(formUiBack);
        assertThat(field.getFormUi()).isEqualTo(formUiBack);

        field.formUi(null);
        assertThat(field.getFormUi()).isNull();
    }

    @Test
    void metaDataTest() {
        Field field = getFieldRandomSampleGenerator();
        FieldUIMetaData fieldUIMetaDataBack = getFieldUIMetaDataRandomSampleGenerator();

        field.setMetaData(fieldUIMetaDataBack);
        assertThat(field.getMetaData()).isEqualTo(fieldUIMetaDataBack);

        field.metaData(null);
        assertThat(field.getMetaData()).isNull();
    }

    @Test
    void integrationFieldMappingTest() {
        Field field = getFieldRandomSampleGenerator();
        IntegrationFieldMapping integrationFieldMappingBack = getIntegrationFieldMappingRandomSampleGenerator();

        field.addIntegrationFieldMapping(integrationFieldMappingBack);
        assertThat(field.getIntegrationFieldMappings()).containsOnly(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getField()).isEqualTo(field);

        field.removeIntegrationFieldMapping(integrationFieldMappingBack);
        assertThat(field.getIntegrationFieldMappings()).doesNotContain(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getField()).isNull();

        field.integrationFieldMappings(new HashSet<>(Set.of(integrationFieldMappingBack)));
        assertThat(field.getIntegrationFieldMappings()).containsOnly(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getField()).isEqualTo(field);

        field.setIntegrationFieldMappings(new HashSet<>());
        assertThat(field.getIntegrationFieldMappings()).doesNotContain(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getField()).isNull();
    }
}
