package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ApiIntegrationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ApiIntegration getApiIntegrationSample1() {
        return new ApiIntegration()
            .id(1L)
            .name("name1")
            .url("url1")
            .description("description1")
            .version("version1")
            .timeout(1)
            .retryRetries(1)
            .retryDelay(1);
    }

    public static ApiIntegration getApiIntegrationSample2() {
        return new ApiIntegration()
            .id(2L)
            .name("name2")
            .url("url2")
            .description("description2")
            .version("version2")
            .timeout(2)
            .retryRetries(2)
            .retryDelay(2);
    }

    public static ApiIntegration getApiIntegrationRandomSampleGenerator() {
        return new ApiIntegration()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .url(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .version(UUID.randomUUID().toString())
            .timeout(intCount.incrementAndGet())
            .retryRetries(intCount.incrementAndGet())
            .retryDelay(intCount.incrementAndGet());
    }
}
