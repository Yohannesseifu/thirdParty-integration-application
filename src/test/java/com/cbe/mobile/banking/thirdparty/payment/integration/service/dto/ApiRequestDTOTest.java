package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ApiRequestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiRequestDTO.class);
        ApiRequestDTO apiRequestDTO1 = new ApiRequestDTO();
        apiRequestDTO1.setId(UUID.randomUUID());
        ApiRequestDTO apiRequestDTO2 = new ApiRequestDTO();
        assertThat(apiRequestDTO1).isNotEqualTo(apiRequestDTO2);
        apiRequestDTO2.setId(apiRequestDTO1.getId());
        assertThat(apiRequestDTO1).isEqualTo(apiRequestDTO2);
        apiRequestDTO2.setId(UUID.randomUUID());
        assertThat(apiRequestDTO1).isNotEqualTo(apiRequestDTO2);
        apiRequestDTO1.setId(null);
        assertThat(apiRequestDTO1).isNotEqualTo(apiRequestDTO2);
    }
}
