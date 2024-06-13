package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class ResponseOutputTestSamples {

    public static ResponseOutput getResponseOutputSample1() {
        return new ResponseOutput()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .outputName("outputName1")
            .responseValuePath("responseValuePath1")
            .constantValueToCompare("constantValueToCompare1");
    }

    public static ResponseOutput getResponseOutputSample2() {
        return new ResponseOutput()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .outputName("outputName2")
            .responseValuePath("responseValuePath2")
            .constantValueToCompare("constantValueToCompare2");
    }

    public static ResponseOutput getResponseOutputRandomSampleGenerator() {
        return new ResponseOutput()
            .id(UUID.randomUUID())
            .outputName(UUID.randomUUID().toString())
            .responseValuePath(UUID.randomUUID().toString())
            .constantValueToCompare(UUID.randomUUID().toString());
    }
}
