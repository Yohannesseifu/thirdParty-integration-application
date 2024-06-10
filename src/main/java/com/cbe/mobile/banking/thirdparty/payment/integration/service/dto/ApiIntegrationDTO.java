package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.AuthType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.ContentType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApiIntegrationDTO implements Serializable {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String url;

    @NotNull
    private ContentType type;

    @NotNull
    private AuthType auth;

    private String description;

    @Size(max = 50)
    private String version;

    private Integer timeout;

    private Integer retryRetries;

    private Integer retryDelay;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public AuthType getAuth() {
        return auth;
    }

    public void setAuth(AuthType auth) {
        this.auth = auth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getRetryRetries() {
        return retryRetries;
    }

    public void setRetryRetries(Integer retryRetries) {
        this.retryRetries = retryRetries;
    }

    public Integer getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(Integer retryDelay) {
        this.retryDelay = retryDelay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiIntegrationDTO)) {
            return false;
        }

        ApiIntegrationDTO apiIntegrationDTO = (ApiIntegrationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, apiIntegrationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiIntegrationDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", url='" + getUrl() + "'" +
            ", type='" + getType() + "'" +
            ", auth='" + getAuth() + "'" +
            ", description='" + getDescription() + "'" +
            ", version='" + getVersion() + "'" +
            ", timeout=" + getTimeout() +
            ", retryRetries=" + getRetryRetries() +
            ", retryDelay=" + getRetryDelay() +
            "}";
    }
}
