package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PaymentDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentDetailDTO.class);
        PaymentDetailDTO paymentDetailDTO1 = new PaymentDetailDTO();
        paymentDetailDTO1.setId(UUID.randomUUID());
        PaymentDetailDTO paymentDetailDTO2 = new PaymentDetailDTO();
        assertThat(paymentDetailDTO1).isNotEqualTo(paymentDetailDTO2);
        paymentDetailDTO2.setId(paymentDetailDTO1.getId());
        assertThat(paymentDetailDTO1).isEqualTo(paymentDetailDTO2);
        paymentDetailDTO2.setId(UUID.randomUUID());
        assertThat(paymentDetailDTO1).isNotEqualTo(paymentDetailDTO2);
        paymentDetailDTO1.setId(null);
        assertThat(paymentDetailDTO1).isNotEqualTo(paymentDetailDTO2);
    }
}
