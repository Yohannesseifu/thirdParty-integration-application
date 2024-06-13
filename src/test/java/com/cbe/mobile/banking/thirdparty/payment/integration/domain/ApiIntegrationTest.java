package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegrationTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.OperationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApiIntegrationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiIntegration.class);
        ApiIntegration apiIntegration1 = getApiIntegrationSample1();
        ApiIntegration apiIntegration2 = new ApiIntegration();
        assertThat(apiIntegration1).isNotEqualTo(apiIntegration2);

        apiIntegration2.setId(apiIntegration1.getId());
        assertThat(apiIntegration1).isEqualTo(apiIntegration2);

        apiIntegration2 = getApiIntegrationSample2();
        assertThat(apiIntegration1).isNotEqualTo(apiIntegration2);
    }

    @Test
    void operationTest() {
        ApiIntegration apiIntegration = getApiIntegrationRandomSampleGenerator();
        Operation operationBack = getOperationRandomSampleGenerator();

        apiIntegration.addOperation(operationBack);
        assertThat(apiIntegration.getOperations()).containsOnly(operationBack);
        assertThat(operationBack.getApiIntegration()).isEqualTo(apiIntegration);

        apiIntegration.removeOperation(operationBack);
        assertThat(apiIntegration.getOperations()).doesNotContain(operationBack);
        assertThat(operationBack.getApiIntegration()).isNull();

        apiIntegration.operations(new HashSet<>(Set.of(operationBack)));
        assertThat(apiIntegration.getOperations()).containsOnly(operationBack);
        assertThat(operationBack.getApiIntegration()).isEqualTo(apiIntegration);

        apiIntegration.setOperations(new HashSet<>());
        assertThat(apiIntegration.getOperations()).doesNotContain(operationBack);
        assertThat(operationBack.getApiIntegration()).isNull();
    }
}
