package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.OperationTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutputTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponseOutputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponseOutput.class);
        ResponseOutput responseOutput1 = getResponseOutputSample1();
        ResponseOutput responseOutput2 = new ResponseOutput();
        assertThat(responseOutput1).isNotEqualTo(responseOutput2);

        responseOutput2.setId(responseOutput1.getId());
        assertThat(responseOutput1).isEqualTo(responseOutput2);

        responseOutput2 = getResponseOutputSample2();
        assertThat(responseOutput1).isNotEqualTo(responseOutput2);
    }

    @Test
    void operationTest() {
        ResponseOutput responseOutput = getResponseOutputRandomSampleGenerator();
        Operation operationBack = getOperationRandomSampleGenerator();

        responseOutput.setOperation(operationBack);
        assertThat(responseOutput.getOperation()).isEqualTo(operationBack);

        responseOutput.operation(null);
        assertThat(responseOutput.getOperation()).isNull();
    }
}
