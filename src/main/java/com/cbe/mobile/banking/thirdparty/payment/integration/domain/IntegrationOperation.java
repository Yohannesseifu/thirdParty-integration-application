package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.OperationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A IntegrationOperation.
 */
@Entity
@Table(name = "integration_operation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegrationOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "formUis", "categoryMenus" }, allowSetters = true)
    private ThirdPartyIntegration thirdPartyIntegration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "apiIntegration", "responseOutputs", "requestInputs" }, allowSetters = true)
    private Operation operation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "integrationOperation")
    @JsonIgnoreProperties(value = { "integrationOperation", "field", "requestInput" }, allowSetters = true)
    private Set<IntegrationFieldMapping> integrationFieldMappings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IntegrationOperation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationType getOperationType() {
        return this.operationType;
    }

    public IntegrationOperation operationType(OperationType operationType) {
        this.setOperationType(operationType);
        return this;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public ThirdPartyIntegration getThirdPartyIntegration() {
        return this.thirdPartyIntegration;
    }

    public void setThirdPartyIntegration(ThirdPartyIntegration thirdPartyIntegration) {
        this.thirdPartyIntegration = thirdPartyIntegration;
    }

    public IntegrationOperation thirdPartyIntegration(ThirdPartyIntegration thirdPartyIntegration) {
        this.setThirdPartyIntegration(thirdPartyIntegration);
        return this;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public IntegrationOperation operation(Operation operation) {
        this.setOperation(operation);
        return this;
    }

    public Set<IntegrationFieldMapping> getIntegrationFieldMappings() {
        return this.integrationFieldMappings;
    }

    public void setIntegrationFieldMappings(Set<IntegrationFieldMapping> integrationFieldMappings) {
        if (this.integrationFieldMappings != null) {
            this.integrationFieldMappings.forEach(i -> i.setIntegrationOperation(null));
        }
        if (integrationFieldMappings != null) {
            integrationFieldMappings.forEach(i -> i.setIntegrationOperation(this));
        }
        this.integrationFieldMappings = integrationFieldMappings;
    }

    public IntegrationOperation integrationFieldMappings(Set<IntegrationFieldMapping> integrationFieldMappings) {
        this.setIntegrationFieldMappings(integrationFieldMappings);
        return this;
    }

    public IntegrationOperation addIntegrationFieldMapping(IntegrationFieldMapping integrationFieldMapping) {
        this.integrationFieldMappings.add(integrationFieldMapping);
        integrationFieldMapping.setIntegrationOperation(this);
        return this;
    }

    public IntegrationOperation removeIntegrationFieldMapping(IntegrationFieldMapping integrationFieldMapping) {
        this.integrationFieldMappings.remove(integrationFieldMapping);
        integrationFieldMapping.setIntegrationOperation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegrationOperation)) {
            return false;
        }
        return getId() != null && getId().equals(((IntegrationOperation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegrationOperation{" +
            "id=" + getId() +
            ", operationType='" + getOperationType() + "'" +
            "}";
    }
}
