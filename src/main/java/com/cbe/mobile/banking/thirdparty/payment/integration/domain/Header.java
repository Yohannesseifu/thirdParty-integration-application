package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A Header.
 */
@Entity
@Table(name = "header")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Header implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "value_str", nullable = false)
    private String valueStr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "headers", "paymentDetail" }, allowSetters = true)
    private ApiRequest apiRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Header id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Header name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueStr() {
        return this.valueStr;
    }

    public Header valueStr(String valueStr) {
        this.setValueStr(valueStr);
        return this;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public ApiRequest getApiRequest() {
        return this.apiRequest;
    }

    public void setApiRequest(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }

    public Header apiRequest(ApiRequest apiRequest) {
        this.setApiRequest(apiRequest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Header)) {
            return false;
        }
        return getId() != null && getId().equals(((Header) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Header{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", valueStr='" + getValueStr() + "'" +
            "}";
    }
}
