package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.MenuTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.MenuTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegrationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Menu.class);
        Menu menu1 = getMenuSample1();
        Menu menu2 = new Menu();
        assertThat(menu1).isNotEqualTo(menu2);

        menu2.setId(menu1.getId());
        assertThat(menu1).isEqualTo(menu2);

        menu2 = getMenuSample2();
        assertThat(menu1).isNotEqualTo(menu2);
    }

    @Test
    void childrenTest() {
        Menu menu = getMenuRandomSampleGenerator();
        Menu menuBack = getMenuRandomSampleGenerator();

        menu.addChildren(menuBack);
        assertThat(menu.getChildren()).containsOnly(menuBack);
        assertThat(menuBack.getParent()).isEqualTo(menu);

        menu.removeChildren(menuBack);
        assertThat(menu.getChildren()).doesNotContain(menuBack);
        assertThat(menuBack.getParent()).isNull();

        menu.children(new HashSet<>(Set.of(menuBack)));
        assertThat(menu.getChildren()).containsOnly(menuBack);
        assertThat(menuBack.getParent()).isEqualTo(menu);

        menu.setChildren(new HashSet<>());
        assertThat(menu.getChildren()).doesNotContain(menuBack);
        assertThat(menuBack.getParent()).isNull();
    }

    @Test
    void dynamicPaymentMenusTest() {
        Menu menu = getMenuRandomSampleGenerator();
        ThirdPartyIntegration thirdPartyIntegrationBack = getThirdPartyIntegrationRandomSampleGenerator();

        menu.addDynamicPaymentMenus(thirdPartyIntegrationBack);
        assertThat(menu.getDynamicPaymentMenus()).containsOnly(thirdPartyIntegrationBack);

        menu.removeDynamicPaymentMenus(thirdPartyIntegrationBack);
        assertThat(menu.getDynamicPaymentMenus()).doesNotContain(thirdPartyIntegrationBack);

        menu.dynamicPaymentMenus(new HashSet<>(Set.of(thirdPartyIntegrationBack)));
        assertThat(menu.getDynamicPaymentMenus()).containsOnly(thirdPartyIntegrationBack);

        menu.setDynamicPaymentMenus(new HashSet<>());
        assertThat(menu.getDynamicPaymentMenus()).doesNotContain(thirdPartyIntegrationBack);
    }

    @Test
    void parentTest() {
        Menu menu = getMenuRandomSampleGenerator();
        Menu menuBack = getMenuRandomSampleGenerator();

        menu.setParent(menuBack);
        assertThat(menu.getParent()).isEqualTo(menuBack);

        menu.parent(null);
        assertThat(menu.getParent()).isNull();
    }
}
