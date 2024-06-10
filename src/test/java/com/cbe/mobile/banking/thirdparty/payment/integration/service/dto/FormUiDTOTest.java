package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class FormUiDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormUiDTO.class);
        FormUiDTO formUiDTO1 = new FormUiDTO();
        formUiDTO1.setId(UUID.randomUUID());
        FormUiDTO formUiDTO2 = new FormUiDTO();
        assertThat(formUiDTO1).isNotEqualTo(formUiDTO2);
        formUiDTO2.setId(formUiDTO1.getId());
        assertThat(formUiDTO1).isEqualTo(formUiDTO2);
        formUiDTO2.setId(UUID.randomUUID());
        assertThat(formUiDTO1).isNotEqualTo(formUiDTO2);
        formUiDTO1.setId(null);
        assertThat(formUiDTO1).isNotEqualTo(formUiDTO2);
    }
}
