package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class IntegrationOperationTestSamples {

    public static IntegrationOperation getIntegrationOperationSample1() {
        return new IntegrationOperation().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static IntegrationOperation getIntegrationOperationSample2() {
        return new IntegrationOperation().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static IntegrationOperation getIntegrationOperationRandomSampleGenerator() {
        return new IntegrationOperation().id(UUID.randomUUID());
    }
}
