package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.RequestInputType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.RequestValueSource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A RequestInput.
 */
@Entity
@Table(name = "request_input")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "input_name", nullable = false)
    private String inputName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "input_type", nullable = false)
    private RequestInputType inputType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    private DataType dataType;

    @Column(name = "test_value")
    private String testValue;

    @Column(name = "default_value")
    private String defaultValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_source")
    private RequestValueSource valueSource;

    @Column(name = "is_encoded")
    private Boolean isEncoded;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "min_length")
    private Integer minLength;

    @Column(name = "min_value")
    private String minValue;

    @Column(name = "max_value")
    private String maxValue;

    @Column(name = "validation_pattern")
    private String validationPattern;

    @Column(name = "is_required")
    private Boolean isRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "apiIntegration", "paymentDetails", "responseOutputs", "requestInputs", "integrationOperations" },
        allowSetters = true
    )
    private Operation operation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestInput")
    @JsonIgnoreProperties(value = { "integrationOperation", "field", "requestInput" }, allowSetters = true)
    private Set<IntegrationFieldMapping> integrationFieldMappings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public RequestInput id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getInputName() {
        return this.inputName;
    }

    public RequestInput inputName(String inputName) {
        this.setInputName(inputName);
        return this;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public RequestInputType getInputType() {
        return this.inputType;
    }

    public RequestInput inputType(RequestInputType inputType) {
        this.setInputType(inputType);
        return this;
    }

    public void setInputType(RequestInputType inputType) {
        this.inputType = inputType;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public RequestInput dataType(DataType dataType) {
        this.setDataType(dataType);
        return this;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getTestValue() {
        return this.testValue;
    }

    public RequestInput testValue(String testValue) {
        this.setTestValue(testValue);
        return this;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public RequestInput defaultValue(String defaultValue) {
        this.setDefaultValue(defaultValue);
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public RequestValueSource getValueSource() {
        return this.valueSource;
    }

    public RequestInput valueSource(RequestValueSource valueSource) {
        this.setValueSource(valueSource);
        return this;
    }

    public void setValueSource(RequestValueSource valueSource) {
        this.valueSource = valueSource;
    }

    public Boolean getIsEncoded() {
        return this.isEncoded;
    }

    public RequestInput isEncoded(Boolean isEncoded) {
        this.setIsEncoded(isEncoded);
        return this;
    }

    public void setIsEncoded(Boolean isEncoded) {
        this.isEncoded = isEncoded;
    }

    public Integer getMaxLength() {
        return this.maxLength;
    }

    public RequestInput maxLength(Integer maxLength) {
        this.setMaxLength(maxLength);
        return this;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMinLength() {
        return this.minLength;
    }

    public RequestInput minLength(Integer minLength) {
        this.setMinLength(minLength);
        return this;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public String getMinValue() {
        return this.minValue;
    }

    public RequestInput minValue(String minValue) {
        this.setMinValue(minValue);
        return this;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return this.maxValue;
    }

    public RequestInput maxValue(String maxValue) {
        this.setMaxValue(maxValue);
        return this;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getValidationPattern() {
        return this.validationPattern;
    }

    public RequestInput validationPattern(String validationPattern) {
        this.setValidationPattern(validationPattern);
        return this;
    }

    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }

    public Boolean getIsRequired() {
        return this.isRequired;
    }

    public RequestInput isRequired(Boolean isRequired) {
        this.setIsRequired(isRequired);
        return this;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public RequestInput operation(Operation operation) {
        this.setOperation(operation);
        return this;
    }

    public Set<IntegrationFieldMapping> getIntegrationFieldMappings() {
        return this.integrationFieldMappings;
    }

    public void setIntegrationFieldMappings(Set<IntegrationFieldMapping> integrationFieldMappings) {
        if (this.integrationFieldMappings != null) {
            this.integrationFieldMappings.forEach(i -> i.setRequestInput(null));
        }
        if (integrationFieldMappings != null) {
            integrationFieldMappings.forEach(i -> i.setRequestInput(this));
        }
        this.integrationFieldMappings = integrationFieldMappings;
    }

    public RequestInput integrationFieldMappings(Set<IntegrationFieldMapping> integrationFieldMappings) {
        this.setIntegrationFieldMappings(integrationFieldMappings);
        return this;
    }

    public RequestInput addIntegrationFieldMapping(IntegrationFieldMapping integrationFieldMapping) {
        this.integrationFieldMappings.add(integrationFieldMapping);
        integrationFieldMapping.setRequestInput(this);
        return this;
    }

    public RequestInput removeIntegrationFieldMapping(IntegrationFieldMapping integrationFieldMapping) {
        this.integrationFieldMappings.remove(integrationFieldMapping);
        integrationFieldMapping.setRequestInput(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestInput)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestInput) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestInput{" +
            "id=" + getId() +
            ", inputName='" + getInputName() + "'" +
            ", inputType='" + getInputType() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", testValue='" + getTestValue() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", valueSource='" + getValueSource() + "'" +
            ", isEncoded='" + getIsEncoded() + "'" +
            ", maxLength=" + getMaxLength() +
            ", minLength=" + getMinLength() +
            ", minValue='" + getMinValue() + "'" +
            ", maxValue='" + getMaxValue() + "'" +
            ", validationPattern='" + getValidationPattern() + "'" +
            ", isRequired='" + getIsRequired() + "'" +
            "}";
    }
}
