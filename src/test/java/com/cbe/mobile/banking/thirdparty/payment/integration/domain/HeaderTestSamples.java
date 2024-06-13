package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class HeaderTestSamples {

    public static Header getHeaderSample1() {
        return new Header().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).name("name1").valueStr("valueStr1");
    }

    public static Header getHeaderSample2() {
        return new Header().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).name("name2").valueStr("valueStr2");
    }

    public static Header getHeaderRandomSampleGenerator() {
        return new Header().id(UUID.randomUUID()).name(UUID.randomUUID().toString()).valueStr(UUID.randomUUID().toString());
    }
}
