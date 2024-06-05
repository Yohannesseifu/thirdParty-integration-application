package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ThirdPartyIntegrationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ThirdPartyIntegration getThirdPartyIntegrationSample1() {
        return new ThirdPartyIntegration()
            .id(1L)
            .integrationName("integrationName1")
            .companyName("companyName1")
            .description("description1")
            .iconPath("iconPath1")
            .accountNumber("accountNumber1")
            .currencyCode("currencyCode1")
            .paymentConfirmationTemplate("paymentConfirmationTemplate1")
            .paymentDetailTemplate("paymentDetailTemplate1");
    }

    public static ThirdPartyIntegration getThirdPartyIntegrationSample2() {
        return new ThirdPartyIntegration()
            .id(2L)
            .integrationName("integrationName2")
            .companyName("companyName2")
            .description("description2")
            .iconPath("iconPath2")
            .accountNumber("accountNumber2")
            .currencyCode("currencyCode2")
            .paymentConfirmationTemplate("paymentConfirmationTemplate2")
            .paymentDetailTemplate("paymentDetailTemplate2");
    }

    public static ThirdPartyIntegration getThirdPartyIntegrationRandomSampleGenerator() {
        return new ThirdPartyIntegration()
            .id(longCount.incrementAndGet())
            .integrationName(UUID.randomUUID().toString())
            .companyName(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .iconPath(UUID.randomUUID().toString())
            .accountNumber(UUID.randomUUID().toString())
            .currencyCode(UUID.randomUUID().toString())
            .paymentConfirmationTemplate(UUID.randomUUID().toString())
            .paymentDetailTemplate(UUID.randomUUID().toString());
    }
}
