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
 * A Operation.
 */
@Entity
@Table(name = "operation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "operation_name", nullable = false)
    private String operationName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "http_method", nullable = false)
    private HttpMethod httpMethod;

    @NotNull
    @Column(name = "endpoint_path", nullable = false)
    private String endpointPath;

    @Column(name = "request_body_template")
    private String requestBodyTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "operations" }, allowSetters = true)
    private ApiIntegration apiIntegration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operation")
    @JsonIgnoreProperties(value = { "apiRequest", "paymentParams", "operation" }, allowSetters = true)
    private Set<PaymentDetail> paymentDetails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operation")
    @JsonIgnoreProperties(value = { "operation" }, allowSetters = true)
    private Set<ResponseOutput> responseOutputs = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operation")
    @JsonIgnoreProperties(value = { "operation", "integrationFieldMappings" }, allowSetters = true)
    private Set<RequestInput> requestInputs = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operation")
    @JsonIgnoreProperties(value = { "thirdPartyIntegration", "operation", "integrationFieldMappings" }, allowSetters = true)
    private Set<IntegrationOperation> integrationOperations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Operation id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOperationName() {
        return this.operationName;
    }

    public Operation operationName(String operationName) {
        this.setOperationName(operationName);
        return this;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public Operation httpMethod(HttpMethod httpMethod) {
        this.setHttpMethod(httpMethod);
        return this;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getEndpointPath() {
        return this.endpointPath;
    }

    public Operation endpointPath(String endpointPath) {
        this.setEndpointPath(endpointPath);
        return this;
    }

    public void setEndpointPath(String endpointPath) {
        this.endpointPath = endpointPath;
    }

    public String getRequestBodyTemplate() {
        return this.requestBodyTemplate;
    }

    public Operation requestBodyTemplate(String requestBodyTemplate) {
        this.setRequestBodyTemplate(requestBodyTemplate);
        return this;
    }

    public void setRequestBodyTemplate(String requestBodyTemplate) {
        this.requestBodyTemplate = requestBodyTemplate;
    }

    public ApiIntegration getApiIntegration() {
        return this.apiIntegration;
    }

    public void setApiIntegration(ApiIntegration apiIntegration) {
        this.apiIntegration = apiIntegration;
    }

    public Operation apiIntegration(ApiIntegration apiIntegration) {
        this.setApiIntegration(apiIntegration);
        return this;
    }

    public Set<PaymentDetail> getPaymentDetails() {
        return this.paymentDetails;
    }

    public void setPaymentDetails(Set<PaymentDetail> paymentDetails) {
        if (this.paymentDetails != null) {
            this.paymentDetails.forEach(i -> i.setOperation(null));
        }
        if (paymentDetails != null) {
            paymentDetails.forEach(i -> i.setOperation(this));
        }
        this.paymentDetails = paymentDetails;
    }

    public Operation paymentDetails(Set<PaymentDetail> paymentDetails) {
        this.setPaymentDetails(paymentDetails);
        return this;
    }

    public Operation addPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetails.add(paymentDetail);
        paymentDetail.setOperation(this);
        return this;
    }

    public Operation removePaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetails.remove(paymentDetail);
        paymentDetail.setOperation(null);
        return this;
    }

    public Set<ResponseOutput> getResponseOutputs() {
        return this.responseOutputs;
    }

    public void setResponseOutputs(Set<ResponseOutput> responseOutputs) {
        if (this.responseOutputs != null) {
            this.responseOutputs.forEach(i -> i.setOperation(null));
        }
        if (responseOutputs != null) {
            responseOutputs.forEach(i -> i.setOperation(this));
        }
        this.responseOutputs = responseOutputs;
    }

    public Operation responseOutputs(Set<ResponseOutput> responseOutputs) {
        this.setResponseOutputs(responseOutputs);
        return this;
    }

    public Operation addResponseOutput(ResponseOutput responseOutput) {
        this.responseOutputs.add(responseOutput);
        responseOutput.setOperation(this);
        return this;
    }

    public Operation removeResponseOutput(ResponseOutput responseOutput) {
        this.responseOutputs.remove(responseOutput);
        responseOutput.setOperation(null);
        return this;
    }

    public Set<RequestInput> getRequestInputs() {
        return this.requestInputs;
    }

    public void setRequestInputs(Set<RequestInput> requestInputs) {
        if (this.requestInputs != null) {
            this.requestInputs.forEach(i -> i.setOperation(null));
        }
        if (requestInputs != null) {
            requestInputs.forEach(i -> i.setOperation(this));
        }
        this.requestInputs = requestInputs;
    }

    public Operation requestInputs(Set<RequestInput> requestInputs) {
        this.setRequestInputs(requestInputs);
        return this;
    }

    public Operation addRequestInput(RequestInput requestInput) {
        this.requestInputs.add(requestInput);
        requestInput.setOperation(this);
        return this;
    }

    public Operation removeRequestInput(RequestInput requestInput) {
        this.requestInputs.remove(requestInput);
        requestInput.setOperation(null);
        return this;
    }

    public Set<IntegrationOperation> getIntegrationOperations() {
        return this.integrationOperations;
    }

    public void setIntegrationOperations(Set<IntegrationOperation> integrationOperations) {
        if (this.integrationOperations != null) {
            this.integrationOperations.forEach(i -> i.setOperation(null));
        }
        if (integrationOperations != null) {
            integrationOperations.forEach(i -> i.setOperation(this));
        }
        this.integrationOperations = integrationOperations;
    }

    public Operation integrationOperations(Set<IntegrationOperation> integrationOperations) {
        this.setIntegrationOperations(integrationOperations);
        return this;
    }

    public Operation addIntegrationOperation(IntegrationOperation integrationOperation) {
        this.integrationOperations.add(integrationOperation);
        integrationOperation.setOperation(this);
        return this;
    }

    public Operation removeIntegrationOperation(IntegrationOperation integrationOperation) {
        this.integrationOperations.remove(integrationOperation);
        integrationOperation.setOperation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Operation)) {
            return false;
        }
        return getId() != null && getId().equals(((Operation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Operation{" +
            "id=" + getId() +
            ", operationName='" + getOperationName() + "'" +
            ", httpMethod='" + getHttpMethod() + "'" +
            ", endpointPath='" + getEndpointPath() + "'" +
            ", requestBodyTemplate='" + getRequestBodyTemplate() + "'" +
            "}";
    }
}
