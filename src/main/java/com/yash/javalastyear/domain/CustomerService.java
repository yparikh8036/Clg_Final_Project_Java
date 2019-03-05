package com.yash.javalastyear.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CustomerService.
 */
@Entity
@Table(name = "customer_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerService implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_service_id")
    private Long customerServiceID;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Service service;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerServiceID() {
        return customerServiceID;
    }

    public CustomerService customerServiceID(Long customerServiceID) {
        this.customerServiceID = customerServiceID;
        return this;
    }

    public void setCustomerServiceID(Long customerServiceID) {
        this.customerServiceID = customerServiceID;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public CustomerService startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public CustomerService endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerService customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public CustomerService service(Service service) {
        this.service = service;
        return this;
    }

    public void setService(Service service) {
        this.service = service;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerService customerService = (CustomerService) o;
        if (customerService.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerService.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerService{" +
            "id=" + getId() +
            ", customerServiceID=" + getCustomerServiceID() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
