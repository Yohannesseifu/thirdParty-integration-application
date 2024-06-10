package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class HeaderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HeaderDTO.class);
        HeaderDTO headerDTO1 = new HeaderDTO();
        headerDTO1.setId(UUID.randomUUID());
        HeaderDTO headerDTO2 = new HeaderDTO();
        assertThat(headerDTO1).isNotEqualTo(headerDTO2);
        headerDTO2.setId(headerDTO1.getId());
        assertThat(headerDTO1).isEqualTo(headerDTO2);
        headerDTO2.setId(UUID.randomUUID());
        assertThat(headerDTO1).isNotEqualTo(headerDTO2);
        headerDTO1.setId(null);
        assertThat(headerDTO1).isNotEqualTo(headerDTO2);
    }
}
