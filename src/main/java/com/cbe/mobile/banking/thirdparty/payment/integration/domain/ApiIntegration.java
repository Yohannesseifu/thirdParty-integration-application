package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.AuthType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.ContentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ApiIntegration.
 */
@Entity
@Table(name = "api_integration")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApiIntegration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ContentType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "auth", nullable = false)
    private AuthType auth;

    @Column(name = "description")
    private String description;

    @Size(max = 50)
    @Column(name = "version", length = 50)
    private String version;

    @Column(name = "timeout")
    private Integer timeout;

    @Column(name = "retry_retries")
    private Integer retryRetries;

    @Column(name = "retry_delay")
    private Integer retryDelay;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apiIntegration")
    @JsonIgnoreProperties(value = { "apiIntegration", "responseOutputs", "requestInputs" }, allowSetters = true)
    private Set<Operation> operations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApiIntegration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ApiIntegration name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public ApiIntegration url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ContentType getType() {
        return this.type;
    }

    public ApiIntegration type(ContentType type) {
        this.setType(type);
        return this;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public AuthType getAuth() {
        return this.auth;
    }

    public ApiIntegration auth(AuthType auth) {
        this.setAuth(auth);
        return this;
    }

    public void setAuth(AuthType auth) {
        this.auth = auth;
    }

    public String getDescription() {
        return this.description;
    }

    public ApiIntegration description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return this.version;
    }

    public ApiIntegration version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    public ApiIntegration timeout(Integer timeout) {
        this.setTimeout(timeout);
        return this;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getRetryRetries() {
        return this.retryRetries;
    }

    public ApiIntegration retryRetries(Integer retryRetries) {
        this.setRetryRetries(retryRetries);
        return this;
    }

    public void setRetryRetries(Integer retryRetries) {
        this.retryRetries = retryRetries;
    }

    public Integer getRetryDelay() {
        return this.retryDelay;
    }

    public ApiIntegration retryDelay(Integer retryDelay) {
        this.setRetryDelay(retryDelay);
        return this;
    }

    public void setRetryDelay(Integer retryDelay) {
        this.retryDelay = retryDelay;
    }

    public Set<Operation> getOperations() {
        return this.operations;
    }

    public void setOperations(Set<Operation> operations) {
        if (this.operations != null) {
            this.operations.forEach(i -> i.setApiIntegration(null));
        }
        if (operations != null) {
            operations.forEach(i -> i.setApiIntegration(this));
        }
        this.operations = operations;
    }

    public ApiIntegration operations(Set<Operation> operations) {
        this.setOperations(operations);
        return this;
    }

    public ApiIntegration addOperation(Operation operation) {
        this.operations.add(operation);
        operation.setApiIntegration(this);
        return this;
    }

    public ApiIntegration removeOperation(Operation operation) {
        this.operations.remove(operation);
        operation.setApiIntegration(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiIntegration)) {
            return false;
        }
        return getId() != null && getId().equals(((ApiIntegration) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiIntegration{" +
            "id=" + getId() +
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
