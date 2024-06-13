package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class ApiRequestTestSamples {

    public static ApiRequest getApiRequestSample1() {
        return new ApiRequest().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).uri("uri1").body("body1");
    }

    public static ApiRequest getApiRequestSample2() {
        return new ApiRequest().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).uri("uri2").body("body2");
    }

    public static ApiRequest getApiRequestRandomSampleGenerator() {
        return new ApiRequest().id(UUID.randomUUID()).uri(UUID.randomUUID().toString()).body(UUID.randomUUID().toString());
    }
}
