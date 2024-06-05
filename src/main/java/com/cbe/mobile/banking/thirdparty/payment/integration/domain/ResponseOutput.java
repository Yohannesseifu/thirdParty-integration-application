package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.CoreMapping;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.LogicalOperator;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.Scope;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A ResponseOutput.
 */
@Entity
@Table(name = "response_output")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponseOutput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "output_name", nullable = false)
    private String outputName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    private DataType dataType;

    @NotNull
    @Column(name = "response_value_path", nullable = false)
    private String responseValuePath;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "response_scope", nullable = false)
    private Scope responseScope;

    @Enumerated(EnumType.STRING)
    @Column(name = "core_mapping")
    private CoreMapping coreMapping;

    @Column(name = "constant_value_to_compare")
    private String constantValueToCompare;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator_to_compare_value")
    private LogicalOperator operatorToCompareValue;

    @Column(name = "is_required")
    private Boolean isRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "apiIntegration", "responseOutputs", "requestInputs" }, allowSetters = true)
    private Operation operation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResponseOutput id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutputName() {
        return this.outputName;
    }

    public ResponseOutput outputName(String outputName) {
        this.setOutputName(outputName);
        return this;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public ResponseOutput dataType(DataType dataType) {
        this.setDataType(dataType);
        return this;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getResponseValuePath() {
        return this.responseValuePath;
    }

    public ResponseOutput responseValuePath(String responseValuePath) {
        this.setResponseValuePath(responseValuePath);
        return this;
    }

    public void setResponseValuePath(String responseValuePath) {
        this.responseValuePath = responseValuePath;
    }

    public Scope getResponseScope() {
        return this.responseScope;
    }

    public ResponseOutput responseScope(Scope responseScope) {
        this.setResponseScope(responseScope);
        return this;
    }

    public void setResponseScope(Scope responseScope) {
        this.responseScope = responseScope;
    }

    public CoreMapping getCoreMapping() {
        return this.coreMapping;
    }

    public ResponseOutput coreMapping(CoreMapping coreMapping) {
        this.setCoreMapping(coreMapping);
        return this;
    }

    public void setCoreMapping(CoreMapping coreMapping) {
        this.coreMapping = coreMapping;
    }

    public String getConstantValueToCompare() {
        return this.constantValueToCompare;
    }

    public ResponseOutput constantValueToCompare(String constantValueToCompare) {
        this.setConstantValueToCompare(constantValueToCompare);
        return this;
    }

    public void setConstantValueToCompare(String constantValueToCompare) {
        this.constantValueToCompare = constantValueToCompare;
    }

    public LogicalOperator getOperatorToCompareValue() {
        return this.operatorToCompareValue;
    }

    public ResponseOutput operatorToCompareValue(LogicalOperator operatorToCompareValue) {
        this.setOperatorToCompareValue(operatorToCompareValue);
        return this;
    }

    public void setOperatorToCompareValue(LogicalOperator operatorToCompareValue) {
        this.operatorToCompareValue = operatorToCompareValue;
    }

    public Boolean getIsRequired() {
        return this.isRequired;
    }

    public ResponseOutput isRequired(Boolean isRequired) {
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

    public ResponseOutput operation(Operation operation) {
        this.setOperation(operation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponseOutput)) {
            return false;
        }
        return getId() != null && getId().equals(((ResponseOutput) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponseOutput{" +
            "id=" + getId() +
            ", outputName='" + getOutputName() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", responseValuePath='" + getResponseValuePath() + "'" +
            ", responseScope='" + getResponseScope() + "'" +
            ", coreMapping='" + getCoreMapping() + "'" +
            ", constantValueToCompare='" + getConstantValueToCompare() + "'" +
            ", operatorToCompareValue='" + getOperatorToCompareValue() + "'" +
            ", isRequired='" + getIsRequired() + "'" +
            "}";
    }
}
