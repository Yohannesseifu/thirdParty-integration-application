package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.FormType;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormUiDTO implements Serializable {

    private UUID id;

    private String formName;

    private String formDescription;

    private FormType formType;

    private Integer stepOrder;

    private ThirdPartyIntegrationDTO thirdPartyIntegration;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormDescription() {
        return formDescription;
    }

    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }

    public FormType getFormType() {
        return formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public ThirdPartyIntegrationDTO getThirdPartyIntegration() {
        return thirdPartyIntegration;
    }

    public void setThirdPartyIntegration(ThirdPartyIntegrationDTO thirdPartyIntegration) {
        this.thirdPartyIntegration = thirdPartyIntegration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormUiDTO)) {
            return false;
        }

        FormUiDTO formUiDTO = (FormUiDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, formUiDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormUiDTO{" +
            "id='" + getId() + "'" +
            ", formName='" + getFormName() + "'" +
            ", formDescription='" + getFormDescription() + "'" +
            ", formType='" + getFormType() + "'" +
            ", stepOrder=" + getStepOrder() +
            ", thirdPartyIntegration=" + getThirdPartyIntegration() +
            "}";
    }
}
