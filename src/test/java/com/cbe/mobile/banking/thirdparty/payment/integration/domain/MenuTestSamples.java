package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MenuTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Menu getMenuSample1() {
        return new Menu().id(1L).menuName("menuName1").menuDescription("menuDescription1").iconPath("iconPath1");
    }

    public static Menu getMenuSample2() {
        return new Menu().id(2L).menuName("menuName2").menuDescription("menuDescription2").iconPath("iconPath2");
    }

    public static Menu getMenuRandomSampleGenerator() {
        return new Menu()
            .id(longCount.incrementAndGet())
            .menuName(UUID.randomUUID().toString())
            .menuDescription(UUID.randomUUID().toString())
            .iconPath(UUID.randomUUID().toString());
    }
}
