package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IntegrationFieldMappingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IntegrationFieldMapping getIntegrationFieldMappingSample1() {
        return new IntegrationFieldMapping().id(1L);
    }

    public static IntegrationFieldMapping getIntegrationFieldMappingSample2() {
        return new IntegrationFieldMapping().id(2L);
    }

    public static IntegrationFieldMapping getIntegrationFieldMappingRandomSampleGenerator() {
        return new IntegrationFieldMapping().id(longCount.incrementAndGet());
    }
}
