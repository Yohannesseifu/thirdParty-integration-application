package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegrationFieldMappingDTO implements Serializable {

    private UUID id;

    private IntegrationOperationDTO integrationOperation;

    private FieldDTO field;

    private RequestInputDTO requestInput;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public IntegrationOperationDTO getIntegrationOperation() {
        return integrationOperation;
    }

    public void setIntegrationOperation(IntegrationOperationDTO integrationOperation) {
        this.integrationOperation = integrationOperation;
    }

    public FieldDTO getField() {
        return field;
    }

    public void setField(FieldDTO field) {
        this.field = field;
    }

    public RequestInputDTO getRequestInput() {
        return requestInput;
    }

    public void setRequestInput(RequestInputDTO requestInput) {
        this.requestInput = requestInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegrationFieldMappingDTO)) {
            return false;
        }

        IntegrationFieldMappingDTO integrationFieldMappingDTO = (IntegrationFieldMappingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, integrationFieldMappingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegrationFieldMappingDTO{" +
            "id='" + getId() + "'" +
            ", integrationOperation=" + getIntegrationOperation() +
            ", field=" + getField() +
            ", requestInput=" + getRequestInput() +
            "}";
    }
}
