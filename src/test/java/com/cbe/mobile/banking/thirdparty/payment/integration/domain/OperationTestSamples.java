package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OperationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Operation getOperationSample1() {
        return new Operation()
            .id(1L)
            .operationName("operationName1")
            .endpointPath("endpointPath1")
            .requestBodyTemplate("requestBodyTemplate1");
    }

    public static Operation getOperationSample2() {
        return new Operation()
            .id(2L)
            .operationName("operationName2")
            .endpointPath("endpointPath2")
            .requestBodyTemplate("requestBodyTemplate2");
    }

    public static Operation getOperationRandomSampleGenerator() {
        return new Operation()
            .id(longCount.incrementAndGet())
            .operationName(UUID.randomUUID().toString())
            .endpointPath(UUID.randomUUID().toString())
            .requestBodyTemplate(UUID.randomUUID().toString());
    }
}
