package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntegrationFieldMappingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrationFieldMappingDTO.class);
        IntegrationFieldMappingDTO integrationFieldMappingDTO1 = new IntegrationFieldMappingDTO();
        integrationFieldMappingDTO1.setId(1L);
        IntegrationFieldMappingDTO integrationFieldMappingDTO2 = new IntegrationFieldMappingDTO();
        assertThat(integrationFieldMappingDTO1).isNotEqualTo(integrationFieldMappingDTO2);
        integrationFieldMappingDTO2.setId(integrationFieldMappingDTO1.getId());
        assertThat(integrationFieldMappingDTO1).isEqualTo(integrationFieldMappingDTO2);
        integrationFieldMappingDTO2.setId(2L);
        assertThat(integrationFieldMappingDTO1).isNotEqualTo(integrationFieldMappingDTO2);
        integrationFieldMappingDTO1.setId(null);
        assertThat(integrationFieldMappingDTO1).isNotEqualTo(integrationFieldMappingDTO2);
    }
}