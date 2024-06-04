package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMappingTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperationTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.OperationTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegrationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class IntegrationOperationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrationOperation.class);
        IntegrationOperation integrationOperation1 = getIntegrationOperationSample1();
        IntegrationOperation integrationOperation2 = new IntegrationOperation();
        assertThat(integrationOperation1).isNotEqualTo(integrationOperation2);

        integrationOperation2.setId(integrationOperation1.getId());
        assertThat(integrationOperation1).isEqualTo(integrationOperation2);

        integrationOperation2 = getIntegrationOperationSample2();
        assertThat(integrationOperation1).isNotEqualTo(integrationOperation2);
    }

    @Test
    void thirdPartyIntegrationTest() {
        IntegrationOperation integrationOperation = getIntegrationOperationRandomSampleGenerator();
        ThirdPartyIntegration thirdPartyIntegrationBack = getThirdPartyIntegrationRandomSampleGenerator();

        integrationOperation.setThirdPartyIntegration(thirdPartyIntegrationBack);
        assertThat(integrationOperation.getThirdPartyIntegration()).isEqualTo(thirdPartyIntegrationBack);

        integrationOperation.thirdPartyIntegration(null);
        assertThat(integrationOperation.getThirdPartyIntegration()).isNull();
    }

    @Test
    void operationTest() {
        IntegrationOperation integrationOperation = getIntegrationOperationRandomSampleGenerator();
        Operation operationBack = getOperationRandomSampleGenerator();

        integrationOperation.setOperation(operationBack);
        assertThat(integrationOperation.getOperation()).isEqualTo(operationBack);

        integrationOperation.operation(null);
        assertThat(integrationOperation.getOperation()).isNull();
    }

    @Test
    void integrationFieldMappingTest() {
        IntegrationOperation integrationOperation = getIntegrationOperationRandomSampleGenerator();
        IntegrationFieldMapping integrationFieldMappingBack = getIntegrationFieldMappingRandomSampleGenerator();

        integrationOperation.addIntegrationFieldMapping(integrationFieldMappingBack);
        assertThat(integrationOperation.getIntegrationFieldMappings()).containsOnly(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getIntegrationOperation()).isEqualTo(integrationOperation);

        integrationOperation.removeIntegrationFieldMapping(integrationFieldMappingBack);
        assertThat(integrationOperation.getIntegrationFieldMappings()).doesNotContain(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getIntegrationOperation()).isNull();

        integrationOperation.integrationFieldMappings(new HashSet<>(Set.of(integrationFieldMappingBack)));
        assertThat(integrationOperation.getIntegrationFieldMappings()).containsOnly(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getIntegrationOperation()).isEqualTo(integrationOperation);

        integrationOperation.setIntegrationFieldMappings(new HashSet<>());
        assertThat(integrationOperation.getIntegrationFieldMappings()).doesNotContain(integrationFieldMappingBack);
        assertThat(integrationFieldMappingBack.getIntegrationOperation()).isNull();
    }
}
