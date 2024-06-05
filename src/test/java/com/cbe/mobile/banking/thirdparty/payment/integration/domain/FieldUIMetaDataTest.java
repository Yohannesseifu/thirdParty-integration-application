package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaDataTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FieldUIMetaDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldUIMetaData.class);
        FieldUIMetaData fieldUIMetaData1 = getFieldUIMetaDataSample1();
        FieldUIMetaData fieldUIMetaData2 = new FieldUIMetaData();
        assertThat(fieldUIMetaData1).isNotEqualTo(fieldUIMetaData2);

        fieldUIMetaData2.setId(fieldUIMetaData1.getId());
        assertThat(fieldUIMetaData1).isEqualTo(fieldUIMetaData2);

        fieldUIMetaData2 = getFieldUIMetaDataSample2();
        assertThat(fieldUIMetaData1).isNotEqualTo(fieldUIMetaData2);
    }

    @Test
    void fieldTest() {
        FieldUIMetaData fieldUIMetaData = getFieldUIMetaDataRandomSampleGenerator();
        Field fieldBack = getFieldRandomSampleGenerator();

        fieldUIMetaData.addField(fieldBack);
        assertThat(fieldUIMetaData.getFields()).containsOnly(fieldBack);
        assertThat(fieldBack.getMetaData()).isEqualTo(fieldUIMetaData);

        fieldUIMetaData.removeField(fieldBack);
        assertThat(fieldUIMetaData.getFields()).doesNotContain(fieldBack);
        assertThat(fieldBack.getMetaData()).isNull();

        fieldUIMetaData.fields(new HashSet<>(Set.of(fieldBack)));
        assertThat(fieldUIMetaData.getFields()).containsOnly(fieldBack);
        assertThat(fieldBack.getMetaData()).isEqualTo(fieldUIMetaData);

        fieldUIMetaData.setFields(new HashSet<>());
        assertThat(fieldUIMetaData.getFields()).doesNotContain(fieldBack);
        assertThat(fieldBack.getMetaData()).isNull();
    }
}
