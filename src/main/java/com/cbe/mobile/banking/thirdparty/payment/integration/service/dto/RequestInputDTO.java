package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.AutoUserValue;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.RequestInputType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.TransactionParams;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestInputDTO implements Serializable {

    private UUID id;

    @NotNull
    private String inputName;

    @NotNull
    private RequestInputType inputType;

    @NotNull
    private DataType dataType;

    private String testValue;

    private String defaultValue;

    private AutoUserValue autoUserValue;

    private Boolean isEncoded;

    private Integer maxLength;

    private Integer minLength;

    private String minValue;

    private String maxValue;

    private String validationPattern;

    private Boolean isRequired;

    private TransactionParams valueFromTransaction;

    private OperationDTO operation;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public RequestInputType getInputType() {
        return inputType;
    }

    public void setInputType(RequestInputType inputType) {
        this.inputType = inputType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public AutoUserValue getAutoUserValue() {
        return autoUserValue;
    }

    public void setAutoUserValue(AutoUserValue autoUserValue) {
        this.autoUserValue = autoUserValue;
    }

    public Boolean getIsEncoded() {
        return isEncoded;
    }

    public void setIsEncoded(Boolean isEncoded) {
        this.isEncoded = isEncoded;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getValidationPattern() {
        return validationPattern;
    }

    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public TransactionParams getValueFromTransaction() {
        return valueFromTransaction;
    }

    public void setValueFromTransaction(TransactionParams valueFromTransaction) {
        this.valueFromTransaction = valueFromTransaction;
    }

    public OperationDTO getOperation() {
        return operation;
    }

    public void setOperation(OperationDTO operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestInputDTO)) {
            return false;
        }

        RequestInputDTO requestInputDTO = (RequestInputDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestInputDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestInputDTO{" +
            "id='" + getId() + "'" +
            ", inputName='" + getInputName() + "'" +
            ", inputType='" + getInputType() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", testValue='" + getTestValue() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", autoUserValue='" + getAutoUserValue() + "'" +
            ", isEncoded='" + getIsEncoded() + "'" +
            ", maxLength=" + getMaxLength() +
            ", minLength=" + getMinLength() +
            ", minValue='" + getMinValue() + "'" +
            ", maxValue='" + getMaxValue() + "'" +
            ", validationPattern='" + getValidationPattern() + "'" +
            ", isRequired='" + getIsRequired() + "'" +
            ", valueFromTransaction='" + getValueFromTransaction() + "'" +
            ", operation=" + getOperation() +
            "}";
    }
}
