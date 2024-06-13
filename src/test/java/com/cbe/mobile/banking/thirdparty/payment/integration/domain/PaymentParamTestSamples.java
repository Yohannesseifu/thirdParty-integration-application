package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class PaymentParamTestSamples {

    public static PaymentParam getPaymentParamSample1() {
        return new PaymentParam().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).name("name1").valueStr("valueStr1");
    }

    public static PaymentParam getPaymentParamSample2() {
        return new PaymentParam().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).name("name2").valueStr("valueStr2");
    }

    public static PaymentParam getPaymentParamRandomSampleGenerator() {
        return new PaymentParam().id(UUID.randomUUID()).name(UUID.randomUUID().toString()).valueStr(UUID.randomUUID().toString());
    }
}
