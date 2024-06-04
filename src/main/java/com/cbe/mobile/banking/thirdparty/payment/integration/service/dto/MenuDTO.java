package com.cbe.mobile.banking.thirdparty.payment.integration.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Menu} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MenuDTO implements Serializable {

    private Long id;

    private String menuName;

    private String menuDescription;

    private String iconPath;

    private Boolean enabled;

    private Set<ThirdPartyIntegrationDTO> dynamicPaymentMenus = new HashSet<>();

    private MenuDTO parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
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

    public Set<ThirdPartyIntegrationDTO> getDynamicPaymentMenus() {
        return dynamicPaymentMenus;
    }

    public void setDynamicPaymentMenus(Set<ThirdPartyIntegrationDTO> dynamicPaymentMenus) {
        this.dynamicPaymentMenus = dynamicPaymentMenus;
    }

    public MenuDTO getParent() {
        return parent;
    }

    public void setParent(MenuDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuDTO)) {
            return false;
        }

        MenuDTO menuDTO = (MenuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, menuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuDTO{" +
            "id=" + getId() +
            ", menuName='" + getMenuName() + "'" +
            ", menuDescription='" + getMenuDescription() + "'" +
            ", iconPath='" + getIconPath() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", dynamicPaymentMenus=" + getDynamicPaymentMenus() +
            ", parent=" + getParent() +
            "}";
    }
}
