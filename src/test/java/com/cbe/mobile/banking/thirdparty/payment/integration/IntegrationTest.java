package com.cbe.mobile.banking.thirdparty.payment.integration;

import com.cbe.mobile.banking.thirdparty.payment.integration.config.AsyncSyncConfiguration;
import com.cbe.mobile.banking.thirdparty.payment.integration.config.EmbeddedSQL;
import com.cbe.mobile.banking.thirdparty.payment.integration.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { ThirdPartyIntegrationApplicationApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedSQL
public @interface IntegrationTest {
}
