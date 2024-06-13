package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A FieldUIMetaData.
 */
@Entity
@Table(name = "field_ui_meta_data")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldUIMetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "label")
    private String label;

    @Column(name = "input_interface")
    private String inputInterface;

    @Column(name = "width")
    private String width;

    @Column(name = "note")
    private String note;

    @Column(name = "validation_pattern")
    private String validationPattern;

    @Column(name = "options")
    private String options;

    @Column(name = "display_options")
    private String displayOptions;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "translations")
    private String translations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldUIMetaData")
    @JsonIgnoreProperties(value = { "formUi", "fieldUIMetaData", "integrationFieldMappings" }, allowSetters = true)
    private Set<Field> fields = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public FieldUIMetaData id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public FieldUIMetaData label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInputInterface() {
        return this.inputInterface;
    }

    public FieldUIMetaData inputInterface(String inputInterface) {
        this.setInputInterface(inputInterface);
        return this;
    }

    public void setInputInterface(String inputInterface) {
        this.inputInterface = inputInterface;
    }

    public String getWidth() {
        return this.width;
    }

    public FieldUIMetaData width(String width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getNote() {
        return this.note;
    }

    public FieldUIMetaData note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getValidationPattern() {
        return this.validationPattern;
    }

    public FieldUIMetaData validationPattern(String validationPattern) {
        this.setValidationPattern(validationPattern);
        return this;
    }

    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }

    public String getOptions() {
        return this.options;
    }

    public FieldUIMetaData options(String options) {
        this.setOptions(options);
        return this;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getDisplayOptions() {
        return this.displayOptions;
    }

    public FieldUIMetaData displayOptions(String displayOptions) {
        this.setDisplayOptions(displayOptions);
        return this;
    }

    public void setDisplayOptions(String displayOptions) {
        this.displayOptions = displayOptions;
    }

    public String getConditions() {
        return this.conditions;
    }

    public FieldUIMetaData conditions(String conditions) {
        this.setConditions(conditions);
        return this;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getTranslations() {
        return this.translations;
    }

    public FieldUIMetaData translations(String translations) {
        this.setTranslations(translations);
        return this;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    public Set<Field> getFields() {
        return this.fields;
    }

    public void setFields(Set<Field> fields) {
        if (this.fields != null) {
            this.fields.forEach(i -> i.setFieldUIMetaData(null));
        }
        if (fields != null) {
            fields.forEach(i -> i.setFieldUIMetaData(this));
        }
        this.fields = fields;
    }

    public FieldUIMetaData fields(Set<Field> fields) {
        this.setFields(fields);
        return this;
    }

    public FieldUIMetaData addField(Field field) {
        this.fields.add(field);
        field.setFieldUIMetaData(this);
        return this;
    }

    public FieldUIMetaData removeField(Field field) {
        this.fields.remove(field);
        field.setFieldUIMetaData(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldUIMetaData)) {
            return false;
        }
        return getId() != null && getId().equals(((FieldUIMetaData) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldUIMetaData{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", inputInterface='" + getInputInterface() + "'" +
            ", width='" + getWidth() + "'" +
            ", note='" + getNote() + "'" +
            ", validationPattern='" + getValidationPattern() + "'" +
            ", options='" + getOptions() + "'" +
            ", displayOptions='" + getDisplayOptions() + "'" +
            ", conditions='" + getConditions() + "'" +
            ", translations='" + getTranslations() + "'" +
            "}";
    }
}
