package com.yash.javalastyear.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "employee_id", nullable = false, unique = true)
    private Long employeeID;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "username")
    private String username;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "employee_role")
    private String employeeRole;

    @Column(name = "photo")
    private String photo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public Employee employeeID(Long employeeID) {
        this.employeeID = employeeID;
        return this;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Employee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public Employee mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public Employee address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public Employee username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Employee password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Employee isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public Employee employeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
        return this;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getPhoto() {
        return photo;
    }

    public Employee photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
        Employee employee = (Employee) o;
        if (employee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", employeeID=" + getEmployeeID() +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", address='" + getAddress() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", employeeRole='" + getEmployeeRole() + "'" +
            ", photo='" + getPhoto() + "'" +
            "}";
    }
}
