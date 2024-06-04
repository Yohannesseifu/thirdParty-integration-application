package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMappingTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.OperationTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInputTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RequestInputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestInput.class);
        RequestInput requestInput1 = getRequestInputSample1();
        RequestInput requestInput2 = new RequestInput();
        assertThat(requestInput1).isNotEqualTo(requestInput2);

        requestInput2.setId(requestInput1.getId());
        assertThat(requestInput1).isEqualTo(requestInput2);

        requestInput2 = getRequestInputSample2();
        assertThat(requestInput1).isNotEqualTo(requestInput2);
    }

    @Test
    void operationTest() {
        RequestInput requestInput = getRequestInputRandomSampleGenerator();
        Operation operationBack = getOperationRandomSampleGenerator();

        requestInput.setOperation(operationBack);
        assertThat(requestInput.getOperation()).isEqualTo(operationBack);

        requestInput.operation(null);
        assertThat(requestInput.getOperation()).isNull();
    }

    @Test
    void integrationFieldMappingTest() {
        RequestInput requestInput = getRequestInputRandomSampleGenerator();
        IntegrationFieldMapping integrationFieldMappingBack = getIntegrationFieldMappingRandomSampleGenerator();

        requestInput.addIntegrationFieldMapping(integrationFieldMappingBack);
        assertThat(requestInput.getIntegrationFieldMappings()).containsOnly(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getRequestInput()).isEqualTo(requestInput);

        requestInput.removeIntegrationFieldMapping(integrationFieldMappingBack);
        assertThat(requestInput.getIntegrationFieldMappings()).doesNotContain(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getRequestInput()).isNull();

        requestInput.integrationFieldMappings(new HashSet<>(Set.of(integrationFieldMappingBack)));
        assertThat(requestInput.getIntegrationFieldMappings()).containsOnly(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getRequestInput()).isEqualTo(requestInput);

        requestInput.setIntegrationFieldMappings(new HashSet<>());
        assertThat(requestInput.getIntegrationFieldMappings()).doesNotContain(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getRequestInput()).isNull();
    }
}
