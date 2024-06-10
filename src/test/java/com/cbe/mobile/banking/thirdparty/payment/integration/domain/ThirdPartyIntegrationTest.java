package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUiTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperationTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.MenuTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegrationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ThirdPartyIntegrationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdPartyIntegration.class);
        ThirdPartyIntegration thirdPartyIntegration1 = getThirdPartyIntegrationSample1();
        ThirdPartyIntegration thirdPartyIntegration2 = new ThirdPartyIntegration();
        assertThat(thirdPartyIntegration1).isNotEqualTo(thirdPartyIntegration2);

        thirdPartyIntegration2.setId(thirdPartyIntegration1.getId());
        assertThat(thirdPartyIntegration1).isEqualTo(thirdPartyIntegration2);

        thirdPartyIntegration2 = getThirdPartyIntegrationSample2();
        assertThat(thirdPartyIntegration1).isNotEqualTo(thirdPartyIntegration2);
    }

    @Test
    void formUiTest() {
        ThirdPartyIntegration thirdPartyIntegration = getThirdPartyIntegrationRandomSampleGenerator();
        FormUi formUiBack = getFormUiRandomSampleGenerator();

        thirdPartyIntegration.addFormUi(formUiBack);
        assertThat(thirdPartyIntegration.getFormUis()).containsOnly(formUiBack);
        assertThat(formUiBack.getThirdPartyIntegration()).isEqualTo(thirdPartyIntegration);

        thirdPartyIntegration.removeFormUi(formUiBack);
        assertThat(thirdPartyIntegration.getFormUis()).doesNotContain(formUiBack);
        assertThat(formUiBack.getThirdPartyIntegration()).isNull();

        thirdPartyIntegration.formUis(new HashSet<>(Set.of(formUiBack)));
        assertThat(thirdPartyIntegration.getFormUis()).containsOnly(formUiBack);
        assertThat(formUiBack.getThirdPartyIntegration()).isEqualTo(thirdPartyIntegration);

        thirdPartyIntegration.setFormUis(new HashSet<>());
        assertThat(thirdPartyIntegration.getFormUis()).doesNotContain(formUiBack);
        assertThat(formUiBack.getThirdPartyIntegration()).isNull();
    }

    @Test
    void integrationOperationTest() {
        ThirdPartyIntegration thirdPartyIntegration = getThirdPartyIntegrationRandomSampleGenerator();
        IntegrationOperation integrationOperationBack = getIntegrationOperationRandomSampleGenerator();

        thirdPartyIntegration.addIntegrationOperation(integrationOperationBack);
        assertThat(thirdPartyIntegration.getIntegrationOperations()).containsOnly(integrationOperationBack);
        assertThat(integrationOperationBack.getThirdPartyIntegration()).isEqualTo(thirdPartyIntegration);

        thirdPartyIntegration.removeIntegrationOperation(integrationOperationBack);
        assertThat(thirdPartyIntegration.getIntegrationOperations()).doesNotContain(integrationOperationBack);
        assertThat(integrationOperationBack.getThirdPartyIntegration()).isNull();

        thirdPartyIntegration.integrationOperations(new HashSet<>(Set.of(integrationOperationBack)));
        assertThat(thirdPartyIntegration.getIntegrationOperations()).containsOnly(integrationOperationBack);
        assertThat(integrationOperationBack.getThirdPartyIntegration()).isEqualTo(thirdPartyIntegration);

        thirdPartyIntegration.setIntegrationOperations(new HashSet<>());
        assertThat(thirdPartyIntegration.getIntegrationOperations()).doesNotContain(integrationOperationBack);
        assertThat(integrationOperationBack.getThirdPartyIntegration()).isNull();
    }

    @Test
    void categoryMenusTest() {
        ThirdPartyIntegration thirdPartyIntegration = getThirdPartyIntegrationRandomSampleGenerator();
        Menu menuBack = getMenuRandomSampleGenerator();

        thirdPartyIntegration.addCategoryMenus(menuBack);
        assertThat(thirdPartyIntegration.getCategoryMenus()).containsOnly(menuBack);
        assertThat(menuBack.getDynamicPaymentMenus()).containsOnly(thirdPartyIntegration);

        thirdPartyIntegration.removeCategoryMenus(menuBack);
        assertThat(thirdPartyIntegration.getCategoryMenus()).doesNotContain(menuBack);
        assertThat(menuBack.getDynamicPaymentMenus()).doesNotContain(thirdPartyIntegration);

        thirdPartyIntegration.categoryMenus(new HashSet<>(Set.of(menuBack)));
        assertThat(thirdPartyIntegration.getCategoryMenus()).containsOnly(menuBack);
        assertThat(menuBack.getDynamicPaymentMenus()).containsOnly(thirdPartyIntegration);

        thirdPartyIntegration.setCategoryMenus(new HashSet<>());
        assertThat(thirdPartyIntegration.getCategoryMenus()).doesNotContain(menuBack);
        assertThat(menuBack.getDynamicPaymentMenus()).doesNotContain(thirdPartyIntegration);
    }
}
