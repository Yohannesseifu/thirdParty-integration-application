package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.HttpMethod;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApiRequestDTO implements Serializable {

    private UUID id;

    @NotNull
    private String uri;

    private String body;

    @NotNull
    private HttpMethod method;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiRequestDTO)) {
            return false;
        }

        ApiRequestDTO apiRequestDTO = (ApiRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, apiRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiRequestDTO{" +
            "id='" + getId() + "'" +
            ", uri='" + getUri() + "'" +
            ", body='" + getBody() + "'" +
            ", method='" + getMethod() + "'" +
            "}";
    }
}
