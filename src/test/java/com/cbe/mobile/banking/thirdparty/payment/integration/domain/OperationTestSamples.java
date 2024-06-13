package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class OperationTestSamples {

    public static Operation getOperationSample1() {
        return new Operation()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .operationName("operationName1")
            .endpointPath("endpointPath1")
            .requestBodyTemplate("requestBodyTemplate1");
    }

    public static Operation getOperationSample2() {
        return new Operation()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .operationName("operationName2")
            .endpointPath("endpointPath2")
            .requestBodyTemplate("requestBodyTemplate2");
    }

    public static Operation getOperationRandomSampleGenerator() {
        return new Operation()
            .id(UUID.randomUUID())
            .operationName(UUID.randomUUID().toString())
            .endpointPath(UUID.randomUUID().toString())
            .requestBodyTemplate(UUID.randomUUID().toString());
    }
}
