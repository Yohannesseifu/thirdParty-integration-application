package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.CoreMapping;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.Scope;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponseOutputDTO implements Serializable {

    private Long id;

    @NotNull
    private String outputName;

    @NotNull
    private DataType dataType;

    @NotNull
    private String responseValuePath;

    @NotNull
    private Scope responseScope;

    private CoreMapping coreMapping;

    private String expectedValue;

    private OperationDTO operation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getResponseValuePath() {
        return responseValuePath;
    }

    public void setResponseValuePath(String responseValuePath) {
        this.responseValuePath = responseValuePath;
    }

    public Scope getResponseScope() {
        return responseScope;
    }

    public void setResponseScope(Scope responseScope) {
        this.responseScope = responseScope;
    }

    public CoreMapping getCoreMapping() {
        return coreMapping;
    }

    public void setCoreMapping(CoreMapping coreMapping) {
        this.coreMapping = coreMapping;
    }

    public String getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
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
        if (!(o instanceof ResponseOutputDTO)) {
            return false;
        }

        ResponseOutputDTO responseOutputDTO = (ResponseOutputDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, responseOutputDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponseOutputDTO{" +
            "id=" + getId() +
            ", outputName='" + getOutputName() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", responseValuePath='" + getResponseValuePath() + "'" +
            ", responseScope='" + getResponseScope() + "'" +
            ", coreMapping='" + getCoreMapping() + "'" +
            ", expectedValue='" + getExpectedValue() + "'" +
            ", operation=" + getOperation() +
            "}";
    }
}
