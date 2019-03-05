package com.yash.javalastyear.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TaxationUpdate.
 */
@Entity
@Table(name = "taxation_update")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaxationUpdate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "taxation_update_id")
    private Long taxationUpdateID;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "photo")
    private String photo;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    private TaxationCategory taxationCategory;

    @ManyToOne
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxationUpdateID() {
        return taxationUpdateID;
    }

    public TaxationUpdate taxationUpdateID(Long taxationUpdateID) {
        this.taxationUpdateID = taxationUpdateID;
        return this;
    }

    public void setTaxationUpdateID(Long taxationUpdateID) {
        this.taxationUpdateID = taxationUpdateID;
    }

    public String getTitle() {
        return title;
    }

    public TaxationUpdate title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public TaxationUpdate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public TaxationUpdate photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public TaxationUpdate createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public TaxationUpdate isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public TaxationCategory getTaxationCategory() {
        return taxationCategory;
    }

    public TaxationUpdate taxationCategory(TaxationCategory taxationCategory) {
        this.taxationCategory = taxationCategory;
        return this;
    }

    public void setTaxationCategory(TaxationCategory taxationCategory) {
        this.taxationCategory = taxationCategory;
    }

    public Employee getEmployee() {
        return employee;
    }

    public TaxationUpdate employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaxationUpdate taxationUpdate = (TaxationUpdate) o;
        if (taxationUpdate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taxationUpdate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaxationUpdate{" +
            "id=" + getId() +
            ", taxationUpdateID=" + getTaxationUpdateID() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
