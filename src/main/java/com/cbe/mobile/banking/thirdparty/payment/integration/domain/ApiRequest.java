package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.HttpMethod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A ApiRequest.
 */
@Entity
@Table(name = "api_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApiRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "body")
    private String body;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private HttpMethod method;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apiRequest")
    @JsonIgnoreProperties(value = { "apiRequest" }, allowSetters = true)
    private Set<Header> headers = new HashSet<>();

    @JsonIgnoreProperties(value = { "apiRequest", "paymentParams", "operation" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "apiRequest")
    private PaymentDetail paymentDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ApiRequest id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUri() {
        return this.uri;
    }

    public ApiRequest uri(String uri) {
        this.setUri(uri);
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getBody() {
        return this.body;
    }

    public ApiRequest body(String body) {
        this.setBody(body);
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public ApiRequest method(HttpMethod method) {
        this.setMethod(method);
        return this;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Set<Header> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Set<Header> headers) {
        if (this.headers != null) {
            this.headers.forEach(i -> i.setApiRequest(null));
        }
        if (headers != null) {
            headers.forEach(i -> i.setApiRequest(this));
        }
        this.headers = headers;
    }

    public ApiRequest headers(Set<Header> headers) {
        this.setHeaders(headers);
        return this;
    }

    public ApiRequest addHeaders(Header header) {
        this.headers.add(header);
        header.setApiRequest(this);
        return this;
    }

    public ApiRequest removeHeaders(Header header) {
        this.headers.remove(header);
        header.setApiRequest(null);
        return this;
    }

    public PaymentDetail getPaymentDetail() {
        return this.paymentDetail;
    }

    public void setPaymentDetail(PaymentDetail paymentDetail) {
        if (this.paymentDetail != null) {
            this.paymentDetail.setApiRequest(null);
        }
        if (paymentDetail != null) {
            paymentDetail.setApiRequest(this);
        }
        this.paymentDetail = paymentDetail;
    }

    public ApiRequest paymentDetail(PaymentDetail paymentDetail) {
        this.setPaymentDetail(paymentDetail);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiRequest)) {
            return false;
        }
        return getId() != null && getId().equals(((ApiRequest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiRequest{" +
            "id=" + getId() +
            ", uri='" + getUri() + "'" +
            ", body='" + getBody() + "'" +
            ", method='" + getMethod() + "'" +
            "}";
    }
}
