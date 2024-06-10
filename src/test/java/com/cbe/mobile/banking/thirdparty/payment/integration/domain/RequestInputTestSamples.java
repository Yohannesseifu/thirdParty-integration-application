package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestInputTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RequestInput getRequestInputSample1() {
        return new RequestInput()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .inputName("inputName1")
            .testValue("testValue1")
            .defaultValue("defaultValue1")
            .maxLength(1)
            .minLength(1)
            .minValue("minValue1")
            .maxValue("maxValue1")
            .validationPattern("validationPattern1");
    }

    public static RequestInput getRequestInputSample2() {
        return new RequestInput()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .inputName("inputName2")
            .testValue("testValue2")
            .defaultValue("defaultValue2")
            .maxLength(2)
            .minLength(2)
            .minValue("minValue2")
            .maxValue("maxValue2")
            .validationPattern("validationPattern2");
    }

    public static RequestInput getRequestInputRandomSampleGenerator() {
        return new RequestInput()
            .id(UUID.randomUUID())
            .inputName(UUID.randomUUID().toString())
            .testValue(UUID.randomUUID().toString())
            .defaultValue(UUID.randomUUID().toString())
            .maxLength(intCount.incrementAndGet())
            .minLength(intCount.incrementAndGet())
            .minValue(UUID.randomUUID().toString())
            .maxValue(UUID.randomUUID().toString())
            .validationPattern(UUID.randomUUID().toString());
    }
}
