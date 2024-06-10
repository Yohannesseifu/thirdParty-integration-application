package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A PaymentDetail.
 */
@Entity
@Table(name = "payment_detail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Size(max = 2000)
    @Column(name = "computed_payment_detail", length = 2000)
    private String computedPaymentDetail;

    @JsonIgnoreProperties(value = { "headers", "paymentDetail" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private ApiRequest apiRequest;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentDetail")
    @JsonIgnoreProperties(value = { "paymentDetail" }, allowSetters = true)
    private Set<PaymentParam> paymentParams = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "apiIntegration", "paymentDetails", "responseOutputs", "requestInputs", "integrationOperations" },
        allowSetters = true
    )
    private Operation operation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public PaymentDetail id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComputedPaymentDetail() {
        return this.computedPaymentDetail;
    }

    public PaymentDetail computedPaymentDetail(String computedPaymentDetail) {
        this.setComputedPaymentDetail(computedPaymentDetail);
        return this;
    }

    public void setComputedPaymentDetail(String computedPaymentDetail) {
        this.computedPaymentDetail = computedPaymentDetail;
    }

    public ApiRequest getApiRequest() {
        return this.apiRequest;
    }

    public void setApiRequest(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }

    public PaymentDetail apiRequest(ApiRequest apiRequest) {
        this.setApiRequest(apiRequest);
        return this;
    }

    public Set<PaymentParam> getPaymentParams() {
        return this.paymentParams;
    }

    public void setPaymentParams(Set<PaymentParam> paymentParams) {
        if (this.paymentParams != null) {
            this.paymentParams.forEach(i -> i.setPaymentDetail(null));
        }
        if (paymentParams != null) {
            paymentParams.forEach(i -> i.setPaymentDetail(this));
        }
        this.paymentParams = paymentParams;
    }

    public PaymentDetail paymentParams(Set<PaymentParam> paymentParams) {
        this.setPaymentParams(paymentParams);
        return this;
    }

    public PaymentDetail addPaymentParams(PaymentParam paymentParam) {
        this.paymentParams.add(paymentParam);
        paymentParam.setPaymentDetail(this);
        return this;
    }

    public PaymentDetail removePaymentParams(PaymentParam paymentParam) {
        this.paymentParams.remove(paymentParam);
        paymentParam.setPaymentDetail(null);
        return this;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public PaymentDetail operation(Operation operation) {
        this.setOperation(operation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((PaymentDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDetail{" +
            "id=" + getId() +
            ", computedPaymentDetail='" + getComputedPaymentDetail() + "'" +
            "}";
    }
}
