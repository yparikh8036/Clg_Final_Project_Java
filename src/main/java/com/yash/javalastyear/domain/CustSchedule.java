package com.yash.javalastyear.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CustSchedule.
 */
@Entity
@Table(name = "cust_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cust_schedule_id")
    private Long custScheduleID;

    @Column(name = "due_date")
    private ZonedDateTime dueDate;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "status")
    private String status;

    @Column(name = "actual_date")
    private ZonedDateTime actualDate;

    @Column(name = "doc_file")
    private String docFile;

    @ManyToOne
    private CustomerService customerService;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustScheduleID() {
        return custScheduleID;
    }

    public CustSchedule custScheduleID(Long custScheduleID) {
        this.custScheduleID = custScheduleID;
        return this;
    }

    public void setCustScheduleID(Long custScheduleID) {
        this.custScheduleID = custScheduleID;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public CustSchedule dueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getRequirements() {
        return requirements;
    }

    public CustSchedule requirements(String requirements) {
        this.requirements = requirements;
        return this;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getStatus() {
        return status;
    }

    public CustSchedule status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getActualDate() {
        return actualDate;
    }

    public CustSchedule actualDate(ZonedDateTime actualDate) {
        this.actualDate = actualDate;
        return this;
    }

    public void setActualDate(ZonedDateTime actualDate) {
        this.actualDate = actualDate;
    }

    public String getDocFile() {
        return docFile;
    }

    public CustSchedule docFile(String docFile) {
        this.docFile = docFile;
        return this;
    }

    public void setDocFile(String docFile) {
        this.docFile = docFile;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public CustSchedule customerService(CustomerService customerService) {
        this.customerService = customerService;
        return this;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
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
        CustSchedule custSchedule = (CustSchedule) o;
        if (custSchedule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), custSchedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustSchedule{" +
            "id=" + getId() +
            ", custScheduleID=" + getCustScheduleID() +
            ", dueDate='" + getDueDate() + "'" +
            ", requirements='" + getRequirements() + "'" +
            ", status='" + getStatus() + "'" +
            ", actualDate='" + getActualDate() + "'" +
            ", docFile='" + getDocFile() + "'" +
            "}";
    }
}
