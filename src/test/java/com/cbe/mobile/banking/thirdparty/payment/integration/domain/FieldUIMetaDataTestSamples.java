package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.UUID;

public class FieldUIMetaDataTestSamples {

    public static FieldUIMetaData getFieldUIMetaDataSample1() {
        return new FieldUIMetaData()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .label("label1")
            .inputInterface("inputInterface1")
            .width("width1")
            .note("note1")
            .validationPattern("validationPattern1")
            .options("options1")
            .displayOptions("displayOptions1")
            .conditions("conditions1")
            .translations("translations1");
    }

    public static FieldUIMetaData getFieldUIMetaDataSample2() {
        return new FieldUIMetaData()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .label("label2")
            .inputInterface("inputInterface2")
            .width("width2")
            .note("note2")
            .validationPattern("validationPattern2")
            .options("options2")
            .displayOptions("displayOptions2")
            .conditions("conditions2")
            .translations("translations2");
    }

    public static FieldUIMetaData getFieldUIMetaDataRandomSampleGenerator() {
        return new FieldUIMetaData()
            .id(UUID.randomUUID())
            .label(UUID.randomUUID().toString())
            .inputInterface(UUID.randomUUID().toString())
            .width(UUID.randomUUID().toString())
            .note(UUID.randomUUID().toString())
            .validationPattern(UUID.randomUUID().toString())
            .options(UUID.randomUUID().toString())
            .displayOptions(UUID.randomUUID().toString())
            .conditions(UUID.randomUUID().toString())
            .translations(UUID.randomUUID().toString());
    }
}
