package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.FormType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FormUi.
 */
@Entity
@Table(name = "form_ui")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormUi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "form_name")
    private String formName;

    @Column(name = "form_description")
    private String formDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "form_type")
    private FormType formType;

    @Column(name = "step_order")
    private Integer stepOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "formUis", "categoryMenus" }, allowSetters = true)
    private ThirdPartyIntegration thirdPartyIntegration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formUi")
    @JsonIgnoreProperties(value = { "formUi", "fieldUIMetaData", "integrationFieldMappings" }, allowSetters = true)
    private Set<Field> fields = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FormUi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormName() {
        return this.formName;
    }

    public FormUi formName(String formName) {
        this.setFormName(formName);
        return this;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormDescription() {
        return this.formDescription;
    }

    public FormUi formDescription(String formDescription) {
        this.setFormDescription(formDescription);
        return this;
    }

    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }

    public FormType getFormType() {
        return this.formType;
    }

    public FormUi formType(FormType formType) {
        this.setFormType(formType);
        return this;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public Integer getStepOrder() {
        return this.stepOrder;
    }

    public FormUi stepOrder(Integer stepOrder) {
        this.setStepOrder(stepOrder);
        return this;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public ThirdPartyIntegration getThirdPartyIntegration() {
        return this.thirdPartyIntegration;
    }

    public void setThirdPartyIntegration(ThirdPartyIntegration thirdPartyIntegration) {
        this.thirdPartyIntegration = thirdPartyIntegration;
    }

    public FormUi thirdPartyIntegration(ThirdPartyIntegration thirdPartyIntegration) {
        this.setThirdPartyIntegration(thirdPartyIntegration);
        return this;
    }

    public Set<Field> getFields() {
        return this.fields;
    }

    public void setFields(Set<Field> fields) {
        if (this.fields != null) {
            this.fields.forEach(i -> i.setFormUi(null));
        }
        if (fields != null) {
            fields.forEach(i -> i.setFormUi(this));
        }
        this.fields = fields;
    }

    public FormUi fields(Set<Field> fields) {
        this.setFields(fields);
        return this;
    }

    public FormUi addField(Field field) {
        this.fields.add(field);
        field.setFormUi(this);
        return this;
    }

    public FormUi removeField(Field field) {
        this.fields.remove(field);
        field.setFormUi(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormUi)) {
            return false;
        }
        return getId() != null && getId().equals(((FormUi) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormUi{" +
            "id=" + getId() +
            ", formName='" + getFormName() + "'" +
            ", formDescription='" + getFormDescription() + "'" +
            ", formType='" + getFormType() + "'" +
            ", stepOrder=" + getStepOrder() +
            "}";
    }
}
