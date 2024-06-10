package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class RequestInputDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestInputDTO.class);
        RequestInputDTO requestInputDTO1 = new RequestInputDTO();
        requestInputDTO1.setId(UUID.randomUUID());
        RequestInputDTO requestInputDTO2 = new RequestInputDTO();
        assertThat(requestInputDTO1).isNotEqualTo(requestInputDTO2);
        requestInputDTO2.setId(requestInputDTO1.getId());
        assertThat(requestInputDTO1).isEqualTo(requestInputDTO2);
        requestInputDTO2.setId(UUID.randomUUID());
        assertThat(requestInputDTO1).isNotEqualTo(requestInputDTO2);
        requestInputDTO1.setId(null);
        assertThat(requestInputDTO1).isNotEqualTo(requestInputDTO2);
    }
}
