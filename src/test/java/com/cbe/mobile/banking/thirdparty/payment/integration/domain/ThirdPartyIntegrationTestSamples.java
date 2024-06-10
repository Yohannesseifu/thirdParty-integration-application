package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class ThirdPartyIntegrationTestSamples {

    public static ThirdPartyIntegration getThirdPartyIntegrationSample1() {
        return new ThirdPartyIntegration()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .integrationName("integrationName1")
            .companyName("companyName1")
            .description("description1")
            .iconPath("iconPath1")
            .accountNumber("accountNumber1")
            .currencyCode("currencyCode1")
            .paymentConfirmationTemplate("paymentConfirmationTemplate1")
            .paymentSuccessTemplate("paymentSuccessTemplate1")
            .paymentErrorTemplate("paymentErrorTemplate1");
    }

    public static ThirdPartyIntegration getThirdPartyIntegrationSample2() {
        return new ThirdPartyIntegration()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .integrationName("integrationName2")
            .companyName("companyName2")
            .description("description2")
            .iconPath("iconPath2")
            .accountNumber("accountNumber2")
            .currencyCode("currencyCode2")
            .paymentConfirmationTemplate("paymentConfirmationTemplate2")
            .paymentSuccessTemplate("paymentSuccessTemplate2")
            .paymentErrorTemplate("paymentErrorTemplate2");
    }

    public static ThirdPartyIntegration getThirdPartyIntegrationRandomSampleGenerator() {
        return new ThirdPartyIntegration()
            .id(UUID.randomUUID())
            .integrationName(UUID.randomUUID().toString())
            .companyName(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .iconPath(UUID.randomUUID().toString())
            .accountNumber(UUID.randomUUID().toString())
            .currencyCode(UUID.randomUUID().toString())
            .paymentConfirmationTemplate(UUID.randomUUID().toString())
            .paymentSuccessTemplate(UUID.randomUUID().toString())
            .paymentErrorTemplate(UUID.randomUUID().toString());
    }
}
