package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FieldUIMetaDataTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FieldUIMetaData getFieldUIMetaDataSample1() {
        return new FieldUIMetaData()
            .id(1L)
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
            .id(2L)
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
            .id(longCount.incrementAndGet())
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
