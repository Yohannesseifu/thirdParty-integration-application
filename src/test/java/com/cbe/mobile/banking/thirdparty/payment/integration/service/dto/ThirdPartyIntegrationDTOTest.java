package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThirdPartyIntegrationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdPartyIntegrationDTO.class);
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO1 = new ThirdPartyIntegrationDTO();
        thirdPartyIntegrationDTO1.setId(1L);
        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO2 = new ThirdPartyIntegrationDTO();
        assertThat(thirdPartyIntegrationDTO1).isNotEqualTo(thirdPartyIntegrationDTO2);
        thirdPartyIntegrationDTO2.setId(thirdPartyIntegrationDTO1.getId());
        assertThat(thirdPartyIntegrationDTO1).isEqualTo(thirdPartyIntegrationDTO2);
        thirdPartyIntegrationDTO2.setId(2L);
        assertThat(thirdPartyIntegrationDTO1).isNotEqualTo(thirdPartyIntegrationDTO2);
        thirdPartyIntegrationDTO1.setId(null);
        assertThat(thirdPartyIntegrationDTO1).isNotEqualTo(thirdPartyIntegrationDTO2);
    }
}
