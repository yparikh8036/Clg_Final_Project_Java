package com.yash.javalastyear.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CustAccDoc entity.
 */
public class CustAccDocDTO implements Serializable {

    private Long id;

    private Long custAccDocID;

    private String docFile;

    private ZonedDateTime docDate;


    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustAccDocID() {
        return custAccDocID;
    }

    public void setCustAccDocID(Long custAccDocID) {
        this.custAccDocID = custAccDocID;
    }

    public String getDocFile() {
        return docFile;
    }

    public void setDocFile(String docFile) {
        this.docFile = docFile;
    }

    public ZonedDateTime getDocDate() {
        return docDate;
    }

    public void setDocDate(ZonedDateTime docDate) {
        this.docDate = docDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustAccDocDTO custAccDocDTO = (CustAccDocDTO) o;
        if (custAccDocDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), custAccDocDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustAccDocDTO{" +
            "id=" + getId() +
            ", custAccDocID=" + getCustAccDocID() +
            ", docFile='" + getDocFile() + "'" +
            ", docDate='" + getDocDate() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
