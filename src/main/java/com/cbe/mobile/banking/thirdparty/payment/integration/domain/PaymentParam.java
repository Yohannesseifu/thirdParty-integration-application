package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.PaymentParamType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A PaymentParam.
 */
@Entity
@Table(name = "payment_param")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PaymentParamType type;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    private DataType dataType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "apiRequest", "paymentParams", "operation" }, allowSetters = true)
    private PaymentDetail paymentDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public PaymentParam id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PaymentParamType getType() {
        return this.type;
    }

    public PaymentParam type(PaymentParamType type) {
        this.setType(type);
        return this;
    }

    public void setType(PaymentParamType type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public PaymentParam name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public PaymentParam value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public PaymentParam dataType(DataType dataType) {
        this.setDataType(dataType);
        return this;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public PaymentDetail getPaymentDetail() {
        return this.paymentDetail;
    }

    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public PaymentParam paymentDetail(PaymentDetail paymentDetail) {
        this.setPaymentDetail(paymentDetail);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentParam)) {
            return false;
        }
        return getId() != null && getId().equals(((PaymentParam) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentParam{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", dataType='" + getDataType() + "'" +
            "}";
    }
}
