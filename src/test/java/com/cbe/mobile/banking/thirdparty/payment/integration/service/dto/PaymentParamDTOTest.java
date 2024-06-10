package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PaymentParamDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentParamDTO.class);
        PaymentParamDTO paymentParamDTO1 = new PaymentParamDTO();
        paymentParamDTO1.setId(UUID.randomUUID());
        PaymentParamDTO paymentParamDTO2 = new PaymentParamDTO();
        assertThat(paymentParamDTO1).isNotEqualTo(paymentParamDTO2);
        paymentParamDTO2.setId(paymentParamDTO1.getId());
        assertThat(paymentParamDTO1).isEqualTo(paymentParamDTO2);
        paymentParamDTO2.setId(UUID.randomUUID());
        assertThat(paymentParamDTO1).isNotEqualTo(paymentParamDTO2);
        paymentParamDTO1.setId(null);
        assertThat(paymentParamDTO1).isNotEqualTo(paymentParamDTO2);
    }
}
