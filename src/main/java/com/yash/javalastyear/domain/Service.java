package com.yash.javalastyear.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Service.
 */
@Entity
@Table(name = "service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_id")
    private Long serviceID;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "documents")
    private String documents;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceID() {
        return serviceID;
    }

    public Service serviceID(Long serviceID) {
        this.serviceID = serviceID;
        return this;
    }

    public void setServiceID(Long serviceID) {
        this.serviceID = serviceID;
    }

    public String getName() {
        return name;
    }

    public Service name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Service description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public Service requirements(String requirements) {
        this.requirements = requirements;
        return this;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getDocuments() {
        return documents;
    }

    public Service documents(String documents) {
        this.documents = documents;
        return this;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Service employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        Service service = (Service) o;
        if (service.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), service.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Service{" +
            "id=" + getId() +
            ", serviceID=" + getServiceID() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", requirements='" + getRequirements() + "'" +
            ", documents='" + getDocuments() + "'" +
            "}";
    }
}
