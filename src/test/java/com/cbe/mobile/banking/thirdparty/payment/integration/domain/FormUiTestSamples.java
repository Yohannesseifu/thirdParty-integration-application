package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class FormUiTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FormUi getFormUiSample1() {
        return new FormUi()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .formName("formName1")
            .formDescription("formDescription1")
            .stepOrder(1);
    }

    public static FormUi getFormUiSample2() {
        return new FormUi()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .formName("formName2")
            .formDescription("formDescription2")
            .stepOrder(2);
    }

    public static FormUi getFormUiRandomSampleGenerator() {
        return new FormUi()
            .id(UUID.randomUUID())
            .formName(UUID.randomUUID().toString())
            .formDescription(UUID.randomUUID().toString())
            .stepOrder(intCount.incrementAndGet());
    }
}
