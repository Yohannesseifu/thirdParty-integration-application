package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.IntegrationType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.Visiblity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ThirdPartyIntegrationDTO implements Serializable {

    private Long id;

    private Boolean isDraft;

    private String integrationName;

    private String companyName;

    private String description;

    private String iconPath;

    private Boolean enabled;

    private String accountNumber;

    private Double minimumAmount;

    private Double maximumAmount;

    private String currencyCode;

    private String paymentConfirmationTemplate;

    private String paymentDetailTemplate;

    private IntegrationType integrationCategory;

    private Visiblity visiblity;

    private Boolean confirmRecipientIdentity;

    private Set<MenuDTO> categoryMenus = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public String getIntegrationName() {
        return integrationName;
    }

    public void setIntegrationName(String integrationName) {
        this.integrationName = integrationName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(Double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public Double getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(Double maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPaymentConfirmationTemplate() {
        return paymentConfirmationTemplate;
    }

    public void setPaymentConfirmationTemplate(String paymentConfirmationTemplate) {
        this.paymentConfirmationTemplate = paymentConfirmationTemplate;
    }

    public String getPaymentDetailTemplate() {
        return paymentDetailTemplate;
    }

    public void setPaymentDetailTemplate(String paymentDetailTemplate) {
        this.paymentDetailTemplate = paymentDetailTemplate;
    }

    public IntegrationType getIntegrationCategory() {
        return integrationCategory;
    }

    public void setIntegrationCategory(IntegrationType integrationCategory) {
        this.integrationCategory = integrationCategory;
    }

    public Visiblity getVisiblity() {
        return visiblity;
    }

    public void setVisiblity(Visiblity visiblity) {
        this.visiblity = visiblity;
    }

    public Boolean getConfirmRecipientIdentity() {
        return confirmRecipientIdentity;
    }

    public void setConfirmRecipientIdentity(Boolean confirmRecipientIdentity) {
        this.confirmRecipientIdentity = confirmRecipientIdentity;
    }

    public Set<MenuDTO> getCategoryMenus() {
        return categoryMenus;
    }

    public void setCategoryMenus(Set<MenuDTO> categoryMenus) {
        this.categoryMenus = categoryMenus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThirdPartyIntegrationDTO)) {
            return false;
        }

        ThirdPartyIntegrationDTO thirdPartyIntegrationDTO = (ThirdPartyIntegrationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, thirdPartyIntegrationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThirdPartyIntegrationDTO{" +
            "id=" + getId() +
            ", isDraft='" + getIsDraft() + "'" +
            ", integrationName='" + getIntegrationName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", description='" + getDescription() + "'" +
            ", iconPath='" + getIconPath() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", minimumAmount=" + getMinimumAmount() +
            ", maximumAmount=" + getMaximumAmount() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", paymentConfirmationTemplate='" + getPaymentConfirmationTemplate() + "'" +
            ", paymentDetailTemplate='" + getPaymentDetailTemplate() + "'" +
            ", integrationCategory='" + getIntegrationCategory() + "'" +
            ", visiblity='" + getVisiblity() + "'" +
            ", confirmRecipientIdentity='" + getConfirmRecipientIdentity() + "'" +
            ", categoryMenus=" + getCategoryMenus() +
            "}";
    }
}
