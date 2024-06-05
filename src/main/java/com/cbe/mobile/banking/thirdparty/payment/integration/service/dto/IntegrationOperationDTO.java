package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.OperationType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegrationOperationDTO implements Serializable {

    private Long id;

    private OperationType operationType;

    private ThirdPartyIntegrationDTO thirdPartyIntegration;

    private OperationDTO operation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public ThirdPartyIntegrationDTO getThirdPartyIntegration() {
        return thirdPartyIntegration;
    }

    public void setThirdPartyIntegration(ThirdPartyIntegrationDTO thirdPartyIntegration) {
        this.thirdPartyIntegration = thirdPartyIntegration;
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
        if (!(o instanceof IntegrationOperationDTO)) {
            return false;
        }

        IntegrationOperationDTO integrationOperationDTO = (IntegrationOperationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, integrationOperationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegrationOperationDTO{" +
            "id=" + getId() +
            ", operationType='" + getOperationType() + "'" +
            ", thirdPartyIntegration=" + getThirdPartyIntegration() +
            ", operation=" + getOperation() +
            "}";
    }
}
