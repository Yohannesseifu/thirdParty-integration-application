package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationOperationAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIntegrationOperationAllPropertiesEquals(IntegrationOperation expected, IntegrationOperation actual) {
        assertIntegrationOperationAutoGeneratedPropertiesEquals(expected, actual);
        assertIntegrationOperationAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIntegrationOperationAllUpdatablePropertiesEquals(IntegrationOperation expected, IntegrationOperation actual) {
        assertIntegrationOperationUpdatableFieldsEquals(expected, actual);
        assertIntegrationOperationUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIntegrationOperationAutoGeneratedPropertiesEquals(IntegrationOperation expected, IntegrationOperation actual) {
        assertThat(expected)
            .as("Verify IntegrationOperation auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIntegrationOperationUpdatableFieldsEquals(IntegrationOperation expected, IntegrationOperation actual) {
        assertThat(expected)
            .as("Verify IntegrationOperation relevant properties")
            .satisfies(e -> assertThat(e.getOperationType()).as("check operationType").isEqualTo(actual.getOperationType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIntegrationOperationUpdatableRelationshipsEquals(IntegrationOperation expected, IntegrationOperation actual) {
        assertThat(expected)
            .as("Verify IntegrationOperation relationships")
            .satisfies(
                e -> assertThat(e.getThirdPartyIntegration()).as("check thirdPartyIntegration").isEqualTo(actual.getThirdPartyIntegration())
            )
            .satisfies(e -> assertThat(e.getOperation()).as("check operation").isEqualTo(actual.getOperation()));
    }
}