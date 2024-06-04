package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMappingTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperationTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInputTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntegrationFieldMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrationFieldMapping.class);
        IntegrationFieldMapping integrationFieldMapping1 = getIntegrationFieldMappingSample1();
        IntegrationFieldMapping integrationFieldMapping2 = new IntegrationFieldMapping();
        assertThat(integrationFieldMapping1).isNotEqualTo(integrationFieldMapping2);

        integrationFieldMapping2.setId(integrationFieldMapping1.getId());
        assertThat(integrationFieldMapping1).isEqualTo(integrationFieldMapping2);

        integrationFieldMapping2 = getIntegrationFieldMappingSample2();
        assertThat(integrationFieldMapping1).isNotEqualTo(integrationFieldMapping2);
    }

    @Test
    void integrationOperationTest() {
        IntegrationFieldMapping integrationFieldMapping = getIntegrationFieldMappingRandomSampleGenerator();
        IntegrationOperation integrationOperationBack = getIntegrationOperationRandomSampleGenerator();

        integrationFieldMapping.setIntegrationOperation(integrationOperationBack);
        assertThat(integrationFieldMapping.getIntegrationOperation()).isEqualTo(integrationOperationBack);

        integrationFieldMapping.integrationOperation(null);
        assertThat(integrationFieldMapping.getIntegrationOperation()).isNull();
    }

    @Test
    void fieldTest() {
        IntegrationFieldMapping integrationFieldMapping = getIntegrationFieldMappingRandomSampleGenerator();
        Field fieldBack = getFieldRandomSampleGenerator();

        integrationFieldMapping.setField(fieldBack);
        assertThat(integrationFieldMapping.getField()).isEqualTo(fieldBack);

        integrationFieldMapping.field(null);
        assertThat(integrationFieldMapping.getField()).isNull();
    }

    @Test
    void requestInputTest() {
        IntegrationFieldMapping integrationFieldMapping = getIntegrationFieldMappingRandomSampleGenerator();
        RequestInput requestInputBack = getRequestInputRandomSampleGenerator();

        integrationFieldMapping.setRequestInput(requestInputBack);
        assertThat(integrationFieldMapping.getRequestInput()).isEqualTo(requestInputBack);

        integrationFieldMapping.requestInput(null);
        assertThat(integrationFieldMapping.getRequestInput()).isNull();
    }
}
