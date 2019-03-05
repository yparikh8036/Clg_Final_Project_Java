package com.yash.javalastyear.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Work entity.
 */
public class WorkDTO implements Serializable {

    private Long id;

    private Long workID;

    private String title;

    private String description;

    private ZonedDateTime createDate;

    private ZonedDateTime dueDate;

    private String status;

    private ZonedDateTime completionDate;

    private String completionRemarks;


    private Long creatorId;

    private Long assignedId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkID() {
        return workID;
    }

    public void setWorkID(Long workID) {
        this.workID = workID;
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

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(ZonedDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public String getCompletionRemarks() {
        return completionRemarks;
    }

    public void setCompletionRemarks(String completionRemarks) {
        this.completionRemarks = completionRemarks;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long employeeId) {
        this.creatorId = employeeId;
    }

    public Long getAssignedId() {
        return assignedId;
    }

    public void setAssignedId(Long employeeId) {
        this.assignedId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkDTO workDTO = (WorkDTO) o;
        if (workDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkDTO{" +
            "id=" + getId() +
            ", workID=" + getWorkID() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            ", completionRemarks='" + getCompletionRemarks() + "'" +
            ", creator=" + getCreatorId() +
            ", assigned=" + getAssignedId() +
            "}";
    }
}
