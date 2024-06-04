package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FormUiTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FormUi getFormUiSample1() {
        return new FormUi().id(1L).formName("formName1").formDescription("formDescription1").stepOrder(1);
    }

    public static FormUi getFormUiSample2() {
        return new FormUi().id(2L).formName("formName2").formDescription("formDescription2").stepOrder(2);
    }

    public static FormUi getFormUiRandomSampleGenerator() {
        return new FormUi()
            .id(longCount.incrementAndGet())
            .formName(UUID.randomUUID().toString())
            .formDescription(UUID.randomUUID().toString())
            .stepOrder(intCount.incrementAndGet());
    }
}
