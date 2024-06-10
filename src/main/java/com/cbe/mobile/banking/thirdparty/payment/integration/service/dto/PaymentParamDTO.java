package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.PaymentParamType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentParamDTO implements Serializable {

    private UUID id;

    private PaymentParamType type;

    private String name;

    private String value;

    @NotNull
    private DataType dataType;

    private PaymentDetailDTO paymentDetail;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PaymentParamType getType() {
        return type;
    }

    public void setType(PaymentParamType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public PaymentDetailDTO getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(PaymentDetailDTO paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentParamDTO)) {
            return false;
        }

        PaymentParamDTO paymentParamDTO = (PaymentParamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentParamDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentParamDTO{" +
            "id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", paymentDetail=" + getPaymentDetail() +
            "}";
    }
}
