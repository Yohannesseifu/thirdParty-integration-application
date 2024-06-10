package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class MenuTestSamples {

    public static Menu getMenuSample1() {
        return new Menu()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .menuName("menuName1")
            .menuDescription("menuDescription1")
            .iconPath("iconPath1");
    }

    public static Menu getMenuSample2() {
        return new Menu()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .menuName("menuName2")
            .menuDescription("menuDescription2")
            .iconPath("iconPath2");
    }

    public static Menu getMenuRandomSampleGenerator() {
        return new Menu()
            .id(UUID.randomUUID())
            .menuName(UUID.randomUUID().toString())
            .menuDescription(UUID.randomUUID().toString())
            .iconPath(UUID.randomUUID().toString());
    }
}
