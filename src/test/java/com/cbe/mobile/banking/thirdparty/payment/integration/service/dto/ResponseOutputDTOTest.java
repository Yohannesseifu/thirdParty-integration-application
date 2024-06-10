package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ResponseOutputDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponseOutputDTO.class);
        ResponseOutputDTO responseOutputDTO1 = new ResponseOutputDTO();
        responseOutputDTO1.setId(UUID.randomUUID());
        ResponseOutputDTO responseOutputDTO2 = new ResponseOutputDTO();
        assertThat(responseOutputDTO1).isNotEqualTo(responseOutputDTO2);
        responseOutputDTO2.setId(responseOutputDTO1.getId());
        assertThat(responseOutputDTO1).isEqualTo(responseOutputDTO2);
        responseOutputDTO2.setId(UUID.randomUUID());
        assertThat(responseOutputDTO1).isNotEqualTo(responseOutputDTO2);
        responseOutputDTO1.setId(null);
        assertThat(responseOutputDTO1).isNotEqualTo(responseOutputDTO2);
    }
}
