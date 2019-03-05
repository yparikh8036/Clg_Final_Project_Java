package com.yash.javalastyear.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TaxationCategory entity.
 */
public class TaxationCategoryDTO implements Serializable {

    private Long id;

    private Long taxationCategoryID;

    private String catName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxationCategoryID() {
        return taxationCategoryID;
    }

    public void setTaxationCategoryID(Long taxationCategoryID) {
        this.taxationCategoryID = taxationCategoryID;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaxationCategoryDTO taxationCategoryDTO = (TaxationCategoryDTO) o;
        if (taxationCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taxationCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaxationCategoryDTO{" +
            "id=" + getId() +
            ", taxationCategoryID=" + getTaxationCategoryID() +
            ", catName='" + getCatName() + "'" +
            "}";
    }
}
