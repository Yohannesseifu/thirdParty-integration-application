package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.IntegrationType;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.enumeration.Visiblity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ThirdPartyIntegration.
 */
@Entity
@Table(name = "third_party_integration")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ThirdPartyIntegration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "is_draft")
    private Boolean isDraft;

    @Column(name = "integration_name")
    private String integrationName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "description")
    private String description;

    @Column(name = "icon_path")
    private String iconPath;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "minimum_amount")
    private Double minimumAmount;

    @Column(name = "maximum_amount")
    private Double maximumAmount;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "payment_confirmation_template")
    private String paymentConfirmationTemplate;

    @Column(name = "payment_detail_template")
    private String paymentDetailTemplate;

    @Enumerated(EnumType.STRING)
    @Column(name = "integration_category")
    private IntegrationType integrationCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "visiblity")
    private Visiblity visiblity;

    @Column(name = "confirm_recipient_identity")
    private Boolean confirmRecipientIdentity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "thirdPartyIntegration")
    @JsonIgnoreProperties(value = { "thirdPartyIntegration", "fields" }, allowSetters = true)
    private Set<FormUi> formUis = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dynamicPaymentMenus")
    @JsonIgnoreProperties(value = { "children", "dynamicPaymentMenus", "parent" }, allowSetters = true)
    private Set<Menu> categoryMenus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ThirdPartyIntegration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsDraft() {
        return this.isDraft;
    }

    public ThirdPartyIntegration isDraft(Boolean isDraft) {
        this.setIsDraft(isDraft);
        return this;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public String getIntegrationName() {
        return this.integrationName;
    }

    public ThirdPartyIntegration integrationName(String integrationName) {
        this.setIntegrationName(integrationName);
        return this;
    }

    public void setIntegrationName(String integrationName) {
        this.integrationName = integrationName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public ThirdPartyIntegration companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return this.description;
    }

    public ThirdPartyIntegration description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public ThirdPartyIntegration iconPath(String iconPath) {
        this.setIconPath(iconPath);
        return this;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public ThirdPartyIntegration enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public ThirdPartyIntegration accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getMinimumAmount() {
        return this.minimumAmount;
    }

    public ThirdPartyIntegration minimumAmount(Double minimumAmount) {
        this.setMinimumAmount(minimumAmount);
        return this;
    }

    public void setMinimumAmount(Double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public Double getMaximumAmount() {
        return this.maximumAmount;
    }

    public ThirdPartyIntegration maximumAmount(Double maximumAmount) {
        this.setMaximumAmount(maximumAmount);
        return this;
    }

    public void setMaximumAmount(Double maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public ThirdPartyIntegration currencyCode(String currencyCode) {
        this.setCurrencyCode(currencyCode);
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPaymentConfirmationTemplate() {
        return this.paymentConfirmationTemplate;
    }

    public ThirdPartyIntegration paymentConfirmationTemplate(String paymentConfirmationTemplate) {
        this.setPaymentConfirmationTemplate(paymentConfirmationTemplate);
        return this;
    }

    public void setPaymentConfirmationTemplate(String paymentConfirmationTemplate) {
        this.paymentConfirmationTemplate = paymentConfirmationTemplate;
    }

    public String getPaymentDetailTemplate() {
        return this.paymentDetailTemplate;
    }

    public ThirdPartyIntegration paymentDetailTemplate(String paymentDetailTemplate) {
        this.setPaymentDetailTemplate(paymentDetailTemplate);
        return this;
    }

    public void setPaymentDetailTemplate(String paymentDetailTemplate) {
        this.paymentDetailTemplate = paymentDetailTemplate;
    }

    public IntegrationType getIntegrationCategory() {
        return this.integrationCategory;
    }

    public ThirdPartyIntegration integrationCategory(IntegrationType integrationCategory) {
        this.setIntegrationCategory(integrationCategory);
        return this;
    }

    public void setIntegrationCategory(IntegrationType integrationCategory) {
        this.integrationCategory = integrationCategory;
    }

    public Visiblity getVisiblity() {
        return this.visiblity;
    }

    public ThirdPartyIntegration visiblity(Visiblity visiblity) {
        this.setVisiblity(visiblity);
        return this;
    }

    public void setVisiblity(Visiblity visiblity) {
        this.visiblity = visiblity;
    }

    public Boolean getConfirmRecipientIdentity() {
        return this.confirmRecipientIdentity;
    }

    public ThirdPartyIntegration confirmRecipientIdentity(Boolean confirmRecipientIdentity) {
        this.setConfirmRecipientIdentity(confirmRecipientIdentity);
        return this;
    }

    public void setConfirmRecipientIdentity(Boolean confirmRecipientIdentity) {
        this.confirmRecipientIdentity = confirmRecipientIdentity;
    }

    public Set<FormUi> getFormUis() {
        return this.formUis;
    }

    public void setFormUis(Set<FormUi> formUis) {
        if (this.formUis != null) {
            this.formUis.forEach(i -> i.setThirdPartyIntegration(null));
        }
        if (formUis != null) {
            formUis.forEach(i -> i.setThirdPartyIntegration(this));
        }
        this.formUis = formUis;
    }

    public ThirdPartyIntegration formUis(Set<FormUi> formUis) {
        this.setFormUis(formUis);
        return this;
    }

    public ThirdPartyIntegration addFormUi(FormUi formUi) {
        this.formUis.add(formUi);
        formUi.setThirdPartyIntegration(this);
        return this;
    }

    public ThirdPartyIntegration removeFormUi(FormUi formUi) {
        this.formUis.remove(formUi);
        formUi.setThirdPartyIntegration(null);
        return this;
    }

    public Set<Menu> getCategoryMenus() {
        return this.categoryMenus;
    }

    public void setCategoryMenus(Set<Menu> menus) {
        if (this.categoryMenus != null) {
            this.categoryMenus.forEach(i -> i.removeDynamicPaymentMenus(this));
        }
        if (menus != null) {
            menus.forEach(i -> i.addDynamicPaymentMenus(this));
        }
        this.categoryMenus = menus;
    }

    public ThirdPartyIntegration categoryMenus(Set<Menu> menus) {
        this.setCategoryMenus(menus);
        return this;
    }

    public ThirdPartyIntegration addCategoryMenus(Menu menu) {
        this.categoryMenus.add(menu);
        menu.getDynamicPaymentMenus().add(this);
        return this;
    }

    public ThirdPartyIntegration removeCategoryMenus(Menu menu) {
        this.categoryMenus.remove(menu);
        menu.getDynamicPaymentMenus().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThirdPartyIntegration)) {
            return false;
        }
        return getId() != null && getId().equals(((ThirdPartyIntegration) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThirdPartyIntegration{" +
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
            "}";
    }
}
