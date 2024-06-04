package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApiIntegrationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiIntegrationDTO.class);
        ApiIntegrationDTO apiIntegrationDTO1 = new ApiIntegrationDTO();
        apiIntegrationDTO1.setId(1L);
        ApiIntegrationDTO apiIntegrationDTO2 = new ApiIntegrationDTO();
        assertThat(apiIntegrationDTO1).isNotEqualTo(apiIntegrationDTO2);
        apiIntegrationDTO2.setId(apiIntegrationDTO1.getId());
        assertThat(apiIntegrationDTO1).isEqualTo(apiIntegrationDTO2);
        apiIntegrationDTO2.setId(2L);
        assertThat(apiIntegrationDTO1).isNotEqualTo(apiIntegrationDTO2);
        apiIntegrationDTO1.setId(null);
        assertThat(apiIntegrationDTO1).isNotEqualTo(apiIntegrationDTO2);
    }
}
