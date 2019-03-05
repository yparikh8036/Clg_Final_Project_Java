package com.yash.javalastyear.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CustDoc entity.
 */
public class CustDocDTO implements Serializable {

    private Long id;

    private Long custDocID;

    private String docTitle;

    private String docFile;

    private String docRemarks;

    private ZonedDateTime issueDate;

    private ZonedDateTime dueDate;


    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustDocID() {
        return custDocID;
    }

    public void setCustDocID(Long custDocID) {
        this.custDocID = custDocID;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocFile() {
        return docFile;
    }

    public void setDocFile(String docFile) {
        this.docFile = docFile;
    }

    public String getDocRemarks() {
        return docRemarks;
    }

    public void setDocRemarks(String docRemarks) {
        this.docRemarks = docRemarks;
    }

    public ZonedDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(ZonedDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
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

        CustDocDTO custDocDTO = (CustDocDTO) o;
        if (custDocDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), custDocDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustDocDTO{" +
            "id=" + getId() +
            ", custDocID=" + getCustDocID() +
            ", docTitle='" + getDocTitle() + "'" +
            ", docFile='" + getDocFile() + "'" +
            ", docRemarks='" + getDocRemarks() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
