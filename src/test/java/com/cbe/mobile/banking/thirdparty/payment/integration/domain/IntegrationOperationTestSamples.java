package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IntegrationOperationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IntegrationOperation getIntegrationOperationSample1() {
        return new IntegrationOperation().id(1L);
    }

    public static IntegrationOperation getIntegrationOperationSample2() {
        return new IntegrationOperation().id(2L);
    }

    public static IntegrationOperation getIntegrationOperationRandomSampleGenerator() {
        return new IntegrationOperation().id(longCount.incrementAndGet());
    }
}
