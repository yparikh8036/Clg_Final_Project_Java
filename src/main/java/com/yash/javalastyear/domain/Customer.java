package com.yash.javalastyear.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerID;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "moblie")
    private String moblie;

    @Column(name = "address")
    private String address;

    @Column(name = "username")
    private String username;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "acc_name")
    private String accName;

    @Column(name = "acc_username")
    private String accUsername;

    @Column(name = "acc_password")
    private String accPassword;

    @Column(name = "dob")
    private ZonedDateTime dob;

    @Column(name = "pan")
    private String pan;

    @Column(name = "gstin")
    private String gstin;

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

    public Long getCustomerID() {
        return customerID;
    }

    public Customer customerID(Long customerID) {
        this.customerID = customerID;
        return this;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMoblie() {
        return moblie;
    }

    public Customer moblie(String moblie) {
        this.moblie = moblie;
        return this;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public Customer username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Customer password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Customer isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getAccName() {
        return accName;
    }

    public Customer accName(String accName) {
        this.accName = accName;
        return this;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccUsername() {
        return accUsername;
    }

    public Customer accUsername(String accUsername) {
        this.accUsername = accUsername;
        return this;
    }

    public void setAccUsername(String accUsername) {
        this.accUsername = accUsername;
    }

    public String getAccPassword() {
        return accPassword;
    }

    public Customer accPassword(String accPassword) {
        this.accPassword = accPassword;
        return this;
    }

    public void setAccPassword(String accPassword) {
        this.accPassword = accPassword;
    }

    public ZonedDateTime getDob() {
        return dob;
    }

    public Customer dob(ZonedDateTime dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(ZonedDateTime dob) {
        this.dob = dob;
    }

    public String getPan() {
        return pan;
    }

    public Customer pan(String pan) {
        this.pan = pan;
        return this;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getGstin() {
        return gstin;
    }

    public Customer gstin(String gstin) {
        this.gstin = gstin;
        return this;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Customer employee(Employee employee) {
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
        Customer customer = (Customer) o;
        if (customer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerID=" + getCustomerID() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", moblie='" + getMoblie() + "'" +
            ", address='" + getAddress() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", accName='" + getAccName() + "'" +
            ", accUsername='" + getAccUsername() + "'" +
            ", accPassword='" + getAccPassword() + "'" +
            ", dob='" + getDob() + "'" +
            ", pan='" + getPan() + "'" +
            ", gstin='" + getGstin() + "'" +
            "}";
    }
}
