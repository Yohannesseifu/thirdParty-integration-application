package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HeaderDTO implements Serializable {

    private UUID id;

    @NotNull
    private String key;

    @NotNull
    private String value;

    private ApiRequestDTO apiRequest;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ApiRequestDTO getApiRequest() {
        return apiRequest;
    }

    public void setApiRequest(ApiRequestDTO apiRequest) {
        this.apiRequest = apiRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HeaderDTO)) {
            return false;
        }

        HeaderDTO headerDTO = (HeaderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, headerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HeaderDTO{" +
            "id='" + getId() + "'" +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            ", apiRequest=" + getApiRequest() +
            "}";
    }
}
