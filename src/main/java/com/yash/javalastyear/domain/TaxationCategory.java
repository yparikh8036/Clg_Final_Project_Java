package com.yash.javalastyear.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TaxationCategory.
 */
@Entity
@Table(name = "taxation_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaxationCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "taxation_category_id")
    private Long taxationCategoryID;

    @Column(name = "cat_name")
    private String catName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxationCategoryID() {
        return taxationCategoryID;
    }

    public TaxationCategory taxationCategoryID(Long taxationCategoryID) {
        this.taxationCategoryID = taxationCategoryID;
        return this;
    }

    public void setTaxationCategoryID(Long taxationCategoryID) {
        this.taxationCategoryID = taxationCategoryID;
    }

    public String getCatName() {
        return catName;
    }

    public TaxationCategory catName(String catName) {
        this.catName = catName;
        return this;
    }

    public void setCatName(String catName) {
        this.catName = catName;
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
        TaxationCategory taxationCategory = (TaxationCategory) o;
        if (taxationCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taxationCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaxationCategory{" +
            "id=" + getId() +
            ", taxationCategoryID=" + getTaxationCategoryID() +
            ", catName='" + getCatName() + "'" +
            "}";
    }
}
