package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FieldTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Field getFieldSample1() {
        return new Field()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .maxLength(1)
            .minLength(1)
            .minValue("minValue1")
            .maxValue("maxValue1")
            .sortOrder(1L);
    }

    public static Field getFieldSample2() {
        return new Field()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .maxLength(2)
            .minLength(2)
            .minValue("minValue2")
            .maxValue("maxValue2")
            .sortOrder(2L);
    }

    public static Field getFieldRandomSampleGenerator() {
        return new Field()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .maxLength(intCount.incrementAndGet())
            .minLength(intCount.incrementAndGet())
            .minValue(UUID.randomUUID().toString())
            .maxValue(UUID.randomUUID().toString())
            .sortOrder(longCount.incrementAndGet());
    }
}
