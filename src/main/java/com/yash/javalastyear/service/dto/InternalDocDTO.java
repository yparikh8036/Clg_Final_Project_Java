package com.yash.javalastyear.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InternalDoc entity.
 */
public class InternalDocDTO implements Serializable {

    private Long id;

    private Long internalDocID;

    private String docTitle;

    private String docFile;

    private String docRemarks;

    private ZonedDateTime issueDate;

    private ZonedDateTime dueDate;


    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInternalDocID() {
        return internalDocID;
    }

    public void setInternalDocID(Long internalDocID) {
        this.internalDocID = internalDocID;
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

        InternalDocDTO internalDocDTO = (InternalDocDTO) o;
        if (internalDocDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), internalDocDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InternalDocDTO{" +
            "id=" + getId() +
            ", internalDocID=" + getInternalDocID() +
            ", docTitle='" + getDocTitle() + "'" +
            ", docFile='" + getDocFile() + "'" +
            ", docRemarks='" + getDocRemarks() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
