package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldUIMetaDataDTO implements Serializable {

    private UUID id;

    private String label;

    private String inputInterface;

    private String width;

    private String note;

    private String validationPattern;

    private String options;

    private String displayOptions;

    private String conditions;

    private String translations;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInputInterface() {
        return inputInterface;
    }

    public void setInputInterface(String inputInterface) {
        this.inputInterface = inputInterface;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getValidationPattern() {
        return validationPattern;
    }

    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getDisplayOptions() {
        return displayOptions;
    }

    public void setDisplayOptions(String displayOptions) {
        this.displayOptions = displayOptions;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getTranslations() {
        return translations;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldUIMetaDataDTO)) {
            return false;
        }

        FieldUIMetaDataDTO fieldUIMetaDataDTO = (FieldUIMetaDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fieldUIMetaDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldUIMetaDataDTO{" +
            "id='" + getId() + "'" +
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
