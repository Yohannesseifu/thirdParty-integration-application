package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class PaymentDetailTestSamples {

    public static PaymentDetail getPaymentDetailSample1() {
        return new PaymentDetail()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .computedPaymentDetail("computedPaymentDetail1");
    }

    public static PaymentDetail getPaymentDetailSample2() {
        return new PaymentDetail()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .computedPaymentDetail("computedPaymentDetail2");
    }

    public static PaymentDetail getPaymentDetailRandomSampleGenerator() {
        return new PaymentDetail().id(UUID.randomUUID()).computedPaymentDetail(UUID.randomUUID().toString());
    }
}
