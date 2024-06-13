package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetailTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentParamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentParam.class);
        PaymentParam paymentParam1 = getPaymentParamSample1();
        PaymentParam paymentParam2 = new PaymentParam();
        assertThat(paymentParam1).isNotEqualTo(paymentParam2);

        paymentParam2.setId(paymentParam1.getId());
        assertThat(paymentParam1).isEqualTo(paymentParam2);

        paymentParam2 = getPaymentParamSample2();
        assertThat(paymentParam1).isNotEqualTo(paymentParam2);
    }

    @Test
    void paymentDetailTest() {
        PaymentParam paymentParam = getPaymentParamRandomSampleGenerator();
        PaymentDetail paymentDetailBack = getPaymentDetailRandomSampleGenerator();

        paymentParam.setPaymentDetail(paymentDetailBack);
        assertThat(paymentParam.getPaymentDetail()).isEqualTo(paymentDetailBack);

        paymentParam.paymentDetail(null);
        assertThat(paymentParam.getPaymentDetail()).isNull();
    }
}
