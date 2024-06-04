package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUiTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegrationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FormUiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormUi.class);
        FormUi formUi1 = getFormUiSample1();
        FormUi formUi2 = new FormUi();
        assertThat(formUi1).isNotEqualTo(formUi2);

        formUi2.setId(formUi1.getId());
        assertThat(formUi1).isEqualTo(formUi2);

        formUi2 = getFormUiSample2();
        assertThat(formUi1).isNotEqualTo(formUi2);
    }

    @Test
    void fieldTest() {
        FormUi formUi = getFormUiRandomSampleGenerator();
        Field fieldBack = getFieldRandomSampleGenerator();

        formUi.addField(fieldBack);
        assertThat(formUi.getFields()).containsOnly(fieldBack);
        assertThat(fieldBack.getFormUi()).isEqualTo(formUi);

        formUi.removeField(fieldBack);
        assertThat(formUi.getFields()).doesNotContain(fieldBack);
        assertThat(fieldBack.getFormUi()).isNull();

        formUi.fields(new HashSet<>(Set.of(fieldBack)));
        assertThat(formUi.getFields()).containsOnly(fieldBack);
        assertThat(fieldBack.getFormUi()).isEqualTo(formUi);

        formUi.setFields(new HashSet<>());
        assertThat(formUi.getFields()).doesNotContain(fieldBack);
        assertThat(fieldBack.getFormUi()).isNull();
    }

    @Test
    void thirdPartyIntegrationTest() {
        FormUi formUi = getFormUiRandomSampleGenerator();
        ThirdPartyIntegration thirdPartyIntegrationBack = getThirdPartyIntegrationRandomSampleGenerator();

        formUi.setThirdPartyIntegration(thirdPartyIntegrationBack);
        assertThat(formUi.getThirdPartyIntegration()).isEqualTo(thirdPartyIntegrationBack);

        formUi.thirdPartyIntegration(null);
        assertThat(formUi.getThirdPartyIntegration()).isNull();
    }
}
