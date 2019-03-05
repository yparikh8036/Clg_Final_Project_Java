package com.yash.javalastyear.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CustContact.
 */
@Entity
@Table(name = "cust_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustContact implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cust_contact_id")
    private Long custContactID;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "custContact")
    @JsonIgnore
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustContactID() {
        return custContactID;
    }

    public CustContact custContactID(Long custContactID) {
        this.custContactID = custContactID;
        return this;
    }

    public void setCustContactID(Long custContactID) {
        this.custContactID = custContactID;
    }

    public String getEmail() {
        return email;
    }

    public CustContact email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public CustContact mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPurpose() {
        return purpose;
    }

    public CustContact purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public CustContact name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustContact customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        CustContact custContact = (CustContact) o;
        if (custContact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), custContact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustContact{" +
            "id=" + getId() +
            ", custContactID=" + getCustContactID() +
            ", email='" + getEmail() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
