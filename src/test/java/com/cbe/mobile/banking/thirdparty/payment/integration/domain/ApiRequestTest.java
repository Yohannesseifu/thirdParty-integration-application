package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequestTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.HeaderTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetailTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApiRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiRequest.class);
        ApiRequest apiRequest1 = getApiRequestSample1();
        ApiRequest apiRequest2 = new ApiRequest();
        assertThat(apiRequest1).isNotEqualTo(apiRequest2);

        apiRequest2.setId(apiRequest1.getId());
        assertThat(apiRequest1).isEqualTo(apiRequest2);

        apiRequest2 = getApiRequestSample2();
        assertThat(apiRequest1).isNotEqualTo(apiRequest2);
    }

    @Test
    void headersTest() {
        ApiRequest apiRequest = getApiRequestRandomSampleGenerator();
        Header headerBack = getHeaderRandomSampleGenerator();

        apiRequest.addHeaders(headerBack);
        assertThat(apiRequest.getHeaders()).containsOnly(headerBack);
        assertThat(headerBack.getApiRequest()).isEqualTo(apiRequest);

        apiRequest.removeHeaders(headerBack);
        assertThat(apiRequest.getHeaders()).doesNotContain(headerBack);
        assertThat(headerBack.getApiRequest()).isNull();

        apiRequest.headers(new HashSet<>(Set.of(headerBack)));
        assertThat(apiRequest.getHeaders()).containsOnly(headerBack);
        assertThat(headerBack.getApiRequest()).isEqualTo(apiRequest);

        apiRequest.setHeaders(new HashSet<>());
        assertThat(apiRequest.getHeaders()).doesNotContain(headerBack);
        assertThat(headerBack.getApiRequest()).isNull();
    }

    @Test
    void paymentDetailTest() {
        ApiRequest apiRequest = getApiRequestRandomSampleGenerator();
        PaymentDetail paymentDetailBack = getPaymentDetailRandomSampleGenerator();

        apiRequest.setPaymentDetail(paymentDetailBack);
        assertThat(apiRequest.getPaymentDetail()).isEqualTo(paymentDetailBack);
        assertThat(paymentDetailBack.getApiRequest()).isEqualTo(apiRequest);

        apiRequest.paymentDetail(null);
        assertThat(apiRequest.getPaymentDetail()).isNull();
        assertThat(paymentDetailBack.getApiRequest()).isNull();
    }
}
