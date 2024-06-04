package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Field.
 */
@Entity
@Table(name = "field")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type")
    private DataType dataType;

    @Column(name = "is_unique")
    private Boolean isUnique;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "min_length")
    private Integer minLength;

    @Column(name = "min_value")
    private String minValue;

    @Column(name = "max_value")
    private String maxValue;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "sort_order")
    private Long sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "thirdPartyIntegration", "fields" }, allowSetters = true)
    private FormUi formUi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "fields" }, allowSetters = true)
    private FieldUIMetaData fieldUIMetaData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "field")
    @JsonIgnoreProperties(value = { "integrationOperation", "field", "requestInput" }, allowSetters = true)
    private Set<IntegrationFieldMapping> integrationFieldMappings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Field id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Field name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public Field dataType(DataType dataType) {
        this.setDataType(dataType);
        return this;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Boolean getIsUnique() {
        return this.isUnique;
    }

    public Field isUnique(Boolean isUnique) {
        this.setIsUnique(isUnique);
        return this;
    }

    public void setIsUnique(Boolean isUnique) {
        this.isUnique = isUnique;
    }

    public Integer getMaxLength() {
        return this.maxLength;
    }

    public Field maxLength(Integer maxLength) {
        this.setMaxLength(maxLength);
        return this;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMinLength() {
        return this.minLength;
    }

    public Field minLength(Integer minLength) {
        this.setMinLength(minLength);
        return this;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public String getMinValue() {
        return this.minValue;
    }

    public Field minValue(String minValue) {
        this.setMinValue(minValue);
        return this;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return this.maxValue;
    }

    public Field maxValue(String maxValue) {
        this.setMaxValue(maxValue);
        return this;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public Boolean getIsRequired() {
        return this.isRequired;
    }

    public Field isRequired(Boolean isRequired) {
        this.setIsRequired(isRequired);
        return this;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Long getSortOrder() {
        return this.sortOrder;
    }

    public Field sortOrder(Long sortOrder) {
        this.setSortOrder(sortOrder);
        return this;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public FormUi getFormUi() {
        return this.formUi;
    }

    public void setFormUi(FormUi formUi) {
        this.formUi = formUi;
    }

    public Field formUi(FormUi formUi) {
        this.setFormUi(formUi);
        return this;
    }

    public FieldUIMetaData getFieldUIMetaData() {
        return this.fieldUIMetaData;
    }

    public void setFieldUIMetaData(FieldUIMetaData fieldUIMetaData) {
        this.fieldUIMetaData = fieldUIMetaData;
    }

    public Field fieldUIMetaData(FieldUIMetaData fieldUIMetaData) {
        this.setFieldUIMetaData(fieldUIMetaData);
        return this;
    }

    public Set<IntegrationFieldMapping> getIntegrationFieldMappings() {
        return this.integrationFieldMappings;
    }

    public void setIntegrationFieldMappings(Set<IntegrationFieldMapping> integrationFieldMappings) {
        if (this.integrationFieldMappings != null) {
            this.integrationFieldMappings.forEach(i -> i.setField(null));
        }
        if (integrationFieldMappings != null) {
            integrationFieldMappings.forEach(i -> i.setField(this));
        }
        this.integrationFieldMappings = integrationFieldMappings;
    }

    public Field integrationFieldMappings(Set<IntegrationFieldMapping> integrationFieldMappings) {
        this.setIntegrationFieldMappings(integrationFieldMappings);
        return this;
    }

    public Field addIntegrationFieldMapping(IntegrationFieldMapping integrationFieldMapping) {
        this.integrationFieldMappings.add(integrationFieldMapping);
        integrationFieldMapping.setField(this);
        return this;
    }

    public Field removeIntegrationFieldMapping(IntegrationFieldMapping integrationFieldMapping) {
        this.integrationFieldMappings.remove(integrationFieldMapping);
        integrationFieldMapping.setField(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Field)) {
            return false;
        }
        return getId() != null && getId().equals(((Field) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Field{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", isUnique='" + getIsUnique() + "'" +
            ", maxLength=" + getMaxLength() +
            ", minLength=" + getMinLength() +
            ", minValue='" + getMinValue() + "'" +
            ", maxValue='" + getMaxValue() + "'" +
            ", isRequired='" + getIsRequired() + "'" +
            ", sortOrder=" + getSortOrder() +
            "}";
    }
}
