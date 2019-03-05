package com.yash.javalastyear.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CustSchedule entity.
 */
public class CustScheduleDTO implements Serializable {

    private Long id;

    private Long custScheduleID;

    private ZonedDateTime dueDate;

    private String requirements;

    private String status;

    private ZonedDateTime actualDate;

    private String docFile;


    private Long customerServiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustScheduleID() {
        return custScheduleID;
    }

    public void setCustScheduleID(Long custScheduleID) {
        this.custScheduleID = custScheduleID;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getActualDate() {
        return actualDate;
    }

    public void setActualDate(ZonedDateTime actualDate) {
        this.actualDate = actualDate;
    }

    public String getDocFile() {
        return docFile;
    }

    public void setDocFile(String docFile) {
        this.docFile = docFile;
    }

    public Long getCustomerServiceId() {
        return customerServiceId;
    }

    public void setCustomerServiceId(Long customerServiceId) {
        this.customerServiceId = customerServiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustScheduleDTO custScheduleDTO = (CustScheduleDTO) o;
        if (custScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), custScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustScheduleDTO{" +
            "id=" + getId() +
            ", custScheduleID=" + getCustScheduleID() +
            ", dueDate='" + getDueDate() + "'" +
            ", requirements='" + getRequirements() + "'" +
            ", status='" + getStatus() + "'" +
            ", actualDate='" + getActualDate() + "'" +
            ", docFile='" + getDocFile() + "'" +
            ", customerService=" + getCustomerServiceId() +
            "}";
    }
}
