package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentDetailDTO implements Serializable {

    private UUID id;

    @Size(max = 2000)
    private String computedPaymentDetail;

    private ApiRequestDTO apiRequest;

    private OperationDTO operation;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComputedPaymentDetail() {
        return computedPaymentDetail;
    }

    public void setComputedPaymentDetail(String computedPaymentDetail) {
        this.computedPaymentDetail = computedPaymentDetail;
    }

    public ApiRequestDTO getApiRequest() {
        return apiRequest;
    }

    public void setApiRequest(ApiRequestDTO apiRequest) {
        this.apiRequest = apiRequest;
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
        if (!(o instanceof PaymentDetailDTO)) {
            return false;
        }

        PaymentDetailDTO paymentDetailDTO = (PaymentDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDetailDTO{" +
            "id='" + getId() + "'" +
            ", computedPaymentDetail='" + getComputedPaymentDetail() + "'" +
            ", apiRequest=" + getApiRequest() +
            ", operation=" + getOperation() +
            "}";
    }
}
