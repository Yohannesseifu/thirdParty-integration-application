package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegrationTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.OperationTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInputTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutputTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operation.class);
        Operation operation1 = getOperationSample1();
        Operation operation2 = new Operation();
        assertThat(operation1).isNotEqualTo(operation2);

        operation2.setId(operation1.getId());
        assertThat(operation1).isEqualTo(operation2);

        operation2 = getOperationSample2();
        assertThat(operation1).isNotEqualTo(operation2);
    }

    @Test
    void apiIntegrationTest() {
        Operation operation = getOperationRandomSampleGenerator();
        ApiIntegration apiIntegrationBack = getApiIntegrationRandomSampleGenerator();

        operation.setApiIntegration(apiIntegrationBack);
        assertThat(operation.getApiIntegration()).isEqualTo(apiIntegrationBack);

        operation.apiIntegration(null);
        assertThat(operation.getApiIntegration()).isNull();
    }

    @Test
    void responseOutputTest() {
        Operation operation = getOperationRandomSampleGenerator();
        ResponseOutput responseOutputBack = getResponseOutputRandomSampleGenerator();

        operation.addResponseOutput(responseOutputBack);
        assertThat(operation.getResponseOutputs()).containsOnly(responseOutputBack);
        assertThat(responseOutputBack.getOperation()).isEqualTo(operation);

        operation.removeResponseOutput(responseOutputBack);
        assertThat(operation.getResponseOutputs()).doesNotContain(responseOutputBack);
        assertThat(responseOutputBack.getOperation()).isNull();

        operation.responseOutputs(new HashSet<>(Set.of(responseOutputBack)));
        assertThat(operation.getResponseOutputs()).containsOnly(responseOutputBack);
        assertThat(responseOutputBack.getOperation()).isEqualTo(operation);

        operation.setResponseOutputs(new HashSet<>());
        assertThat(operation.getResponseOutputs()).doesNotContain(responseOutputBack);
        assertThat(responseOutputBack.getOperation()).isNull();
    }

    @Test
    void requestInputTest() {
        Operation operation = getOperationRandomSampleGenerator();
        RequestInput requestInputBack = getRequestInputRandomSampleGenerator();

        operation.addRequestInput(requestInputBack);
        assertThat(operation.getRequestInputs()).containsOnly(requestInputBack);
        assertThat(requestInputBack.getOperation()).isEqualTo(operation);

        operation.removeRequestInput(requestInputBack);
        assertThat(operation.getRequestInputs()).doesNotContain(requestInputBack);
        assertThat(requestInputBack.getOperation()).isNull();

        operation.requestInputs(new HashSet<>(Set.of(requestInputBack)));
        assertThat(operation.getRequestInputs()).containsOnly(requestInputBack);
        assertThat(requestInputBack.getOperation()).isEqualTo(operation);

        operation.setRequestInputs(new HashSet<>());
        assertThat(operation.getRequestInputs()).doesNotContain(requestInputBack);
        assertThat(requestInputBack.getOperation()).isNull();
    }
}
