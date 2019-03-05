package com.yash.javalastyear.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CustomerService entity.
 */
public class CustomerServiceDTO implements Serializable {

    private Long id;

    private Long customerServiceID;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;


    private Long customerId;

    private Long serviceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerServiceID() {
        return customerServiceID;
    }

    public void setCustomerServiceID(Long customerServiceID) {
        this.customerServiceID = customerServiceID;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerServiceDTO customerServiceDTO = (CustomerServiceDTO) o;
        if (customerServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerServiceDTO{" +
            "id=" + getId() +
            ", customerServiceID=" + getCustomerServiceID() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", customer=" + getCustomerId() +
            ", service=" + getServiceId() +
            "}";
    }
}
