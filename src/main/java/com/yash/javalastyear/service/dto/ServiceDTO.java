package com.yash.javalastyear.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Service entity.
 */
public class ServiceDTO implements Serializable {

    private Long id;

    private Long serviceID;

    private String name;

    private String description;

    private String requirements;

    private String documents;


    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceID() {
        return serviceID;
    }

    public void setServiceID(Long serviceID) {
        this.serviceID = serviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
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

        ServiceDTO serviceDTO = (ServiceDTO) o;
        if (serviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceDTO{" +
            "id=" + getId() +
            ", serviceID=" + getServiceID() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", requirements='" + getRequirements() + "'" +
            ", documents='" + getDocuments() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
