package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_description")
    private String menuDescription;

    @Column(name = "icon_path")
    private String iconPath;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @JsonIgnoreProperties(value = { "children", "dynamicPaymentMenus", "parent" }, allowSetters = true)
    private Set<Menu> children = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_menu__dynamic_payment_menus",
        joinColumns = @JoinColumn(name = "menu_id"),
        inverseJoinColumns = @JoinColumn(name = "dynamic_payment_menus_id")
    )
    @JsonIgnoreProperties(value = { "formUis", "integrationOperations", "categoryMenus" }, allowSetters = true)
    private Set<ThirdPartyIntegration> dynamicPaymentMenus = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "children", "dynamicPaymentMenus", "parent" }, allowSetters = true)
    private Menu parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Menu id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public Menu menuName(String menuName) {
        this.setMenuName(menuName);
        return this;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescription() {
        return this.menuDescription;
    }

    public Menu menuDescription(String menuDescription) {
        this.setMenuDescription(menuDescription);
        return this;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public Menu iconPath(String iconPath) {
        this.setIconPath(iconPath);
        return this;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Menu enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Menu> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Menu> menus) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (menus != null) {
            menus.forEach(i -> i.setParent(this));
        }
        this.children = menus;
    }

    public Menu children(Set<Menu> menus) {
        this.setChildren(menus);
        return this;
    }

    public Menu addChildren(Menu menu) {
        this.children.add(menu);
        menu.setParent(this);
        return this;
    }

    public Menu removeChildren(Menu menu) {
        this.children.remove(menu);
        menu.setParent(null);
        return this;
    }

    public Set<ThirdPartyIntegration> getDynamicPaymentMenus() {
        return this.dynamicPaymentMenus;
    }

    public void setDynamicPaymentMenus(Set<ThirdPartyIntegration> thirdPartyIntegrations) {
        this.dynamicPaymentMenus = thirdPartyIntegrations;
    }

    public Menu dynamicPaymentMenus(Set<ThirdPartyIntegration> thirdPartyIntegrations) {
        this.setDynamicPaymentMenus(thirdPartyIntegrations);
        return this;
    }

    public Menu addDynamicPaymentMenus(ThirdPartyIntegration thirdPartyIntegration) {
        this.dynamicPaymentMenus.add(thirdPartyIntegration);
        return this;
    }

    public Menu removeDynamicPaymentMenus(ThirdPartyIntegration thirdPartyIntegration) {
        this.dynamicPaymentMenus.remove(thirdPartyIntegration);
        return this;
    }

    public Menu getParent() {
        return this.parent;
    }

    public void setParent(Menu menu) {
        this.parent = menu;
    }

    public Menu parent(Menu menu) {
        this.setParent(menu);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return getId() != null && getId().equals(((Menu) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", menuName='" + getMenuName() + "'" +
            ", menuDescription='" + getMenuDescription() + "'" +
            ", iconPath='" + getIconPath() + "'" +
            ", enabled='" + getEnabled() + "'" +
            "}";
    }
}
