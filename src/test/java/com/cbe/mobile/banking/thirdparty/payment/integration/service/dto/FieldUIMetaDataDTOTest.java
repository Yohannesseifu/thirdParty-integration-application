package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldUIMetaDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldUIMetaDataDTO.class);
        FieldUIMetaDataDTO fieldUIMetaDataDTO1 = new FieldUIMetaDataDTO();
        fieldUIMetaDataDTO1.setId(1L);
        FieldUIMetaDataDTO fieldUIMetaDataDTO2 = new FieldUIMetaDataDTO();
        assertThat(fieldUIMetaDataDTO1).isNotEqualTo(fieldUIMetaDataDTO2);
        fieldUIMetaDataDTO2.setId(fieldUIMetaDataDTO1.getId());
        assertThat(fieldUIMetaDataDTO1).isEqualTo(fieldUIMetaDataDTO2);
        fieldUIMetaDataDTO2.setId(2L);
        assertThat(fieldUIMetaDataDTO1).isNotEqualTo(fieldUIMetaDataDTO2);
        fieldUIMetaDataDTO1.setId(null);
        assertThat(fieldUIMetaDataDTO1).isNotEqualTo(fieldUIMetaDataDTO2);
    }
}
