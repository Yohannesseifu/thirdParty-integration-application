package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.CoreTransferParams;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.LogicalOperator;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.Scope;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponseOutputDTO implements Serializable {

    private UUID id;

    @NotNull
    private String outputName;

    @NotNull
    private DataType dataType;

    @NotNull
    private String responseValuePath;

    @NotNull
    private Scope responseScope;

    private CoreTransferParams transferCoreMapping;

    private Boolean isLogicField;

    private String constantValueToCompare;

    private LogicalOperator operatorToCompareValue;

    private Boolean isRequired;

    private OperationDTO operation;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public CoreTransferParams getTransferCoreMapping() {
        return transferCoreMapping;
    }

    public void setTransferCoreMapping(CoreTransferParams transferCoreMapping) {
        this.transferCoreMapping = transferCoreMapping;
    }

    public Boolean getIsLogicField() {
        return isLogicField;
    }

    public void setIsLogicField(Boolean isLogicField) {
        this.isLogicField = isLogicField;
    }

    public String getConstantValueToCompare() {
        return constantValueToCompare;
    }

    public void setConstantValueToCompare(String constantValueToCompare) {
        this.constantValueToCompare = constantValueToCompare;
    }

    public LogicalOperator getOperatorToCompareValue() {
        return operatorToCompareValue;
    }

    public void setOperatorToCompareValue(LogicalOperator operatorToCompareValue) {
        this.operatorToCompareValue = operatorToCompareValue;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
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
            "id='" + getId() + "'" +
            ", outputName='" + getOutputName() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", responseValuePath='" + getResponseValuePath() + "'" +
            ", responseScope='" + getResponseScope() + "'" +
            ", transferCoreMapping='" + getTransferCoreMapping() + "'" +
            ", isLogicField='" + getIsLogicField() + "'" +
            ", constantValueToCompare='" + getConstantValueToCompare() + "'" +
            ", operatorToCompareValue='" + getOperatorToCompareValue() + "'" +
            ", isRequired='" + getIsRequired() + "'" +
            ", operation=" + getOperation() +
            "}";
    }
}
