package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationFieldMappingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIntegrationFieldMappingAllPropertiesEquals(IntegrationFieldMapping expected, IntegrationFieldMapping actual) {
        assertIntegrationFieldMappingAutoGeneratedPropertiesEquals(expected, actual);
        assertIntegrationFieldMappingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIntegrationFieldMappingAllUpdatablePropertiesEquals(
        IntegrationFieldMapping expected,
        IntegrationFieldMapping actual
    ) {
        assertIntegrationFieldMappingUpdatableFieldsEquals(expected, actual);
        assertIntegrationFieldMappingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIntegrationFieldMappingAutoGeneratedPropertiesEquals(
        IntegrationFieldMapping expected,
        IntegrationFieldMapping actual
    ) {
        assertThat(expected)
            .as("Verify IntegrationFieldMapping auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIntegrationFieldMappingUpdatableFieldsEquals(
        IntegrationFieldMapping expected,
        IntegrationFieldMapping actual
    ) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIntegrationFieldMappingUpdatableRelationshipsEquals(
        IntegrationFieldMapping expected,
        IntegrationFieldMapping actual
    ) {
        assertThat(expected)
            .as("Verify IntegrationFieldMapping relationships")
            .satisfies(
                e -> assertThat(e.getIntegrationOperation()).as("check integrationOperation").isEqualTo(actual.getIntegrationOperation())
            )
            .satisfies(e -> assertThat(e.getField()).as("check field").isEqualTo(actual.getField()))
            .satisfies(e -> assertThat(e.getRequestInput()).as("check requestInput").isEqualTo(actual.getRequestInput()));
    }
}