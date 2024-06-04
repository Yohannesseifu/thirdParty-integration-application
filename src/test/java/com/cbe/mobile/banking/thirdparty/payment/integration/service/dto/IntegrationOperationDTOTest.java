package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntegrationOperationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrationOperationDTO.class);
        IntegrationOperationDTO integrationOperationDTO1 = new IntegrationOperationDTO();
        integrationOperationDTO1.setId(1L);
        IntegrationOperationDTO integrationOperationDTO2 = new IntegrationOperationDTO();
        assertThat(integrationOperationDTO1).isNotEqualTo(integrationOperationDTO2);
        integrationOperationDTO2.setId(integrationOperationDTO1.getId());
        assertThat(integrationOperationDTO1).isEqualTo(integrationOperationDTO2);
        integrationOperationDTO2.setId(2L);
        assertThat(integrationOperationDTO1).isNotEqualTo(integrationOperationDTO2);
        integrationOperationDTO1.setId(null);
        assertThat(integrationOperationDTO1).isNotEqualTo(integrationOperationDTO2);
    }
}
