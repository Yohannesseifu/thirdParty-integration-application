package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.HttpMethod;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperationDTO implements Serializable {

    private Long id;

    @NotNull
    private String operationName;

    @NotNull
    private HttpMethod httpMethod;

    @NotNull
    private String endpointPath;

    private String requestBodyTemplate;

    private ApiIntegrationDTO apiIntegration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getEndpointPath() {
        return endpointPath;
    }

    public void setEndpointPath(String endpointPath) {
        this.endpointPath = endpointPath;
    }

    public String getRequestBodyTemplate() {
        return requestBodyTemplate;
    }

    public void setRequestBodyTemplate(String requestBodyTemplate) {
        this.requestBodyTemplate = requestBodyTemplate;
    }

    public ApiIntegrationDTO getApiIntegration() {
        return apiIntegration;
    }

    public void setApiIntegration(ApiIntegrationDTO apiIntegration) {
        this.apiIntegration = apiIntegration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationDTO)) {
            return false;
        }

        OperationDTO operationDTO = (OperationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, operationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationDTO{" +
            "id=" + getId() +
            ", operationName='" + getOperationName() + "'" +
            ", httpMethod='" + getHttpMethod() + "'" +
            ", endpointPath='" + getEndpointPath() + "'" +
            ", requestBodyTemplate='" + getRequestBodyTemplate() + "'" +
            ", apiIntegration=" + getApiIntegration() +
            "}";
    }
}
