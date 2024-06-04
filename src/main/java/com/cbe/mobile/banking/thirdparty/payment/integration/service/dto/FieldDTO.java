package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.DataType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Field} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldDTO implements Serializable {

    private Long id;

    private String name;

    private DataType dataType;

    private Boolean isUnique;

    private Integer maxLength;

    private Integer minLength;

    private String minValue;

    private String maxValue;

    private Boolean isRequired;

    private Long sortOrder;

    private FieldUIMetaDataDTO fieldUIMetaData;

    private FormUiDTO formUi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Boolean getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(Boolean isUnique) {
        this.isUnique = isUnique;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public FieldUIMetaDataDTO getFieldUIMetaData() {
        return fieldUIMetaData;
    }

    public void setFieldUIMetaData(FieldUIMetaDataDTO fieldUIMetaData) {
        this.fieldUIMetaData = fieldUIMetaData;
    }

    public FormUiDTO getFormUi() {
        return formUi;
    }

    public void setFormUi(FormUiDTO formUi) {
        this.formUi = formUi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldDTO)) {
            return false;
        }

        FieldDTO fieldDTO = (FieldDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fieldDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldDTO{" +
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
            ", fieldUIMetaData=" + getFieldUIMetaData() +
            ", formUi=" + getFormUi() +
            "}";
    }
}
