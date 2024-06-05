package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ResponseOutputTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ResponseOutput getResponseOutputSample1() {
        return new ResponseOutput()
            .id(1L)
            .outputName("outputName1")
            .responseValuePath("responseValuePath1")
            .constantValueToCompare("constantValueToCompare1");
    }

    public static ResponseOutput getResponseOutputSample2() {
        return new ResponseOutput()
            .id(2L)
            .outputName("outputName2")
            .responseValuePath("responseValuePath2")
            .constantValueToCompare("constantValueToCompare2");
    }

    public static ResponseOutput getResponseOutputRandomSampleGenerator() {
        return new ResponseOutput()
            .id(longCount.incrementAndGet())
            .outputName(UUID.randomUUID().toString())
            .responseValuePath(UUID.randomUUID().toString())
            .constantValueToCompare(UUID.randomUUID().toString());
    }
}
