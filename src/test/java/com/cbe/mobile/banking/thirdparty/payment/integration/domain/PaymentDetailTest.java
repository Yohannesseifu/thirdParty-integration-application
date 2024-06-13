package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequestTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.OperationTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetailTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PaymentDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentDetail.class);
        PaymentDetail paymentDetail1 = getPaymentDetailSample1();
        PaymentDetail paymentDetail2 = new PaymentDetail();
        assertThat(paymentDetail1).isNotEqualTo(paymentDetail2);

        paymentDetail2.setId(paymentDetail1.getId());
        assertThat(paymentDetail1).isEqualTo(paymentDetail2);

        paymentDetail2 = getPaymentDetailSample2();
        assertThat(paymentDetail1).isNotEqualTo(paymentDetail2);
    }

    @Test
    void apiRequestTest() {
        PaymentDetail paymentDetail = getPaymentDetailRandomSampleGenerator();
        ApiRequest apiRequestBack = getApiRequestRandomSampleGenerator();

        paymentDetail.setApiRequest(apiRequestBack);
        assertThat(paymentDetail.getApiRequest()).isEqualTo(apiRequestBack);

        paymentDetail.apiRequest(null);
        assertThat(paymentDetail.getApiRequest()).isNull();
    }

    @Test
    void paymentParamsTest() {
        PaymentDetail paymentDetail = getPaymentDetailRandomSampleGenerator();
        PaymentParam paymentParamBack = getPaymentParamRandomSampleGenerator();

        paymentDetail.addPaymentParams(paymentParamBack);
        assertThat(paymentDetail.getPaymentParams()).containsOnly(paymentParamBack);
        assertThat(paymentParamBack.getPaymentDetail()).isEqualTo(paymentDetail);

        paymentDetail.removePaymentParams(paymentParamBack);
        assertThat(paymentDetail.getPaymentParams()).doesNotContain(paymentParamBack);
        assertThat(paymentParamBack.getPaymentDetail()).isNull();

        paymentDetail.paymentParams(new HashSet<>(Set.of(paymentParamBack)));
        assertThat(paymentDetail.getPaymentParams()).containsOnly(paymentParamBack);
        assertThat(paymentParamBack.getPaymentDetail()).isEqualTo(paymentDetail);

        paymentDetail.setPaymentParams(new HashSet<>());
        assertThat(paymentDetail.getPaymentParams()).doesNotContain(paymentParamBack);
        assertThat(paymentParamBack.getPaymentDetail()).isNull();
    }

    @Test
    void operationTest() {
        PaymentDetail paymentDetail = getPaymentDetailRandomSampleGenerator();
        Operation operationBack = getOperationRandomSampleGenerator();

        paymentDetail.setOperation(operationBack);
        assertThat(paymentDetail.getOperation()).isEqualTo(operationBack);

        paymentDetail.operation(null);
        assertThat(paymentDetail.getOperation()).isNull();
    }
}
