package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A IntegrationFieldMapping.
 */
@Entity
@Table(name = "integration_field_mapping")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegrationFieldMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "thirdPartyIntegration", "operation", "integrationFieldMappings" }, allowSetters = true)
    private IntegrationOperation integrationOperation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "formUi", "fieldUIMetaData", "integrationFieldMappings" }, allowSetters = true)
    private Field field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "operation", "integrationFieldMappings" }, allowSetters = true)
    private RequestInput requestInput;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IntegrationFieldMapping id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IntegrationOperation getIntegrationOperation() {
        return this.integrationOperation;
    }

    public void setIntegrationOperation(IntegrationOperation integrationOperation) {
        this.integrationOperation = integrationOperation;
    }

    public IntegrationFieldMapping integrationOperation(IntegrationOperation integrationOperation) {
        this.setIntegrationOperation(integrationOperation);
        return this;
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public IntegrationFieldMapping field(Field field) {
        this.setField(field);
        return this;
    }

    public RequestInput getRequestInput() {
        return this.requestInput;
    }

    public void setRequestInput(RequestInput requestInput) {
        this.requestInput = requestInput;
    }

    public IntegrationFieldMapping requestInput(RequestInput requestInput) {
        this.setRequestInput(requestInput);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegrationFieldMapping)) {
            return false;
        }
        return getId() != null && getId().equals(((IntegrationFieldMapping) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegrationFieldMapping{" +
            "id=" + getId() +
            "}";
    }
}
