package com.yash.javalastyear.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CustContact entity.
 */
public class CustContactDTO implements Serializable {

    private Long id;

    private Long custContactID;

    private String email;

    private String mobile;

    private String purpose;

    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustContactID() {
        return custContactID;
    }

    public void setCustContactID(Long custContactID) {
        this.custContactID = custContactID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustContactDTO custContactDTO = (CustContactDTO) o;
        if (custContactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), custContactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustContactDTO{" +
            "id=" + getId() +
            ", custContactID=" + getCustContactID() +
            ", email='" + getEmail() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
