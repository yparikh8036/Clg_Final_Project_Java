package com.yash.javalastyear.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TaxationUpdate entity.
 */
public class TaxationUpdateDTO implements Serializable {

    private Long id;

    private Long taxationUpdateID;

    private String title;

    private String description;

    private String photo;

    private ZonedDateTime createDate;

    private Boolean isActive;


    private Long taxationCategoryId;

    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxationUpdateID() {
        return taxationUpdateID;
    }

    public void setTaxationUpdateID(Long taxationUpdateID) {
        this.taxationUpdateID = taxationUpdateID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getTaxationCategoryId() {
        return taxationCategoryId;
    }

    public void setTaxationCategoryId(Long taxationCategoryId) {
        this.taxationCategoryId = taxationCategoryId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaxationUpdateDTO taxationUpdateDTO = (TaxationUpdateDTO) o;
        if (taxationUpdateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taxationUpdateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaxationUpdateDTO{" +
            "id=" + getId() +
            ", taxationUpdateID=" + getTaxationUpdateID() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", taxationCategory=" + getTaxationCategoryId() +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
