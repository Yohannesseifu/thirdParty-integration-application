package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class IntegrationFieldMappingTestSamples {

    public static IntegrationFieldMapping getIntegrationFieldMappingSample1() {
        return new IntegrationFieldMapping().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static IntegrationFieldMapping getIntegrationFieldMappingSample2() {
        return new IntegrationFieldMapping().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static IntegrationFieldMapping getIntegrationFieldMappingRandomSampleGenerator() {
        return new IntegrationFieldMapping().id(UUID.randomUUID());
    }
}
